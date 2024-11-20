import java.util.*;

public class TicketImpl implements Ticket{
    private final int id;
    private final Site site;
    boolean resolved;
    private final ArrayList<Integer> entries = new ArrayList<>();

    // Association between ID and Site
    // Simulation of database behavior
    private static int idTracker = 0;

    private static final Map<Integer, Ticket> idMap = new HashMap<Integer, Ticket>();

    public TicketImpl(int siteId){
        id = idTracker++;
        site = SiteImpl.getSite(siteId);

        idMap.put(id, this); // Make the index searchable
        site.addTicket(id);
    }

    @Override
    public Site site() {
        return this.site;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public String description() {
        return "Description for ticket " + this.id();
    }

    @Override
    public boolean resolved() {
        return resolved;
    }

    @Override
    public ArrayList<Integer> entryIDs() {
        return entries;
    }

    public static Ticket getTicket(int id) {
        return idMap.get(id);
    }

    @Override
    public void addEntry(int id) {
        entries.add(id);
    }
}
