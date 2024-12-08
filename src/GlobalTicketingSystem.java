import java.util.ArrayList;
import java.util.UUID;

// A static class for fetching Global Site, Ticket, and Entry objects from their associated UUIDs
public class GlobalTicketingSystem {
    // Entry point for the global database
    public static ArrayList<Site> globalSites = new ArrayList<>();
    public static ArrayList<Site> getAllSites() {
        return globalSites;
    }

    // Static getter methods
    static Site getSite(UUID id) {
        for (Site globalSite : globalSites) {
            if (globalSite.id().equals(id)) {
                return globalSite;
            }
        }
        return null;
    }

    static Ticket getTicket(UUID id) {
        for (Site globalSite : globalSites) {
            for (Ticket globalTicket : globalSite.tickets()) {
                if (globalTicket.id().equals(id)) {
                    return globalTicket;
                }
            }
        }
        return null;
    }

    static Entry getEntry(UUID id) {
        for (Site globalSite : globalSites) {
            for (Ticket globalTicket : globalSite.tickets()) {
                for (Entry globalEntry : globalTicket.entries()) {
                    if (globalEntry.id().equals(id)) {
                        return globalEntry;
                    }
                }

            }
        }
        return null;
    }

}
