import javax.swing.*;
import java.util.Set;

// Site model general accessors
public interface Site {
    // ID fields (unique to instance)
    String title();
    int id();

    // Display fields (may repeat)
    String state();
    String city();
    String description();
    String address();
    String zip();
    String phoneNumber();
    String emailAddress();

    // Swing components
    ImageIcon imageIcon();

    // Branching to related components
    Set<Integer> ticketIDs();

    static Site getSite(int id) {
        return null;
    }

    // Testing methods, may be refactored
    void addTicket(int id);
}
