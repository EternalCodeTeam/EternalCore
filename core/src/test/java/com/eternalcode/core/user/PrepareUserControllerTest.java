package com.eternalcode.core.user;

import com.eternalcode.core.user.client.ClientBukkitSettings;
import com.eternalcode.core.user.client.ClientSettings;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import panda.std.Option;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrepareUserControllerTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();
    private static final String FAKE_NAME = "Martin";

    private PrepareUserController prepareUserController;

    @Mock
    private UserManager userManager;

    @Mock
    private Server server;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.prepareUserController = new PrepareUserController(userManager, server);
    }

    @Test
    void createUserAndSetClientSettings() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        when(player.getName()).thenReturn(FAKE_NAME);

        PlayerJoinEvent event = new PlayerJoinEvent(player, "fake join message");

        User user = mock(User.class);
        when(this.userManager.getOrCreate(FAKE_UUID, FAKE_NAME)).thenReturn(user);

        this.prepareUserController.onJoin(event);

        verify(user).setClientSettings(any(ClientBukkitSettings.class));
    }

    @Test
    void removeClientSettingIfPlayerQuit() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        PlayerQuitEvent event = new PlayerQuitEvent(player, "fake quit message");

        User user = mock(User.class);
        when(this.userManager.getUser(FAKE_UUID)).thenReturn(Option.of(user));

        this.prepareUserController.onQuit(event);

        verify(user).setClientSettings(ClientSettings.NONE);
    }

    @Test
    void removeClientSettingIfPlayerKick() {
        Player player = mock(Player.class);

        when(player.getUniqueId()).thenReturn(FAKE_UUID);
        PlayerKickEvent event = new PlayerKickEvent(player, "fake kick message", "fake kicker");

        User user = mock(User.class);
        when(this.userManager.getUser(FAKE_UUID)).thenReturn(Option.of(user));

        this.prepareUserController.onKick(event);

        verify(user).setClientSettings(ClientSettings.NONE);
    }
}
