import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

// The Global representation of Site-type objects that are accessible within Intro sessions
public class GlobalSite implements Site{
    // Ticketing identifier
    final UUID id;

    // Human-readable testing parameters
    int counter;
    static int siteCounter = 0;

    // Site information fields initialized to default values
    String title;
    String state;
    String city;
    String description;
    String address;
    String zip;
    String phoneNumber;
    String emailAddress;

    // Randomly generated default icon
    ImageIcon imageIcon = new CustomImageIcon();

    // ADT linkage to child GlobalTickets
    ArrayList<Ticket> globalTickets = new ArrayList<>();

    // Constructor for generating dummy Site objects
    // This client is not responsible for sourcing new GlobalSites so no custom constructor has been created
    GlobalSite() {
        this.id = UUID.randomUUID();
        this.counter = siteCounter++;

        // Human-readable identifying fields
        this.title = STR."Title \{this.counter}";
        this.state = STR."State \{this.counter}";
        this.city = STR."City \{this.counter}";
        this.description = STR."Description \{this.counter}";
        this.address = STR."Address \{this.counter}";
        this.zip = STR."Zip \{this.counter}";
        this.phoneNumber = STR."Phone \{this.counter}";
        this.emailAddress = STR."Email \{this.counter}";

        // Add the current entry to the Global database
        GlobalTicketingSystem.globalSites.add(this);
    }

    // Direct channel for adding GlobalTickets to the list of child Tickets
    public void addTicket(GlobalTicket newGlobalTicket) {
        globalTickets.add(newGlobalTicket);
    }

    @Override
    // Child Ticket objects associated with this GlobalSite
    public ArrayList<Ticket> tickets() { return globalTickets;}

    // Getter methods for each field to tie it to its parent interface
    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String state() {
        return this.state;
    }

    @Override
    public String city() {
        return this.city;
    }

    @Override
    public ImageIcon imageIcon() {
        return imageIcon;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public String address() {
        return this.address;
    }

    @Override
    public String zip() {
        return this.zip;
    }

    @Override
    public String phoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String emailAddress() {
        return this.emailAddress;
    }


}
