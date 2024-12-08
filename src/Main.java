public class Main {
    public static void main(String[] args) {

        // Generate test Entries, Tickets, and Sites
        for (int i = 0; i < 20; i++) { // Generate 20 Sites

            GlobalSite newGlobalSite = new GlobalSite();
            GlobalTicketingSystem.globalSites.add(newGlobalSite);

            for (int j = 0; j < 10; j++) { // Each with 10 Tickets
                GlobalTicket globalTicket = new GlobalTicket();

                if (j % 2 == 0){
                    globalTicket.resolved = true; // Half of which are resolved
                }

                newGlobalSite.globalTickets.add(globalTicket);

                for (int k = 0; k < 20; k++) { // Each with 20 Entries
                    GlobalEntry globalEntry = new GlobalEntry();
                    globalTicket.entries.add(globalEntry);
                }
            }
        }
        Controller c = new ControllerImpl(); // Instantiate a Controller
        c.displaySiteSelectionFrameIntro(); // Initiate an Intro session
        }
    }
