import java.util.List;
import java.util.Set;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


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

        c.displaySiteSelectionFrameIntro();

        }
    }
