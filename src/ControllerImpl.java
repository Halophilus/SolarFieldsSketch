import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ControllerImpl implements Controller {
    public SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame();
    public SiteInfoFrame siteInfoFrame;
    TicketingSystem ticketingSystem;

    ControllerImpl(TicketingSystem ticketingSystem) {
        this.ticketingSystem = ticketingSystem;
    }

    // SiteSelectionFrame methods
    // Takes a set of integers and adds the relevant IDs to the selection frame
    public void addSitesToSiteSelectionFrame(Set<UUID> selectedSites){
        Set.copyOf(selectedSites).forEach(this::addSiteToSiteSelectionFrameFromID);
    }

    // Takes an individual ID and attempts to add it to the selection frame
    public void addSiteToSiteSelectionFrameFromID(UUID id){
        Site newSite = ticketingSystem.getSite(id);
        if (newSite != null){
            addSiteToSiteSelectionFrame(newSite);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    // Adds a site to the selection frame
    private void addSiteToSiteSelectionFrame(Site site) {
        siteSelectionFrame.addSite(site.id, site.title, site.state, site.city);
    }

    // SiteInfoFrameMethods
    public void displaySiteInfoFrameIntro(UUID siteId){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelFromID(siteId, true);
        assert innerDisplayPanel != null;
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, true);
        innerDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameIntro newFrame = assembleSiteInfoFrameIntro(innerDisplayPanel);
        newFrame.setVisible(true);
    }

    public SiteInfoFrameIntro assembleSiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel){
        return new SiteInfoFrameIntro(siteInfoDisplayPanel, this);
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
        Site fetchedSite = ticketingSystem.getSite(siteId);
        if (fetchedSite != null){
            return new SiteInfoDisplayPanel(fetchedSite.id, fetchedSite.imageIcon, fetchedSite.title, fetchedSite.description, fetchedSite.address, fetchedSite.city, fetchedSite.state, fetchedSite.zip, fetchedSite.phoneNumber, fetchedSite.emailAddress, isIntro, this);
        }
        return null;
    }

    private TicketPanel makeTicketPanelFromId(UUID ticketId, boolean isIntro){
        Ticket newTicket = ticketingSystem.getTicket(ticketId);
        List<UUID> ticketEntryIds = newTicket.entryIds();
        LocalDate mostRecentDate = LocalDate.MAX; //
        Entry currentEntry = null;

        for (UUID entryId : ticketEntryIds){ // For each value in the ticket entries
            currentEntry = ticketingSystem.getEntry(entryId); // Get the entry associated with that ticket
            if (currentEntry != null){ // If that entry isn't null
                if (mostRecentDate.isAfter(currentEntry.date)) {
                    mostRecentDate = currentEntry.date;
                }
            }
        }
        return new TicketPanel(ticketId, mostRecentDate, ticketEntryIds.size(), newTicket.resolved, isIntro, this);
    }

    // Methods for entry display
    public Site fetchSite(UUID siteId){
        return ticketingSystem.getSite(siteId);
    }

    public Ticket fetchTicket(UUID ticketId){
        return ticketingSystem.getTicket(ticketId);
    }

    public Entry fetchEntry(UUID entryId){
        return ticketingSystem.getEntry(entryId);
    }

    public void displayEntryDisplayFrameIntro(UUID ticketId){
        EntryDisplayPanel innerDisplayPanel = makeEntryDisplayPanelFromID(ticketId, true);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, true);
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
        Ticket fetchedTicket = fetchTicket(ticketId);
        if (fetchedTicket != null){
            return new EntryDisplayPanel(ticketId, fetchedTicket.description, fetchedTicket.resolved, isIntro);
        }
        return null;
    }

    // Generate an entry panel to be added to the ticket entry screen in the intro section of the app
    public EntryPanel makeEntryPanelFromId(UUID entryId, boolean isIntro){
        Entry fetchedEntry = fetchEntry(entryId);
        if (fetchedEntry != null){
            return new EntryPanel(fetchedEntry.id, fetchedEntry.date, fetchedEntry.description,  fetchedEntry.imageIcon, fetchedEntry.reviewed, isIntro);
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

