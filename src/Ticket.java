import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// A general interface for simplifying operations on Local and Global Ticket objects
public interface Ticket {
    // State variables
    UUID id();
    String description();
    boolean resolved();

    List<UUID> entryIds(); // All associated entry IDs
    ArrayList<Entry> entries(); // All associated entry objects

    boolean isNew(); // Has been generated in the edit section prior to last upload
    boolean updated(); // Has had new entries generated for it since last upload

}
