import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// An amphoteric JPanel class that can store TicketPanels associated with Ticket objects in both Edit and Intro sessions
public class EntryDisplayPanel {
    // Determines whether the frame is generated for an Intro or Edit session
    boolean isIntro;

    // Ticket info
    UUID ticketId;
    String description;
    boolean resolved;

    // Set of EntryPanels generated for the display panel
    // Can be sorted as a stream
    Set<EntryPanel> entryPanels = new HashSet<>();

    // Value used to alternate the background color of successive EntryPanel objects
    int counter;

    // Visual subunits
    public JPanel outerPanel = new JPanel(new BorderLayout()); // Outer content panel
    public JPanel scrollPanel = new JPanel(); // Panel to be inserted within the scrollPane
    public JScrollPane scrollPane = new JScrollPane(scrollPanel); // Adds scrolling functionality

    // Headers for displaying information about the parent Ticket
    public JLabel idLabel = new JLabel();
    public JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public JLabel descriptionLabel = new JLabel();
    public JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

    // Only contains superficial information about the Ticket it is associated with / is not connected to a Ticket object except by UUID
    public EntryDisplayPanel(UUID ticketId, String description, boolean resolved, boolean isIntro) {
        this.ticketId = ticketId;
        this.description = description;
        this.resolved = resolved;

        // Define if this is for an Intro or Edit session
        this.isIntro = isIntro;

        // Format scrollPanel to stack elements vertically
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        // Add scrollPane to outerPanel
        outerPanel.setSize(800, 500);
        outerPanel.add(scrollPane);

        // Create ID header
        idLabel.setText(STR."Ticket ID: \{this.ticketId.toString()}");
        idLabel.setBackground(Color.WHITE);
        idPanel.add(idLabel);

        // Create description header
        descriptionLabel.setText(description);
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK); // Wraps the description in a black outline
        descriptionLabel.setBorder(blackLine);
        descriptionLabel.setBackground(Color.LIGHT_GRAY);
        descriptionPanel.add(descriptionLabel);

        // Add header elements to idPanel
        scrollPanel.add(idPanel);
        scrollPanel.add(descriptionPanel);

    }

    // Add EntryPanel to set of EntryPanel objects associated with this EntryDisplayPanel
    public void addEntryPanel(EntryPanel newPanel) {
        entryPanels.add(newPanel);
    }

    // Retrieves outer content panel
    public JPanel mainPanel() {
        return this.outerPanel;
    }

    // Adds EntryPanels to outer content panel
    public void addEntriesToScrollPanel(){
        // If there are no associated EntryPanels
        if (entryPanels.isEmpty()){
            scrollPanel.add(new JLabel("No entries found")); // Indicate it in the GUI
            System.out.println("No entries found"); // Print an error message
            return; // Close the function
        }

        // Add all EntryPanels in the local set to the outer content panel
        for (EntryPanel entryPanel : entryPanels){
            if (counter % 2 == 0){ // Alternate the background color of each EntryPanel to add contrast to the display
                entryPanel.changeBackgroundColor(Color.LIGHT_GRAY);
            }
            counter++;
            scrollPanel.add(entryPanel.mainPanel());
        }
    }
}
