import javax.swing.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ModelImpl {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;

    public GlobalSite fetchGlobalSite(UUID siteID){
        return (GlobalSite)GlobalTicketingSystem.getSite(siteID);
    }

    public void downloadLocalSite(LocalSite newLocalSite){
        LocalTicketingSystem.addSite(newLocalSite);
    }

    public ArrayList<LocalSite> getLocallyStoredSites(){
        return LocalTicketingSystem.getAllSites();
    }

    public GlobalTicket fetchGlobalTicket(UUID ticketId){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(ticketId);
    }

    public List<UUID> globalSiteIds(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }

    public List<UUID> localSiteIds(){
        return LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
    }

    public LocalSite globalToLocalSite(GlobalSite globalSite){
        return new LocalSite(globalSite);
    }

    public LocalTicket coerceLocalTicket(Ticket ticket){
        return (LocalTicket) ticket;
    }

    public LocalEntry coerceLocalEntry(Entry entry){
        return (LocalEntry) entry;
    }

    private ArrayList<LocalSite> fetchLocalSites(){
        return LocalTicketingSystem.getAllSites();
    }

    public List<LocalSite> fetchUpdatedLocalSites(){
        return fetchLocalSites().stream().filter(localSite -> localSite.updated).toList();
    }

    public List<Ticket> getUpdatedOldTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::updated).filter(ticket ->!ticket.isNew()).toList();
    }

    public List<Ticket> getNewTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::isNew).toList();
    }

    public Set<Entry> newEntriesSet(LocalTicket updatedTicket){
        return new HashSet<>(updatedTicket.entries().stream().filter(Entry::isNew).toList());
    }

    public GlobalEntry localToGlobalEntry(LocalEntry newLocalEntry){
        return new GlobalEntry(newLocalEntry);
    }

    public GlobalTicket fetchCorrespondingGlobalTicket(UUID id){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(id);
    }

    public void addEntryToGlobalTicket(GlobalTicket correspondingGlobalTicket, GlobalEntry newGlobalEntry){
        correspondingGlobalTicket.addEntry(newGlobalEntry); // Add new entry
        correspondingGlobalTicket.resolve(false); // Update resolved flag to reflect the new entry
    }

    public GlobalTicket genericTicketToGlobal(Ticket newTicket) {
        return new GlobalTicket((LocalTicket) newTicket);
    }

    public void syncLocalEntryToCorrespondingTicket(GlobalTicket correspondingTicket, GlobalEntry newGlobalEntry){
        correspondingTicket.addEntry(newGlobalEntry);
    }

    public List<UUID> globalSiteIdCollectionList(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }

    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        new LocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        new LocalEntry(date, description, icon, ticketId, siteId, entryId);
    }

    public Site fetchGenericSiteFromGlobalDatabase(UUID uuid){
        return GlobalTicketingSystem.getSite(uuid);
    }

    public Site fetchGenericSiteFromLocalDatabase(UUID uuid){
        return LocalTicketingSystem.getSite(uuid);
    }

    public Set<UUID> setOfTicketsPerSite(Site targetSite){
        return new HashSet<UUID>(targetSite.tickets().stream().map(Ticket::id).distinct().toList());
    }

    public Ticket fetchGenericTicketFromGlobalDatabase(UUID ticketId){
        return GlobalTicketingSystem.getTicket(ticketId);
    }

    public Ticket fetchGenericTicketFromLocalDatabase(UUID ticketId){
        return LocalTicketingSystem.getTicket(ticketId);
    }

    public List<Entry> genericListOfEntries(Ticket newTicket){
        return newTicket.entries().stream().distinct().toList();
    }

    public Entry fetchGenericEntryFromGlobalDatabase(UUID entryId){
        return GlobalTicketingSystem.getEntry(entryId);
    }
}
