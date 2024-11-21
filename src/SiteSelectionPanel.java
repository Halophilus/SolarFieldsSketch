import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SiteSelectionPanel{
    public JPanel panel;
    private final JCheckBox checkBox;
    private final JLabel titleLabel;
    private final JLabel locationLabel;
    private Site site;

    // De-coupling view from controller
    private final UUID id;
    private final String title;
    private final String state;
    private final String city;

    // User-manipulated state
    public static Set<UUID> selected = new HashSet<>();

    // Formatting
    private static int counter = 0;

    public SiteSelectionPanel(UUID id, String title, String state, String city){
        this.id = id;
        this.title = title;
        this.state = state;
        this.city = city;

        // Get site from ID
        // site = SiteImpl.getSite(id);

        // root panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ID panel
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        // Header
        titleLabel = new JLabel(this.title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);

        locationLabel = new JLabel( this.state + ", " + this.city);
        locationLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        locationLabel.setForeground(Color.DARK_GRAY);

        checkBox = new JCheckBox(); // Selection checkbox
        checkBox.addItemListener(e->{
             if(e.getStateChange() == ItemEvent.SELECTED) {
                 selected.add(this.id);
                 System.out.println(this.id + " selected");
             } else {
                 selected.remove(this.id);
                 System.out.println(this.id + " removed");
             }
        });
        idPanel.add(titleLabel);
        idPanel.add(locationLabel);


        panel.add(idPanel, BorderLayout.WEST);
        panel.add(checkBox, BorderLayout.EAST);

        if (counter % 2 == 0) {
            idPanel.setBackground(Color.LIGHT_GRAY);
            panel.setBackground(Color.LIGHT_GRAY);
            checkBox.setBackground(Color.LIGHT_GRAY);
        }

        counter++;

    }
}
