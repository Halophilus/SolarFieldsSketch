import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SiteSelectionFrame {
    // Control
    boolean isIntro;
    Controller controller;
    ArrayList<SiteSelectionPanel> addedPanels = new ArrayList<>();

    // Indicates whether the user has been notified of a restored internet connection
    final boolean[] beenNotified = {false}; // Array allows boolean to be accessible on the heap
    final boolean[] endSyncCheck = {false};

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
    JButton uploadButton = new JButton("UPLOAD");


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
            buttonPanel.add(downloadButton, BorderLayout.EAST);
            downloadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // TODO: pass data from selected panes to download all the data related to the selected globalSites
                    System.out.println("Download Button pressed");

                    // Duplicate the selected integer values
                    Set<UUID> selectedSites = new HashSet<UUID>();
                    selectedSites.addAll(SiteSelectionPanel.selected);
                    controller.closeAllOpenedFrames();
                    // Perform some action
                    // In this case, pass them to the controller to download site data
                    controller.displaySiteSelectionFrameEdit(selectedSites);
                    // Clear the set of selected globalSites
                    SiteSelectionPanel.selected.clear();
                    frame.dispose();

                    //setVisible(false);
                }
            });

        }

        // Close method
        frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    controller.closeAllOpenedFrames();
                }
            });

                // Button panel when edit
        if (!isIntro) {

            // Store notification flag as an array to make it accessible from the heap
            downloadButton.setEnabled(false);
            buttonPanel.add(exportButton, BorderLayout.EAST);
            buttonPanel.add(uploadButton, BorderLayout.WEST);

            // Start checking to see if the system is online
            Thread.startVirtualThread(this::attemptSyncLoop);
            // Set action listeners
            // Export button
            exportButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Export Button pressed");

                    Set<UUID> selectedSites = new HashSet<UUID>();
                    selectedSites.addAll(SiteSelectionPanel.selected);

                    // todo: implement an export screen for selected site IDs
                    controller.displayExportScreenForSelectLocations(selectedSites);
                    SiteSelectionPanel.selected.clear();
                }
            });
            uploadButton.addActionListener(new ActionListener() {
                // TODO: Create an upload operation
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Upload Button pressed");
                    controller.uploadLocalEntries(); // Add all new local entries
                    controller.markLocalStorageAsUploaded(); // Lower updated flags for local data
                    frame.dispose();
                }
            });
        }

        outerPanel.add(buttonPanel, BorderLayout.SOUTH);


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

    public void attemptSyncLoop(){
        // Check to see if the connection has been restored
        System.out.println("Checking for internet connection...");
        try {
            if (controller.checkOnlineStatus()){
                System.out.println("Connection is restored");
                // If the user hasn't been notified yet
                if (!beenNotified[0]){
                    controller.displayConnectionRestoredPopup(); // Prompt them to sync now
                }
                beenNotified[0] = true;  // Flag the user as having been notified
                uploadButton.setEnabled(true); // Enable the download button on site selection
            } else { // If the connection is lost
                System.out.println("Connection has been lost");
                beenNotified[0] = false; // Restore notification flag
                uploadButton.setEnabled(false); // Disable download
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Mechanism for breaking the infinite recursion
        if (endSyncCheck[0]) {return;}

        // Wait and then call the method again
        try {
            Thread.sleep(Duration.ofMinutes(1));
            attemptSyncLoop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }




}
