import java.util.ArrayList;
import java.util.UUID;

public class LocalTicket {
    final UUID id;

    String description;
    boolean resolved = false;


    // Special local variables
    ArrayList<LocalEntry> localEntries = new ArrayList<>();
    boolean isNew;

    public LocalTicket(Ticket ticket) {
        this.id = ticket.id;
        this.description = ticket.description;
        this.resolved = ticket.resolved;
        this.isNew = false;
    }

    public Ticket(String description)

}
