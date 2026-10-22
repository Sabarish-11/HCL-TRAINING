package com.hcl.auth;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * Uses:
 * Map/HashMap for username-to-ID lookup,
 * Set/HashSet for case-insensitive username uniqueness,
 * Queue/ArrayDeque for FIFO pending activations,
 * and the generic repository's LinkedHashMap for account records.
 */
public final class InMemoryUserRepository implements UserRepository {
    private final Repository<UserAccount, Long> accounts = new InMemoryRepository<>();
    private final Map<String, Long> usernameIndex = new HashMap<>();
    private final Set<String> usernames = new HashSet<>();
    private final Queue<Long> pendingActivationQueue = new ArrayDeque<>();

    @Override
    public void saveUser(UserAccount user) throws DuplicateUsernameException {
        String normalized = normalize(user.getUsername());
        if (usernames.contains(normalized)) {
            throw new DuplicateUsernameException(user.getUsername());
        }
        accounts.save(user);
        usernames.add(normalized);
        usernameIndex.put(normalized, user.getId());
        pendingActivationQueue.offer(user.getId());
    }

    @Override
    public void save(UserAccount user) {
        try {
            saveUser(user);
        } catch (DuplicateUsernameException exception) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return accounts.findById(id);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        if (username == null) return Optional.empty();
        Long id = usernameIndex.get(normalize(username));
        return id == null ? Optional.empty() : accounts.findById(id);
    }

    @Override
    public boolean usernameExists(String username) {
        return username != null && usernames.contains(normalize(username));
    }

    @Override
    public List<UserAccount> findAll() {
        return accounts.findAll();
    }

    @Override
    public void markActivated(long id) {
        pendingActivationQueue.remove(id);
    }

    @Override
    public List<Long> pendingActivationIds() {
        return List.copyOf(pendingActivationQueue);
    }

    private String normalize(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }
}
