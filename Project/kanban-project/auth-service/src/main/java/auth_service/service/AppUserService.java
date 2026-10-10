package auth_service.service;

import auth_service.entity.AppUser;
import auth_service.exception.DuplicateUserException;
import auth_service.exception.InvalidUserStateException;
import auth_service.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser registerUser(AppUser user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateUserException(
                    "Username already exists: " + user.getUsername()
            );
        }

        user.setActive(false);
        return userRepository.save(user);
    }

    public AppUser activateUser(Long id) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidUserStateException(
                                "User not found: " + id
                        ));

        if (user.isActive()) {
            throw new InvalidUserStateException(
                    "User is already active: " + user.getUsername()
            );
        }

        user.setActive(true);
        return userRepository.save(user);
    }
}