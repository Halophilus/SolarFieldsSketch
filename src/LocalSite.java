import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocalSite implements Site{
    final UUID id;

    String title;
    boolean updated = false; // Used to filter sites that have new tickets/entries

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
    ArrayList<Ticket> localTickets = new ArrayList<>();

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

        for(Ticket globalTicket : globalSite.globalTickets) {
            LocalTicket downloadedTicket = new LocalTicket((GlobalTicket)globalTicket);
            localTickets.add(downloadedTicket);
        }

        LocalTicketingSystem.downloadedSites.add(this);
    }

    // Gets associated ticket IDs for a downloaded site
    public List<UUID> ticketIds() {
        return localTickets.stream().map(ticket -> ticket.id()).toList();
    }

    public void indicateUpdated() {
        updated = true;
    }


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
