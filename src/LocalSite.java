import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

// The Local representation of Site-type objects accessivle within an Entry session
public class LocalSite implements Site{
    // Ticketing identifier
    final UUID id;

    // Site information
    String title;
    String state;
    String city;
    String description;
    String address;
    String zip;
    String phoneNumber;
    String emailAddress;
    ImageIcon imageIcon;

    // Control flags
    boolean updated = false; // Used to filter sites that have new tickets/entries

    // Child tickets associated with this Site
    ArrayList<Ticket> localTickets = new ArrayList<>();

    // Transferring GlobalSite data to a corresponding LocalSite
    LocalSite(GlobalSite globalSite) {
        this.id = globalSite.id;
        this.title = globalSite.title;
        this.state = globalSite.state;
        this.city = globalSite.city;
        this.description = globalSite.description;
        this.address = globalSite.address;
        this.zip = globalSite.zip;
        this.phoneNumber = globalSite.phoneNumber;
        this.emailAddress = globalSite.emailAddress;
        this.imageIcon = globalSite.imageIcon;

        // Iterate through the list of child Tickets and generate all corresponding LocalTickets
        for(Ticket globalTicket : globalSite.globalTickets) {
            LocalTicket downloadedTicket = new LocalTicket((GlobalTicket)globalTicket);
            localTickets.add(downloadedTicket);
        }

        // Add the new LocalSite to the local database
        LocalTicketingSystem.downloadedSites.add(this);
    }

    // Raised when a new Entry or Ticket is generated for this LocalSite
    public void indicateUpdated() {
        updated = true;
    }

    // Called when local data is uploaded to the global database within an ongoing Edit session
    public void reset(){
        this.updated = false;
    }

    // Interface compatible getters
    @Override
    public UUID id() {
        return id;
    }

    @Override
    public ArrayList<Ticket> tickets() {
        return localTickets;
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
        return this.imageIcon;
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
