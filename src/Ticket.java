import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface Ticket {
    UUID id();
    String description();
    boolean resolved();
    List<UUID> entryIds();

    ArrayList<Entry> entries();


    boolean isNew();

    boolean updated();
}
