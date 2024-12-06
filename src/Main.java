import java.time.Duration;
import java.util.List;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        GlobalTicketingSystem globalTicketingSystem = new GlobalTicketingSystem();
        for (int i = 0; i < 20; i++) {
            GlobalSite newGlobalSite = new GlobalSite();
            //System.out.println("GlobalSite number: " + newGlobalSite.id);
            globalTicketingSystem.globalSites.add(newGlobalSite);

            for (int j = 0; j < 10; j++) {
                GlobalTicket globalTicket = new GlobalTicket();
                //System.out.println("GlobalTicket id: " + globalTicket.id);
                if (j % 2 == 0){
                    globalTicket.resolved = true;
                }
                newGlobalSite.globalTickets.add(globalTicket);

                for (int k = 0; k < 20; k++) {
                    GlobalEntry globalEntry = new GlobalEntry();
                    //System.out.println("GlobalEntry id: " + globalEntry.id);

                    globalTicket.entries.add(globalEntry);
                }

                //System.out.println("GlobalEntry ids for globalTicket " + j + ": " + globalTicket.entries.stream().map(entry -> entry.id).toList());
            }
            //System.out.println("GlobalTicket ids for site " + i + ": " + newGlobalSite.globalTickets.stream().map(ticket -> ticket.id).toList());
        }

        List<UUID> siteIds = globalTicketingSystem.globalSites.stream().map(site -> site.id()).toList();
        System.out.println("SiteIDs: " + siteIds);

        ControllerImpl c = new ControllerImpl();

        c.displaySiteSelectionFrameIntro();

        }
    }
