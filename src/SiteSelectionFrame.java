import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SiteSelectionFrame {
    // Control
    boolean isIntro;
    Controller controller;
    ArrayList<SiteSelectionPanel> addedPanels = new ArrayList<>();

    // Visual subunits
    public JFrame frame = new JFrame();
    public JPanel scrollPanel = new JPanel();  // Used to be root
    JScrollPane scrollPane = new JScrollPane(scrollPanel);
    JPanel outerPanel = new JPanel(new BorderLayout());

    // Button formatting
    public JPanel buttonPanel = new JPanel(new BorderLayout()); // general button panel


    // Intro buttons
    JButton downloadButton = new JButton("DOWNLOAD");
    // Edit buttons
    JButton exportButton = new JButton("EXPORT");
    JButton importButton = new JButton("IMPORT");


    public SiteSelectionFrame(boolean isIntro, Controller controller) {
        this.isIntro = isIntro;
        this.controller = controller;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Select target globalSites");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        //frame.add(scrollPane);

        outerPanel.setSize(800, 500);
        outerPanel.add(scrollPane);
        frame.add(outerPanel);

        // Formatting button panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // Button panel when intro
        if (isIntro) {
            frame.add(downloadButton, BorderLayout.SOUTH);
            downloadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // TODO: pass data from selected panes to download all the data related to the selected globalSites
                    System.out.println("Download Button pressed");

                    // Duplicate the selected integer values
                    Set<UUID> selectedSites = new HashSet<UUID>();
                    selectedSites.addAll(SiteSelectionPanel.selected);

                    // Perform some action
                    // In this case, pass them to the controller to download site data
                    System.out.println(selectedSites);
                    controller.displaySiteSelectionFrameEdit(selectedSites);
                    // Clear the set of selected globalSites
                    SiteSelectionPanel.selected.clear();
                    frame.dispose();

                    //setVisible(false);
                }
            });
        }

        // Button panel when edit
        if (!isIntro) {
            frame.add(exportButton, BorderLayout.EAST);
            frame.add(importButton, BorderLayout.WEST);

            // Set action listeners 
        }



    }

    // Add site to selection screen
    public void addSite(UUID id, String title, String state, String city, boolean isIntro, Controller controller){
        SiteSelectionPanel newPanel = new SiteSelectionPanel(id, title, state, city, isIntro, controller, this);
        addedPanels.add(newPanel);
        scrollPanel.add(newPanel.panel);
    }

    // Find a SiteSelectionPanel and check its box
    public void selectChildPanel(UUID panelId){
        SiteSelectionPanel targetPanel = null;
        for (SiteSelectionPanel panel : addedPanels) {
            if (panel.siteId.equals(panelId)) {
                targetPanel = panel;
            }
        }
        assert targetPanel != null;
        targetPanel.checkSelectionBox();
    }

    // Reveal the frame
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }






}
