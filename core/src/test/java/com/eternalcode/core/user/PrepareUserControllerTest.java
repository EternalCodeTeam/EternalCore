package com.eternalcode.core.user;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.eternalcode.core.user.client.ClientBukkitSettings;
import com.eternalcode.core.user.client.ClientSettings;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import panda.std.Option;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrepareUserControllerTest {

    private static ServerMock server;
    private static MockPlugin plugin;

    @BeforeAll
    public static void load() {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }

    @Test
    void testOnJoinAndQuit() {
        UserManager userManager = new UserManager();
        PrepareUserController prepareUserController = new PrepareUserController(userManager, server);

        server.getPluginManager().registerEvents(prepareUserController, plugin);
        PlayerMock playerMock = server.addPlayer("vLucky");

        Option<User> userOption = userManager.getUser("vLucky");
        assertTrue(userOption.isPresent());

        User user = userOption.get();
        assertEquals("vLucky", user.getName());

        {
            ClientSettings clientSettings = user.getClientSettings();
            assertTrue(clientSettings.isOnline());

            assertInstanceOf(ClientBukkitSettings.class, clientSettings);
        }

        {
            playerMock.kick(Component.empty());

            ClientSettings clientSettings = user.getClientSettings();
            assertTrue(clientSettings.isOffline());
            assertEquals(ClientSettings.NONE, clientSettings);
        }
    }

}
