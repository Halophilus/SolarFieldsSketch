import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

// A static class for fetching Local Site, Ticket, and Entry objects from their associated UUID
public class LocalTicketingSystem {
    // Entry point for the local database
    public static ArrayList<LocalSite> downloadedSites = new ArrayList<>();
    public static ArrayList<LocalSite> getAllSites() { return downloadedSites;}
    public static void addSite(LocalSite site) {
        downloadedSites.add(site);
    }

    // Method for flushing the local database when an Edit session ends
    public static void clearSites(){
        downloadedSites.clear();
    }

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

    // Used to indicate that a Site has been updated when only the Ticket UUID is accessible
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
}
