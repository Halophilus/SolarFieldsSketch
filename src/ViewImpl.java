import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ViewImpl implements View {
    // Stores frames that have been open in a section (intro vs. edit)
    ArrayList<JFrame> openFrames;

    public ViewImpl(){
        this.openFrames = new ArrayList<>();
    }

    @Override
    public void closeAllOpenedFrames(){
        for (JFrame frame : openFrames){
            frame.dispose();
        }
        openFrames.clear();
    }

    @Override
    // Generates and displays a popup when the connection is restored
    public void displayConnectionRestoredPopup(Controller controller){
        ConnectionRestoredPopup connectionRestoredPopup = new ConnectionRestoredPopup(controller);
        openFrames.add(connectionRestoredPopup);
        connectionRestoredPopup.setVisible(true);
    }

    @Override
    // Generates a site-selection frame for sites to be downloaded in the intro section
    public SiteSelectionFrame generateSiteSelectionFrameIntro(Controller controller){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(true, controller);
        openFrames.add(siteSelectionFrame.frame);
        siteSelectionFrame.setVisible(true);
        return siteSelectionFrame;
    }

    @Override
    // Generates a site display frame for downloaded sites in the edit section
    public SiteSelectionFrame generateSiteSelectionFrameEdit(Controller controller){
        SiteSelectionFrame siteSelectionFrame = new SiteSelectionFrame(false, controller);
        openFrames.add(siteSelectionFrame.frame);
        siteSelectionFrame.setVisible(true);
        return siteSelectionFrame;
    }

    @Override
    // Generates an amphoteric SiteInfoDisplayPanel to insert into a SiteInfoFrame
    public SiteInfoDisplayPanel generateSiteInfoDisplayPanel(UUID siteId, ImageIcon icon, String title, String description, String address, String city, String state, String zip, String phoneNumber, String emailAddress, boolean isIntro, Controller controller){
        return new SiteInfoDisplayPanel(siteId, icon, title, description, address, city, state, zip, phoneNumber, emailAddress, isIntro, controller);
    }

    @Override
    // Adds site panels to an existing SiteSelectionFrame and refreshes the frame
    public void addSiteToSiteSelectionFrame(SiteSelectionFrame siteSelectionFrame, UUID id, String title, String state, String city, boolean isIntro, Controller controller){
        siteSelectionFrame.addSite(id, title, state, city, isIntro, controller);
        siteSelectionFrame.frame.invalidate();
        siteSelectionFrame.frame.validate();
        siteSelectionFrame.frame.repaint();
    }

    @Override
    // Generates amphoteric TicketPanels to be inserted into SiteInfoFrames
    public TicketPanel generateTicketPanel(UUID ticketId, LocalDate mostRecentEditDate, int numEntries, boolean resolved, boolean isIntro, Controller controller){
        return new TicketPanel(ticketId, mostRecentEditDate, numEntries, resolved, isIntro, controller);
    }

    @Override
    // Displays a SiteInfoFrame for the intro section
    public void displaySiteInfoFrameIntro(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller){
        siteInfoDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameIntro newSiteInfoFrameIntro = new SiteInfoFrameIntro(siteInfoDisplayPanel, controller, siteSelectionFrame);
        openFrames.add(newSiteInfoFrameIntro.frame);
        newSiteInfoFrameIntro.setVisible(true);
    }

    @Override
    // Displays a SiteInfoFrame for the edit section
    public void displaySiteInfoFrameEdit(SiteInfoDisplayPanel siteInfoDisplayPanel, SiteSelectionFrame siteSelectionFrame, Controller controller){
        siteInfoDisplayPanel.addTicketsToScrollPane();
        SiteInfoFrameEdit newSiteInfoFrameEdit = new SiteInfoFrameEdit(siteInfoDisplayPanel, controller, siteSelectionFrame);
        openFrames.add(newSiteInfoFrameEdit.frame);
        newSiteInfoFrameEdit.setVisible(true);
    }

    @Override
    // Generates an amphoteric EntryDisplayPanel to insert into a SiteInfoFrame
    public EntryDisplayPanel generateEntryDisplayPanelIntro(UUID ticketId, String description, boolean resolved, boolean isIntro){
        return new EntryDisplayPanel(ticketId, description, resolved, isIntro);
    }

    @Override
    // Generates and displays an EntryDisplayFrameIntro
    public void displayEntryDisplayFrameIntro(EntryDisplayPanel entryDisplayPanel, Controller controller){
        entryDisplayPanel.addEntriesToScrollPanel();
        EntryDisplayFrameIntro entryDisplayFrameIntro = new EntryDisplayFrameIntro(entryDisplayPanel, controller);
        openFrames.add(entryDisplayFrameIntro.frame);
        entryDisplayFrameIntro.setVisible(true);
    }

    @Override
    // Generates and displays an EntryDisplayFrameEdit
    public void displayEntryDisplayFrameEdit(EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, UUID siteId, Controller controller){
        entryDisplayPanel.addEntriesToScrollPanel();
        EntryDisplayFrameEdit entryDisplayFrameEdit = new EntryDisplayFrameEdit(entryDisplayPanel, parentTicketPanel, siteId, controller);
        openFrames.add(entryDisplayFrameEdit.frame);
        entryDisplayFrameEdit.setVisible(true);
    }

    @Override
    // Generates a EntryPanel for insertion into an EntryDisplayPanel
    public EntryPanel generateEntryPanel(UUID entryId, LocalDate datePosted, String description, ImageIcon image, boolean reviewed, boolean isIntro){
        return new EntryPanel(entryId, datePosted, description, image, reviewed, isIntro);
    }

    @Override
    // Generates an AddTicketScreen for inputting new tickets locally
    public void displayAddTicketScreen(UUID siteId, SiteInfoFrameEdit siteInfoFrameEdit, Controller controller) {
        AddTicketScreen newAddTicketScreen = new AddTicketScreen(siteId, siteInfoFrameEdit, controller);
        newAddTicketScreen.setVisible(true);
    }

    @Override
    // Generates an AddEntryScreen for inputting new Entries locally
    public void displayAddEntryScreen(EntryDisplayFrameEdit entryDisplayFrame, EntryDisplayPanel entryDisplayPanel, TicketPanel parentTicketPanel, Controller controller){
        AddEntryScreen addEntryScreen = new AddEntryScreen(entryDisplayFrame, entryDisplayPanel, parentTicketPanel, controller);
        openFrames.add(addEntryScreen.frame);
        addEntryScreen.setVisible(true);
    }




}
