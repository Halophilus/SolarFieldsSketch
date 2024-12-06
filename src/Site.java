import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

// A general interface for simplifying symettrical operations on Local and Global Sites
public interface Site {
    // State variables
    UUID id();
    String title();
    String state();
    String city();
    ImageIcon imageIcon();
    String description();
    String address();
    String zip();
    String phoneNumber();
    String emailAddress();

    // Hierarchy variables
    ArrayList<Ticket> tickets(); // Stores all child Ticket objects associated with the site
}
