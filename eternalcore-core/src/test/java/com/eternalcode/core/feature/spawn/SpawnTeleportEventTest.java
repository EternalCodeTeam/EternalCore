package com.eternalcode.core.feature.spawn;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.spawn.event.PreSpawnTeleportEvent;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.viewer.Viewer;
import java.time.Duration;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpawnTeleportEventTest {

    @ParameterizedTest
    @CsvSource({
        "delayed, false", "delayed, true",
        "bypass, false", "bypass, true",
        "other, false", "other, true",
        "api, false", "api, true"
    })
    void testSpawnTeleportEvent(String mode, boolean cancelled) {
        World world = mock(World.class);
        when(world.getName()).thenReturn("world");
        Player player = mock(Player.class);
        UUID playerId = UUID.randomUUID();
        Location start = new Location(world, 0, 64, 0);
        Location destination = new Location(world, 100, 64, 100);
        Location redirected = new Location(world, 200, 70, 200);
        when(player.getUniqueId()).thenReturn(playerId);
        when(player.getLocation()).thenReturn(start);
        when(player.hasPermission("eternalcore.spawn.bypass")).thenReturn(mode.equals("bypass"));

        TeleportTaskService tasks = mock(TeleportTaskService.class);
        TeleportService teleports = mock(TeleportService.class);
        NoticeService notices = mock(NoticeService.class);
        doReturn(mock(EternalCoreBroadcast.class, RETURNS_SELF)).when(notices).create();
        SpawnService spawn = mock(SpawnService.class);
        when(spawn.getSpawnLocation()).thenReturn(destination);
        SpawnJoinSettings settings = mock(SpawnJoinSettings.class);
        Duration delay = Duration.ofSeconds(5);
        when(settings.spawnTeleportTime()).thenReturn(delay);
        EventCaller events = mock(EventCaller.class);
        when(events.callEvent(any())).thenAnswer(invocation -> {
            PreSpawnTeleportEvent event = invocation.getArgument(0);
            assertThat(event.isAsynchronous()).isFalse();
            assertThat(event.getPlayer()).isSameAs(player);
            assertThat(event.getDestination()).isEqualTo(destination);
            event.setDestination(redirected);
            event.setCancelled(cancelled);
            return event;
        });

        SpawnCommand command = new SpawnCommand(tasks, settings, teleports, notices, spawn, events);
        switch (mode) {
            case "delayed", "bypass" -> command.executeSelf(player);
            case "other" -> command.execute(mock(Viewer.class), player);
            case "api" -> {
                LocationsConfiguration locations = new LocationsConfiguration();
                locations.spawn = PositionAdapter.convert(destination);
                SpawnServiceImpl service = new SpawnServiceImpl(
                    locations, mock(ConfigurationManager.class), Logger.getAnonymousLogger(), events
                );
                try (var bukkit = mockStatic(Bukkit.class)) {
                    bukkit.when(() -> Bukkit.getWorld("world")).thenReturn(world);
                    service.teleportToSpawn(player);
                }
            }
            default -> throw new IllegalArgumentException(mode);
        }

        verify(events).callEvent(any(PreSpawnTeleportEvent.class));
        if (cancelled) {
            verify(tasks, never()).createTeleport(any(), any(), any(), any());
            verifyNoInteractions(teleports, notices);
            verify(player, never()).teleportAsync(any(Location.class));
        }
        else if (mode.equals("delayed")) {
            verify(tasks).createTeleport(
                playerId, PositionAdapter.convert(start), PositionAdapter.convert(redirected), delay
            );
        }
        else if (mode.equals("api")) {
            verify(player).teleportAsync(redirected);
        }
        else {
            verify(teleports).teleport(player, redirected);
        }
    }
}
