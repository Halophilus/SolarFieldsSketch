import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// GlobalEntry model general accessors
public class GlobalEntry implements Entry{
    final UUID id;

    int counter;
    static int entryCounter = 0;
    LocalDate date = null;
    String description = "";
    boolean reviewed = false;

    ImageIcon icon = new CustomImageIcon();

    GlobalEntry() {
        this.id = UUID.randomUUID();
        this.counter = entryCounter++;

        // Generating a random date
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        // Generating a random image
        this.date = randomDate;
        this.description = "Description for entry " + this.counter;
    }

    // Generating a new global entry for a new local ticket
    GlobalEntry(LocalEntry uploadedEntry) {
        this.id = uploadedEntry.id();
        this.date = uploadedEntry.date();
        this.description = uploadedEntry.description();
        this.reviewed = false;
        this.icon = uploadedEntry.icon();
    }


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
