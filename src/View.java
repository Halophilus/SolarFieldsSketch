import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

// An interface for generating display items for Solar Fields
public interface View {
    void closeAllOpenedFrames();

    void displayConnectionRestoredPopup(Controller controller);

    // Site selection screen
    SiteSelectionFrame generateSiteSelectionFrameIntro(Controller controller);
    SiteSelectionFrame generateSiteSelectionFrameEdit(Controller controller);
    void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, UUID id, String title, String state, String city, boolean isIntro, Controller controller);

    // Site info / ticket display screen
    SiteInfoDisplayPanel generateSiteInfoDisplayPanel(UUID siteId, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro, Controller controller);
    void displaySiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller);
    void displaySiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller);
    void displayAddTicketScreen(UUID siteId, SiteInfoFrameEdit siteInfoFrameEdit, Controller controller);

    TicketPanel generateTicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller);

    // Ticket info / entry display screen
    EntryDisplayPanel generateEntryDisplayPanelIntro(UUID ticketId, String description, boolean resolved, boolean isIntro);
    void displayEntryDisplayFrameIntro(EntryDisplayPanel entryDisplayPanel, Controller controller);
    void displayEntryDisplayFrameEdit(EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, UUID siteId, Controller controller);
    void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, Controller controller);
    EntryPanel generateEntryPanel(UUID entryId, LocalDate datePosted, String description, ImageIcon image, boolean reviewed, boolean isIntro);

}
