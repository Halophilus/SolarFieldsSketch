import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class GlobalTicketingSystem {
    public static ArrayList<Site> globalSites = new ArrayList<>();

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

    public static ArrayList<Site> getAllSites() {
        return globalSites;
    }
}
