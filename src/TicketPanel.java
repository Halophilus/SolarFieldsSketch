import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TicketPanel implements Comparable<TicketPanel> {
    // Determines whether the frame is from intro or edit section
    boolean isIntro;

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


    public TicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller c) {
        this.ticketId = ticketId;
        this.mostRecentEditDate = mostRecentEditDate;
        this.numEntries = numEntries;
        this.resolved = resolved;
        this.isIntro = isIntro;

        // Get site from ID
        // site = SiteImpl.getSite(id);

        // root panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ID panel
        // Stores all identifying info about a ticket
        idPanel = new JPanel();
        //idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); //; align left, horizontal gap of 5 pixels

        // The ticket number
        idLabel = new JLabel("#" + this.ticketId);
        //idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        idLabel.setForeground(Color.BLUE);

        // The divider between them
        dividerLabel = new JLabel("|");
        //dividerLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // The number of entries
        entriesLabel = new JLabel("No. Entries: " + this.numEntries);
        //entriesLabel.setFont(new Font("Arial", Font.ITALIC, ));

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

        //panel.setPreferredSize(new Dimension(100, idPanel.getHeight()));
        panel.add(idPanel, BorderLayout.WEST);
        panel.add(dateLabel, BorderLayout.EAST);

        // Action that opens up a new ticket window when clicked on
        idPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                c
            }
        })



    }

    public JPanel mainPanel() {
        return panel;
    }

    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        panel.setBackground(color);
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
