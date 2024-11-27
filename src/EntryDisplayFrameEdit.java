import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

public class EntryDisplayFrameEdit {
    // Control variables
    public UUID ticketId;
    public UUID siteId;
    public EntryDisplayPanel entryDisplayPanel;
    public TicketPanel parentTicketPanel;
    public Controller controller;

    // Visual subunits
    public JFrame frame = new JFrame();
    public JPanel panel; // Will be taken from entryDisplayPanel
    public JPanel buttonPanel = new JPanel(new BorderLayout()); //
    public JPanel headerPanel = new JPanel(new BorderLayout());
    //Buttons
    public JButton exportButton = new JButton("EXPORT");
    public JButton newEntryButton = new JButton("ADD ENTRY");

    public EntryDisplayFrameEdit(EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, UUID siteId, Controller controller){
        this.entryDisplayPanel = entryDisplayPanel;
        this.ticketId = entryDisplayPanel.ticketId;
        this.siteId = siteId;
        this.parentTicketPanel = parentTicketPanel;

        // Set up content panel
        this.panel = entryDisplayPanel.mainPanel();

        // Format frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Entries for LocalTicket No. " + this.ticketId);
        frame.setSize(800, 500);
        // Add EntryDisplayPanelContent
        frame.add(panel, BorderLayout.CENTER);

        // Set up export button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(exportButton, BorderLayout.WEST);
        buttonPanel.add(newEntryButton, BorderLayout.EAST);

        // Set up action listeners
        exportButton.addActionListener(new ActionListener() {
            // TODO: Generate an export screen
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export button pressed");
            }
        });
        newEntryButton.addActionListener(new ActionListener() {
            // TODO: Generate a new entry screen
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Entry Button Pressed");
                // TODO: add functionality for different date settings
                // TODO: generate a new entry screen
                parentTicketPanel.updateEntries(LocalDate.now()); // Automatically assigns date to current date
            }
        });
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
