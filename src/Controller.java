import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public class Controller {
    public SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame();
    public SiteInfoFrame siteInfoFrame;

    // SiteSelectionFrame methods
    // Takes a set of integers and adds the relevant IDs to the selection frame
    public void addSitesToSiteSelectionFrame(Set<Integer> selectedSites){
        Set.copyOf(selectedSites).forEach(this::addSiteToSiteSelectionFrameFromID);
    }

    // Takes an individual ID and attempts to add it to the selection frame
    public void addSiteToSiteSelectionFrameFromID(int id){
        Site newSite = SiteImpl.getSite(id);
        if (newSite != null){
            addSiteToSiteSelectionFrame(newSite);
        } else {
            System.out.println("Couldn't find site with id " + id);
        }
    }

    // Adds a site to the selection frame
    private void addSiteToSiteSelectionFrame(Site site) {
        siteSelectionFrame.addSite(site.id(), site.title(), site.state(), site.city());
    }

    // SiteInfoFrameMethods
    public void generateSiteInfoAndDisplay(int siteId){
        makeSiteInfoFrameFromID(siteId);
        siteInfoFrame.setVisible(true);
    }

    private void makeSiteInfoFrameFromID(int id){
        Site newSite = SiteImpl.getSite(id);
        siteInfoFrame = new SiteInfoFrame(newSite.id(),
                                        newSite.title(),
                                        newSite.description(),
                                        newSite.address(),
                                        newSite.city(),
                                        newSite.state(),
                                        newSite.zip(),
                                        newSite.phoneNumber(),
                                        newSite.emailAddress());

        parseTicketsFromListOfTicketIds(newSite.ticketIDs());
    }

    private void parseTicketsFromListOfTicketIds(Set<Integer> tickets){
        for (Integer id : tickets){
            addTicketToSiteInfoFrameFromID(id);
        }
    }

    private void addTicketToSiteInfoFrameFromID(Integer id) {
        Ticket newTicket = TicketImpl.getTicket(id);
        ArrayList<Integer> ticketEntryIds = newTicket.entryIDs();
        LocalDate mostRecentDate = LocalDate.MAX; //
        Entry currentEntry = null;

        for (int entryId : ticketEntryIds){ // For each value in the ticket entries
            currentEntry = EntryImpl.getEntry(entryId); // Get the entry associated with that ticket
            if (currentEntry != null){ // If that entry isn't null
                if (mostRecentDate.isAfter(currentEntry.date())) {
                    mostRecentDate = currentEntry.date();
                }
            }
        }
        if (newTicket != null){
            TicketPanel newTicketPanel = new TicketPanel(newTicket.id(), mostRecentDate, ticketEntryIds.size(), newTicket.resolved());
        }
    }



}

