package com.eternalcode.core.feature.vanish;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VanishInvisibleServiceTest {

    @Test
    void shouldDisableCollisionWhenPlayerIsHidden() {
        Player vanishedPlayer = player("Vanished", true);
        VanishInvisibleService vanishInvisibleService = newVanishInvisibleService(List.of(vanishedPlayer));

        vanishInvisibleService.hidePlayer(vanishedPlayer);

        verify(vanishedPlayer).setCollidable(false);
    }

    @Test
    void shouldRestorePreviousCollisionStateWhenPlayerIsShown() {
        Player vanishedPlayer = player("Vanished", true);
        VanishInvisibleService vanishInvisibleService = newVanishInvisibleService(List.of(vanishedPlayer));

        vanishInvisibleService.hidePlayer(vanishedPlayer);
        vanishInvisibleService.showPlayer(vanishedPlayer);

        verify(vanishedPlayer).setCollidable(true);
    }

    @Test
    void shouldKeepCollisionDisabledWhenItWasDisabledBeforeVanish() {
        Player vanishedPlayer = player("Vanished", false);
        VanishInvisibleService vanishInvisibleService = newVanishInvisibleService(List.of(vanishedPlayer));

        vanishInvisibleService.hidePlayer(vanishedPlayer);
        vanishInvisibleService.showPlayer(vanishedPlayer);

        verify(vanishedPlayer, never()).setCollidable(true);
    }

    private static VanishInvisibleService newVanishInvisibleService(List<Player> onlinePlayers) {
        Server server = mock(Server.class);
        doReturn((Collection<? extends Player>) onlinePlayers).when(server).getOnlinePlayers();

        Plugin plugin = mock(Plugin.class);
        when(plugin.getServer()).thenReturn(server);

        return new VanishInvisibleService(plugin);
    }

    private static Player player(String name, boolean collidable) {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn(name);
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        when(player.isCollidable()).thenReturn(collidable);

        return player;
    }
}
