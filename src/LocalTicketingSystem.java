import java.util.ArrayList;
import java.util.UUID;

public class LocalTicketingSystem {
    public static ArrayList<LocalSite> downloadedSites = new ArrayList<>();

    static LocalSite getSite(UUID id) {
        for (LocalSite localSite : downloadedSites) {
            if (localSite.id.equals(id)) {
                return localSite;
            }
        }
        return null;
    }

    static LocalTicket getTicket(UUID id) {
        for (LocalSite localSite : downloadedSites) {
            for (LocalTicket localTicket : localSite.localTickets) {
                if (localTicket.id.equals(id)) {
                    return localTicket;
                }
            }
        }
        return null;
    }

    static LocalEntry getEntry(UUID id) {
        for (LocalSite localSite : downloadedSites) {
            for (LocalTicket localTicket : localSite.localTickets) {
                for (LocalEntry localEntry : localTicket.localEntries) {
                    if (localEntry.id.equals(id)) {
                        return localEntry;
                    }
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

}
