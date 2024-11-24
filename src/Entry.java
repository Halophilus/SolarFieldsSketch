import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

public interface Entry {
    UUID id();
    String description();
    LocalDate date();
    boolean reviewed();
    ImageIcon icon();

    boolean isNew();
}
