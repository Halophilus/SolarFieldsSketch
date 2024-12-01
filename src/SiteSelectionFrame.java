import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Button panel when edit
        if (!isIntro) {




            // Store notification flag as an array to make it accessible from the heap
            final boolean[] beenNotified = {false};
            downloadButton.setEnabled(false);
            buttonPanel.add(exportButton, BorderLayout.EAST);
            buttonPanel.add(uploadButton, BorderLayout.WEST);

            // Start checking to see if the system is online
            Thread.startVirtualThread(() -> {

                for(;;) {
                    if (controller.checkOnlineStatus()) {
                        downloadButton.setEnabled(true);
                        if (!beenNotified[0]) {
                            // Create an anonymous class that creates a popup screen
                            class NotificationScreen extends JFrame{
                                JLabel descriptionLabel = new JLabel();
                                JPanel buttonPanel = new JPanel(new BorderLayout());

                                JButton uploadButton = new JButton("UPLOAD");
                                JButton cancelButton = new JButton("CANCEL");
                                public NotificationScreen(){
                                    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

                                    // Create a description object and append it to the warning window
                                    descriptionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
                                    descriptionLabel.setText("Internet connection has been restored! Upload local storage now?");
                                    this.getContentPane().add(descriptionLabel);

                                    // Build buttonPanel
                                    buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                                    buttonPanel.add(uploadButton, BorderLayout.EAST);
                                    buttonPanel.add(cancelButton, BorderLayout.WEST);

                                    // Set action listeners
                                    uploadButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                        }
                                    });


                                }
                            }
                        }
                        beenNotified[0] = true;
                    } else {

                    }
                    try {
                        Thread.sleep(Duration.ofMinutes(1));
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
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






}
