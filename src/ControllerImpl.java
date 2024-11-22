import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;

    ControllerImpl(GlobalTicketingSystem globalTicketingSystem) {
        this.globalTicketingSystem = globalTicketingSystem;
    }

    // Download data associated with specific site Ids
    public void downloadSiteData

    // Upload local entries to global database
    public void uploadLocalEntries(){
        // Extract local sites
        ArrayList<LocalSite> locallyStoredSites = LocalTicketingSystem.downloadedSites;
        // Filter out unchanged sites
        List<LocalSite> updatedLocalSites = locallyStoredSites.stream().filter(site->site.updated).toList();

        // For all updated sites
        for (LocalSite updatedSite : updatedLocalSites) {
            List<LocalTicket> oldTicketsWithNewEntries = updatedSite.localTickets.stream().filter(ticket->ticket.updated).filter(ticket->!ticket.isNew).toList();
            List<LocalTicket> newTickets = updatedSite.localTickets.stream().filter(ticket->ticket.isNew).toList();

            // Iterate through the old updated tickets
            for (LocalTicket updatedTicket : oldTicketsWithNewEntries) {
                // Filter out existing tickets
                List<LocalEntry> newEntries = updatedTicket.localEntries.stream().filter(entry->entry.isNew).toList();
                // For all the new entries in the updated tickets
                for (LocalEntry newEntry : newEntries) {
                    // Generate a new global entry from the local entry
                    GlobalEntry newGlobalEntry = new GlobalEntry(newEntry);
                    // Get the corresponding global ticket
                    GlobalTicket correspondingGlobalTicket = GlobalTicketingSystem.getTicket(updatedTicket.id());
                    // Add the newly generated global entry to the in-memory local entry
                    correspondingGlobalTicket.addEntry(newGlobalEntry);
                }
            }

            // Iterate through all the new tickets
            for (LocalTicket newTicket : newTickets) {
                // Generate a new global ticket
                GlobalTicket newGlobalTicket = new GlobalTicket(newTicket);
                // Iterate through the local ticket's entries
                for (LocalEntry newEntry : newTicket.localEntries) {
                    // Generate a new global entry
                    GlobalEntry newGlobalEntry = new GlobalEntry(newEntry);
                    // Add it to the new global ticket
                    newGlobalTicket.addEntry(newGlobalEntry);
                }
                // Fetch the corresponding global site
                GlobalSite correspondingGlobalSite = GlobalTicketingSystem.getSite(updatedSite.id());
                // Add the ticket to the existing global site
                correspondingGlobalSite.addTicket(newGlobalTicket);

            }
        }

        //TODO: add means of disposing of old saved data


    }

    // SiteSelectionFrame methods
    // Intro
    public void displaySiteSelectionFrameIntro(){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(true, this);
        List<UUID> siteIdCollectionList = globalTicketingSystem.getAllSites().stream().map(site -> site.id).distinct().collect(Collectors.toList());
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, true);
        siteSelectionFrame.setVisible(true);
    }

    //
    // Takes a set of integers and adds the relevant IDs to the selection frame
    public void addSitesToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, List<UUID> selectedSites, boolean isIntro){
        for (UUID siteId : selectedSites){
            addSiteToSiteSelectionFrameFromID(siteSelectionFrame, siteId, isIntro);
        }
    }

    // Takes an individual ID and attempts to add it to the selection frame
    public void addSiteToSiteSelectionFrameFromID(SiteSelectionFrame siteSelectionFrame, UUID id, boolean isIntro){
        GlobalSite newGlobalSite = GlobalTicketingSystem.getSite(id);
        if (newGlobalSite != null){
            addSiteToSiteSelectionFrame(siteSelectionFrame, newGlobalSite, isIntro);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    // Adds a globalSite to the selection frame
    private void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, GlobalSite globalSite, boolean isIntro) {
        siteSelectionFrame.addSite(globalSite.id, globalSite.title, globalSite.state, globalSite.city, isIntro, this);
    }


    // SiteInfoFrameMethods
    public void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelFromID(siteId, true);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameIntro newFrame = assembleSiteInfoFrameIntro(innerDisplayPanel, siteSelectionFrame);
        newFrame.setVisible(true);
    }

    public SiteInfoFrameIntro assembleSiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame) {
        return new SiteInfoFrameIntro(siteInfoDisplayPanel, this, siteSelectionFrame);
    }

    public void addTicketPanelsToSiteInfoDisplayPanel(SiteInfoDisplayPanel siteInfoDisplayPanel, boolean isIntro){
        List<UUID> listOfTickets = fetchSite(siteInfoDisplayPanel.siteId).ticketIds();

        if(!listOfTickets.isEmpty()){
            for (UUID ticketId : listOfTickets){
                TicketPanel newTicketPanel = makeTicketPanelFromId(ticketId, isIntro);
                siteInfoDisplayPanel.addTicketPanel(newTicketPanel);
            }
        }
    }

    private SiteInfoDisplayPanel makeSiteInfoDisplayPanelFromID(UUID siteId, boolean isIntro) {
        GlobalSite fetchedGlobalSite = GlobalTicketingSystem.getSite(siteId);
        if (fetchedGlobalSite != null){
            return new SiteInfoDisplayPanel(fetchedGlobalSite.id, fetchedGlobalSite.imageIcon, fetchedGlobalSite.title, fetchedGlobalSite.description, fetchedGlobalSite.address, fetchedGlobalSite.city, fetchedGlobalSite.state, fetchedGlobalSite.zip, fetchedGlobalSite.phoneNumber, fetchedGlobalSite.emailAddress, isIntro, this);
        }
        return null;
    }

    private TicketPanel makeTicketPanelFromId(UUID ticketId, boolean isIntro){
        GlobalTicket newGlobalTicket = GlobalTicketingSystem.getTicket(ticketId);
        List<UUID> ticketEntryIds = newGlobalTicket.entryIds();
        LocalDate mostRecentDate = LocalDate.MAX; //
        GlobalEntry currentGlobalEntry = null;

        for (UUID entryId : ticketEntryIds){ // For each value in the ticket entries
            currentGlobalEntry = GlobalTicketingSystem.getEntry(entryId); // Get the entry associated with that ticket
            if (currentGlobalEntry != null){ // If that entry isn't null
                if (mostRecentDate.isAfter(currentGlobalEntry.date)) {
                    mostRecentDate = currentGlobalEntry.date;
                }
            }
        }
        return new TicketPanel(ticketId, mostRecentDate, ticketEntryIds.size(), newGlobalTicket.resolved, isIntro, this);
    }

    // Methods for entry display
    public GlobalSite fetchSite(UUID siteId){
        return GlobalTicketingSystem.getSite(siteId);
    }

    public GlobalTicket fetchTicket(UUID ticketId){
        return GlobalTicketingSystem.getTicket(ticketId);
    }

    public GlobalEntry fetchEntry(UUID entryId){
        return GlobalTicketingSystem.getEntry(entryId);
    }

    public void displayEntryDisplayFrameIntro(UUID ticketId){
        EntryDisplayPanel innerDisplayPanel = makeEntryDisplayPanelFromID(ticketId, true);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addEntriesToScrollPanel();
        EntryDisplayFrameIntro newFrame = assembleEntryDisplayPanelFrameIntro(innerDisplayPanel);
        newFrame.setVisible(true);
    }

    public EntryDisplayFrameIntro assembleEntryDisplayPanelFrameIntro(EntryDisplayPanel entryDisplayPanel){
        return new EntryDisplayFrameIntro(entryDisplayPanel, this);
    }

    public void addEntryPanelsToEntryDisplayPanel(EntryDisplayPanel entryDisplayPanel, boolean isIntro){
        List<UUID> listOfEntries = fetchTicket(entryDisplayPanel.ticketId).entryIds();

        if (!listOfEntries.isEmpty()){
            for (UUID entryId : listOfEntries){
                EntryPanel newEntryPanel = makeEntryPanelFromId(entryId, isIntro);
                entryDisplayPanel.addEntryPanel(newEntryPanel);
            }
        }

    }

    // Generate a display panel for ticket entries in the intro section of the app
    public EntryDisplayPanel makeEntryDisplayPanelFromID(UUID ticketId, boolean isIntro){
        GlobalTicket fetchedGlobalTicket = fetchTicket(ticketId);
        if (fetchedGlobalTicket != null){
            return new EntryDisplayPanel(ticketId, fetchedGlobalTicket.description, fetchedGlobalTicket.resolved, isIntro);
        }
        return null;
    }

    // Generate an entry panel to be added to the ticket entry screen in the intro section of the app
    public EntryPanel makeEntryPanelFromId(UUID entryId, boolean isIntro){
        GlobalEntry fetchedGlobalEntry = fetchEntry(entryId);
        if (fetchedGlobalEntry != null){
            return new EntryPanel(fetchedGlobalEntry.id, fetchedGlobalEntry.date, fetchedGlobalEntry.description,  fetchedGlobalEntry.icon, fetchedGlobalEntry.reviewed, isIntro);
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

