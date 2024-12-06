import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// Interface for handling interactions between global and local databases
public interface Model {

    // Broad database operations
    void downloadSiteData(Set<UUID> selectedSiteIds);
    void uploadLocalEntries();
    void markLocalStorageAsUploaded();
    void clearLocalStorage();

    // Specific database queries
    default ArrayList<LocalSite> fetchLocalSites() {
        return LocalTicketingSystem.getAllSites();
    }
    Set<UUID> setOfTicketsPerSite(Site targetSite);
    List<Entry> genericListOfEntries(Ticket newTicket);
    List<UUID> entryUUIDsFromTicket(Ticket newTicket);
    UUID getParentSiteId(UUID ticketId);

    // List of stored sites in both local and global databases
    List<UUID> globalSiteIdCollectionList();
    List<UUID> localSiteIDCollectionList();

    // Means of generating new entries (edit section only)
    void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId);
    void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId);

    // Generic fetch queries for the global database
    Site fetchGenericSiteFromGlobalDatabase(UUID uuid);
    Ticket fetchGenericTicketFromGlobalDatabase(UUID ticketId);
    Entry fetchGenericEntryFromGlobalDatabase(UUID entryId);

    // Generic fetch queries for the local database
    Site fetchGenericSiteFromLocalDatabase(UUID uuid);
    Ticket fetchGenericTicketFromLocalDatabase(UUID ticketId);
    Entry fetchGenericEntryFromLocalDatabase(UUID entryId);
    
}
