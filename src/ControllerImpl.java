import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;
    Model model;
    ArrayList<JFrame> openFrames;

    ControllerImpl(GlobalTicketingSystem globalTicketingSystem) {
        this.globalTicketingSystem = globalTicketingSystem;
        openFrames = new ArrayList<JFrame>();
        this.model = new ModelImpl();
    }


    // Download data associated with specific site Ids
    public void downloadSiteData(Set<UUID> selectedSiteIds){
        // For each UUID passed to the method
        for (UUID siteId : selectedSiteIds){
            // Find the corresponding global site
            GlobalSite globalSite = model.fetchGlobalSite(siteId);
            // Generate a new local site based on it
            // Thereby downloading the hierarchical data associated with it
            LocalSite newLocalSite = model.generateLocalSite(globalSite)
            model.downloadLocalSite(newLocalSite);
        }
    }

    public void clearLocalStorage(){
        LocalTicketingSystem.clearSites();
    }

    public void closeAllOpenedFrames(){
        for (JFrame frame : openFrames){
            frame.dispose();
        }
        openFrames.clear();
    }

    @Override
    public void displayConnectionRestoredPopup() {
        ConnectionRestoredPopup connectionRestoredPopup = new ConnectionRestoredPopup(this);
        openFrames.add(connectionRestoredPopup);
        connectionRestoredPopup.setVisible(true);
    }

    // Iterates through all local data and updates and lowers the flags that indicate new entries/tickets
    public void markLocalStorageAsUploaded(){
        for(LocalSite site : LocalTicketingSystem.getAllSites()){
            site.reset();
            for (Ticket ticket : site.tickets()){
                LocalTicket localTicket = (LocalTicket) ticket;
                localTicket.reset();
                for (Entry entry : ticket.entries()){
                    LocalEntry localEntry = (LocalEntry) entry;
                    localEntry.reset();
                }

            }
        }
    }

    // Upload local entries to global database
    public void uploadLocalEntries(){
        // Extract local sites
        ArrayList<LocalSite> locallyStoredSites = LocalTicketingSystem.getAllSites();
        // Filter out unchanged sites
        List<LocalSite> updatedLocalSites = locallyStoredSites.stream().filter(site->site.updated).toList();

        // For all updated sites
        for (LocalSite updatedSite : updatedLocalSites) {
            List<Ticket> oldTicketsWithNewEntries = updatedSite.tickets().stream().filter(Ticket::updated).filter(ticket ->!ticket.isNew()).toList();
            System.out.println("Old tickets with new entries: " + oldTicketsWithNewEntries);
            List<Ticket> newTickets = updatedSite.tickets().stream().filter(Ticket::isNew).toList();
            System.out.println("New tickets: " + newTickets);

            // Iterate through the old updated tickets
            for (Ticket updatedTicket : oldTicketsWithNewEntries) {
                System.out.println("Processing old tickets with new entries");
                // Filter out existing tickets
                List<Entry> newEntries = updatedTicket.entries().stream().filter(Entry::isNew).toList();
                System.out.println("Current ticket: " + updatedTicket);
                System.out.println("Updated ticket entries: " + updatedTicket.entries());
                // For all the new entries in the updated tickets
                Set<Entry> entriesSet = new HashSet<>(newEntries);
                for (Entry newEntry : entriesSet) {
                    System.out.println("Processing newEntry " + newEntry);
                    LocalEntry newLocalEntry = (LocalEntry)newEntry;
                    // Generate a new global entry from the local entry
                    GlobalEntry newGlobalEntry = new GlobalEntry(newLocalEntry);
                    // Get the corresponding global ticket
                    GlobalTicket correspondingGlobalTicket = (GlobalTicket)GlobalTicketingSystem.getTicket(updatedTicket.id());
                    // Add the newly generated global entry to the in-memory local entry
                    correspondingGlobalTicket.addEntry(newGlobalEntry);
                    correspondingGlobalTicket.resolve(false);
                }
            }

            // Iterate through all the new tickets
            for (Ticket newTicket : newTickets) {
                System.out.println("Processing new tickets");
                // Generate a new global ticket
                GlobalTicket newGlobalTicket = new GlobalTicket((LocalTicket)newTicket);
                // Iterate through the local ticket's entries
                for (Entry newEntry : newTicket.entries()) {
                    LocalEntry newLocalEntry = (LocalEntry)newEntry;
                    // Generate a new global entry
                    GlobalEntry newGlobalEntry = new GlobalEntry(newLocalEntry);
                    // Add it to the new global ticket
                    newGlobalTicket.addEntry(newGlobalEntry);
                }
                // Fetch the corresponding global site
                GlobalSite correspondingGlobalSite = (GlobalSite) GlobalTicketingSystem.getSite(updatedSite.id());
                // Add the ticket to the existing global site
                correspondingGlobalSite.addTicket(newGlobalTicket);

            }
        }

        // Delete locally stored data
        //LocalTicketingSystem.clearSites();
    }

    // Add ticket screen for edit section
    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        LocalTicket newTicket = new LocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    @Override
    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        LocalEntry newLocalEntry = new LocalEntry(date, description, icon, ticketId, siteId, entryId);
    }

    @Override
    public boolean checkOnlineStatus(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("res/online.txt"));
            return br.readLine() != null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // SiteSelectionFrame methods
    // Intro
    public void displaySiteSelectionFrameIntro(){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(true, this);
        openFrames.add(siteSelectionFrame.frame);
        List<UUID> siteIdCollectionList = GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, true);
        siteSelectionFrame.setVisible(true);
    }

    // Edit
    public void displaySiteSelectionFrameEdit(Set<UUID> selectedSiteIds){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(false, this);
        openFrames.add(siteSelectionFrame.frame);
        downloadSiteData(selectedSiteIds);

        List<UUID> siteIdCollectionList = LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, false);
        siteSelectionFrame.setVisible(true);
    }

    @Override
    public void displayExportScreenForSelectLocations(Set<UUID> selectedSites) {

    }


    // Takes a set of integers and adds the relevant IDs to the selection frame
    public void addSitesToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, List<UUID> selectedSites, boolean isIntro){
        for (UUID siteId : selectedSites){
            addSiteToSiteSelectionFrameFromID(siteSelectionFrame, siteId, isIntro);
        }
    }

    // Takes an individual ID and attempts to add it to the selection frame
    public void addSiteToSiteSelectionFrameFromID(SiteSelectionFrame siteSelectionFrame, UUID id, boolean isIntro){
        Site newSite;
        if (isIntro){
            newSite = GlobalTicketingSystem.getSite(id);
        } else {
            newSite = LocalTicketingSystem.getSite(id);
        }
        if (newSite != null){
            addSiteToSiteSelectionFrame(siteSelectionFrame, newSite, isIntro);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    // Adds a globalSite to the selection frame
    private void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, Site site, boolean isIntro) {
        siteSelectionFrame.addSite(site.id(), site.title(), site.state(), site.city(), isIntro, this);
    }


    // SiteInfoFrameMethods
    // For intro
    public void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelFromID(siteId, true);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameIntro newFrame = assembleSiteInfoFrameIntro(innerDisplayPanel, siteSelectionFrame);
        openFrames.add(newFrame.frame);
        newFrame.setVisible(true);
    }
    // For the edit section
    public void displaySiteInfoFrameEdit(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelFromID(siteId, false);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, false);
        innerDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameEdit newFrame = assembleSiteInfoFrameEdit(innerDisplayPanel, siteSelectionFrame);
        openFrames.add(newFrame.frame);
        newFrame.setVisible(true);


    }


    // Assembling site info frame
    // Intro
    public SiteInfoFrameIntro assembleSiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame) {
        return new SiteInfoFrameIntro(siteInfoDisplayPanel, this, siteSelectionFrame);
    }

    public SiteInfoFrameEdit assembleSiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame) {
        return new SiteInfoFrameEdit(siteInfoDisplayPanel, this, siteSelectionFrame);
    }


    public void addTicketPanelsToSiteInfoDisplayPanel(SiteInfoDisplayPanel siteInfoDisplayPanel, boolean isIntro){
        Site targetSite = null;
        if (isIntro) {
            targetSite = GlobalTicketingSystem.getSite(siteInfoDisplayPanel.siteId);
        } else {
            targetSite = LocalTicketingSystem.getSite(siteInfoDisplayPanel.siteId);
        }

        List<UUID> listOfTickets = targetSite.tickets().stream().map(Ticket::id).distinct().toList();
        Set<UUID> setOfTickets = new HashSet<UUID>(listOfTickets);

        if(!setOfTickets.isEmpty()){
            for (UUID ticketId : setOfTickets){
                TicketPanel newTicketPanel = makeTicketPanelFromId(ticketId, isIntro);
                siteInfoDisplayPanel.addTicketPanel(newTicketPanel);
            }
        }
    }

    private SiteInfoDisplayPanel makeSiteInfoDisplayPanelFromID(UUID siteId, boolean isIntro) {
        Site site = null;
        if (isIntro) {
            site = GlobalTicketingSystem.getSite(siteId);
        } else {
            site = LocalTicketingSystem.getSite(siteId);
        }
        if (site != null){
            return new SiteInfoDisplayPanel(site.id(), site.imageIcon(), site.title(), site.description(), site.address(), site.city(), site.state(), site.zip(), site.phoneNumber(), site.emailAddress(), isIntro, this);
        }
        return null;
    }

    public TicketPanel makeTicketPanelFromId(UUID ticketId, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = GlobalTicketingSystem.getTicket(ticketId);
        } else {
            newTicket = LocalTicketingSystem.getTicket(ticketId);
        }
        List<Entry> entries = newTicket.entries().stream().distinct().toList();
        LocalDate mostRecentDate = LocalDate.MAX; //

        for (Entry entry : entries){ // For each value in the ticket entries
                if (mostRecentDate.isAfter(entry.date())) {
                    mostRecentDate = entry.date();
            }
        }
        return new TicketPanel(ticketId, mostRecentDate, entries.size(), newTicket.resolved(), isIntro, this);
    }

    // Methods for entry display
    // Global database
    public Site fetchSiteGlobal(UUID siteId){
        return GlobalTicketingSystem.getSite(siteId);
    }

    public Ticket fetchTicketGlobal(UUID ticketId){
        return GlobalTicketingSystem.getTicket(ticketId);
    }

    public Entry fetchEntryGlobal(UUID entryId){
        return GlobalTicketingSystem.getEntry(entryId);
    }

    public Site fetchSiteLocal(UUID siteId){
        return LocalTicketingSystem.getSite(siteId);
    }
    public Ticket fetchTicketLocal(UUID ticketId){
        return LocalTicketingSystem.getTicket(ticketId);
    }

    public Entry fetchEntryLocal(UUID entryId){
        return LocalTicketingSystem.getEntry(entryId);
    }

    // Displaying the entries associated with a certain ticket
    // Intro
    public void displayEntryDisplayFrameIntro(UUID ticketId){
        EntryDisplayPanel innerDisplayPanel = makeEntryDisplayPanelFromID(ticketId, true);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addEntriesToScrollPanel();
        EntryDisplayFrameIntro newFrame = assembleEntryDisplayPanelFrameIntro(innerDisplayPanel);
        openFrames.add(newFrame.frame);
        newFrame.setVisible(true);
    }
    //Edit section
    @Override
    public void displayEntryDisplayFrameEdit(UUID ticketId, TicketPanel ticketPanel) {
        // Set up display panel
        EntryDisplayPanel innerDisplayPanel = makeEntryDisplayPanelFromID(ticketId, false);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, false);
        innerDisplayPanel.addEntriesToScrollPanel();

        // Fetch Site Data
        EntryDisplayFrameEdit newFrame = assembleEntryDisplayPanelFrameEdit(ticketId, innerDisplayPanel, ticketPanel);
        openFrames.add(newFrame.frame);
        newFrame.setVisible(true);
        //EntryDisplayFrameEdit

    }

    public EntryDisplayFrameIntro assembleEntryDisplayPanelFrameIntro(EntryDisplayPanel entryDisplayPanel){
        return new EntryDisplayFrameIntro(entryDisplayPanel, this);
    }

    public EntryDisplayFrameEdit assembleEntryDisplayPanelFrameEdit(UUID ticketId, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel){
        UUID parentSiteId = LocalTicketingSystem.getSiteFromTicket(ticketId).id();
        return new EntryDisplayFrameEdit(entryDisplayPanel, parentTicketPanel, parentSiteId, this);
    }

    @Override
    public void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame,  EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel){
        AddEntryScreen addEntryScreen = new AddEntryScreen(entryDisplayFrame, entryDisplayPanel, parentTicketPanel, this);
        openFrames.add(addEntryScreen.frame);
        addEntryScreen.setVisible(true);
    }




    public void addEntryPanelsToEntryDisplayPanel(EntryDisplayPanel entryDisplayPanel, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = fetchTicketGlobal(entryDisplayPanel.ticketId);
        } else {
            newTicket = fetchTicketLocal(entryDisplayPanel.ticketId);
        }
        List<UUID> listOfEntries = newTicket.entryIds();

        if (!listOfEntries.isEmpty()){
            for (UUID entryId : listOfEntries){
                EntryPanel newEntryPanel = makeEntryPanelFromId(entryId, isIntro);
                entryDisplayPanel.addEntryPanel(newEntryPanel);
            }
        }

    }

    // Generate a display panel for ticket entries in the intro section of the app
    public EntryDisplayPanel makeEntryDisplayPanelFromID(UUID ticketId, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = fetchTicketGlobal(ticketId);
        } else {
            newTicket = fetchTicketLocal(ticketId);
        }
        if (newTicket != null){
            return new EntryDisplayPanel(ticketId, newTicket.description(), newTicket.resolved(), isIntro);
        }
        return null;
    }



    // Generate an entry panel to be added to the ticket entry screen in the intro section of the app
    public EntryPanel makeEntryPanelFromId(UUID entryId, boolean isIntro){
        Entry newEntry = null;
        if (isIntro) {
            newEntry = fetchEntryGlobal(entryId);
        } else {
            newEntry = fetchEntryLocal(entryId);
        }
        if (newEntry != null){
            return new EntryPanel(newEntry.id(), newEntry.date(), newEntry.description(),  newEntry.icon(), newEntry.reviewed(), isIntro);
        }
        return null;
    }



    @Override
    public void openSiteInfoScreenFromIntroSection(UUID siteID) {

    }

    @Override
    public void exportSiteWindowFromIntroSiteInfoScreen(UUID siteID) {

    }

    @Override
    public void selectSiteFromIntroSiteInfoScreenAndCloseInfoScreen(UUID siteID) {

    }

    @Override
    public void openTicketInfoScreenFromIntroSiteInfoScreen(UUID TicketID) {

    }

    @Override
    public void exportTicketInfoFromIntroSiteInfoScreen(UUID siteID) {

    }

    @Override
    public void openSiteInfoWindowFromEditSection(UUID siteID) {

    }

    @Override
    public void openTicketInfoWindowFromEditSection(UUID ticketID) {

    }

    @Override
    public void openAddTicketScreenFromEditSection(UUID siteID) {

    }

    @Override
    public void openAddEntryToTicketScreenFromEditSection(UUID siteID) {

    }
}

