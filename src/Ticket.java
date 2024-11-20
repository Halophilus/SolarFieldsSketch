import java.util.ArrayList;

// Ticket model general accessors
public interface Ticket {
    Site site();
    int id();

    String description();
    boolean resolved();

    ArrayList<Integer> entryIDs();
    static Ticket getTicket(int id){
        return null;
    };

    void addEntry(int id);

}
