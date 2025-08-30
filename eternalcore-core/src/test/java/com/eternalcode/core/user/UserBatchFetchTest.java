package com.eternalcode.core.user;

import com.eternalcode.core.database.DatabaseConfig;
import com.eternalcode.core.database.DatabaseDriverType;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.user.database.UserRepository;
import com.eternalcode.core.user.database.UserRepositoryConfig;
import com.eternalcode.core.user.database.UserRepositoryOrmLite;
import com.eternalcode.core.util.IntegrationTestSpec;
import com.eternalcode.core.util.TestScheduler;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class UserBatchFetchTest extends IntegrationTestSpec {

    @Container
    private static final MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("testdb");

    private final TestScheduler testScheduler = new TestScheduler();

    private DatabaseManager databaseManager;

    @Test
    void testWithMySQL(@TempDir Path tempDir) throws SQLException {
        DatabaseConfig config = new DatabaseConfig();
        config.username = container.getUsername();
        config.password = container.getPassword();
        config.database = container.getDatabaseName();

        config.hostname = container.getHost();
        config.port = container.getFirstMappedPort();

        databaseManager = new DatabaseManager(Logger.getLogger("test"), tempDir.toFile(), config);
        databaseManager.connect();

        UserRepository userRepository = new UserRepositoryOrmLite(databaseManager, this.testScheduler);
        UserManager userManager = new UserManager(userRepository, new UserRepositoryConfig());

        Assertions.assertEquals(0, userManager.getUsers().size());

        UUID randomUUID = UUID.randomUUID();
        userManager.getOrCreate(randomUUID, "test1");

        Assertions.assertEquals(1, userManager.getUsers().size());

        userRepository.getUser(randomUUID).thenAccept(user -> {
            Assertions.assertNotNull(user);
            Assertions.assertEquals("test1", user.get().getName());
        });

        databaseManager.close();
    }

    @Test
    void testBatchVsAllFetch(@TempDir Path tempDir) throws Exception {
        DatabaseConfig config = new DatabaseConfig();
        config.databaseType = DatabaseDriverType.H2_TEST;
        config.username = "sa";
        config.password = "";

        // ✅ Use H2 in-memory database
        config.hostname = null; // not used
        config.port = 0;        // not used
        config.database = "eternalcode"; // any name works

        DatabaseManager db = new DatabaseManager(Logger.getLogger("test"), tempDir.toFile(), config);
        db.connect();

        UserRepository userRepo = new UserRepositoryOrmLite(db, new TestScheduler());

        // Insert 5000 users
        for (int i = 0; i < 50000; i++) {
            userRepo.saveUser(new User(UUID.randomUUID(), "user" + i)).join();
        }

        long start = System.nanoTime();
        var allUsers = userRepo.fetchAllUsers().join();
        long allFetchTime = System.nanoTime() - start;

        start = System.nanoTime();
        var batchedUsers = userRepo.fetchUsersBatch(500).join();
        long batchFetchTime = System.nanoTime() - start;

        System.out.printf("All fetch: %d ms, Batched fetch: %d ms%n",
            allFetchTime / 1_000_000, batchFetchTime / 1_000_000);

        Assertions.assertEquals(allUsers.size(), batchedUsers.size());
    }


}
