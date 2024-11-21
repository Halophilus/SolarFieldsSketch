import java.util.List;
import java.util.Set;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*
        var frame = new JFrame();
        frame.setTitle("Solar Fields");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set up main panel
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        // Add some example panels to the main panel


        JScrollPane jScrollPane = new JScrollPane(root);
        frame.add(jScrollPane);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setSize(800, 500);
        outerPanel.add(jScrollPane);
        frame.add(outerPanel);

        // Bottom nav bar
        frame.add(new JButton("SOUTH"), BorderLayout.SOUTH);

        //frame.pack();
        frame.setVisible(true);
        */

        TicketingSystem ticketingSystem = new TicketingSystem();
        for (int i = 0; i < 20; i++) {
            Site newSite = new Site();
            //System.out.println("Site number: " + newSite.id);
            ticketingSystem.sites.add(newSite);

            for (int j = 0; j < 10; j++) {
                Ticket ticket = new Ticket();
                //System.out.println("Ticket id: " + ticket.id);
                if (j % 2 == 0){
                    ticket.resolved = true;
                }
                newSite.tickets.add(ticket);

                for (int k = 0; k < 20; k++) {
                    Entry entry = new Entry();
                    //System.out.println("Entry id: " + entry.id);

                    ticket.entries.add(entry);
                }

                //System.out.println("Entry ids for ticket " + j + ": " + ticket.entries.stream().map(entry -> entry.id).toList());
            }
            //System.out.println("Ticket ids for site " + i + ": " + newSite.tickets.stream().map(ticket -> ticket.id).toList());
        }
        List<UUID> siteIds = ticketingSystem.sites.stream().map(site -> site.id).toList();
        System.out.println("SiteIDs: " + siteIds);
        ControllerImpl c = new ControllerImpl(ticketingSystem);


        c.displaySiteInfoFrameIntro(siteIds.getFirst());
        }
    }
