import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;
// Displays a user interface for generating and submitting a new Entry object associated with an existing LocalTicket
public class AddEntryScreen {
    // ID Variables
    public UUID ticketId;
    public UUID siteId;
    public UUID entryId;

    // Context variables
    public EntryDisplayFrameEdit entryDisplayFrame;
    public EntryDisplayPanel entryDisplayPanel;
    public TicketPanel associatedTicketPanel; // To update the ticketPanel in the previous screen
    public Controller controller;

    // Display information
    public String description;
    public ImageIcon icon;
    public LocalDate date;

    // Visual Components
    // Outer frame
    public JFrame frame = new JFrame();
    // Entry Description
    public JTextArea entryDescriptionInput = new JTextArea("Enter Entry description", 5, 0);
    public JScrollPane descriptionInputScrollPane = new JScrollPane(entryDescriptionInput);
    // Entry Date
    JPanel dateSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    JLabel dateSelectionHeader = new JLabel("Date:");
    JLabel currentDate = new JLabel(); // Default to today's date

    // Components for selecting an ImageIcon
    JPanel imageSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Stacked components
    JButton fileSelectButton = new JButton("Upload Image");
    JFileChooser imageChooser = new JFileChooser(".");
    JTextField imageFilepath = new JTextField("Select a file",15);

    // Bottom button panel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton submitButton = new JButton("SUBMIT");
    public JButton cancelButton = new JButton("CANCEL");

    // Contains references to the parent EntryDisplayFrameEdit, EntryDisplayPanel, and TicketPanel to refresh them after updating the local database
    public AddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame, EntryDisplayPanel entryDisplayPanel, TicketPanel associatedTicketPanel, Controller controller){
        // Establish context
        this.entryDisplayFrame = entryDisplayFrame;
        this.entryDisplayPanel = entryDisplayPanel;
        this.associatedTicketPanel = associatedTicketPanel;
        // Connect to controller
        this.controller = controller;

        // Pull ID variables
        this.ticketId = entryDisplayPanel.ticketId;
        this.siteId = entryDisplayFrame.siteId;
        this.entryId = UUID.randomUUID(); // Assigns a random UUID to the new entry

        // Generate default values
        this.date = LocalDate.now();

        // Format frame for vertical stacking of elements
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Date Panel
        dateSelectionPanel.add(dateSelectionHeader);
        // TODO: Modify this to accept an arbitrary range of dates
        currentDate.setText(date.toString());
        dateSelectionPanel.add(currentDate);
        frame.getContentPane().add(dateSelectionPanel);

        // Image selection panel
        // Set up image chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ImageIcon file formats", "gif", "jpg", "png"); // Filters out incompatible types from the selection screen
        imageChooser.addChoosableFileFilter(filter);
        fileSelectButton.addActionListener(e->{
            File file;
            int response;
            imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Restrict visible files
            response = imageChooser.showOpenDialog(null); // Makes the chooser appear as a standalone popup
            if (response == JFileChooser.APPROVE_OPTION){ // If a valid selection is made
                file = imageChooser.getSelectedFile();
                imageFilepath.setText(file.getName());
                try {
                    if (file.isFile()){
                        icon = new ImageIcon(file.getAbsolutePath());
                        imageFilepath.setText(file.getName()); // Sets filepath field to the absolute path of the selected file
                    } else {
                        System.out.println("File not selected");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace(); // Basic error message for basic debugging
                }
            }
        });

        // Add buttons and textfield to panel
        imageSelectionPanel.add(fileSelectButton);
        imageSelectionPanel.add(imageFilepath);
        frame.getContentPane().add(imageSelectionPanel);

        // Insert description input field
        frame.getContentPane().add(descriptionInputScrollPane);

        // Generate button panel and insert
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(submitButton, BorderLayout.EAST);
        frame.getContentPane().add(buttonPanel);

        // Add action listeners to bottom button panel
        cancelButton.addActionListener(e->{ // Closes the window
            frame.dispose();
        });

        submitButton.addActionListener(e->{ // Generates a new LocalEntry then closes the screen
            controller.generateLocalEntry(this.date, this.entryDescriptionInput.getText(), this.icon, this.ticketId, this.siteId, this.entryId); // Pulls description from entry field
            associatedTicketPanel.updateEntries(this.date); // Automatically assigns date to current date
            entryDisplayFrame.frame.dispose();
            controller.displayEntryDisplayFrameEdit(this.ticketId, this.associatedTicketPanel);
            this.frame.dispose();
        });

        // Defining frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(STR."Generate a new entry for T# \{this.ticketId}");
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.pack();
    }

    // Sets the visibility of the principal JFrame
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
