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
import java.time.Duration;
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
    private final Logger logger  = Logger.getLogger("test");

    @Test
    void testWithMySQL(@TempDir Path tempDir) throws SQLException {
        DatabaseConfig config = new DatabaseConfig();
        config.username = container.getUsername();
        config.password = container.getPassword();
        config.database = container.getDatabaseName();

        config.hostname = container.getHost();
        config.port = container.getFirstMappedPort();

        databaseManager = new DatabaseManager(this.logger, tempDir.toFile(), config);
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

        config.hostname = null;
        config.port = 0;
        config.database = "eternalcode";

        DatabaseManager db = new DatabaseManager(Logger.getLogger("test"), tempDir.toFile(), config);
        db.connect();

        UserRepository userRepo = new UserRepositoryOrmLite(db, new TestScheduler());

        for (int i = 0; i < 50000; i++) {
            userRepo.saveUser(new User(UUID.randomUUID(), "user" + i)).join();
        }

        IntegrationTestSpec spec = new IntegrationTestSpec();

        long start = System.nanoTime();
        var allUsers = spec.await(userRepo.fetchAllUsers(Duration.ofDays(7)));
        long allFetchTime = System.nanoTime() - start;

        start = System.nanoTime();
        var batchedUsers = spec.await(userRepo.fetchUsersBatch(500));
        long batchFetchTime = System.nanoTime() - start;


        this.logger.info(String.format("All users fetch time: %d ms", allFetchTime / 1_000_000));
        this.logger.info(String.format("Batched users fetch time: %d ms", batchFetchTime / 1_000_000));

        Assertions.assertEquals(allUsers.size(), batchedUsers.size());
    }


}
