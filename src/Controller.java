import javax.swing.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface Controller {
    // GlobalSite Info Screen Operations
    // Intro section operations on Sites

    // Internet connectivity behavior
    boolean checkOnlineStatus();
    void displayConnectionRestoredPopup();

    // Model control
    void uploadLocalEntries();
    void markLocalStorageAsUploaded();

    // Site selection screen
    void displaySiteSelectionFrameIntro();
    void displaySiteSelectionFrameEdit(Set<UUID> selectedSiteIds);

    // Site info / ticket display screen
    void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame);
    void displaySiteInfoFrameEdit(UUID siteId, SiteSelectionFrame parentFrame);
    TicketPanel makeTicketPanelFromId(UUID ticketId, boolean b); // Used as both a helper method and directly for when new tickets are generated

    // Edit section ticket creation
    void displayAddTicketScreen(UUID siteId, SiteInfoFrameEdit siteInfoFrameEdit, Controller controller);

    // Clears all locally stored sites
    void clearLocalStorage();

    void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId);

    // Ticket info / entry display screen
    void displayEntryDisplayFrameIntro(UUID ticketId);
    void displayEntryDisplayFrameEdit(UUID ticketId, TicketPanel ticketPanel);

    // Edit section entry creation
    void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel);
    void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId);

    // JSON export functionality
    void displayExportScreenForSelectLocations(Set<UUID> selectedSites);

    // View control
    void closeAllOpenedFrames();
}
