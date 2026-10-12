package auth_service.service;

import auth_service.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CollectionService {

    // FR1: List maintains the current in-memory user collection.
    private final List<AppUser> users = new ArrayList<>();

    // FR2: Set guarantees that usernames are unique.
    private final Set<String> usernames = new HashSet<>();

    // FR3: Queue maintains users waiting for activation.
    private final Deque<AppUser> activationQueue = new ArrayDeque<>();

    public void addUser(AppUser user) {
        users.add(user);
        usernames.add(user.getUsername());
        activationQueue.addLast(user);
    }

    public List<AppUser> getUsers() {
        return List.copyOf(users);
    }

    public Set<String> getUsernames() {
        return Set.copyOf(usernames);
    }

    public AppUser processNextActivation() {
        return activationQueue.pollFirst();
    }

    public int getQueueSize() {
        return activationQueue.size();
    }
}