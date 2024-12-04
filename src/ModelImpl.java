import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ModelImpl {
    GlobalTicketingSystem globalTicketingSystem;
    LocalTicketingSystem localTicketingSystem;

    public GlobalSite fetchGlobalSite(UUID siteID){
        return (GlobalSite)GlobalTicketingSystem.getSite(siteID);
    }

    public void downloadLocalSite(LocalSite newLocalSite){
        LocalTicketingSystem.addSite(newLocalSite);
    }

    public ArrayList<LocalSite> getLocallyStoredSites(){
        return LocalTicketingSystem.getAllSites();
    }

    public GlobalTicket fetchGlobalTicket(UUID ticketId){
        return (GlobalTicket)GlobalTicketingSystem.getTicket(ticketId);
    }

    public List<UUID> globalSiteIds(){
        return GlobalTicketingSystem.getAllSites().stream().map(Site::id).distinct().collect(Collectors.toList());
    }

    public List<UUID> localSiteIds(){
        return LocalTicketingSystem.getAllSites().stream().map(LocalSite::id).distinct().collect(Collectors.toList());
    }

    public 
}
