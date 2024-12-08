import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SiteSelectionPanel{
    // App section flag
    boolean isIntro;
    Controller controller;
    SiteSelectionFrame parentFrame;

    public JPanel panel;
    private final JCheckBox checkBox;
    private GlobalSite globalSite;

    // De-coupling view from controller
    public final UUID siteId;

    // User-manipulated state
    public static Set<UUID> selected = new HashSet<>();

    // Formatting
    private static int counter = 0;

    public SiteSelectionPanel(UUID siteId, String title, String state, String city, boolean isIntro, Controller controller, SiteSelectionFrame parentFrame){
        this.siteId = siteId;
        this.isIntro = isIntro;
        this.controller = controller;
        this.parentFrame = parentFrame;

        // Get globalSite from ID
        // globalSite = SiteImpl.getSite(id);

        // root panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ID panel
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        // Header
        JLabel titleLabel = new JLabel(title);
        //titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);

        JLabel locationLabel = new JLabel(STR."\{state}, \{city}");
        //locationLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        locationLabel.setForeground(Color.DARK_GRAY);

        checkBox = new JCheckBox(); // Selection checkbox
        checkBox.addItemListener(e->{
             if(e.getStateChange() == ItemEvent.SELECTED) {
                 selected.add(this.siteId);
                 System.out.println(STR."\{this.siteId} selected");
             } else {
                 selected.remove(this.siteId);
                 System.out.println(STR."\{this.siteId} removed");
             }
        });
        idPanel.add(titleLabel);
        idPanel.add(locationLabel);

        idPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(STR."Mouse clicked on SiteSelectionPanel \{siteId}");
                if (isIntro) {
                    controller.displaySiteInfoFrameIntro(siteId, parentFrame);
                } else {
                    controller.displaySiteInfoFrameEdit(siteId, parentFrame);
                }

            }
        });

        panel.add(idPanel, BorderLayout.WEST);
        panel.add(checkBox, BorderLayout.EAST);

        if (counter % 2 == 0) {
            idPanel.setBackground(Color.LIGHT_GRAY);
            panel.setBackground(Color.LIGHT_GRAY);
            checkBox.setBackground(Color.LIGHT_GRAY);
        }

        counter++;

    }
    public void checkSelectionBox(){
        checkBox.doClick();
    }
}
