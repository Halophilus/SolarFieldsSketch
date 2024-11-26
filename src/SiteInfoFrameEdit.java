import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class SiteInfoFrameEdit {
    public UUID siteId;
    public String title;
    public SiteInfoDisplayPanel siteInfoDisplayPanel;
    public Controller controller;
    public SiteSelectionFrame siteSelectionFrame;

    // Visual subunits
    // Structural elements
    public JFrame frame = new JFrame();
    public JPanel panel; // Will be taken from siteInfoDisplayPanel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JPanel headerPanel = new JPanel(new BorderLayout()); // Added for future formatting options
    // Buttons
    public JButton exportButton = new JButton("EXPORT");
    public JButton newTicketButton = new JButton("NEW TICKET");

    public SiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, Controller controller, SiteSelectionFrame siteSelectionFrame) {
        this.siteId = siteInfoDisplayPanel.siteId;
        this.title = siteInfoDisplayPanel.title;
        this.siteInfoDisplayPanel = siteInfoDisplayPanel;
        this.controller = controller;
        this.siteSelectionFrame = siteSelectionFrame;

        // Set up content panel
        panel = siteInfoDisplayPanel.mainPanel();

        // Format frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(this.title + " Stored Site Information");
        frame.setSize(800, 500);
        // Add SiteInfoDisplayContent
        frame.add(panel, BorderLayout.CENTER);

        // Set up buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST);
        buttonPanel.add(newTicketButton, BorderLayout.EAST);

        // Set up action listeners for the buttons

        exportButton.addActionListener(e -> {
            System.out.println("Export button pressed");
            // TODO: Set up an export screen/function
        });
        newTicketButton.addActionListener(e -> {
            System.out.println("New ticket button pressed");
            AddTicketScreen newAddTicketScreen = new AddTicketScreen(this.siteId, this, controller);
            newAddTicketScreen.setVisible(true);
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void updateDisplay(){

    }
}
