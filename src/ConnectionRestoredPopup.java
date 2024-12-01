import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionRestoredPopup extends JFrame {
    JLabel descriptionLabel = new JLabel();
    JPanel buttonPanel = new JPanel(new BorderLayout());

    JButton uploadButton = new JButton("UPLOAD");
    JButton cancelButton = new JButton("CANCEL");

    public ConnectionRestoredPopup(Controller controller) {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Create a description object and append it to the warning window
        descriptionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        descriptionLabel.setText("Internet connection has been restored! Upload local storage now?");
        this.getContentPane().add(descriptionLabel);

        // Build buttonPanel
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonPanel.add(uploadButton, BorderLayout.EAST);
        buttonPanel.add(cancelButton, BorderLayout.WEST);

        JFrame thisAlias = this;

        // Set action listeners
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.uploadLocalEntries(); // Add all new local entries
                controller.markLocalStorageAsUploaded(); // Lower updated flags for local data
                thisAlias.dispose();
            }
        });

    }
}
