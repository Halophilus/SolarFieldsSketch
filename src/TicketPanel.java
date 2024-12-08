import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.UUID;

public class TicketPanel implements Comparable<TicketPanel> {
    // Determines whether the frame is from intro or edit section
    boolean isIntro;
    Controller controller;

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
    public JLabel dateLabel = new JLabel();


    public TicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller) {
        this.ticketId = ticketId;
        this.mostRecentEditDate = mostRecentEditDate;
        this.numEntries = numEntries;
        this.resolved = resolved;
        this.isIntro = isIntro;
        this.controller = controller;

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
        idLabel = new JLabel(STR."#\{this.ticketId}");
        //idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        idLabel.setForeground(Color.BLUE);

        // The divider between them
        dividerLabel = new JLabel("|");
        //dividerLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // The number of entries
        entriesLabel = new JLabel(STR."No. Entries: \{this.numEntries}");
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

        if (mostRecentEditDate != LocalDate.MAX) {dateLabel.setText(STR."Last edited: \{this.mostRecentEditDate}");}

        //panel.setPreferredSize(new Dimension(100, idPanel.getHeight()));
        panel.add(idPanel, BorderLayout.WEST);
        panel.add(dateLabel, BorderLayout.EAST);

        TicketPanel parentPanel = this;
        // Action that opens up a new ticket window when clicked on
        // TODO: Create an edit section event
        idPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(STR."Mouse clicked on TicketPanel \{ticketId}");
                System.out.println(STR."IsIntro: \{isIntro}");
                if (isIntro) {
                    controller.displayEntryDisplayFrameIntro(ticketId);
                } else {
                    controller.displayEntryDisplayFrameEdit(ticketId, parentPanel);// this points to a MouseAdapter
                }
            }
        });



    }

    public JPanel mainPanel() {
        return panel;
    }

    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        panel.setBackground(color);
    }

    // Method for updating the original ticket panel when a new ticket is added
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
