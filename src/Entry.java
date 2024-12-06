import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;
// A general interface for simplifying symmetrical operations on local and global entry objects
public interface Entry {
    // State variables
    UUID id();
    String description();
    LocalDate date();
    boolean reviewed();
    ImageIcon icon();

    boolean isNew(); // Flag indicating if it was generated since the last upload operation
}
