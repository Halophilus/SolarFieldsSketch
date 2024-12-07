import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ControllerImpl implements Controller {
    Model model;
    View view;


    ControllerImpl() {
        this.model = new ModelImpl();
        this.view = new ViewImpl();
    }

    @Override
    // Closes all frames associated with a given section
    public void closeAllOpenedFrames(){
        view.closeAllOpenedFrames();
    }

    // General popups
    @Override
    // Displays pop up screen once the connection is restored
    public void displayConnectionRestoredPopup() {
        view.displayConnectionRestoredPopup(this);
    }

    // Model management controls
    @Override
    // Marks all new entries/tickets as old to differentiate them from unuploaded elements
    public void markLocalStorageAsUploaded(){
        model.markLocalStorageAsUploaded();
    }

    @Override
    // Upload local entries to global database via model
    public void uploadLocalEntries(){
        model.uploadAllNewContent();
    }

    @Override
    // Clears all locally stored sites
    public void clearLocalStorage() {
        model.clearLocalStorage();
    }

    @Override
    // Generates a new ticket and stores it in a local database
    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        model.generateLocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    @Override
    // Generates a new entry and stores it in a local database
    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        model.generateLocalEntry(date, description, icon, ticketId, siteId, entryId);
    }

    @Override
    // Checks for online status
    // This method is a dummy method to simulate checking an external variable in the background
    // Internet connectivity in the case is being simulated by the state of a text file (either full or empty)
    // Returns true if "connected" (text file contains content)
    // Returns false otherwise
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

    // Site Selection Behavior control methods
    @Override
    // Displays site selection frame for intro section of the app
    public void displaySiteSelectionFrameIntro(){
        SiteSelectionFrame siteSelectionFrame= view.generateSiteSelectionFrameIntro(this);
        List<UUID> siteIdCollectionList = model.globalSiteIdCollectionList();
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, true);
    }


    @Override
    // Displays site selection frame for edit section of the app
    public void displaySiteSelectionFrameEdit(Set<UUID> selectedSiteIds){
        SiteSelectionFrame siteSelectionFrame = view.generateSiteSelectionFrameEdit(this);
        model.downloadSiteData(selectedSiteIds); // Download sites selected in Intro section

        List<UUID> siteIdCollectionList = model.localSiteIDCollectionList();
        addSitesToSiteSelectionFrame(siteSelectionFrame, siteIdCollectionList, false);
    }


    // Helper method that iterates through site Ids and adds them to a generic SiteSelectionFrame
    private void addSitesToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, List<UUID> selectedSites, boolean isIntro){
        for (UUID siteId : selectedSites){
            addSiteToSiteSelectionFrameFromID(siteSelectionFrame, siteId, isIntro);
        }
    }


    // Takes IDs passed from addSitesToSiteSelectionFrame and uses them to generate sitePanels
    private void addSiteToSiteSelectionFrameFromID(SiteSelectionFrame siteSelectionFrame, UUID id, boolean isIntro){
        Site site = null;
        if (isIntro){
            site = model.fetchGenericSiteFromGlobalDatabase(id);
        } else {
            site = model.fetchGenericSiteFromLocalDatabase(id);
        }
        if (site != null){
            view.addSiteToSiteSelectionFrame(siteSelectionFrame,site.id(), site.title(), site.state(), site.city(), isIntro, this);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    @Override
    public void displayExportScreenForSelectLocations(Set<UUID> selectedSites) {

    }
    // SiteInfoFrameMethods
    @Override
    // Displays a SiteInfoFrame for a given site in the intro section of the app
    public void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelHeader(siteId, true);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, true);
        view.displaySiteInfoFrameIntro(innerDisplayPanel, siteSelectionFrame, this);
    }

    @Override
    // Displays a SiteInfoFrame for a given site in the edit section of the app
    public void displaySiteInfoFrameEdit(UUID siteId, SiteSelectionFrame siteSelectionFrame){
        SiteInfoDisplayPanel innerDisplayPanel = makeSiteInfoDisplayPanelHeader(siteId, false);
        addTicketPanelsToSiteInfoDisplayPanel(innerDisplayPanel, false);
        view.displaySiteInfoFrameEdit(innerDisplayPanel, siteSelectionFrame, this);
    }

    // Helper method for iterating through the tickets associated with a site and generating panels for each one
    private void addTicketPanelsToSiteInfoDisplayPanel(SiteInfoDisplayPanel siteInfoDisplayPanel, boolean isIntro){
        // Access flow changes depending on the section of the app
        Site targetSite = null;
        if (isIntro) {
            targetSite = model.fetchGenericSiteFromGlobalDatabase(siteInfoDisplayPanel.siteId);
        } else {
            targetSite = model.fetchGenericSiteFromLocalDatabase(siteInfoDisplayPanel.siteId);
        }

        // Get a set of all tickets associated with the target site
        Set<UUID> setOfTickets = model.setOfTicketsPerSite(targetSite);

        // Iterate through them all and generate/add new tickets to the display panel
        if(!setOfTickets.isEmpty()){
            for (UUID ticketId : setOfTickets){
                TicketPanel newTicketPanel = makeTicketPanelFromId(ticketId, isIntro);
                siteInfoDisplayPanel.addTicketPanel(newTicketPanel);
            }
        }
    }

    @Override
    // General method for generating new TicketPanels given a specific UUID
    public TicketPanel makeTicketPanelFromId(UUID ticketId, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = model.fetchGenericTicketFromGlobalDatabase(ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(ticketId);
        }

        List<Entry> entries = model.genericListOfEntries(newTicket);
        LocalDate mostRecentDate = LocalDate.MAX;

        for (Entry entry : entries){ // For each value in the ticket entries
            if (mostRecentDate.isAfter(entry.date())) { // Mark the most recent date of entry for the given ticket
                mostRecentDate = entry.date();
            }
        }
        // Generate a ticket panel given the existing ticket data
        return view.generateTicketPanel(ticketId, mostRecentDate, entries.size(), newTicket.resolved(), isIntro, this);
    }

    // Helper method for generating a header/starter for a SiteInfoDisplayPanel with all the info for a given Site
    private SiteInfoDisplayPanel makeSiteInfoDisplayPanelHeader(UUID siteId, boolean isIntro) {
        Site site = null;
        if (isIntro) {
            site = model.fetchGenericSiteFromGlobalDatabase(siteId);
        } else {
            site = model.fetchGenericSiteFromLocalDatabase(siteId);
        }
        if (site != null){
            return view.generateSiteInfoDisplayPanel(site.id(), site.imageIcon(), site.title(), site.description(), site.address(), site.city(), site.state(), site.zip(), site.phoneNumber(), site.emailAddress(), isIntro, this);
        }
        return null;
    }

    @Override
    // Displays a screen for adding new tickets to a given site
    // Available in the edit section of the app
    public void displayAddTicketScreen(UUID siteId, SiteInfoFrameEdit siteInfoFrameEdit, Controller controller) {
        view.displayAddTicketScreen(siteId, siteInfoFrameEdit, controller);
    }

    // Methods for controlling the display of ticket info / associated entries
    @Override
    // Generates an EntryDisplayFrame for the intro section of the app
    public void displayEntryDisplayFrameIntro(UUID ticketId){
        EntryDisplayPanel innerDisplayPanel = generateEntryDisplayPanelFromID(ticketId, true);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, true);
        view.displayEntryDisplayFrameIntro(innerDisplayPanel, this);
    }

    @Override
    // Generates an EntryDisplayFrame for the edit section of the app
    public void displayEntryDisplayFrameEdit(UUID ticketId, TicketPanel ticketPanel) {
        // Set up display panel
        EntryDisplayPanel innerDisplayPanel = generateEntryDisplayPanelFromID(ticketId, false);
        addEntryPanelsToEntryDisplayPanel(innerDisplayPanel, false);

        // Generate and display EntryDisplayPanelFrame
        generateEntryDisplayPanelFrameEdit(ticketId, innerDisplayPanel, ticketPanel);
    }

    // Helper method for generating an EntryDisplayPanel for the edit section of the app
    // This requires access to the Controller so that it can derive the parent Site UUID prior to displaying the frame, which is otherwise not locally stored
    private void generateEntryDisplayPanelFrameEdit(UUID ticketId, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel){
        UUID parentSiteId = model.getParentSiteId(ticketId);
        view.displayEntryDisplayFrameEdit(entryDisplayPanel, parentTicketPanel, parentSiteId, this);
    }

    // Helper method for creating and adding new EntryPanels from a list of UUIDs associated with that ticket
    private void addEntryPanelsToEntryDisplayPanel(EntryDisplayPanel entryDisplayPanel, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = model.fetchGenericTicketFromGlobalDatabase(entryDisplayPanel.ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(entryDisplayPanel.ticketId);
        }
        List<UUID> listOfEntries = model.entryUUIDsFromTicket(newTicket);

        if (!listOfEntries.isEmpty()){
            for (UUID entryId : listOfEntries){
                EntryPanel newEntryPanel = generateEntryPanelFromId(entryId, isIntro);
                entryDisplayPanel.addEntryPanel(newEntryPanel);
            }
        }
    }


    // Helper method for generating display panel for ticket entries in the intro section of the app
    private EntryDisplayPanel generateEntryDisplayPanelFromID(UUID ticketId, boolean isIntro){
        Ticket newTicket = null;
        if (isIntro) {
            newTicket = model.fetchGenericTicketFromGlobalDatabase(ticketId);
        } else {
            newTicket = model.fetchGenericTicketFromLocalDatabase(ticketId);
        }
        if (newTicket != null){
            return view.generateEntryDisplayPanelIntro(ticketId, newTicket.description(), newTicket.resolved(), isIntro);
        }
        return null;
    }

    // Helper method for generating entry panels given an entry's ID
    private EntryPanel generateEntryPanelFromId(UUID entryId, boolean isIntro){
        Entry newEntry = null;
        if (isIntro) {
            newEntry = model.fetchGenericEntryFromGlobalDatabase(entryId);
        } else {
            newEntry = model.fetchGenericEntryFromLocalDatabase(entryId);
        }
        if (newEntry != null){
            return view.generateEntryPanel(newEntry.id(), newEntry.date(), newEntry.description(),  newEntry.icon(), newEntry.reviewed(), isIntro);
        }
        return null;
    }

    @Override
    // Displays a screen for adding new entries to a given ticket
    // Available in the edit section of the app only
    public void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame,  EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel){
        view.displayAddEntryScreen(entryDisplayFrame, entryDisplayPanel, parentTicketPanel, this);
    }

}

