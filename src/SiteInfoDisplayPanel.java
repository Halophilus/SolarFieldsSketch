import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toCollection;

// A panel for storing information about a Site and a brief overview of all Ticket objects connected to it
public class SiteInfoDisplayPanel {
    // Determines whether the frame is from intro or edit session
    boolean isIntro;

    // Controller access
    Controller controller;

    // Ticketing identifier
    UUID siteId;

    // Site information
    String title;
    String description;
    ImageIcon icon;

    // Location
    String address;
    String city;
    String state;
    String zip;

    // Contact
    String phoneNumber;
    String emailAddress;

    // Set of TicketPanels to be displayed in frame
    Set<TicketPanel> ticketPanels = new HashSet<>();

    // For alternating TicketPanel background colors
    int counter;

    // Outer content panel
    public JPanel outerPanel = new JPanel(new BorderLayout());
    // Content panel for ScrollPane
    public JPanel scrollPanel = new JPanel();
    // Adds scrolling functionality
    public JScrollPane scrollPane = new JScrollPane(scrollPanel);

    // Section displaying TicketPanels associated with unresolved Ticket objects
    public JPanel unresolvedPanel = new JPanel(new BorderLayout());
    public JLabel unresolvedTicketsHeader = new JLabel("Unresolved Tickets", JLabel.CENTER);

    // And for resolved Ticket objects
    public JPanel resolvedPanel = new JPanel(new BorderLayout());
    public JLabel resolvedTicketsHeader = new JLabel("Resolved Tickets", JLabel.CENTER);

    // Controller generates and inserts TicketPanel objects post hoc
    public SiteInfoDisplayPanel(UUID siteId, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro, Controller controller) {

        this.siteId = siteId;
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.isIntro = isIntro;
        this.controller = controller;

        // Format the content panel so that elements are stacked vertically
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        // Insert scrollable media into the outer content panel
        outerPanel.setSize(800, 500);
        outerPanel.add(scrollPane);

        // Format unresolvedTicketsHeader
        unresolvedTicketsHeader.setBackground(Color.BLACK);
        unresolvedTicketsHeader.setForeground(Color.WHITE);
        unresolvedPanel.add(unresolvedTicketsHeader);

        // Format resolvedTicketsHeader
        resolvedTicketsHeader.setBackground(Color.BLACK);
        resolvedTicketsHeader.setForeground(Color.WHITE);
        resolvedPanel.add(resolvedTicketsHeader);

        // Generate and insert SiteInfoHeaderPanel to display the relevant info about the Site itself
        SiteInfoHeaderPanel siteInfoHeader = new SiteInfoHeaderPanel(siteId, icon, address, city, state, zip, phoneNumber, emailAddress);
        scrollPanel.add(siteInfoHeader.mainPanel());

    }


    // Adds a TicketPanel to the list of stored TicketPanels
    public void addTicketPanel(TicketPanel newPanel){
        ticketPanels.add(newPanel);
    }

    // Insert all stored TicketPanels as scrollable media
    public void addTicketsToScrollPane(){

        if (ticketPanels.isEmpty()){ // If there are no TicketPanels
            scrollPanel.add(new JLabel("No globalTickets found")); // Indicate within the GUI
            System.out.println("No globalTickets found"); // Print an error message
            return; // End the function
        }

        // Filter the local list of TicketPanels into resolved and unresolved elements
        ArrayList<TicketPanel> resolvedTickets = ticketPanels.stream().filter(ticket->ticket.resolved).sorted().collect(toCollection(ArrayList::new));
        ArrayList<TicketPanel> unresolvedTickets = ticketPanels.stream().filter(ticket->!ticket.resolved).collect(toCollection(ArrayList::new));

        // Add each set to the display in order
        insertTicketPanelsWithContrast(resolvedTickets, resolvedPanel);
        insertTicketPanelsWithContrast(unresolvedTickets, unresolvedPanel);
    }

    // Formats and inserts TicketPanel objects as scrollable media to appear as a list
    private void insertTicketPanelsWithContrast(ArrayList<TicketPanel> resolvedTickets, JPanel resolvedPanel) {
        resolvedPanel.setBackground(Color.BLACK);
        scrollPanel.add(resolvedPanel);
        for (TicketPanel ticket : resolvedTickets){ // Alternate background color for contrast
            ticket.changeBackgroundColor(Color.WHITE);
            if (counter % 2 == 0) {
                ticket.changeBackgroundColor(Color.LIGHT_GRAY);
            }
            counter++;
            scrollPanel.add(ticket.mainPanel());
        }
    }

    // Refreshes the panel after additions are made to it
    public void clearAndRefresh(){
        // Clear panel
        scrollPanel.removeAll();
        // Generate new header
        SiteInfoHeaderPanel siteInfoHeader = new SiteInfoHeaderPanel(siteId, icon, address, city, state, zip, phoneNumber, emailAddress);
        scrollPanel.add(siteInfoHeader.mainPanel());
        // Add new tickets to control panel
        addTicketsToScrollPane();
        // Refresh
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    // Returns main content panel that can be inserted into a JFrame
    public JPanel mainPanel() {
        return this.outerPanel;
    }
}


