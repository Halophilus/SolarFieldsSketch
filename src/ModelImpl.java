import javax.swing.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;

    @Override
    // Clears local storage at closure of edit section
    public void clearLocalStorage(){
        LocalTicketingSystem.clearSites();
    }

    @Override
    // Marks all local storage as having been uploaded
    // Allows new entry/ticket operations to continue in the edit section after uploading it.
    public void markLocalStorageAsUploaded(){
        // For all the locally stored sites
        for(LocalSite site : getLocallyStoredSites()){
            site.reset(); // Reset updated/new flags
            for (Ticket ticket : site.tickets()){
                LocalTicket localTicket = coerceLocalTicket(ticket);
                localTicket.reset(); // Reset flags for each ticket
                for (Entry entry : ticket.entries()){
                    LocalEntry localEntry = coerceLocalEntry(entry);
                    localEntry.reset(); // Reset flags for each entry
                }

            }
        }
    }

    // Helper method for getting all locally stored sites
    private ArrayList<LocalSite> getLocallyStoredSites(){
        return LocalTicketingSystem.getAllSites();
    }

    // Helper method for switching control flow to access a Ticket as a LocalTicket
    private LocalTicket coerceLocalTicket(Ticket ticket){
        return (LocalTicket) ticket;
    }

    // Local -> Global operations
    @Override
    // Uploads all new entries and tickets generated while accessing the edit section
    public void uploadAllNewContent(){
        // Extract local sites, filter out unchanged sites
        List<LocalSite> updatedLocalSites = fetchUpdatedLocalSites();

        // For all updated sites
        for (LocalSite updatedSite : updatedLocalSites) {
            List<Ticket> oldTicketsWithNewEntries = getUpdatedOldTickets(updatedSite);
            List<Ticket> newTickets = getNewTickets(updatedSite);

            // Iterate through the old updated tickets
            for (Ticket updatedTicket : oldTicketsWithNewEntries) {
                System.out.println("Processing old tickets with new entries");
                Set<Entry> entriesSet = newEntriesSet(updatedTicket);
                for (Entry newEntry : entriesSet) {
                    System.out.println(STR."Processing newEntry \{newEntry}");
                    LocalEntry newLocalEntry = coerceLocalEntry(newEntry);
                    // Generate a new global entry from the local entry
                    GlobalEntry newGlobalEntry = localToGlobalEntry(newLocalEntry);
                    // Get the corresponding global ticket
                    GlobalTicket correspondingGlobalTicket = fetchCorrespondingGlobalTicket(updatedTicket.id());
                    // Add the newly generated global entry to the in-memory local entry
                    addEntryToGlobalTicket(correspondingGlobalTicket, newGlobalEntry);
                }
            }

            // Iterate through all the new tickets
            for (Ticket newTicket : newTickets) {
                System.out.println("Processing new tickets");
                // Generate a new global ticket
                GlobalTicket newGlobalTicket = genericTicketToGlobal(newTicket);
                // Iterate through the local ticket's entries
                for (Entry newEntry : newTicket.entries()) {
                    LocalEntry newLocalEntry = coerceLocalEntry(newEntry);
                    // Generate a new global entry
                    GlobalEntry newGlobalEntry = localToGlobalEntry(newLocalEntry);
                    // Add it to the new global ticket
                    addEntryToGlobalTicket(newGlobalTicket, newGlobalEntry);
                }
                // Fetch the corresponding global site
                GlobalSite correspondingGlobalSite = fetchCorrespondingGlobalSite(updatedSite.id());
                // Add the ticket to the existing global site
                correspondingGlobalSite.addTicket(newGlobalTicket);

            }
        }
    }

    // Upload helper methods
    // Returns a list of LocalSites that have been updated in some way (new tickets/entries)
    private List<LocalSite> fetchUpdatedLocalSites(){
        return fetchLocalSites().stream().filter(localSite -> localSite.updated).toList();
    }

    // A list of tickets that existed at time of download with new entries
    private List<Ticket> getUpdatedOldTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::updated).filter(ticket ->!ticket.isNew()).toList();
    }

    // A list of all new tickets
    private List<Ticket> getNewTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::isNew).toList();
    }

    // A set of all new entries for a ticket
    private Set<Entry> newEntriesSet(Ticket updatedTicket){
        return new HashSet<>(updatedTicket.entries().stream().filter(Entry::isNew).toList());
    }

    // Uploads a local entry to the global database by generating a new global entry
    private GlobalEntry localToGlobalEntry(LocalEntry newLocalEntry){
        return new GlobalEntry(newLocalEntry);
    }

    // Gets a GlobalTicket from its associated UUID
    private GlobalTicket fetchCorrespondingGlobalTicket(UUID id){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(id);
    }

    // Gets a GlobalSite from its associated UUID
    private GlobalSite fetchCorrespondingGlobalSite(UUID siteId){
        return (GlobalSite) GlobalTicketingSystem.getSite(siteId);
    }

    // Adds a new GlobalEntry to an existing GlobalTicket
    private void addEntryToGlobalTicket(GlobalTicket correspondingGlobalTicket, GlobalEntry newGlobalEntry){
        correspondingGlobalTicket.addEntry(newGlobalEntry); // Add new entry
        correspondingGlobalTicket.resolve(false); // Update resolved flag to reflect the new entry
    }

    // Coerces a generic Entry into a LocalEntry object to shift control flow
    private LocalEntry coerceLocalEntry(Entry entry){
        return (LocalEntry) entry;
    }

    // Uploads a generic Ticket to the global database by generating a new GlobalTicket
    private GlobalTicket genericTicketToGlobal(Ticket newTicket) {
        return new GlobalTicket((LocalTicket) newTicket);
    }

    // Global -> Local operations
    @Override
    // Downloads data associated from a set of UUIDs associated with GlobalSites so that it can be manipulated in the edit section
    public void downloadSiteData(Set<UUID> selectedSiteIds){
        // For each UUID passed to the method
        for (UUID siteId : selectedSiteIds){
            // Find the corresponding global site
            GlobalSite globalSite = fetchGlobalSite(siteId);
            // Generate a new local site based on it
            // Thereby downloading the hierarchical data associated with it
            LocalSite newLocalSite = globalToLocalSite(globalSite);
            downloadLocalSite(newLocalSite);
        }
    }

    // Associated helper methods
    // Gets a GlobalSite from the global database
    private GlobalSite fetchGlobalSite(UUID siteID){
        return (GlobalSite)GlobalTicketingSystem.getSite(siteID);
    }

    // Adds a LocalSite to the Local Database
    private void downloadLocalSite(LocalSite newLocalSite){
        LocalTicketingSystem.addSite(newLocalSite);
    }

    // Generates a LocalSite from the data in a GlobalSite
    private LocalSite globalToLocalSite(GlobalSite globalSite){
        return new LocalSite(globalSite);
    }

    // Generic local database queries
    @Override
    public Ticket fetchGenericTicketFromLocalDatabase(UUID ticketId){return LocalTicketingSystem.getTicket(ticketId);}
    @Override
    public Entry fetchGenericEntryFromLocalDatabase(UUID entryId){
        return LocalTicketingSystem.getEntry(entryId);
    }
    @Override
    public Site fetchGenericSiteFromLocalDatabase(UUID uuid){
        return LocalTicketingSystem.getSite(uuid);
    }
    // Returns list of all global Site IDs
    @Override
    public List<UUID> globalSiteIdCollectionList(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }


    // Generic global database queries
    @Override
    public Site fetchGenericSiteFromGlobalDatabase(UUID uuid){
        return GlobalTicketingSystem.getSite(uuid);
    }
    @Override
    public Ticket fetchGenericTicketFromGlobalDatabase(UUID ticketId){return GlobalTicketingSystem.getTicket(ticketId);}
    @Override
    public Entry fetchGenericEntryFromGlobalDatabase(UUID entryId){return GlobalTicketingSystem.getEntry(entryId);}
    // Returns list of all locally stored Site Ids
    @Override
    public List<UUID> localSiteIDCollectionList(){
        return LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
    }

    // Amphoteric database operations
    @Override
    // Pulls all unique ticket IDs associated with a given site
    public Set<UUID> setOfTicketsPerSite(Site targetSite){
        return new HashSet<UUID>(targetSite.tickets().stream().map(Ticket::id).distinct().toList());
    }

    // New content operations
    @Override
    // Generates a new local Ticket
    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        new LocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    @Override
    // Generates a new local Entry
    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        new LocalEntry(date, description, icon, ticketId, siteId, entryId);
    }

    @Override
    // Returns a list of UUIDs associated with the entries of a ticket
    public List<UUID> entryUUIDsFromTicket(Ticket newTicket){
        return newTicket.entryIds();
    }

    @Override
    // Returns a list of generic Entries from a generic Ticket
    public List<Entry> genericListOfEntries(Ticket newTicket){
        return newTicket.entries().stream().distinct().toList();
    }

    @Override
    // Gets the parentSiteId from the UUID of a given ticket
    public UUID getParentSiteId(UUID ticketId){
        return LocalTicketingSystem.getSiteFromTicket(ticketId).id();
    }
}
