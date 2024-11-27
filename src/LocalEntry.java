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
        parentTicket.entries().add(this);

        // Indicate that the parent site has been updated
        assert LocalTicketingSystem.getSite(parentSiteId) == null;
        LocalSite parentSite = (LocalSite)LocalTicketingSystem.getSite(parentSiteId);
        parentSite.indicateUpdated();
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
        return false;
    }
}
