import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocalTicket implements Ticket{
    final UUID id;

    String description;
    boolean resolved = false;
    boolean updated = false;

    // Special local variables
    ArrayList<Entry> localEntries = new ArrayList<>();
    boolean isNew;

    // For downloading globalTickets
    public LocalTicket(GlobalTicket globalTicket) {
        this.id = globalTicket.id();
        this.description = globalTicket.description();
        this.resolved = globalTicket.resolved();
        this.isNew = false;

        for (Entry globalEntry : globalTicket.entries()) {
            System.out.println(globalEntry.date());
            GlobalEntry savedEntry = (GlobalEntry) globalEntry;
            LocalEntry downloadedEntry = new LocalEntry(savedEntry);
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
        LocalSite parentSite = (LocalSite)LocalTicketingSystem.getSite(destinationSiteId);
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
        return localEntries.stream().map(entry -> entry.id()).toList();
    }

    @Override
    public ArrayList<Entry> entries() {
        return localEntries;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public boolean updated() {
        return false;
    }


    public void indicateUpdated(){
        this.updated = true;
    }

}
