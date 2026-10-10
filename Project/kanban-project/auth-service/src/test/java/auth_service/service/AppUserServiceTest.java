package auth_service.service;

import auth_service.entity.AppUser;
import auth_service.exception.DuplicateUserException;
import auth_service.exception.InvalidUserStateException;
import auth_service.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AppUserService userService;

    @Test
    void registerUser_shouldRejectDuplicateUsername() {

        AppUser existingUser = new AppUser();
        existingUser.setUsername("sabarish");

        when(userRepository.findByUsername("sabarish"))
                .thenReturn(Optional.of(existingUser));

        AppUser newUser = new AppUser();
        newUser.setUsername("sabarish");

        assertThrows(
                DuplicateUserException.class,
                () -> userService.registerUser(newUser)
        );

        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void activateUser_shouldRejectAlreadyActiveUser() {

        AppUser existingUser = new AppUser();
        existingUser.setUsername("sabarish");
        existingUser.setActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        assertThrows(
                InvalidUserStateException.class,
                () -> userService.activateUser(1L)
        );

        verify(userRepository, never()).save(any(AppUser.class));
    }
}