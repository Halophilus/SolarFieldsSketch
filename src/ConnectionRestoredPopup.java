import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.classfile.instruction.ThrowInstruction;
// Popup informing the user that the internet connection has been restored
public class ConnectionRestoredPopup extends JFrame {
    JPanel descriptionPanel = new JPanel(new BorderLayout());
    JLabel descriptionLabel = new JLabel();
    JPanel buttonPanel = new JPanel(new BorderLayout());

    // User can choose to upload now or close the popup
    JButton uploadButton = new JButton("UPLOAD");
    JButton cancelButton = new JButton("CANCEL");

    public ConnectionRestoredPopup(Controller controller) {
        this.getContentPane().setLayout(new BorderLayout());
        this.setTitle("Connection has been restored");

        // Create a description object and append it to the warning window
        //descriptionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        descriptionLabel.setText("Internet connection has been restored! Upload local storage now?");
        descriptionPanel.add(descriptionLabel, BorderLayout.WEST);
        this.getContentPane().add(descriptionLabel, BorderLayout.NORTH);

        // Build buttonPanel
        buttonPanel.add(uploadButton, BorderLayout.EAST);
        buttonPanel.add(cancelButton, BorderLayout.WEST);

        JFrame thisAlias = this;

        // Set action listeners
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Upload all new local storage
                controller.uploadLocalEntries(); // Add all new local entries
                controller.markLocalStorageAsUploaded(); // Lower updated flags for local data
                thisAlias.dispose(); // Close popup
            }
        });
        cancelButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               thisAlias.dispose();
           }
       });
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
    }
}
