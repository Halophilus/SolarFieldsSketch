import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// Entry model general accessors
public class Entry {
    final UUID id;

    int counter;
    static int entryCounter = 0;
    LocalDate date = null;
    String description = "";
    boolean reviewed = false;

    ImageIcon imageIcon = new CustomImageIcon();

    Entry() {
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

    Entry(UUID id) {
        this.id = id;
    }
}
