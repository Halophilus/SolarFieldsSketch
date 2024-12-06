import javax.swing.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;

    @Override
    public GlobalSite fetchGlobalSite(UUID siteID){
        return (GlobalSite)GlobalTicketingSystem.getSite(siteID);
    }

    @Override
    public LocalSite generateLocalSite(GlobalSite globalSite){
        return new LocalSite(globalSite);
    }

    @Override
    public void clearLocalStorage(){
        LocalTicketingSystem.clearSites();
    }


    @Override
    public void downloadLocalSite(LocalSite newLocalSite){
        LocalTicketingSystem.addSite(newLocalSite);
    }

    @Override
    public ArrayList<LocalSite> getLocallyStoredSites(){
        return LocalTicketingSystem.getAllSites();
    }

    @Override
    public GlobalTicket fetchGlobalTicket(UUID ticketId){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(ticketId);
    }

    @Override
    public List<UUID> globalSiteIds(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }

    @Override
    public List<UUID> localSiteIds(){
        return LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
    }

    @Override
    public LocalSite globalToLocalSite(GlobalSite globalSite){
        return new LocalSite(globalSite);
    }

    @Override
    public LocalTicket coerceLocalTicket(Ticket ticket){
        return (LocalTicket) ticket;
    }

    @Override
    public LocalEntry coerceLocalEntry(Entry entry){
        return (LocalEntry) entry;
    }

    @Override
    public List<LocalSite> fetchUpdatedLocalSites(){
        return fetchLocalSites().stream().filter(localSite -> localSite.updated).toList();
    }

    @Override
    public List<Ticket> getUpdatedOldTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::updated).filter(ticket ->!ticket.isNew()).toList();
    }

    @Override
    public List<Ticket> getNewTickets(LocalSite updatedSite){
        return updatedSite.tickets().stream().filter(Ticket::isNew).toList();
    }

    @Override
    public Set<Entry> newEntriesSet(LocalTicket updatedTicket){
        return new HashSet<>(updatedTicket.entries().stream().filter(Entry::isNew).toList());
    }

    @Override
    public GlobalEntry localToGlobalEntry(LocalEntry newLocalEntry){
        return new GlobalEntry(newLocalEntry);
    }

    @Override
    public GlobalTicket fetchCorrespondingGlobalTicket(UUID id){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(id);
    }

    @Override
    public void addEntryToGlobalTicket(GlobalTicket correspondingGlobalTicket, GlobalEntry newGlobalEntry){
        correspondingGlobalTicket.addEntry(newGlobalEntry); // Add new entry
        correspondingGlobalTicket.resolve(false); // Update resolved flag to reflect the new entry
    }

    @Override
    public GlobalTicket genericTicketToGlobal(Ticket newTicket) {
        return new GlobalTicket((LocalTicket) newTicket);
    }

    @Override
    public void syncLocalEntryToCorrespondingTicket(GlobalTicket correspondingTicket, GlobalEntry newGlobalEntry){
        correspondingTicket.addEntry(newGlobalEntry);
    }

    @Override
    public List<UUID> globalSiteIdCollectionList(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }

    @Override
    public List<UUID> localSiteIDCollectionList(){
        return LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
    }

    @Override
    public void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId){
        new LocalTicket(ticketId, ticketDescriptionInput, siteId);
    }

    @Override
    public void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId) {
        new LocalEntry(date, description, icon, ticketId, siteId, entryId);
    }

    @Override
    public Site fetchGenericSiteFromGlobalDatabase(UUID uuid){
        return GlobalTicketingSystem.getSite(uuid);
    }

    @Override
    public Site fetchGenericSiteFromLocalDatabase(UUID uuid){
        return LocalTicketingSystem.getSite(uuid);
    }

    @Override
    public Set<UUID> setOfTicketsPerSite(Site targetSite){
        return new HashSet<UUID>(targetSite.tickets().stream().map(Ticket::id).distinct().toList());
    }

    @Override
    public Ticket fetchGenericTicketFromGlobalDatabase(UUID ticketId){
        return GlobalTicketingSystem.getTicket(ticketId);
    }

    @Override
    public Ticket fetchGenericTicketFromLocalDatabase(UUID ticketId){
        return LocalTicketingSystem.getTicket(ticketId);
    }

    @Override
    public List<Entry> genericListOfEntries(Ticket newTicket){
        return newTicket.entries().stream().distinct().toList();
    }

    @Override
    public Entry fetchGenericEntryFromGlobalDatabase(UUID entryId){
        return GlobalTicketingSystem.getEntry(entryId);
    }

    @Override
    public Entry fetchGenericEntryFromLocalDatabase(UUID entryId){
        return LocalTicketingSystem.getEntry(entryId);
    }

    @Override
    public List<UUID> entriesFromTicket(Ticket newTicket){
        return newTicket.entryIds();
    }
}
