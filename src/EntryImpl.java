import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EntryImpl implements Entry {
    private int id;
    private ImageIcon image;
    private int ticketId;

    private static int nextId = 0;
    private static final Map<Integer, Entry> idMap = new HashMap<Integer, Entry>();

    public EntryImpl(int ticketId) {
        // Set up local instance
        this.ticketId = ticketId;
        this.id = nextId++;
        image = new CustomImageIcon();

        // Modify original ticket
        Ticket ticket = TicketImpl.getTicket(ticketId);
        ticket.addEntry(id);

        // Update database
        idMap.put(id, this);
    }

    @Override
    public Ticket ticket() {
        return TicketImpl.getTicket(ticketId);
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public LocalDate date() {
        return null;
    }

    @Override
    public String description() {
        return "Description for entry " + id();
    }

    @Override
    public boolean reviewed() {
        return false;
    }

    @Override
    public ImageIcon imageIcon() {
        return image;
    }

    public static Entry getEntry(int id) {
        return idMap.get(id);
    }
}
