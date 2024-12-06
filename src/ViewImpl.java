import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ViewImpl implements View {
    Controller controller;
    ArrayList<JFrame> openFrames;

    @Override
    public void closeAllOpenedFrames(){
        for (JFrame frame : openFrames){
            frame.dispose();
        }
        openFrames.clear();
    }

    @Override
    public void displayConnectionRestoredPopup(Controller controller){
        ConnectionRestoredPopup connectionRestoredPopup = new ConnectionRestoredPopup(controller);
        openFrames.add(connectionRestoredPopup);
        connectionRestoredPopup.setVisible(true);
    }

    @Override
    public SiteSelectionFrame generateSiteSelectionFrameIntro(Controller controller){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(true, controller);
        openFrames.add(siteSelectionFrame.frame);
        return siteSelectionFrame;
    }

    @Override
    public SiteSelectionFrame generateSiteSelectionFrameEdit(Controller controller){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(false, this);
        openFrames.add(siteSelectionFrame.frame);
        return siteSelectionFrame;
    }

    @Override
    public SiteInfoDisplayPanel generateSiteInfoDisplayPanel(UUID siteId, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro, Controller controller){
        return new SiteInfoDisplayPanel(siteId, icon, title, description, address, city, state, zip, phoneNumber, emailAddress, isIntro, controller);
    }

    @Override
    public void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, UUID id, String title, String state, String city, boolean isIntro, Controller controller){
        siteSelectionFrame.addSite(id, title, state, city, isIntro, controller);
    }

    @Override
    public TicketPanel generateTicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller){
        return new TicketPanel(ticketId, mostRecentEditDate, numEntries, resolved, isIntro, controller);
    }

    @Override
    public void displaySiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller){
        SiteInfoFrameIntro newSiteInfoFrameIntro = new SiteInfoFrameIntro(siteInfoDisplayPanel, controller, siteSelectionFrame);
        openFrames.add(newSiteInfoFrameIntro.frame);
        newSiteInfoFrameIntro.setVisible(true);
    }

    @Override
    public void displaySiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller){

    }


}
