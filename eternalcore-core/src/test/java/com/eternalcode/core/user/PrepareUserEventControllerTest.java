package com.eternalcode.core.user;

import com.eternalcode.core.test.MockServer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.eternalcode.core.test.OptionAssertions.assertOptionEmpty;
import static com.eternalcode.core.test.OptionAssertions.assertOptionPresent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrepareUserEventControllerTest {

    private static final UUID PLAYER_UUID = UUID.randomUUID();
    private static final String PLAYER_NAME = "Martin";

    private MockServer mockServer;
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        this.mockServer = new MockServer();
        this.userManager = new UserManager();

        PrepareUserController controller = new PrepareUserController(this.userManager, this.mockServer.getServer());

        this.mockServer.listenJoin(controller::onJoin);
        this.mockServer.listenQuit(controller::onQuit);
        this.mockServer.listenKick(controller::onKick);
    }

    @Test
    @DisplayName("should create user and set ClientBukkitSettings for user")
    void testJoinPlayer() {
        assertOptionEmpty(this.userManager.getUser(PLAYER_UUID));

        this.mockServer.joinPlayer(PLAYER_NAME, PLAYER_UUID);
        User user = assertOptionPresent(this.userManager.getUser(PLAYER_UUID));

        UserClientSettings userClientSettings = user.getClientSettings();
        assertTrue(userClientSettings.isOnline());
        assertInstanceOf(UserClientBukkitSettings.class, userClientSettings);
    }

    @Test
    @DisplayName("should create user and reset client settings to NONE after quit")
    void testPlayerQuit() {
        Player player = this.mockServer.joinPlayer(PLAYER_NAME, PLAYER_UUID);
        User user = assertOptionPresent(this.userManager.getUser(PLAYER_UUID));

        this.mockServer.quitPlayer(player);

        UserClientSettings userClientSettings = user.getClientSettings();
        assertTrue(userClientSettings.isOffline());
        assertEquals(UserClientSettings.NONE, userClientSettings);
    }

    @Test
    @DisplayName("should create user and reset client settings to NONE after kick")
    void testPlayerKick() {
        Player player = this.mockServer.joinPlayer(PLAYER_NAME, PLAYER_UUID);
        User user = assertOptionPresent(this.userManager.getUser(PLAYER_UUID));

        this.mockServer.kickPlayer(player);

        UserClientSettings userClientSettings = user.getClientSettings();
        assertTrue(userClientSettings.isOffline());
        assertEquals(UserClientSettings.NONE, userClientSettings);
    }

}
