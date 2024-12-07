import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;
// Displays the JPanel that contains the set of Entry objects associated with a LocalTicket and connects to the AddEntryScreen within an Edit session
public class EntryDisplayFrameEdit {
    // Identifying variables
    public UUID ticketId;
    public UUID siteId;

    // Context variables
    public EntryDisplayPanel entryDisplayPanel;
    public TicketPanel parentTicketPanel;

    // Connection to controller
    public Controller controller;

    // Visual subunits
    public JFrame frame = new JFrame(); // Outer frame
    public JPanel panel; // Will be taken from entryDisplayPanel

    // Buttons for exporting Ticket information or generating and adding a new LocalEntry
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton exportButton = new JButton("EXPORT");
    public JButton newEntryButton = new JButton("ADD ENTRY");

    // Contains references to the parent EntryDisplayPanel and TicketPanel so that they can be immediately updated to reflect any changes to the Ticket
    public EntryDisplayFrameEdit(EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, UUID siteId, Controller controller){
        // Define identifying variables
        this.ticketId = entryDisplayPanel.ticketId;
        this.siteId = siteId;

        // Define context within the GUI
        this.parentTicketPanel = parentTicketPanel;
        this.entryDisplayPanel = entryDisplayPanel;

        // Pull the main content panel
        this.panel = entryDisplayPanel.mainPanel();

        // Format frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Entries for LocalTicket No. " + this.ticketId);
        frame.setSize(800, 500);
        // Add EntryDisplayPanelContent to center of the frame
        frame.add(panel, BorderLayout.CENTER);

        // Format button panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST); // Appears on the left side of the screen
        buttonPanel.add(newEntryButton, BorderLayout.EAST); // Appears on the right

        // Set up action listeners for the visible buttons
        EntryDisplayFrameEdit thisAlias = this;
        exportButton.addActionListener(new ActionListener() { // Opens a screen to export data
            // TODO: Make an export screen
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export button pressed");
            }
        });
        newEntryButton.addActionListener(new ActionListener() { // Opens an AddEntryScreen
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Entry Button Pressed");
                // TODO: add functionality for different date settings
                controller.displayAddEntryScreen(thisAlias, entryDisplayPanel, parentTicketPanel);
            }
        });

        // Add the button panel to the bottom of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Sets visibility for
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
