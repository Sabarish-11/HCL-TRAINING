package auth_service.service;

import auth_service.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapServiceTest {

    private MapService mapService;
    private AppUser arun;
    private AppUser priya;
    private AppUser meena;

    @BeforeEach
    void setUp() {
        mapService = new MapService();

        arun = createUser("arun", "arun@example.com", true);
        priya = createUser("priya", "priya@example.com", true);
        meena = createUser("meena", "meena@example.com", false);

        // Add in a deliberately unsorted order.
        mapService.addUser(priya);
        mapService.addUser(arun);
        mapService.addUser(meena);
    }

    private AppUser createUser(
            String username, String email, boolean active) {

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("test-password");
        user.setActive(active);
        return user;
    }

    @Test
    void findsUserByUsername() {
        assertSame(arun, mapService.findByUsername("arun"));
    }

    @Test
    void returnsNullForUnknownUsername() {
        assertNull(mapService.findByUsername("unknown"));
    }

    @Test
    void returnsReportSortedByUsername() {
        Map<String, String> report = mapService.getSortedUserReport();

        assertEquals(
                List.of("arun", "meena", "priya"),
                List.copyOf(report.keySet())
        );
        assertEquals("active=true", report.get("arun"));
        assertEquals("active=false", report.get("meena"));
    }

    @Test
    void countsActiveAndInactiveUsers() {
        Map<String, Integer> counts = mapService.getStatusCounts();

        assertEquals(2, counts.get("Active"));
        assertEquals(1, counts.get("Inactive"));
    }

    @Test
    void sortsUsersByUsernameThenEmail() {
        List<AppUser> sorted =
                mapService.getUsersSortedByUsernameThenEmail();

        assertEquals(
                List.of("arun", "meena", "priya"),
                sorted.stream().map(AppUser::getUsername).toList()
        );
    }

    @Test
    void duplicateUsernameReplacesExistingEntry() {
        AppUser replacement =
                createUser("arun", "new-arun@example.com", false);

        mapService.addUser(replacement);

        assertEquals(3, mapService.size());
        assertSame(replacement, mapService.findByUsername("arun"));
        assertEquals(
                "active=false",
                mapService.getSortedUserReport().get("arun")
        );
    }

    @Test
    void rejectsNullUser() {
        assertThrows(
                IllegalArgumentException.class,
                () -> mapService.addUser(null)
        );
    }

    @Test
    void rejectsBlankUsername() {
        AppUser invalid = createUser("", "invalid@example.com", true);

        assertThrows(
                IllegalArgumentException.class,
                () -> mapService.addUser(invalid)
        );
    }
}
