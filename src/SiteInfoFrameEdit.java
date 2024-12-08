import javax.swing.*;
import java.awt.*;
import java.util.UUID;

// A JFrame for displaying relevant Site information in an Edit Session
public class SiteInfoFrameEdit {
    // Ticketing identifier
    public UUID siteId;

    // Context
    public SiteSelectionFrame siteSelectionFrame;
    // Contents
    public SiteInfoDisplayPanel siteInfoDisplayPanel;
    // Site information
    public String title;

    // Connection to Controller
    public Controller controller;

    // Visual subunits
    public JFrame frame = new JFrame(); // Outer frame
    public JPanel panel; // Taken from siteInfoDisplayPanel
    // Action Buttons
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton exportButton = new JButton("EXPORT");
    public JButton newTicketButton = new JButton("NEW TICKET");

    // Generated from an existing SiteInfoDisplayPanel
    public SiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, Controller controller, SiteSelectionFrame siteSelectionFrame) {
        this.siteInfoDisplayPanel = siteInfoDisplayPanel;
        // Information pulled from the panel
        this.siteId = siteInfoDisplayPanel.siteId;
        this.title = siteInfoDisplayPanel.title;
        // Parent frame
        this.siteSelectionFrame = siteSelectionFrame;

        this.controller = controller;

        // Pull contents
        panel = siteInfoDisplayPanel.mainPanel();

        // Format frame and add the content panel
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(STR."\{this.title} Stored Site Information");
        frame.setSize(800, 500)
        frame.add(panel, BorderLayout.CENTER);

        // Set up buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST);
        buttonPanel.add(newTicketButton, BorderLayout.EAST);

        // Create functionality for the buttons
        exportButton.addActionListener(e -> { // Open an export screen
            System.out.println("Export button pressed");
            // TODO: Set up an export screen/function
        });
        newTicketButton.addActionListener(e -> { // Open a screen to generate a new Ticket for this Site
            System.out.println("New ticket button pressed");
            controller.displayAddTicketScreen(this.siteId, this, controller);
        });

        // Add the panel to the bottom of the frame
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Sets visibility for the frame
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
