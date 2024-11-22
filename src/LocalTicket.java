import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocalTicket implements Ticket{
    final UUID id;

    String description;
    boolean resolved = false;
    boolean updated = false;

    // Special local variables
    List<LocalEntry> localEntries = new ArrayList<>();
    boolean isNew;

    // For downloading globalTickets
    public LocalTicket(GlobalTicket globalTicket) {
        this.id = globalTicket.id();
        this.description = globalTicket.description();
        this.resolved = globalTicket.resolved();
        this.isNew = false;

        for (GlobalEntry globalEntry : globalTicket.entries) {
            LocalEntry downloadedEntry = new LocalEntry(globalEntry);
            localEntries.add(downloadedEntry);
        }
    }

    // For generating new globalTickets while offline
    public LocalTicket(UUID uuid, String description, UUID destinationSiteId) {
        this.id = uuid;
        this.description = description;
        this.resolved = false;
        this.isNew = true;

        // Add the new ticket to the locally stored site's list of tickets
        assert LocalTicketingSystem.getSite(destinationSiteId) != null;
        LocalSite parentSite = LocalTicketingSystem.getSite(destinationSiteId);
        parentSite.indicateUpdated(); // Indicate that the parent site has been updated
        parentSite.localTickets.add(this);
    }

    @Override
    public UUID id() {
        return this.id;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public boolean resolved() {
        return this.resolved;
    }

    @Override
    public List<UUID> entryIds() {
        return localEntries.stream().map(entry -> entry.id).toList();
    }

    public List<LocalEntry> localEntryList() {
        return this.localEntries;
    }

    public void indicateUpdated(){
        this.updated = true;
    }

}
