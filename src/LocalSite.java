import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

public class LocalSite {
    final UUID id;

    int counter;
    String title;

    // Display fields (may repeat)
    String state;
    String city;
    String description;
    String address;
    String zip;
    String phoneNumber;
    String emailAddress;

    // Swing components
    ImageIcon imageIcon;

    // Backup information
    boolean isNew;
    ArrayList<LocalTicket> localTickets = new ArrayList<>();

    LocalSite(Site globalSite) {
        this.id = globalSite.id;
        this.counter = globalSite.counter;
        this.title = globalSite.title;
        this.state = globalSite.state;
        this.city = globalSite.city;
        this.description = globalSite.description;
        this.address = globalSite.address;
        this.zip = globalSite.zip;
        this.phoneNumber = globalSite.phoneNumber;
        this.emailAddress = globalSite.emailAddress;
        this.imageIcon = globalSite.imageIcon;

        this.isNew = false;
        for(Ticket globalTicket : globalSite.tickets) {
            LocalTicket downloadedTicket = new LocalTicket(globalTicket);
            localTickets.add(downloadedTicket);
        }
        TicketingSystem.sites.add(this);
    }

    Site(UUID id) {
        this.id = id;

    }

    ArrayList<LocalTicket> tickets = new ArrayList<>();
}
