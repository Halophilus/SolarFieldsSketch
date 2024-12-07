import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;
// A general interface for simplifying symmetrical operations on local and global Entry objects
public interface Entry {
    // Informational state variables
    UUID id();
    String description();
    LocalDate date();
    boolean reviewed();
    ImageIcon icon();

    // Flag indicating if it was generated since the last upload operation
    // Default false for GlobalEntry objects
    // Is true for any new LocalEntry objects generated during an Edit session
    boolean isNew();
}
