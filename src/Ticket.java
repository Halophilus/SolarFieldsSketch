import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Ticket model general accessors
public class Ticket {
    final UUID id;

    int counter;
    static int siteCounter = 0;
    String description = "";
    boolean resolved = false;
    ArrayList<Entry> entries = new ArrayList<>();

    Ticket() {
        this.id = UUID.randomUUID();
        this.counter = siteCounter++;

        this.description = "Description for ticket " + this.counter;
    }

    Ticket(LocalTicket uploadedTicket){
        this.id = uploadedTicket.id;
        this.description = uploadedTicket.description;
        
    }

    List<UUID> entryIds() {
        return entries.stream().map(entry -> entry.id).toList();
    }
}

