import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Dummy SiteImpl for testing
public class SiteImpl implements Site {
    private final int id;
    private final ImageIcon icon;
    private Set<Integer> tickets = new HashSet<>();

    // Association between ID and Site
    private static int idTracker = 0;
    private static final Map<Integer, Site> map = new HashMap<Integer, Site>();


    public SiteImpl(int id) {
        this.id = id;
        icon = new CustomImageIcon();

        map.put(id, this);
    }

    @Override
    public String title() {
        return "Site " + id();
    }

    @Override
    public int id() {
        return this.id();
    }

    @Override
    public String state() {
        return "State " + id();
    }

    @Override
    public String city() {
        return "City " + id();
    }

    @Override
    public String description() {
        return "Description for Site " + id();
    }

    @Override
    public String address() {
        return "Address for site " + id();
    }

    @Override
    public String zip() {
        return "Zip code for site " + id();
    }

    @Override
    public String phoneNumber() {
        return "Phone number for site " + id();
    }

    @Override
    public String emailAddress() {
        return "Email address for site " + id();
    }
    @Override
    public Set<Integer> ticketIDs() {
        return tickets;
    }
    @Override
    public ImageIcon imageIcon() {
        return icon;
    }


    static Site getSite(int id){
        return map.get(id);
    }

    @Override
    public void addTicket(int id) {
        tickets.add(id);
    }

    public static Set<Integer> siteIds(){
        return map.keySet();
    }
}
