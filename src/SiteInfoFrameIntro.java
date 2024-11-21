import javax.swing.*;
import java.awt.*;
import java.util.UUID;

// Displays all ticket entries associated with
public class SiteInfoFrameIntro {
    public UUID siteId;
    public String title;
    public SiteInfoDisplayPanel siteInfoDisplayPanel;
    public Controller controller;

    // Visual subunits
    // Structural elements
    public JFrame frame = new JFrame();
    public JPanel panel; // Will be taken from siteInfoDisplayPanel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JPanel headerPanel = new JPanel(new BorderLayout()); // Added for future formatting options
    // Buttons
    public JButton exportButton = new JButton("EXPORT");
    public JButton selectButton = new JButton("SELECT");

    public SiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, Controller controller) {
        this.siteId = siteInfoDisplayPanel.siteId;
        this.title = siteInfoDisplayPanel.title;
        this.siteInfoDisplayPanel = siteInfoDisplayPanel;
        this.controller = controller;

        // Set up content panel
        panel = siteInfoDisplayPanel.mainPanel();

        // Format frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(this.title + " Site Information");
        frame.setSize(800, 500);
        // Add SiteInfoDisplayContent
        frame.add(panel, BorderLayout.CENTER);

        // Set up buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST);
        buttonPanel.add(selectButton, BorderLayout.EAST);

        // Set up action listeners for the buttons
        // TODO: Set up action listener that interacts with the controller to perform some action
        exportButton.addActionListener(e -> {
            System.out.println("Export button pressed");
        });
        selectButton.addActionListener(e -> {
            System.out.println("Select button pressed");
        });

    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
