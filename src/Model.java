import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Model {
    GlobalSite fetchGlobalSite(UUID siteID);

    LocalSite generateLocalSite(GlobalSite globalSite);

    void clearLocalStorage();

    void downloadLocalSite(LocalSite newLocalSite);

    ArrayList<LocalSite> getLocallyStoredSites();

    GlobalTicket fetchGlobalTicket(UUID ticketId);

    List<UUID> globalSiteIds();

    List<UUID> localSiteIds();

    LocalSite globalToLocalSite(GlobalSite globalSite);

    LocalTicket coerceLocalTicket(Ticket ticket);

    LocalEntry coerceLocalEntry(Entry entry);

    default ArrayList<LocalSite> fetchLocalSites() {
        return LocalTicketingSystem.getAllSites();
    }

    void markLocalStorageAsUploaded();

    void uploadLocalEntries();

    List<LocalSite> fetchUpdatedLocalSites();

    List<Ticket> getUpdatedOldTickets(LocalSite updatedSite);

    List<Ticket> getNewTickets(LocalSite updatedSite);

    Set<Entry> newEntriesSet(LocalTicket updatedTicket);

    Set<Entry> newEntriesSet(Ticket updatedTicket);

    GlobalEntry localToGlobalEntry(LocalEntry newLocalEntry);

    GlobalTicket fetchCorrespondingGlobalTicket(UUID id);

    GlobalSite fetchCorrespondingGlobalSite(UUID siteiD);

    void addEntryToGlobalTicket(GlobalTicket correspondingGlobalTicket, GlobalEntry newGlobalEntry);

    GlobalTicket genericTicketToGlobal(Ticket newTicket);

    void syncLocalEntryToCorrespondingTicket(GlobalTicket correspondingTicket, GlobalEntry newGlobalEntry);

    List<UUID> globalSiteIdCollectionList();

    List<UUID> localSiteIDCollectionList();

    void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId);

    void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId);

    Site fetchGenericSiteFromGlobalDatabase(UUID uuid);

    Site fetchGenericSiteFromLocalDatabase(UUID uuid);

    Set<UUID> setOfTicketsPerSite(Site targetSite);

    Ticket fetchGenericTicketFromGlobalDatabase(UUID ticketId);

    Ticket fetchGenericTicketFromLocalDatabase(UUID ticketId);

    List<Entry> genericListOfEntries(Ticket newTicket);

    Entry fetchGenericEntryFromGlobalDatabase(UUID entryId);

    Entry fetchGenericEntryFromLocalDatabase(UUID entryId);

    List<UUID> entriesFromTicket(Ticket newTicket);

    void downloadSiteData(Set<UUID> selectedSiteIds);
}
