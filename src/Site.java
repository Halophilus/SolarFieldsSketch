import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface Site {
    UUID id();
    ArrayList<Ticket> tickets();

    String title();

    String state();

    String city();

    ImageIcon imageIcon();

    String description();

    String address();

    String zip();

    String phoneNumber();

    String emailAddress();

}
