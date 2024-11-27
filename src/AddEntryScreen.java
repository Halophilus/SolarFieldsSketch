import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

public class AddEntryScreen {
    // Control variables
    public UUID ticketId;
    public UUID siteId;
    public UUID entryId;
    public EntryDisplayFrameEdit entryDisplayFrame;
    public EntryDisplayPanel entryDisplayPanel;
    public TicketPanel associatedTicketPanel; // To update the ticketPanel in the previous screen
    public Controller controller;

    // State variables
    // TODO: link the title into the addTicketScreen
    public String siteTitle;
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
    public JTextArea entryDescriptionInput = new JTextArea("Enter Entry description", 5, 0);
    public JScrollPane descriptionInputScrollPane = new JScrollPane(entryDescriptionInput);

    JPanel dateSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    JLabel dateSelectionHeader = new JLabel("Date:");
    JLabel currentDate = new JLabel();
    //TODO: Implement a means of designating a date

    // Default to today's date

    // Selecting an image icon
    JPanel imageSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Stacked components
    JLabel imageSelectionLabel = new JLabel();
    JButton fileSelectButton = new JButton("Upload Image");
    JFileChooser imageChooser = new JFileChooser(".");
    JTextField imageFilepath = new JTextField("Select a file",15);
    public String filepath;

    // Bottom button panel
    public JPanel buttonPanel = new JPanel(new BorderLayout());
    public JButton submitButton = new JButton("SUBMIT");
    public JButton cancelButton = new JButton("CANCEL");

    public AddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame,  EntryDisplayPanel entryDisplayPanel, TicketPanel associatedTicketPanel, Controller controller){
        // Establish context
        this.entryDisplayFrame = entryDisplayFrame;
        this.entryDisplayPanel = entryDisplayPanel;
        this.associatedTicketPanel = associatedTicketPanel;
        // Connect to controller
        this.controller = controller;

        // Pull state variables
        this.ticketId = entryDisplayPanel.ticketId;
        this.siteId = entryDisplayFrame.siteId;
        this.entryId = UUID.randomUUID(); // Generate a new entry ID
        this.date = LocalDate.now();

        // Format frame
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Date Panel
        dateSelectionPanel.add(dateSelectionHeader);
        // TODO: Modify this to accept an arbitrary range of dates
        currentDate.setText(date.toString());
        //currentDate.setHorizontalAlignment(SwingConstants.EAST);
        dateSelectionPanel.add(currentDate);
        frame.getContentPane().add(dateSelectionPanel);

        // Image selection panel
        // Set up image chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ImageIcon file formats", "gif", "jpg", "png");
        imageChooser.addChoosableFileFilter(filter);
        fileSelectButton.addActionListener(e->{
            File file;
            int response;

            imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            response = imageChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                file = imageChooser.getSelectedFile();
                imageFilepath.setText(file.getName());

                try {
                    if (file.isFile()){
                        icon = new ImageIcon(file.getAbsolutePath());
                        imageFilepath.setText(file.getName());

                    } else {
                        System.out.println("File not selected");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
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
        cancelButton.addActionListener(e->{
            frame.dispose();
        });
        submitButton.addActionListener(e->{
            controller.generateLocalEntry(this.date, this.entryDescriptionInput.getText(), this.ticketId, this.siteId, this.entryId);
            EntryPanel newEntryPanel = controller.makeEntryPanelFromId(this.entryId, false);
            entryDisplayPanel.addEntryPanel(newEntryPanel);
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(STR."Generate a new entry for T# \{this.ticketId}");
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.pack();

    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

}
