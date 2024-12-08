import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Popup informing the user that the internet connection has been restored
public class ConnectionRestoredPopup extends JFrame {
    // Visual components
    JPanel descriptionPanel = new JPanel(new BorderLayout());
    JLabel descriptionLabel = new JLabel();
    JPanel buttonPanel = new JPanel(new BorderLayout());

    // User can choose to upload now or close the popup using these buttons
    JButton uploadButton = new JButton("UPLOAD");
    JButton cancelButton = new JButton("CANCEL");

    public ConnectionRestoredPopup(Controller controller) {
        // Set visible properties of the JFrame
        this.getContentPane().setLayout(new BorderLayout());
        this.setTitle("Connection has been restored");

        // Create a description object and append it to the warning window
        descriptionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        descriptionLabel.setText("Internet connection has been restored! Upload local storage now?");
        descriptionPanel.add(descriptionLabel, BorderLayout.WEST);
        this.getContentPane().add(descriptionLabel, BorderLayout.NORTH);

        // Build buttonPanel
        buttonPanel.add(uploadButton, BorderLayout.EAST);
        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JFrame thisAlias = this;

        // Set action listeners for visible buttons
        // Syncs local database with global database
        uploadButton.addActionListener(e -> { // Upload all new local storage
            controller.uploadLocalEntries(); // Add all new local values to the global database
            controller.markLocalStorageAsUploaded(); // Lower updated flags for local data
            thisAlias.dispose(); // Close popup
        });
        // Dismisses the popup
        cancelButton.addActionListener(e -> thisAlias.dispose());
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
    }
}
