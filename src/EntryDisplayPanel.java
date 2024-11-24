import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Displays all the entries for a given ticket
public class EntryDisplayPanel {
    // Determines whether the frame is from intro or edit section
    boolean isIntro;

    // GlobalTicket info
    UUID ticketId;
    String description;
    boolean resolved;

    // Set of EntryPanels for sorting
    Set<EntryPanel> entryPanels = new HashSet<EntryPanel>();

    // Formatting constant
    int counter = 0;

    // Visual subunits
    // Structural Panels
    public JPanel outerPanel = new JPanel(new BorderLayout());
    public JPanel scrollPanel = new JPanel();
    public JScrollPane scrollPane = new JScrollPane(scrollPanel);

    // Inserted Panels
    // GlobalTicket ID header
    public JLabel idLabel = new JLabel();
    public JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Description header
    public JLabel descriptionLabel = new JLabel();
    public JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    // Labels



    public EntryDisplayPanel(UUID ticketId, String description, boolean resolved, boolean isIntro) {
        // Assign fields
        this.ticketId = ticketId;
        this.description = description; // Format description to wrap text
        this.resolved = resolved;
        this.isIntro = isIntro;

        // Stack elements in scrollPanel
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        // Add scrollPane to outerPanel
        outerPanel.setSize(800, 500);
        outerPanel.add(scrollPane);

        // Create id header
        idLabel.setText(this.description);
        idLabel.setBackground(Color.WHITE);
        idPanel.add(idLabel);

        // Create description header
        descriptionLabel.setText(description);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        descriptionLabel.setBorder(blackline);
        descriptionLabel.setBackground(Color.LIGHT_GRAY);
        descriptionPanel.add(descriptionLabel);

        // Add header elements to idPanel
        scrollPanel.add(idPanel);
        scrollPanel.add(descriptionPanel);


    }

    // Add GlobalEntry to set
    public void addEntryPanel(EntryPanel newPanel) {
        entryPanels.add(newPanel);
    }

    public JPanel mainPanel() {
        return this.outerPanel;
    }

    // Add entries to scrollPanel
    public void addEntriesToScrollPanel(){
        if (entryPanels.isEmpty()){
            scrollPanel.add(new JLabel("No entries found"));
            System.out.println("No entries found");
            return;
        }

        // Add all the entries in the list
        for (EntryPanel entryPanel : entryPanels){
            if (counter % 2 == 1){
                entryPanel.changeBackgroundColor(Color.LIGHT_GRAY);
            }
            counter++;
            scrollPanel.add(entryPanel.mainPanel());

        }

    }

}
