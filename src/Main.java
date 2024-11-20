import javax.swing.*;
import java.awt.*;
import java.util.Set;

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

        int jStart = 0;
        int kStart = 0;
        for (int i = 0; i < 20; i++) {
            System.out.println("Site number: " + i);
            SiteImpl newSite = new SiteImpl(i);
            int jEnd = jStart + 5;
            for (int j = jStart; j < jEnd; j++) {
                System.out.println("Ticket id: " + j);
                TicketImpl ticket = new TicketImpl(i);
                int kEnd = kStart + 5;
                for (int k = kStart; k < kEnd; k++) {
                    System.out.println("Entry id: " + k);
                    EntryImpl entry = new EntryImpl(j);
                }
                System.out.println("Entry ids for ticket " + j + ": " + TicketImpl.getTicket(j).entryIDs());
            }
            jStart = jEnd;
            System.out.println("Ticket ids for site " + i + ": " + SiteImpl.getSite(i).ticketIDs());
        }
        Set<Integer> siteIds = SiteImpl.siteIds();
        System.out.println("SiteIDs: " + siteIds);
        //Controller c = new Controller();
        //c.addSitesToSiteSelectionFrame(siteIds);

        //c.siteSelectionFrame.setVisible(true);

        }
    }
