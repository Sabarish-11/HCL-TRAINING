package auth_service.service;

import auth_service.entity.AppUser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CollectionServiceTest {

    @Test
    void shouldStoreUsersInListAndUniqueUsernamesInSet() {
        CollectionService service = new CollectionService();

        AppUser user1 = createUser("arun");
        AppUser user2 = createUser("priya");

        service.addUser(user1);
        service.addUser(user2);

        List<AppUser> users = service.getUsers();
        Set<String> usernames = service.getUsernames();

        assertEquals(2, users.size());
        assertEquals(2, usernames.size());
        assertTrue(usernames.contains("arun"));
        assertTrue(usernames.contains("priya"));
    }

    @Test
    void shouldProcessActivationQueueInFifoOrder() {
        CollectionService service = new CollectionService();

        AppUser user1 = createUser("arun");
        AppUser user2 = createUser("priya");

        service.addUser(user1);
        service.addUser(user2);

        assertSame(user1, service.processNextActivation());
        assertSame(user2, service.processNextActivation());
        assertEquals(0, service.getQueueSize());
    }

    private AppUser createUser(String username) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setActive(false);
        return user;
    }
}