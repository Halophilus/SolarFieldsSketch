import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

// Displays all the entries for a given ticket in an intro screen
public class EntryDisplayFrameIntro {
    public UUID ticketID;
    public EntryDisplayPanel entryDisplayPanel;

    // Visual subunits
    // Structural elements
    public JFrame frame = new JFrame();
    public JPanel panel; // Will be taken from EntryDisplayPanel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JPanel headerPanel = new JPanel(new BorderLayout()); // Added for future formatting options
    // Buttons
    public JButton exportButton = new JButton("EXPORT");

    public EntryDisplayFrameIntro(EntryDisplayPanel entryDisplayPanel) {
        this.ticketID = entryDisplayPanel.ticketId;
        this.entryDisplayPanel = entryDisplayPanel;

        // Set up content panel
        panel = entryDisplayPanel.mainPanel();

        // Format frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Entries for Ticket No. " + this.ticketID);
        frame.setSize(800, 500);
        // Add EntryDisplayPanelContent
        frame.add(panel, BorderLayout.CENTER);

        // Set up export button
        buttonPanel.add(exportButton, BorderLayout.EAST);

        // TODO: Set up action listener that interacts with the controller to perform an export action
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export button pressed");
            }
        });
    }

    // Makes the frame visible
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
