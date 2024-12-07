import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;

// An amphoteric JPanel object that stores data about an Entry object that is relevant for user interaction for both Edit and Intro sessions
public class EntryPanel implements Comparable<EntryPanel> {
    // Indicates whether the EntryPanel is being generated for an Intro or Edit session
    boolean isIntro;

    // Information about the Entry
    UUID entryId;
    LocalDate datePosted;
    String description;
    ImageIcon image;
    boolean reviewed;

    // Visual components
    public JPanel outerPanel; // main content panel

    public JPanel stackedPanel; // Stacks Entry ID and general Entry information vertically
    // Entry header
    public JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Header for EntryPanel
    public JLabel idHeader;
    public JLabel datePostedHeader;
    // General Entry information
    public JPanel imageAndDescriptionPanel; // Stores remaining Entry information
    public JLabel imageLabel;
    public JLabel descriptionLabel;

    // Contains superficial information about the Entry object to which it is connected
    public EntryPanel(UUID entryId, LocalDate datePosted, String description, ImageIcon image, boolean reviewed, boolean isIntro) {
        this.entryId = entryId;
        this.datePosted = datePosted;
        this.description = "<html><p>" + description + "</p></html>"; // Formatting description string to ensure wrapping text;
        this.image = image;
        this.reviewed = reviewed;
        this.isIntro = isIntro;

        // Outer content panel that stores all nested panels
        outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Formats the space that will store the Entry header and the general Entry information
        stackedPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(stackedPanel, BoxLayout.Y_AXIS);
        stackedPanel.setLayout(boxLayout);

        // Formats panel that contains Entry header
        idHeader = new JLabel(entryId.toString()); // Displays Entry ID
        idHeader.setForeground(Color.BLACK);
        datePostedHeader = new JLabel(datePosted.toString()); // Displays date of creation for Entry
        // Adds both to header ID panel
        idPanel.add(idHeader);
        idPanel.add(datePostedHeader);

        // Formats the space that will store general Entry information
        imageAndDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        imageLabel = new JLabel(image); // Entry ImageIcon
        descriptionLabel = new JLabel(this.description); // Entry Description
        // Add both to general Entry information panel
        imageAndDescriptionPanel.add(imageLabel);
        imageAndDescriptionPanel.add(descriptionLabel);

        // Add subpanels to the formatted space so that they appear stacked on top of each other
        stackedPanel.add(idPanel);
        stackedPanel.add(imageAndDescriptionPanel);

        // Insert the formatted panel into the outer content panel
        outerPanel.add(stackedPanel);

    }

    // Returns outer content panel
    public JPanel mainPanel() {
        return outerPanel;
    }

    // Method for changing the background color of the panel for greater contrast between EntryPanels when inserted into the EntryDisplayPanel
    public void changeBackgroundColor(Color color) {
        idPanel.setBackground(color);
        outerPanel.setBackground(color);
        stackedPanel.setBackground(color);
        imageAndDescriptionPanel.setBackground(color);
    }

    @Override
    // Comparable for sorting the EntryPanels by date
    // TODO: Figure out why this doesn't work
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


