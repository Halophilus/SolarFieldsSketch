import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.UUID;
// Displays a user interface for generating and submitting a new Ticket item associated with an existing LocalSite
public class AddTicketScreen {
    // Context variables
    public UUID siteId;
    public UUID ticketId;
    public String title;
    public Controller controller;
    public SiteInfoFrameEdit parentSiteFrame;

    // Visual components
    public JFrame frame = new JFrame();
    // Header for ticket info display
    public JPanel ticketIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    public JLabel ticketIdHeader = new JLabel(); // Short title for ID
    public JLabel ticketIdLabel = new JLabel(); // Randomly generated UUID
    // Description
    public JTextArea ticketDescriptionInput = new JTextArea("Enter ticket description", 5, 0);
    public JScrollPane descriptionInputScrollPane = new JScrollPane(ticketDescriptionInput);
    // Button panel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton addTicketButton = new JButton("ADD TICKET");
    public JButton cancelButton = new JButton("CANCEL");

    // Contains references to the parent SiteInfoFrameEdit so it can be updated in realtime
    public AddTicketScreen(UUID siteId, SiteInfoFrameEdit parentSiteFrame, Controller controller){
        this.ticketId = UUID.randomUUID();
        this.siteId = siteId;
        this.parentSiteFrame = parentSiteFrame;
        this.controller = controller;

        // Set Layout of Frame contentPane
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Generate ticket header
        ticketIdHeader.setText("Ticket #: ");
        ticketIdLabel.setText(this.ticketId.toString());

        // Format
        ticketIdHeader.setForeground(Color.LIGHT_GRAY);
        ticketIdLabel.setForeground(Color.RED);

        // Add to ticketIdPanel, then to frame
        ticketIdPanel.add(ticketIdHeader);
        ticketIdPanel.add(ticketIdLabel);
        frame.getContentPane().add(ticketIdPanel);

        // Insert description input field
        frame.getContentPane().add(descriptionInputScrollPane);

        // Generate button panel and insert
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(addTicketButton, BorderLayout.EAST);
        frame.getContentPane().add(buttonPanel);

        // Create event listeners for the two buttons
        // TODO: Generate functional action listeners
        cancelButton.addActionListener(e -> { // Closes the current frame
            System.out.println("Cancel button pressed");
            frame.dispose();
        });
        addTicketButton.addActionListener(e -> { // Generates a new LocalTicket based on provided parameters
            System.out.println("Add ticket button pressed");
            controller.generateLocalTicket(this.ticketId, this.ticketDescriptionInput.getText(),this.siteId);
            frame.dispose();
            TicketPanel newTicketPanel = controller.makeTicketPanelFromId(ticketId, false);
            parentSiteFrame.siteInfoDisplayPanel.addTicketPanel(newTicketPanel);
            parentSiteFrame.siteInfoDisplayPanel.clearAndRefresh();
        });

        // Set up visible JFrame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Generate a new ticket for " + this.title);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.pack();
    }

    // Makes frame visible
    public void setVisible(boolean visible) {frame.setVisible(visible);}


}
