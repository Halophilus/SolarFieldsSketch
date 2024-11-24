import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// GlobalTicket model general accessors
public class GlobalTicket implements Ticket {
    final UUID id;

    int counter;
    static int siteCounter = 0;
    String description = "";
    boolean resolved = false;
    ArrayList<Entry> entries = new ArrayList<>();

    GlobalTicket() {
        this.id = UUID.randomUUID();
        this.counter = siteCounter++;

        this.description = "Description for ticket " + this.counter;
    }

    // Creating a new global ticket from an uploaded one
    GlobalTicket(LocalTicket uploadedTicket){
        this.id = uploadedTicket.id();
        this.description = uploadedTicket.description();
        this.resolved = false;
    }

    // InterfaceMethods
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
        return entries.stream().map(entry -> entry.id()).toList();
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


    public void addEntry(GlobalEntry newGlobalEntry) {
        entries.add(newGlobalEntry);
    }
}

