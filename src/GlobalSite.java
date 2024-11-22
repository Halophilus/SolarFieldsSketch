import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// GlobalSite model general accessors
public class GlobalSite {
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

    ArrayList<GlobalTicket> globalTickets = new ArrayList<>();

    GlobalSite() {
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

        GlobalTicketingSystem.globalSites.add(this);
    }

    GlobalSite(UUID id) {
        this.id = id;

    }

    UUID id() {
        return id;
    }

    List<UUID> ticketIds() {
        return globalTickets.stream().map(ticket -> ticket.id).toList();
    }

    public void addTicket(GlobalTicket newGlobalTicket) {
        globalTickets.add(newGlobalTicket);
    }
}
