import java.util.UUID;

public interface Controller {
    // Site Info Screen Operations
    // Intro section operations on Sites
    void openSiteInfoScreenFromIntroSection(UUID siteID);
    void exportSiteWindowFromIntroSiteInfoScreen(UUID siteID);
    void selectSiteFromIntroSiteInfoScreenAndCloseInfoScreen(UUID siteID);
    // Intro section operations on Tickets
    void openTicketInfoScreenFromIntroSiteInfoScreen(UUID TicketID);
    // Intro section operations on Entries
    void exportTicketInfoFromIntroSiteInfoScreen(UUID siteID);

    // Edit Section operations
    // Open Site Info Window
    void openSiteInfoWindowFromEditSection(UUID siteID);
    // Open Ticket Info Window
    void openTicketInfoWindowFromEditSection(UUID ticketID);
    // Adding a new ticket
    void openAddTicketScreenFromEditSection(UUID siteID);
    // Adding a new entry
    void openAddEntryToTicketScreenFromEditSection(UUID siteID);

    // Methods I'm actually using through this interface
    public void displayEntryDisplayFrameIntro(UUID ticketId);
    public void displaySiteInfoFrameIntro(UUID siteId);
}
