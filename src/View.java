import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

public interface View {
    void closeAllOpenedFrames();

    void displayConnectionRestoredPopup(Controller controller);

    SiteSelectionFrame generateSiteSelectionFrameIntro(Controller controller);

    SiteSelectionFrame generateSiteSelectionFrameEdit(Controller controller);

    SiteInfoDisplayPanel generateSiteInfoDisplayPanel(UUID siteId, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro, Controller controller);

    void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, UUID id, String title, String state, String city, boolean isIntro, Controller controller);

    TicketPanel generateTicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller);

    void displaySiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller);

    void displaySiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller);
}
