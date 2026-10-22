public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException(
                    "UserRepository cannot be null.");
        }
        this.userRepository = userRepository;
    }

    public AppUser registerUser(
            String username, String email, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists: " + username);
        }

        return userRepository.create(username, email, password);
    }

    public AppUser activateUser(long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + id));

        user.activate();
        return user;
    }

    public java.util.List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}