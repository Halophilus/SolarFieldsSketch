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
    View view;
    ArrayList<JFrame> openFrames;

    ControllerImpl(GlobalTicketingSystem globalTicketingSystem) {
        this.globalTicketingSystem = globalTicketingSystem;
        openFrames = new ArrayList<JFrame>();
        this.model = new ModelImpl();
    }


    public void closeAllOpenedFrames(){
        for (JFrame frame : openFrames){
            frame.dispose();
        }
        openFrames.clear();
    }

    @Override
    public void displayConnectionRestoredPopup() {
        view.displayConnectionRestoredPopup(this);
    }

    // Iterates through all local data and updates and lowers the flags that indicate new entries/tickets
    public void markLocalStorageAsUploaded(){
        model.markLocalStorageAsUploaded();
    }

    // Upload local entries to global database
    public void uploadLocalEntries(){
        model.uploadLocalEntries();
    }

    @Override
    public void clearLocalStorage() {
        model.clearLocalStorage();
    }

    // Add ticket screen for edit section
    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        model.generateLocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    @Override
    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        model.generateLocalEntry(date, description, icon, ticketId, siteId, entryId);
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
        SiteSelectionFrame siteSelectionFrame= view.generateSiteSelectionFrameIntro(this);
        List<UUID> siteIdCollectionList = model.globalSiteIdCollectionList();
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, true);
        siteSelectionFrame.setVisible(true);
    }

    // Edit
    public void displaySiteSelectionFrameEdit(Set<UUID> selectedSiteIds){
        SiteSelectionFrame siteSelectionFrame = view.generateSiteSelectionFrameEdit(this);
        model.downloadSiteData(selectedSiteIds);

        List<UUID> siteIdCollectionList = model.localSiteIDCollectionList();
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
            newSite = model.fetchGenericSiteFromGlobalDatabase(id);
        } else {
            newSite = model.fetchGenericSiteFromLocalDatabase(id);
        }
        if (newSite != null){
            addSiteToSiteSelectionFrame(siteSelectionFrame, newSite, isIntro);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    // Adds a globalSite to the selection frame
    public void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, Site site, boolean isIntro) {
        view.addSiteToSiteSelectionFrame(siteSelectionFrame,site.id(), site.title(), site.state(), site.city(), isIntro, this);
    }


    // SiteInfoFrameMethods
    // For intro
    public void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelFromID(siteId, true);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addTicketsToScrollPane();
        view.displaySiteInfoFrameIntro(innerDisplayPanel, siteSelectionFrame, this);
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
            targetSite = model.fetchGenericSiteFromGlobalDatabase(siteInfoDisplayPanel.siteId);//GlobalTicketingSystem.getSite(siteInfoDisplayPanel.siteId);
        } else {
            targetSite = model.fetchGenericSiteFromLocalDatabase(siteInfoDisplayPanel.siteId);
        }

        Set<UUID> setOfTickets = model.setOfTicketsPerSite(targetSite);

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
            return view.generateSiteInfoDisplayPanel(site.id(), site.imageIcon(), site.title(), site.description(), site.address(), site.city(), site.state(), site.zip(), site.phoneNumber(), site.emailAddress(), isIntro, this);
        }
        return null;
    }

    public TicketPanel makeTicketPanelFromId(UUID ticketId, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = model.fetchGenericTicketFromGlobalDatabase(ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(ticketId);
        }
        List<Entry> entries = model.genericListOfEntries(newTicket);
        LocalDate mostRecentDate = LocalDate.MAX; //

        for (Entry entry : entries){ // For each value in the ticket entries
                if (mostRecentDate.isAfter(entry.date())) {
                    mostRecentDate = entry.date();
            }
        }
        return view.generateTicketPanel(ticketId, mostRecentDate, entries.size(), newTicket.resolved(), isIntro, this);
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
            newTicket = model.fetchGenericTicketFromGlobalDatabase(entryDisplayPanel.ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(entryDisplayPanel.ticketId);
        }
        List<UUID> listOfEntries = model.entriesFromTicket(newTicket);

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
            newTicket = model.fetchGenericTicketFromGlobalDatabase(ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(ticketId);
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
            newEntry = model.fetchGenericEntryFromGlobalDatabase(entryId);
        } else {
            newEntry = model.fetchGenericEntryFromLocalDatabase(entryId);
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

