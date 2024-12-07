import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

// The Local representation of Entry-type objects accessible within Edit sessions
public class LocalEntry implements Entry{
    // Ticketing identifier
    final UUID id;

    // Entry information
    LocalDate date;
    String description;
    ImageIcon icon;

    // Control flags
    boolean reviewed;
    boolean isNew; // Raised for user-generated entries

    // Transferring GlobalEntry data into a corresponding LocalEntry
    LocalEntry(GlobalEntry downloadedEntry){
        this.id = downloadedEntry.id();
        this.date = downloadedEntry.date();
        this.icon = downloadedEntry.icon();
        this.description = downloadedEntry.description();
        this.reviewed = downloadedEntry.reviewed();
        this.isNew = false;
    }

    // Constructor for new user-generated LocalEntry objects
    LocalEntry(LocalDate date, String description, ImageIcon icon, UUID parentTicketId, UUID parentSiteId, UUID id){
        // Diagnostic print statements
        System.out.println(STR."Generating new LocalEntry \{id.toString()}");
        System.out.println(STR."Description: \{description}");

        this.id = id;
        this.date = date;
        this.description = description;
        this.icon = icon;
        this.reviewed = false;
        this.isNew = true;

        // Invoke the parent Ticket using the LocalTicketingSystem
        LocalTicket parentTicket = (LocalTicket)LocalTicketingSystem.getTicket(parentTicketId);
        // Assert that the parent Ticket exists
        assert parentTicket != null;

        // Indicate that they have been updated within the local database
        parentTicket.indicateUpdated();
        parentTicket.unresolve();

        // Add the new entry to the parent Ticket's list of entries
        parentTicket.entries().add(this);

        // Now say it for the camera, please
        System.out.println(STR."Parent ticket: \{parentTicketId}");
        System.out.println(STR."PT description: \{parentTicket.description()}");
        System.out.println(STR."Res. Upd. New: \{parentTicket.resolved()} \{parentTicket.updated()} \{parentTicket.isNew()}");

        // Invoke the parent Site from its UUID
        LocalSite parentSite = (LocalSite)LocalTicketingSystem.getSite(parentSiteId);
        // Assert that the parent site exists
        assert parentSite != null;

        // Indicate that the parent site has been updated
        parentSite.indicateUpdated();
        System.out.println(STR."Parent site: \{parentSite.title()}");
        System.out.println(STR."Upd. \{parentSite.updated}");
    }

    // Lowers isNew flag when the Entry is uploaded to the global database without exiting the Entry session
    public void reset(){
        isNew = false;
    }

    // Interface compatible getters
    @Override
    public UUID id() {
        return this.id;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public LocalDate date() {
        return this.date;
    }

    @Override
    public boolean reviewed() {
        return this.reviewed;
    }

    @Override
    public ImageIcon icon() {
        return this.icon;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    // Diagnostic toString
    public String toString(){
        return STR."Ticket ID: \{this.id()}\nDescription: \{this.description()}\nisNew: \{this.isNew()}";
    }
}
