import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// The Global representation of Ticket-type objects that are accessible within Intro sessions
public class GlobalTicket implements Ticket {
    // Ticketing identifier
    final UUID id;

    // Testing parameters
    int counter;
    static int siteCounter = 0;

    // Default values for basic information fields
    String description;
    boolean resolved = false;
    ArrayList<Entry> entries = new ArrayList<>();

    // Constructor for generating dummy Ticket objects
    GlobalTicket() {
        this.id = UUID.randomUUID();
        this.counter = siteCounter++;
        this.description = STR."Description for ticket \{this.counter}";
    }

    // GlobalTickets generated through uploaded GlobalTickets
    GlobalTicket(LocalTicket uploadedTicket){
        this.id = uploadedTicket.id();
        this.description = uploadedTicket.description();
        this.resolved = false;
    }

    // Sets resolved flag for when new entries are added to a previously resolved ticket
    public void resolve(boolean resolve) { this.resolved = resolve;}

    // Adds child entries to the ticket while enforcing distinct entries
    public void addEntry(GlobalEntry newGlobalEntry) {
        if (!this.entryIds().contains(newGlobalEntry.id())) {entries.add(newGlobalEntry);}
    }

    // Interface-compatible getter methods
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
        return entries.stream().map(Entry::id).toList();
    }

    @Override
    public ArrayList<Entry> entries() {
        return entries;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public boolean updated() {
        return false;
    }


}

