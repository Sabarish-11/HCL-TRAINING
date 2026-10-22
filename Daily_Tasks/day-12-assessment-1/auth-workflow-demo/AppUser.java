
public class AppUser {
    private final long id;
    private final String username;
    private final String email;
    private final String password;
    private boolean active;

    public AppUser(long id, String username, String email, String password) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = false;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        if (active) {
            throw new IllegalStateException("User is already active.");
        }
        active = true;
    }

    @Override
    public String toString() {
        return "AppUser{id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", active=" + active + "}";
    }
}
