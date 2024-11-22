import java.util.ArrayList;
import java.util.UUID;

public class TicketingSystem {
    public static ArrayList<Site> sites = new ArrayList<>();

    Site getSite(UUID id) {
        for (Site site : sites) {
            if (site.id.equals(id)) {
                return site;
            }
        }
        return null;
    }

    Ticket getTicket(UUID id) {
        for (Site site : sites) {
            for (Ticket ticket : site.tickets) {
                if (ticket.id.equals(id)) {
                    return ticket;
                }
            }
        }
        return null;
    }

    Entry getEntry(UUID id) {
        for (Site site : sites) {
            for (Ticket ticket : site.tickets) {
                for (Entry entry : ticket.entries) {
                    if (entry.id.equals(id)) {
                        return entry;
                    }
                }

            }
        }
        return null;
    }

    ArrayList<Site> getAllSites(){
        return sites;
    }
}
