import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.UUID;

// A display panel for storing Ticket information at a glance for SiteInfoFrames in both sessions of the app
public class TicketPanel implements Comparable<TicketPanel> {
    boolean isIntro;
    Controller controller;

    // Ticketing identifier
    UUID ticketId;

    // Visible information
    LocalDate mostRecentEditDate;
    int numEntries;
    boolean resolved;

    // Swing components
    public JPanel panel;
    public JPanel idPanel;
    public JLabel idLabel;
    public JLabel entriesLabel;
    public JLabel dividerLabel;
    public JLabel dateLabel = new JLabel();

    // Only displays site ID, number of active tickets, and the date of the most recent ticket
    public TicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller) {
        this.ticketId = ticketId;
        this.mostRecentEditDate = mostRecentEditDate;
        this.numEntries = numEntries;
        this.resolved = resolved;
        this.isIntro = isIntro;
        this.controller = controller;

        // Content panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ID panel
        // Stores all identifying info about a ticket
        idPanel = new JPanel();
        //idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); //; align left, horizontal gap of 5 pixels

        // The ticket UUID
        idLabel = new JLabel(STR."#\{this.ticketId}");
        idLabel.setForeground(Color.BLUE);

        // The divider between them
        dividerLabel = new JLabel("|");

        // The number of Entry objects associated with it
        entriesLabel = new JLabel(STR."No. Entries: \{this.numEntries}");

        // Font color as a visual indicator of unresolved issues
        if (!resolved){ // If the issues are unresolved
            entriesLabel.setForeground(Color.RED);
        } else{
            entriesLabel.setForeground(Color.DARK_GRAY);

        }

        // Adding the labels to the idPanel
        idPanel.add(idLabel);
        idPanel.add(dividerLabel);
        idPanel.add(entriesLabel);

        // If there is an associated Entry, display the most recent date of Entry
        if (mostRecentEditDate != LocalDate.MAX) {dateLabel.setText(STR."Last edited: \{this.mostRecentEditDate}");}

        panel.add(idPanel, BorderLayout.WEST);
        panel.add(dateLabel, BorderLayout.EAST);

        // Set up an alias for _this_ that can be passed into a lambda function
        TicketPanel parentPanel = this;
        // Action that opens up a new ticket window when clicked on
        idPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(STR."Mouse clicked on TicketPanel \{ticketId}");
                System.out.println(STR."IsIntro: \{isIntro}");
                // Varied behavior depending on session type
                if (isIntro) {
                    controller.displayEntryDisplayFrameIntro(ticketId);
                } else {
                    controller.displayEntryDisplayFrameEdit(ticketId, parentPanel);// this points to a MouseAdapter
                }
            }
        });
    }

    // Returns main content panel
    public JPanel mainPanel() {
        return panel;
    }

    // Used for displaying contrast between adjacent TicketPanels
    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        panel.setBackground(color);
    }

    // Method for refreshing the ticket panel when a new Entry is added to the Ticket
    public void updateEntries(LocalDate newDate){
        // Update state variables
        this.numEntries++;
        this.mostRecentEditDate = newDate;
        this.resolved = false;
        // Update labels
        this.entriesLabel.setText(STR."No. Entries: \{this.numEntries}");
        this.dateLabel.setText(STR."Last edited: \{this.mostRecentEditDate}");
        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }

    @Override
    // Used for sorting TicketPanels by their date of entry
    public int compareTo(TicketPanel otherTicket) {
        if (this.mostRecentEditDate.isBefore(otherTicket.mostRecentEditDate)){
            return 1; // If the current date is before the provided date
        } else if (this.mostRecentEditDate.isAfter(otherTicket.mostRecentEditDate)){
            return -1;
        } else {
            return 0;
        }
    }
}
