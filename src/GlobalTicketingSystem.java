import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class GlobalTicketingSystem {
    public static ArrayList<GlobalSite> globalSites = new ArrayList<>();

    static GlobalSite getSite(UUID id) {
        for (GlobalSite globalSite : globalSites) {
            if (globalSite.id.equals(id)) {
                return globalSite;
            }
        }
        return null;
    }

    static GlobalTicket getTicket(UUID id) {
        for (GlobalSite globalSite : globalSites) {
            for (GlobalTicket globalTicket : globalSite.globalTickets) {
                if (globalTicket.id.equals(id)) {
                    return globalTicket;
                }
            }
        }
        return null;
    }

    static GlobalEntry getEntry(UUID id) {
        for (GlobalSite globalSite : globalSites) {
            for (GlobalTicket globalTicket : globalSite.globalTickets) {
                for (GlobalEntry globalEntry : globalTicket.entries) {
                    if (globalEntry.id.equals(id)) {
                        return globalEntry;
                    }
                }

            }
        }
        return null;
    }

    public ArrayList<GlobalSite> getAllSites() {
        return globalSites;
    }
}
