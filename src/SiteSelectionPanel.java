import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Individual panel representing a single Site to be inserted into a SiteSelectionFrame
public class SiteSelectionPanel{
    boolean isIntro;
    Controller controller;
    // Context
    SiteSelectionFrame parentFrame;

    // Swing components
    public JPanel panel;
    private final JCheckBox checkBox;

    // Ticketing identifier
    public final UUID siteId;

    // List of UUIDs associated with user-selected sites
    public static Set<UUID> selected = new HashSet<>();

    // Static variable for keeping track of alternating SiteSelectionPanels
    private static int counter = 0;

    public SiteSelectionPanel(UUID siteId, String title, String state, String city, boolean isIntro, Controller controller, SiteSelectionFrame parentFrame){
        this.siteId = siteId;
        this.isIntro = isIntro;
        this.controller = controller;
        this.parentFrame = parentFrame;

        // root panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Display elements
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.BLUE);
        JLabel locationLabel = new JLabel(STR."\{state}, \{city}");
        locationLabel.setForeground(Color.DARK_GRAY);

        // Used to pass selected Site UUIDs to the Controller
        checkBox = new JCheckBox(); // Selection checkbox
        checkBox.addItemListener(e->{ // Add or remove the selected Site's UUID from the stored list
             if(e.getStateChange() == ItemEvent.SELECTED) {
                 selected.add(this.siteId);
                 System.out.println(STR."\{this.siteId} selected");
             } else {
                 selected.remove(this.siteId);
                 System.out.println(STR."\{this.siteId} removed");
             }
        });

        // Insert site information header
        idPanel.add(titleLabel);
        idPanel.add(locationLabel);

        // Opens up SiteInfoFrame related to the SiteSelectionPanel when clicked
        idPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(STR."Mouse clicked on SiteSelectionPanel \{siteId}");
                if (isIntro) { // Has different behavior depending on the session type
                    controller.displaySiteInfoFrameIntro(siteId, parentFrame);
                } else {
                    controller.displaySiteInfoFrameEdit(siteId, parentFrame);
                }

            }
        });

        panel.add(idPanel, BorderLayout.WEST);
        panel.add(checkBox, BorderLayout.EAST);

        //  Alternate the background color for contrast
        if (counter % 2 == 0) {
            idPanel.setBackground(Color.LIGHT_GRAY);
            panel.setBackground(Color.LIGHT_GRAY);
            checkBox.setBackground(Color.LIGHT_GRAY);
        }

        counter++; // increment the static placement counter

    }

    // Used to remotely select checkboxes from SiteInfoFrames
    public void checkSelectionBox(){
        checkBox.doClick();
    }
}
