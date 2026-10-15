
package auth_service.repository;

import auth_service.entity.AppUser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    @Test
    void storesAndFindsAppUsers() {
        InMemoryRepository<AppUser, Long> repository =
                new InMemoryRepository<>(AppUser::getId);

        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("arun");
        user.setEmail("arun@example.com");
        user.setActive(true);

        repository.save(user);

        assertEquals(1, repository.count());
        assertSame(user, repository.findById(1L).orElseThrow());
        assertEquals("arun", repository.findAll().get(0).getUsername());
    }

    @Test
    void supportsASecondEntityType() {
        InMemoryRepository<String, String> repository =
                new InMemoryRepository<>(String::toString);

        repository.save("task-101");

        assertEquals(
                Optional.of("task-101"),
                repository.findById("task-101")
        );
        assertEquals(1, repository.count());
    }

    @Test
    void deletesAnEntityById() {
        InMemoryRepository<String, String> repository =
                new InMemoryRepository<>(String::toString);

        repository.save("task-101");

        assertTrue(repository.deleteById("task-101"));
        assertFalse(repository.deleteById("task-101"));
        assertEquals(0, repository.count());
    }

    @Test
    void rejectsAnEntityWithNullId() {
        InMemoryRepository<AppUser, Long> repository =
                new InMemoryRepository<>(AppUser::getId);

        AppUser user = new AppUser();
        user.setUsername("missing-id");

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(user)
        );
    }
}
