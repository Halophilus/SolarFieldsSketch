import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class SiteInfoHeaderPanel {
    // ID info
    UUID id;
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

    // Contact info
    JPanel contactPanel;
    // Nested content
    JLabel addressHeader = new JLabel("Address: ");
    JPanel addressLabelPanel;
    JLabel addressLabel;

    // Address info
    JPanel addressPanel;
    // Nested content
    JLabel contactHeader = new JLabel("Contact: ");
    JPanel contactLabelPanel;
    JLabel contactLabel;


    public SiteInfoHeaderPanel(UUID id, ImageIcon icon, String address, String city, String state, String zip, String phoneNumber, String emailAddress) {
        // Assign fields
        this.id = id;
        this.icon = icon;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;

        // Create outer panel to store header info
        outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Create panel to store indented contact information
        contactPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(contactPanel, BoxLayout.Y_AXIS);
        contactPanel.setLayout(boxLayout);
        contactLabel = new JLabel(this.phoneNumber + "\n" + this.emailAddress);

        JLabel phoneNumberLabel = new JLabel(this.phoneNumber);
        JLabel emailAddressLabel = new JLabel(this.emailAddress);
        contactLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        contactLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); //indent the label
        //contactLabelPanel.add(contactLabel);
        contactPanel.add(contactHeader);
        contactPanel.add(phoneNumberLabel);
        contactPanel.add(emailAddressLabel);
        contactPanel.add(contactLabelPanel);


        // Create panel to store indented address information;
        addressPanel = new JPanel();
        BoxLayout boxLayout2 = new BoxLayout(addressPanel, BoxLayout.Y_AXIS);
        addressPanel.setLayout(boxLayout2);
        //addressPanel.add(this.addressHeader);
        addressLabel = new JLabel(this.address);
        JLabel stateAndCityLabel = new JLabel(this.city + ", " + this.state + " " + this.zip);


        addressLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        addressLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        //addressLabelPanel.add(addressLabel);
        addressPanel.add(addressHeader);
        addressPanel.add(addressLabel);
        addressPanel.add(stateAndCityLabel);

        // Scale the image by the size of the contact panel (widest element in panel)
        this.icon = scaleImage(icon, contactPanel.getHeight());
        iconLabel = new JLabel(this.icon);

        // Add content panels to the outer panel
        outerPanel.add(iconLabel);
        outerPanel.add(addressPanel);
        outerPanel.add(contactPanel);

    }

    // Scales image icon to appropriate height
    private ImageIcon scaleImage(ImageIcon icon, int newHeight)
    {
        /*
        int currentWidth = icon.getIconWidth();
        int currentHeight = icon.getIconHeight();

        currentHeight = newHeight;
        currentWidth = (icon.getIconWidth() * currentHeight) / icon.getIconHeight();

        return new ImageIcon(icon.getImage().getScaledInstance(currentWidth, currentHeight, Image.SCALE_DEFAULT));
         */
        return icon;
    }

    public JPanel mainPanel() {
        return outerPanel;
    }

}
