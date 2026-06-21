package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TpaAcceptCommandTest {

    private static final int MINIMUM_ACCEPT_Y = -30;

    @Test
    void shouldNotAcceptTeleportRequestBelowMinimumYLevel() {
        Player acceptingPlayer = player("AcceptingPlayer", -31.0);
        Player requestingPlayer = player("RequestingPlayer", 70.0);
        TeleportRequestService requestService = mock(TeleportRequestService.class);
        TeleportTaskService teleportTaskService = mock(TeleportTaskService.class);
        TpaAcceptCommand command = newCommand(requestService, teleportTaskService);

        command.executeTarget(acceptingPlayer, requestingPlayer);

        verify(teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        verify(requestService, never()).removeRequest(requestingPlayer.getUniqueId());
    }

    @Test
    void shouldAcceptTeleportRequestAtMinimumYLevel() {
        Player acceptingPlayer = player("AcceptingPlayer", -30.0);
        Player requestingPlayer = player("RequestingPlayer", 70.0);
        TeleportRequestService requestService = mock(TeleportRequestService.class);
        TeleportTaskService teleportTaskService = mock(TeleportTaskService.class);
        TpaAcceptCommand command = newCommand(requestService, teleportTaskService);

        command.executeTarget(acceptingPlayer, requestingPlayer);

        verify(teleportTaskService).createTeleport(eq(requestingPlayer.getUniqueId()), any(), any(), eq(Duration.ofSeconds(10)));
        verify(requestService).removeRequest(requestingPlayer.getUniqueId());
    }

    @Test
    void shouldNotAcceptAllTeleportRequestsBelowMinimumYLevel() {
        Player acceptingPlayer = player("AcceptingPlayer", -31.0);
        TeleportRequestService requestService = mock(TeleportRequestService.class);
        TeleportTaskService teleportTaskService = mock(TeleportTaskService.class);
        TpaAcceptCommand command = newCommand(requestService, teleportTaskService);

        command.executeAll(acceptingPlayer);

        verify(requestService, never()).findRequests(acceptingPlayer.getUniqueId());
        verify(teleportTaskService, never()).createTeleport(any(), any(), any(), any());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static TpaAcceptCommand newCommand(
        TeleportRequestService requestService,
        TeleportTaskService teleportTaskService
    ) {
        TeleportRequestSettings settings = mock(TeleportRequestSettings.class);
        when(settings.tpaTimer()).thenReturn(Duration.ofSeconds(10));
        when(settings.minimumTpaAcceptY()).thenReturn(MINIMUM_ACCEPT_Y);

        NoticeService noticeService = mock(NoticeService.class);
        when(noticeService.create()).thenReturn(mock(EternalCoreBroadcast.class, RETURNS_SELF));

        return new TpaAcceptCommand(
            requestService,
            teleportTaskService,
            noticeService,
            settings,
            mock(Server.class)
        );
    }

    private static Player player(String name, double y) {
        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        when(player.getName()).thenReturn(name);
        when(player.getLocation()).thenReturn(new Location(mock(World.class), 0.0, y, 0.0));

        return player;
    }
}
