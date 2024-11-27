import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;

public class AddEntryScreen {
    // Control variables
    public UUID ticketId;
    public UUID siteId;
    public UUID entryId;
    public EntryDisplayFrameEdit parentDisplayFrame;
    public TicketPanel associatedTicketPanel; // To update the ticketPanel in the previous screen
    public Controller controller;

    // State variables
    public String description;
    public ImageIcon icon;
    public LocalDate date;

    // Visual Components
    public JFrame frame = new JFrame();
    // Header for ticket info
    public JPanel ticketIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    public JLabel ticketIdHeader = new JLabel(); // Short title for ID
    public JLabel ticketIdLabel = new JLabel();
    // Description
    public JTextArea ticketDescriptionInput = new JTextArea("Enter ticket description", 5, 0);
    public JScrollPane descriptionInputScrollPane = new JScrollPane(ticketDescriptionInput);
    JPanel dateSelectionPanel = new JPanel();
    JLabel dateSelectionHeader = new JLabel();
    //TODO: Implement a means of designating a date

    // Default to today's date
    //Selecting an image icon
    JPanel imageSelectionPanel = new JPanel(); // Stacked components
    JLabel imageSelectionLabel = new JLabel();
    JFileChooser imageChooser = new JFileChooser();


}
