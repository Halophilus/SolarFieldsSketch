import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// The Local representation of Ticket-type objects accessible within an Edit session
public class LocalTicket implements Ticket{
    // Ticketing identifier
    final UUID id;

    // Ticket information
    String description;

    // Control flags
    boolean resolved;
    boolean updated = false;

    // List of child Entries
    ArrayList<Entry> localEntries = new ArrayList<>();
    boolean isNew; // Raised for user-generated Tickets

    // Transferring GlobalTicket data into a corresponding LocalTicket
    public LocalTicket(GlobalTicket globalTicket) {
        this.id = globalTicket.id();
        this.description = globalTicket.description();
        this.resolved = globalTicket.resolved();
        this.isNew = false;
        for (Entry globalEntry : globalTicket.entries()) {
            GlobalEntry savedEntry = (GlobalEntry) globalEntry;
            LocalEntry downloadedEntry = new LocalEntry(savedEntry);
            localEntries.add(downloadedEntry);
        }
    }

    // Constructor for user-generated LocalTicket objects
    public LocalTicket(UUID uuid, String description, UUID destinationSiteId) {
        this.id = uuid;
        this.description = description;
        this.resolved = false;
        this.isNew = true;

        // Diagnostic print statements
        System.out.println(STR."New ticket ID: \{this.id()}");
        System.out.println(STR."New ticket generated with description: \{this.description}");

        // Invoke the parent site from its UUID
        LocalSite parentSite = (LocalSite)LocalTicketingSystem.getSite(destinationSiteId);
        assert parentSite != null;

        // Look right into the camera this time
        System.out.println(STR."Site title: \{parentSite.title()}");
        System.out.println(STR."Current list of local tickets: \{parentSite.localTickets}");

        // Indicate that the parent site has been updated
        parentSite.indicateUpdated(); // Indicate that the parent site has been updated
        parentSite.localTickets.add(this); // Add it to the site's list of tickets

        // Verify that the site's list of tickets has been updated
        System.out.println(STR."Updated list of local tickets: \{parentSite.localTickets}");
        System.out.println(LocalTicketingSystem.getTicket(this.id));
    }

    // Called when a previously resolved ticket acquires a new entry
    public void unresolve(){
        this.resolved = false;
    }

    // Called when a new entry is generated for an existing ticket
    public void indicateUpdated(){
        this.updated = true;
    }

    // Called when a LocalTicket is uploaded to the global database in an ongoing Edit session
    public void reset(){
        this.isNew = false;
        this.updated = false;
    }

    // Interface compatible getters
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
        return this.updated;
    }

    @Override
    public String toString(){
        return STR."Ticket ID: \{this.id()}\nTicket Description: \{this.description()}\n";
    }

}
