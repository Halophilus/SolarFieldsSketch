import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SiteSelectionFrame {
    // Visual subunits
    public JFrame frame = new JFrame();
    public JPanel scrollPanel = new JPanel();  // Used to be root
    JScrollPane scrollPane = new JScrollPane(scrollPanel);
    JPanel outerPanel = new JPanel(new BorderLayout());
    JButton downloadButton = new JButton("DOWNLOAD");

    public SiteSelectionFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Select target sites");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        //frame.add(scrollPane);

        outerPanel.setSize(800, 500);
        outerPanel.add(scrollPane);
        frame.add(outerPanel);

        frame.add(downloadButton, BorderLayout.SOUTH);
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: pass data from selected panes to download all the data related to the selected sites
                System.out.println("Download Button pressed");

                // Duplicate the selected integer values
                Set<UUID> selectedSites = new HashSet<UUID>();
                selectedSites.addAll(SiteSelectionPanel.selected);

                // Perform some action
                // In this case, pass them to the controller to download site data
                System.out.println(selectedSites);

                // Clear the set of selected sites
                SiteSelectionPanel.selected.clear();

                //setVisible(false);
            }
        });

    }

    // Add site to selection screen
    public void addSite(UUID id, String title, String state, String city){
        SiteSelectionPanel newPanel = new SiteSelectionPanel(id, title, state, city);
        scrollPanel.add(newPanel.panel);
    }

    // Reveal the frame
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }


}
