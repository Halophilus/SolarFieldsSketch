import javax.swing.*;
import java.awt.*;
import java.util.UUID;

// Displays all TicketPanel objects associated with a given Site within an Intro session
public class SiteInfoFrameIntro {
    // Ticketing identifier
    public UUID siteId;
    public Controller controller;

    // Site information
    public String title;

    // Contents
    public SiteInfoDisplayPanel siteInfoDisplayPanel;

    //Context
    public SiteSelectionFrame siteSelectionFrame;

    // Visual subunits
    public JFrame frame = new JFrame(); // Visible window frame
    public JPanel panel; // Taken from siteInfoDisplayPanel
    // Buttons
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton exportButton = new JButton("EXPORT");
    public JButton selectButton = new JButton("SELECT");

    public SiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, Controller controller, SiteSelectionFrame siteSelectionFrame) {
        this.siteInfoDisplayPanel = siteInfoDisplayPanel;
        // Pull Site info from the content panel
        this.siteId = siteInfoDisplayPanel.siteId;
        this.title = siteInfoDisplayPanel.title;
        // Connect it to the parent frame
        this.siteSelectionFrame = siteSelectionFrame;
        this.controller = controller;

        // Pull content from the SiteInfoDisplayPanel
        panel = siteInfoDisplayPanel.mainPanel();

        // Format frame and add the Site info content
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(STR."\{this.title} GlobalSite Information");
        frame.setSize(800, 500);
        frame.add(panel, BorderLayout.CENTER);

        // Set up button panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST); // On the left side
        buttonPanel.add(selectButton, BorderLayout.EAST); // On the right side

        // Create functionality for the buttons
        exportButton.addActionListener(e -> { // Opens a screen to export Site information
            System.out.println("Export button pressed");
            // TODO: Set up an export screen/function
        });
        selectButton.addActionListener(e -> { // Selects this Site in the original SiteSelectionFrame and closes this window
            System.out.println("Select button pressed");
            siteSelectionFrame.selectChildPanel(this.siteId);
            frame.dispose();
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Sets visibility of main content frame
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
