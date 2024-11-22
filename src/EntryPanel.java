import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;

public class EntryPanel implements Comparable<EntryPanel> {
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
    public JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    public JPanel imageAndDescriptionPanel;
    public JPanel entryPanel;

    // Labels
    public JLabel idHeader;
    public JLabel datePostedHeader;
    public JLabel imageLabel;
    public JLabel descriptionLabel;


    public EntryPanel(UUID entryId, LocalDate datePosted, String description, ImageIcon image, boolean reviewed, boolean isIntro) {
        this.entryId = entryId;
        this.datePosted = datePosted;
        this.description = "<html><p>" + description + "</p></html>"; // Formatting description string to ensure wrapping text;
        this.image = image;
        this.reviewed = reviewed;
        this.isIntro = isIntro;

        // Stores all entry data
        outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Creates space for Entry ID and Icon+Image
        stackedPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(stackedPanel, BoxLayout.Y_AXIS);
        stackedPanel.setLayout(boxLayout);

        // Creates space for entry ID + date header
        // Create ID label
        idHeader = new JLabel(entryId.toString());
        idHeader.setForeground(Color.BLACK);
        // Create date panel
        datePostedHeader = new JLabel(datePosted.toString());
        // Add it to iDPanel
        idPanel.add(idHeader);
        idPanel.add(datePostedHeader);

        // Creates space for Icon + Image
        imageAndDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        // Create Icon and Image
        imageLabel = new JLabel(image);
        descriptionLabel = new JLabel(this.description);
        // Add labels to relevant panel
        imageAndDescriptionPanel.add(imageLabel);
        imageAndDescriptionPanel.add(descriptionLabel);

        // Add subpanels to stackedPanel
        stackedPanel.add(idPanel);
        stackedPanel.add(imageAndDescriptionPanel);

        // Add stackedPanel to outerPanel
        outerPanel.add(stackedPanel);

    }

    public JPanel mainPanel() {
        return outerPanel;
    }

    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        outerPanel.setBackground(color);
        stackedPanel.setBackground(color);
        imageAndDescriptionPanel.setBackground(color);
    }

    @Override
    public int compareTo(EntryPanel otherEntry) {
        if (this.datePosted.isBefore(otherEntry.datePosted)) {
            return 1; // If the current date is before the provided date
        } else if (this.datePosted.isAfter(otherEntry.datePosted)) {
            return -1;
        } else {
            return 0;
        }
    }
}


