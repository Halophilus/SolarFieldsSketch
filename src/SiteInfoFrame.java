import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toCollection;

public class SiteInfoFrame {
        // Determines whether the frame is from intro or edit section
        boolean isIntro;

        // ID info
        UUID iD;
        String title;
        String description;
        ImageIcon icon;

        // Location information
        String address;
        String city;
        String state;
        String zip;

        // Contact information
        String phoneNumber;
        String emailAddress;
        String contactNames;

        // Visual subunits
        public JFrame frame = new JFrame(); // Outer frame
        public JPanel scrollPanel = new JPanel();  // Used to be root
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        JPanel outerPanel = new JPanel(new BorderLayout());

        JPanel unresolvedPanel = new JPanel(new BorderLayout());
        JLabel unresolvedTicketsHeader = new JLabel("Unresolved Tickets", JLabel.CENTER);

        JPanel resolvedPanel = new JPanel(new BorderLayout());
        JLabel resolvedTicketsHeader = new JLabel("Resolved Tickets", JLabel.CENTER);

        JButton exportButton = new JButton("EXPORT");
        JButton selectButton = new JButton("SELECT");

        // Set of TicketPanels for sorting
        Set<TicketPanel> ticketPanels = new HashSet<>();

        // Formatting constant
        int counter = 0;

        public SiteInfoFrame(UUID id, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro) {

            this.iD = id;
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

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(this.title + " GlobalSite Information");
            frame.setSize(800, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

            outerPanel.setSize(800, 500);
            outerPanel.add(scrollPane);
            frame.add(outerPanel);

            // Format unresolvedTicketsHeader
            unresolvedTicketsHeader.setBackground(Color.BLACK);
            unresolvedTicketsHeader.setForeground(Color.WHITE);
            unresolvedTicketsHeader.setFont(new Font("Arial", Font.ITALIC, 20));
            unresolvedPanel.add(unresolvedTicketsHeader);

            // Format resolvedTicketsHeader
            resolvedTicketsHeader.setBackground(Color.BLACK);
            resolvedTicketsHeader.setForeground(Color.WHITE);
            resolvedTicketsHeader.setFont(new Font("Arial", Font.ITALIC, 20));
            resolvedPanel.add(resolvedTicketsHeader);

            SiteInfoHeaderPanel siteInfoHeader = new SiteInfoHeaderPanel(iD, icon, address, city, state, zip, phoneNumber, emailAddress);
            scrollPanel.add(siteInfoHeader.mainPanel());



        }

        // Add ticket to stream
        public void addTicketPanel(TicketPanel newPanel){
            ticketPanels.add(newPanel);
        }

        // Process globalTickets in stream and generate list of resolved and unresolved globalTickets
        public void addTicketsToScrollPane(){
            // Formatting constant
            counter = 0;

            if (ticketPanels.isEmpty()){
                scrollPanel.add(new JLabel("No globalTickets found"));
                System.out.println("No globalTickets found");
                return;
            }

            // Create list of filtered
            ArrayList<TicketPanel> resolvedTickets = ticketPanels.stream().filter(ticket->ticket.resolved).sorted().collect(toCollection(ArrayList::new));
            ArrayList<TicketPanel> unresolvedTickets = ticketPanels.stream().filter(ticket->!ticket.resolved).collect(toCollection(ArrayList::new));

            // Iterate through list of resolved globalTickets (if any) and add them to the display
            if (!resolvedTickets.isEmpty()){
                resolvedPanel.setBackground(Color.BLACK);
                scrollPanel.add(resolvedPanel);
                for (TicketPanel ticket : resolvedTickets){
                    ticket.changeBackgroundColor(Color.WHITE);
                    if (counter % 2 == 0) {
                        ticket.changeBackgroundColor(Color.LIGHT_GRAY);
                    }
                    counter++;
                    scrollPanel.add(ticket.mainPanel());

                }
            }

            // Do the same with the
            if (!unresolvedTickets.isEmpty()){
                unresolvedPanel.setBackground(Color.BLACK);
                scrollPanel.add(unresolvedPanel);
                for (TicketPanel ticket : unresolvedTickets){
                    System.out.println(counter);
                    ticket.changeBackgroundColor(Color.WHITE);
                    if (counter % 2 == 0) {
                        System.out.println(counter);
                        ticket.changeBackgroundColor(Color.LIGHT_GRAY);
                    }
                    counter++;
                    scrollPanel.add(ticket.mainPanel());
                }
            }


        }

        // Reveal the frame
        public void setVisible(boolean visible) {
            frame.setVisible(visible);
        }


    }


