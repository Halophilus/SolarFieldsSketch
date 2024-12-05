import javax.swing.*;
import java.util.ArrayList;

public class ViewImpl {
    ArrayList<JFrame> openFrames;

    public void closeAllOpenedFrames(){
        for (JFrame frame : openFrames){
            frame.dispose();
        }
        openFrames.clear();
    }

    public void generateNewConnectionRestoredPopup(Controller controller){
        ConnectionRestoredPopup connectionRestoredPopup = new ConnectionRestoredPopup(this);
        openFrames.add(connectionRestoredPopup);
        connectionRestoredPopup.setVisible(true);
    }


}
