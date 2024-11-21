import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TicketPanel implements Comparable<TicketPanel> {
    // ID Info
    UUID ticketId;
    LocalDate mostRecentEditDate;
    int numEntries;
    boolean resolved;

    // Visual components
    // Panels
    public JPanel panel;
    public JPanel idPanel;

    // Labels
    public JLabel idLabel;
    public JLabel entriesLabel;
    public JLabel dividerLabel;
    public JLabel dateLabel;

    // Formatting
    private static int counter = 0;

    public TicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved) {
        this.ticketId = ticketId;
        this.mostRecentEditDate = mostRecentEditDate;
        this.numEntries = numEntries;
        this.resolved = resolved;

        // Get site from ID
        // site = SiteImpl.getSite(id);

        // root panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ID panel
        // Stores all identifying info about a ticket
        idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); //; align left, horizontal gap of 5 pixels

        // The ticket number
        idLabel = new JLabel("#" + this.ticketId);
        idLabel.setFont(new Font("Arial", Font.BOLD, 24));
        idLabel.setForeground(Color.BLUE);

        // The divider between them
        dividerLabel = new JLabel("|");
        dividerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // The number of entries
        entriesLabel = new JLabel("No. Entries: " + this.numEntries);
        entriesLabel.setFont(new Font("Arial", Font.ITALIC, 20));

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

        dateLabel = new JLabel("Last edited: " + mostRecentEditDate);

        panel.add(idPanel, BorderLayout.WEST);
        panel.add(dateLabel, BorderLayout.EAST);

        if (counter % 2 == 0) {
            idPanel.setBackground(Color.LIGHT_GRAY);
            panel.setBackground(Color.LIGHT_GRAY);
        }

        counter++;

    }

    public JPanel mainPanel() {
        return panel;
    }

    @Override
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
