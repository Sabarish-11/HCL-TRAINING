package auth_service.repository;

import auth_service.entity.AppUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CsvUserLoader {

    public int loadUsers(
            InMemoryRepository<AppUser, Long> repository)
            throws IOException {

        Objects.requireNonNull(repository, "Repository must not be null");

        InputStream stream = getClass().getResourceAsStream("/data/users.csv");

        if (stream == null) {
            throw new IOException("Could not find /data/users.csv");
        }

        int loaded = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // Skip the header

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);

                if (columns.length != 4) {
                    throw new IOException("Invalid CSV row: " + line);
                }

                try {
                    Long id = Long.parseLong(columns[0].trim());
                    String username = columns[1].trim();
                    String email = columns[2].trim();
                    boolean active = Boolean.parseBoolean(
                            columns[3].trim());

                    AppUser user = new AppUser();
                    user.setId(id);
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setActive(active);

                    repository.save(user);
                    loaded++;
                } catch (IllegalArgumentException exception) {
                    throw new IOException(
                            "Invalid user data in row: " + line,
                            exception);
                }
            }
        }

        return loaded;
    }
}