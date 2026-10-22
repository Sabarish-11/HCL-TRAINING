import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final Map<Long, AppUser> users = new LinkedHashMap<>();
    private long nextId = 1;

    public AppUser create(String username, String email, String password) {
        AppUser user = new AppUser(nextId, username, email, password);
        users.put(user.getId(), user);
        nextId++;
        return user;
    }

    public Optional<AppUser> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<AppUser> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public List<AppUser> findAll() {
        return List.copyOf(users.values());
    }
}