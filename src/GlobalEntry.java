import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// The Global representation of Entry-type objects that are accessible within Intro sessions of the application
public class GlobalEntry implements Entry{
    // Ticketing identifier
    final UUID id;

    // These parameters are only used for generating dummy entries
    // This makes it easier for humans to identify different entries
    int counter;
    static int entryCounter = 0;

    // Entry information fields initialized to default values
    LocalDate date;
    String description;

    boolean reviewed = false;

    // Default icon is a square ImageIcon of a random color
    ImageIcon icon = new CustomImageIcon();

    // Constructor for generating dummy Entry objects
    GlobalEntry() {
        this.id = UUID.randomUUID(); // Generate a random UUID
        this.counter = entryCounter++; // Iterate dummy entry counter

        // Generating a random date
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        this.date = LocalDate.ofEpochDay(randomDay);
        this.description = STR."Description for entry \{this.counter}";
    }

    // GlobalEntries generated through the process of uploading a LocalEntry to the global database
    GlobalEntry(LocalEntry uploadedEntry) {
        this.id = uploadedEntry.id();
        this.date = uploadedEntry.date();
        this.description = uploadedEntry.description();
        this.reviewed = false;
        this.icon = uploadedEntry.icon();
    }

    // Getter methods for each field to tie them to their parent interface
    @Override
    public UUID id() {
        return this.id;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public LocalDate date() {
        return this.date;
    }

    @Override
    public boolean reviewed() {
        return this.reviewed;
    }

    @Override
    public ImageIcon icon() {
        return this.icon;
    }

    @Override
    public boolean isNew() {
        return false;
    }

}
