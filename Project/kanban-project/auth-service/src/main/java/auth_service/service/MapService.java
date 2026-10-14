package auth_service.service;

import auth_service.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MapService {

    // FR4: Fast in-memory lookup by username.
    private final Map<String, AppUser> usersByUsername = new HashMap<>();

    public void addUser(AppUser user) {
        if (user == null || user.getUsername() == null
                || user.getUsername().isBlank()) {
            throw new IllegalArgumentException(
                    "User and username must not be null or blank");
        }

        usersByUsername.put(user.getUsername(), user);
    }

    public AppUser findByUsername(String username) {
        return usersByUsername.get(username);
    }

    // FR5: Return a report sorted alphabetically by username.
    public Map<String, String> getSortedUserReport() {
        Map<String, String> report = new TreeMap<>();

        for (Map.Entry<String, AppUser> entry
                : usersByUsername.entrySet()) {
            AppUser user = entry.getValue();

            report.put(
                    entry.getKey(),
                    "active=" + user.isActive()
            );
        }

        return report;
    }

    // FR6: Count active and inactive users.
    public Map<String, Integer> getStatusCounts() {
        Map<String, Integer> counts = new TreeMap<>();

        for (AppUser user : usersByUsername.values()) {
            String status = user.isActive() ? "Active" : "Inactive";
            counts.merge(status, 1, Integer::sum);
        }

        return counts;
    }

    // Sort users by username, then email.
    public List<AppUser> getUsersSortedByUsernameThenEmail() {
        List<AppUser> users = new ArrayList<>(usersByUsername.values());

        users.sort(
                Comparator.comparing(AppUser::getUsername)
                        .thenComparing(
                                AppUser::getEmail,
                                Comparator.nullsLast(
                                        Comparator.naturalOrder()))
        );

        return List.copyOf(users);
    }

    public int size() {
        return usersByUsername.size();
    }
}