package com.eternalcode.core.feature.teleportrequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

class TpaAcceptCommandTest {

    @Test
    void shouldAcceptTeleportRequestAtNegativeYByDefault() {
        Fixture fixture = new Fixture();
        Player requester = fixture.playerAtY(64);
        Player recipient = fixture.playerAtY(-64);

        fixture.command.executeTarget(recipient, requester);

        verify(fixture.teleportTaskService).createTeleport(
            eq(requester.getUniqueId()),
            any(Position.class),
            any(Position.class),
            eq(fixture.settings.tpaTimer())
        );
        verify(fixture.requestService).removeRequest(requester.getUniqueId());
    }

    @Test
    void shouldRejectTeleportRequestBelowExplicitMinimumY() {
        Fixture fixture = new Fixture();
        fixture.settings.minimumTpaAcceptY = -32;
        Player requester = fixture.playerAtY(64);
        Player recipient = fixture.playerAtY(-64);

        fixture.command.executeTarget(recipient, requester);

        verifyNoInteractions(fixture.teleportTaskService);
        verify(fixture.requestService, never()).removeRequest(any());
        verify(fixture.broadcast).placeholder("{Y}", "-32");
        verify(fixture.broadcast).send();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class Fixture {

        private final TeleportRequestConfig settings = new TeleportRequestConfig();
        private final TeleportRequestService requestService = mock(TeleportRequestService.class);
        private final TeleportTaskService teleportTaskService = mock(TeleportTaskService.class);
        private final NoticeService noticeService = mock(NoticeService.class);
        private final Server server = mock(Server.class);
        private final EternalCoreBroadcast broadcast = mock(EternalCoreBroadcast.class, Answers.RETURNS_SELF);
        private final TpaAcceptCommand command;

        private Fixture() {
            when(this.noticeService.create()).thenReturn(this.broadcast);
            this.command = new TpaAcceptCommand(
                this.requestService,
                this.teleportTaskService,
                this.noticeService,
                this.settings,
                this.server
            );
        }

        private Player playerAtY(double y) {
            UUID uniqueId = UUID.randomUUID();
            World world = mock(World.class);
            when(world.getUID()).thenReturn(UUID.randomUUID());
            when(world.getName()).thenReturn("world");

            Player player = mock(Player.class);
            when(player.getUniqueId()).thenReturn(uniqueId);
            when(player.getName()).thenReturn("Player");
            when(player.getLocation()).thenReturn(new Location(world, 0, y, 0));

            return player;
        }
    }
}
