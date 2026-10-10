package auth_service.entity;

import jakarta.persistence.Entity;


@Entity
public class AppUser extends BaseEntity {


    private String username;
    private String email;
    private String password;
    private boolean active;

    public AppUser() {
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isActive() {
    return active;
    }

    public void setActive(boolean active) {
    this.active = active;
    }
}