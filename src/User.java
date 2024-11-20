import java.util.Set;

// User model general accessors
public interface User {
    String username();
    Set<Integer> entryIDs();

    User getUser(String username);
}
