import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

public class LocalEntry implements Entry{
    final UUID id;

    LocalDate date;
    String description;
    boolean reviewed;
    ImageIcon icon;
    boolean isNew;

    // For downloaded entries
    LocalEntry(GlobalEntry downloadedEntry){
        this.id = downloadedEntry.id();
        this.date = downloadedEntry.date();
        this.icon = downloadedEntry.icon();
        this.description = downloadedEntry.description();
        this.reviewed = downloadedEntry.reviewed();
        this.isNew = false;
    }

    // For new entries
    LocalEntry(LocalDate date, String description, ImageIcon icon, UUID parentTicketId, UUID parentSiteId, UUID id){
        System.out.println("Generating new LocalEntry " + id.toString());
        System.out.println("Description: " + description);
        this.id = id;
        this.date = date;
        this.description = description;
        this.icon = icon;
        this.reviewed = false;
        this.isNew = true;

        // Indicate parent ticket has been updated and add new entry to the list
        assert LocalTicketingSystem.getTicket(parentTicketId) == null;
        LocalTicket parentTicket = (LocalTicket)LocalTicketingSystem.getTicket(parentTicketId);
        parentTicket.indicateUpdated();
        parentTicket.unresolve();
        parentTicket.entries().add(this);
        System.out.println("Parent ticket: " + parentTicketId );
        System.out.println("PT description: " + parentTicket.description());
        System.out.println("Res. Upd. New: " + parentTicket.resolved() + " " + parentTicket.updated() + " " + parentTicket.isNew());

        // Indicate that the parent site has been updated
        assert LocalTicketingSystem.getSite(parentSiteId) == null;
        LocalSite parentSite = (LocalSite)LocalTicketingSystem.getSite(parentSiteId);
        parentSite.indicateUpdated();
        System.out.println("Parent site: " + parentSite.title());
        System.out.println("Upd. " + parentSite.updated);
    }


    // Shared interface methods
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

    public void reset(){
        isNew = false;
    }

    @Override
    public String toString(){
        return "Ticket ID: " + this.id() + "\nDescription: " + this.description() + "\nisNew: " + this.isNew();
    }
}
