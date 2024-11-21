import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Controller {
    public SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame();
    public SiteInfoFrame siteInfoFrame;
    TicketingSystem ticketingSystem;

    Controller(TicketingSystem ticketingSystem) {
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
    public void generateSiteInfoAndDisplay(UUID siteId){
        makeSiteInfoFrameFromID(siteId);
        siteInfoFrame.setVisible(true);
    }

    private void makeSiteInfoFrameFromID(UUID id){
        Site newSite = ticketingSystem.getSite(id);
        siteInfoFrame = new SiteInfoFrame(newSite.id,
                                        newSite.imageIcon,
                                        newSite.title,
                                        newSite.description,
                                        newSite.address,
                                        newSite.city,
                                        newSite.state,
                                        newSite.zip,
                                        newSite.phoneNumber,
                                        newSite.emailAddress);

        parseTicketsFromListOfTicketIds(Set.copyOf(newSite.ticketIds()));
    }

    private void parseTicketsFromListOfTicketIds(Set<UUID> tickets){
        for (UUID id : tickets){
            addTicketToSiteInfoFrameFromID(id);
        }
    }

    private void addTicketToSiteInfoFrameFromID(UUID id) {
        Ticket newTicket = ticketingSystem.getTicket(id);
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
        if (newTicket != null){
            TicketPanel newTicketPanel = new TicketPanel(newTicket.id, mostRecentDate, ticketEntryIds.size(), newTicket.resolved);
        }
    }



}

