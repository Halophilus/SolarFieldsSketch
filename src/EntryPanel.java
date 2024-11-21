import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;

public class EntryPanel {
    // Determines whether the frame is from intro or edit section
    boolean isIntro;

    // ID Info
    UUID entryId;
    LocalDate datePosted;
    String description;
    ImageIcon image;
    boolean reviewed;

    // Visual components
    // Panels
    public JPanel outerPanel;
    public JPanel stackedPanel;
    public JPanel imageAndDescriptionPanel;
    public JPanel entryPanel;

    // Labels
    public JLabel idHeader;
    public JLabel entryLabel;


    public EntryPanel(UUID entryId, LocalDate datePosted, String description, ImageIcon image, boolean reviewed, boolean isIntro) {
        this.entryId = entryId;
        this.datePosted = datePosted;
        this.description = description;
        this.image = image;
        this.reviewed = reviewed;
        this.isIntro = isIntro;

        // Formatting description string to ensure wrapping text
        this.description = "<html><p>" + description + "</p></html>";

        // Stores all entry data
        outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Creates space for Entry ID and Icon+Image
        stackedPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(stackedPanel, BoxLayout.Y_AXIS);
        stackedPanel.setLayout(boxLayout);

        // Creates space for Icon + Image
        imageAndDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        // Entry label
        entryLabel = new JLabel(image);
        entryLabel.setText(description);
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
        outerPanel.add(idPanel, BorderLayout.WEST);
        outerPanel.add(dateLabel, BorderLayout.EAST);




    }

    public JPanel mainPanel() {
        return outerPanel;
    }

    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        outerPanel.setBackground(color);
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

}
