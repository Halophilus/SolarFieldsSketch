import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

// Displays the JPanel that contains the set of Entry objects associated with a given GlobalTicket within an Intro session
public class EntryDisplayFrameIntro {
    // Identifying variables
    public UUID ticketID;
    // Context variables
    public EntryDisplayPanel entryDisplayPanel;
    // Connection to Controller
    public Controller controller;

    // Visual subunits
    // Structural elements
    public JFrame frame = new JFrame();
    public JPanel panel; // Will be taken from EntryDisplayPanel
    public JPanel buttonPanel = new JPanel(new BorderLayout());

    // Buttons for exporting GlobalTicket data
    public JButton exportButton = new JButton("EXPORT");

    // Contains the EntryDisplayPanel which itself contains all TicketPanels relevant to the session
    public EntryDisplayFrameIntro(EntryDisplayPanel entryDisplayPanel, Controller controller) {
        // Pull ID from the child JPanel
        this.ticketID = entryDisplayPanel.ticketId;

        this.entryDisplayPanel = entryDisplayPanel;
        this.controller = controller;

        // Substitute main panel for panel from provided EntryDisplayPanel
        panel = entryDisplayPanel.mainPanel();

        // Format visible components of frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Entries for GlobalTicket No. " + this.ticketID);
        frame.setSize(800, 500);
        // Insert main panel into the center of the visible frame
        frame.add(panel, BorderLayout.CENTER);

        // Set up export button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST);

        // TODO: Make an export screen
        exportButton.addActionListener(new ActionListener() { // Opens an export screen for the related Ticket
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export button pressed");
            }
        });

        // Add the button panel to the bottom of the screen
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Sets the visibility for the main Jframe
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
