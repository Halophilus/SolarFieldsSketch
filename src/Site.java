import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// Site model general accessors
public class Site {
    // ID fields (unique to instance)
    final UUID id;

    int counter;
    static int siteCounter = 0;
    String title = "";

    // Display fields (may repeat)
    String state = "";
    String city = "";
    String description = "";
    String address = "";
    String zip = "";
    String phoneNumber = "";
    String emailAddress = "";

    // Swing components
    ImageIcon imageIcon = new CustomImageIcon();

    ArrayList<Ticket> tickets = new ArrayList<>();

    Site() {
        this.id = UUID.randomUUID();
        this.counter = siteCounter++;

        this.title = "Title " + this.counter;
        this.state = "State " + this.counter;
        this.city = "City " + this.counter;
        this.description = "Description " + this.counter;
        this.address = "Address " + this.counter;
        this.zip = "Zip " + this.counter;
        this.phoneNumber = "Phone " + this.counter;
        this.emailAddress = "Email " + this.counter;

        TicketingSystem.sites.add(this);
    }

    Site(UUID id) {
        this.id = id;

    }

    List<UUID> ticketIds() {
        return tickets.stream().map(ticket -> ticket.id).toList();
    }
}
