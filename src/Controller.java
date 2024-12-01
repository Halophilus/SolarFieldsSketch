import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface Controller {
    // GlobalSite Info Screen Operations
    // Intro section operations on Sites
    void openSiteInfoScreenFromIntroSection(UUID siteID);

    void exportSiteWindowFromIntroSiteInfoScreen(UUID siteID);

    void selectSiteFromIntroSiteInfoScreenAndCloseInfoScreen(UUID siteID);

    // Intro section operations on Tickets
    void openTicketInfoScreenFromIntroSiteInfoScreen(UUID TicketID);

    // Intro section operations on Entries
    void exportTicketInfoFromIntroSiteInfoScreen(UUID siteID);

    // Edit Section operations
    // Open GlobalSite Info Window
    void openSiteInfoWindowFromEditSection(UUID siteID);

    // Open GlobalTicket Info Window
    void openTicketInfoWindowFromEditSection(UUID ticketID);

    // Adding a new ticket
    void openAddTicketScreenFromEditSection(UUID siteID);

    // Adding a new entry
    void openAddEntryToTicketScreenFromEditSection(UUID siteID);

    // Methods I'm actually using through this interface
    void displayEntryDisplayFrameIntro(UUID ticketId);

    void displaySiteInfoFrameIntro(UUID siteId, SiteSelectionFrame siteSelectionFrame);

    void displaySiteSelectionFrameEdit(Set<UUID> selectedSiteIds);

    void displayExportScreenForSelectLocations(Set<UUID> selectedSites);

    void displaySiteInfoFrameEdit(UUID siteId, SiteSelectionFrame parentFrame);

    void displayEntryDisplayFrameEdit(UUID ticketId, TicketPanel ticketPanel);

    void generateLocalTicket(UUID ticketId, String ticketDescriptionInput, UUID siteId);

    TicketPanel makeTicketPanelFromId(UUID ticketId, boolean b);

    void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel);

    void generateLocalEntry(LocalDate date, String description, ImageIcon icon, UUID ticketId, UUID siteId, UUID entryId);

    boolean checkOnlineStatus();

    public void uploadLocalEntries();

    public void clearLocalStorage();


    EntryPanel makeEntryPanelFromId(UUID entryId, boolean b);

    void markLocalStorageAsUploaded();

    void closeAllOpenedFrames();

    void displayConnectionRestoredPopup();

    void displaySiteSelectionFrameIntro();
}
