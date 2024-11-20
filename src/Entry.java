import javax.swing.*;
import java.time.LocalDate;

// Entry model general accessors
public interface Entry {
    Ticket ticket();
    default Site site(){
        return this.ticket().site();
    }

    int id();
    LocalDate date();
    String description();
    boolean reviewed();

    ImageIcon imageIcon();

}
