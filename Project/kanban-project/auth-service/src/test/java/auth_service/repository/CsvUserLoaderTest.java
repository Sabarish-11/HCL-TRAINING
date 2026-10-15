package auth_service.repository;

import auth_service.entity.AppUser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvUserLoaderTest {

    @Test
    void loadsUsersFromCsv() throws IOException {
        InMemoryRepository<AppUser, Long> repository =
                new InMemoryRepository<>(AppUser::getId);

        CsvUserLoader loader = new CsvUserLoader();

        int loaded = loader.loadUsers(repository);

        assertEquals(4, loaded);
        assertEquals(4, repository.count());

        AppUser arun = repository.findById(1L).orElseThrow();
        assertEquals("arun", arun.getUsername());
        assertEquals("arun@example.com", arun.getEmail());
        assertTrue(arun.isActive());

        AppUser kumar = repository.findById(3L).orElseThrow();
        assertEquals("kumar", kumar.getUsername());
        assertFalse(kumar.isActive());

        List<AppUser> users = repository.findAll();
        assertEquals(4, users.size());
    }
}