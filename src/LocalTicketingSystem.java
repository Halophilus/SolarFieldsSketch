import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class LocalTicketingSystem {
    public static ArrayList<LocalSite> downloadedSites = new ArrayList<>();

    static Site getSite(UUID id) {
        for (Site localSite : downloadedSites) {
            if (localSite.id().equals(id)) {
                return localSite;
            }
        }
        return null;
    }

    static Ticket getTicket(UUID id) {
        for (Site localSite : downloadedSites) {
            for (Ticket localTicket : localSite.tickets()) {
                if (localTicket.id().equals(id)) {
                    return localTicket;
                }
            }
        }
        return null;
    }

    static Entry getEntry(UUID id) {
        for (Site localSite : downloadedSites) {
            for (Ticket localTicket : localSite.tickets()) {
                for (Entry localEntry : localTicket.entries()) {
                    if (localEntry.id().equals(id)) {
                        return localEntry;
                    }
                }

            }
        }
        return null;
    }

    // Mapping association between ticket ID and download ID
    static Site getSiteFromTicket(UUID ticketId) {
        for (Site localSite : downloadedSites) {
            for (Ticket localTicket : localSite.tickets()) {
                if (localTicket.id().equals(ticketId)) {
                    return localSite;
                }
            }
        }
        return null;
    }


    public static void addSite(LocalSite site) {
        downloadedSites.add(site);
    }

    public static void clearSites(){
        downloadedSites.clear();
    }

    public static ArrayList<LocalSite> getAllSites() { return downloadedSites;
    }
}
