package com.eternalcode.core.user;

import com.eternalcode.core.user.client.ClientSettings;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrepareUserControllerTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();
    private static final String FAKE_NAME = "Martin";

    private UserManager userManager;
    private PrepareUserController prepareUserController;

    private AutoCloseable mocks;

    @Mock
    private Server server;

    @BeforeEach
    void setUp() {
        this.mocks = MockitoAnnotations.openMocks(this);
        this.userManager = new UserManager();
        this.prepareUserController = new PrepareUserController(this.userManager, this.server);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mocks.close();
    }

    @Test
    void createUserAndSetClientSettings() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        when(player.getName()).thenReturn(FAKE_NAME);

        PlayerJoinEvent event = new PlayerJoinEvent(player, "fake join message");

        User user = new User(FAKE_UUID, FAKE_NAME);
        User result = this.userManager.getOrCreate(FAKE_UUID, FAKE_NAME);
        assertEquals(user, result);

        this.prepareUserController.onJoin(event);

        ClientSettings clientSettings = user.getClientSettings();
        assertTrue(clientSettings.isOnline());
    }

    @Test
    void removeClientSettingIfPlayerQuit() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        PlayerQuitEvent event = new PlayerQuitEvent(player, "fake quit message");

        User user = new User(FAKE_UUID, FAKE_NAME);
        User result = this.userManager.getOrCreate(FAKE_UUID, FAKE_NAME);
        assertEquals(user, result);

        this.prepareUserController.onQuit(event);

        ClientSettings clientSettings = user.getClientSettings();
        assertTrue(clientSettings.isOffline());
        assertEquals(ClientSettings.NONE, clientSettings);
    }

    @Test
    void removeClientSettingIfPlayerKick() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        PlayerKickEvent event = new PlayerKickEvent(player, "fake kick message", "fake kicker");

        User user = new User(FAKE_UUID, FAKE_NAME);
        User result = this.userManager.getOrCreate(FAKE_UUID, FAKE_NAME);
        assertEquals(user, result);

        this.prepareUserController.onKick(event);

        ClientSettings clientSettings = user.getClientSettings();
        assertTrue(clientSettings.isOffline());
        assertEquals(ClientSettings.NONE, clientSettings);
    }
}
