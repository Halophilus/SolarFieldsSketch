import javax.swing.*;
import java.awt.*;
import java.util.UUID;

// A graphical header for displaying contact and location information about a Site in a SiteInfoFrame both session types
public class SiteInfoHeaderPanel {
    // Ticketing information
    UUID id;

    // Visual identifier
    ImageIcon icon;

    // Location information
    String address;
    String city;
    String state;
    String zip;

    // Contact information
    String phoneNumber;
    String emailAddress;

    // Storage for header content
    JPanel outerPanel;

    // Storage for icon
    JLabel iconLabel;

    // Displaying contact information
    JPanel contactPanel;
    JLabel addressHeader = new JLabel("Address: ");
    JPanel addressLabelPanel;
    JLabel addressLabel;

    // Displaying address information
    JPanel addressPanel;
    JLabel contactHeader = new JLabel("Contact: ");
    JPanel contactLabelPanel;
    JLabel contactLabel;

    // The role of this class is purely for display purposes and has no connection to the Controller or other elements of the application
    public SiteInfoHeaderPanel(UUID id, ImageIcon icon, String address, String city, String state, String zip, String phoneNumber, String emailAddress) {
        this.id = id;
        this.icon = icon;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;

        // Create space to display information so that each successive frame stacks horizontally from left to right
        outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Contact information formatting
        contactPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(contactPanel, BoxLayout.Y_AXIS); // Each part of the contact information is to be stacked vertically within its panel
        contactPanel.setLayout(boxLayout);
        contactLabel = new JLabel(STR."\{this.phoneNumber}\n\{this.emailAddress}");

        JLabel phoneNumberLabel = new JLabel(this.phoneNumber);
        JLabel emailAddressLabel = new JLabel(this.emailAddress);
        contactLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        contactLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Indent the actual contact information
        contactPanel.add(contactHeader);
        contactPanel.add(phoneNumberLabel);
        contactPanel.add(emailAddressLabel);
        contactPanel.add(contactLabelPanel);

        // Location information formatting
        addressPanel = new JPanel();
        BoxLayout boxLayout2 = new BoxLayout(addressPanel, BoxLayout.Y_AXIS);
        addressPanel.setLayout(boxLayout2);
        addressLabel = new JLabel(this.address);
        JLabel stateAndCityLabel = new JLabel(STR."\{this.city}, \{this.state} \{this.zip}");

        addressLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        addressLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        addressPanel.add(addressHeader);
        addressPanel.add(addressLabel);
        addressPanel.add(stateAndCityLabel);

        // TODO: Create a method for automatically scaling the height of the icon to fit the
        this.icon = scaleImage(icon, contactPanel.getHeight());
        iconLabel = new JLabel(this.icon);

        // Add medi to the outer content panel
        outerPanel.add(iconLabel);
        outerPanel.add(addressPanel);
        outerPanel.add(contactPanel);

    }

    // Scales image icon to appropriate height
    private ImageIcon scaleImage(ImageIcon icon, int newHeight)
    {
        return icon;
    }

    // Returns content panel containing header
    public JPanel mainPanel() {
        return this.outerPanel;
    }
}
