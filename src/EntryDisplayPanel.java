import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntryDisplayPanel {
    // Determines whether the frame is from intro or edit section
    boolean isIntro;

    // Ticket info
    UUID ticketId;
    String description;
    boolean resolved;

    // Set of EntryPanels for sorting
    Set<EntryPanel> entryPanels = new HashSet<EntryPanel>();

    // Formatting constant
    int counter = 0;

    // Visual subunits
    // Panels
    public JPanel outerPanel = new JPanel(new BorderLayout());
    public JPanel scrollPanel = new JPanel();
    public JScrollPane scrollPane = new JScrollPane(scrollPanel);
    public JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    // Labels
    
}
