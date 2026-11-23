package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.teleport.TeleportTaskService;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TpaAcceptCommandTest {

    private final World world = mock(World.class);
    private final Server server = mock(Server.class);
    private final TeleportRequestSettings settings = mock(TeleportRequestSettings.class);
    private final NoticeService notices = mock(NoticeService.class);
    private final TeleportTaskService tasks = mock(TeleportTaskService.class);
    private final EventCaller events = mock(EventCaller.class);

    private TeleportRequestService requests() {
        doReturn(mock(EternalCoreBroadcast.class, RETURNS_SELF)).when(this.notices).create();
        when(this.world.getName()).thenReturn("world");
        when(this.settings.tpaRequestExpire()).thenReturn(Duration.ofSeconds(80));
        when(this.settings.tpaTimer()).thenReturn(Duration.ofSeconds(10));
        when(this.settings.minimumTpaAcceptY()).thenReturn(-60);
        return new TeleportRequestService(this.settings);
    }

    private Player player() {
        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        when(player.getLocation()).thenReturn(new Location(this.world, 0, 64, 0));
        when(this.server.getPlayer(player.getUniqueId())).thenReturn(player);
        return player;
    }

    @ParameterizedTest
    @CsvSource({
        "false, false, false", "false, true, false", "false, true, true",
        "true, false, false", "true, true, false", "true, true, true"
    })
    void testAcceptance(boolean all, boolean cancelled, boolean removeRequest) {
        TeleportRequestService requests = this.requests();
        Player requester = this.player();
        Player recipient = this.player();
        requests.createRequest(requester.getUniqueId(), recipient.getUniqueId());
        Location redirected = new Location(this.world, 100, 70, 100);

        when(this.events.callEvent(any())).thenAnswer(invocation -> {
            TeleportRequestAcceptEvent event = invocation.getArgument(0);
            assertThat(event.isAsynchronous()).isFalse();
            assertThat(event.getRequester()).isSameAs(requester);
            assertThat(event.getRecipient()).isSameAs(recipient);
            assertThat(event.getTeleportingPlayer()).isSameAs(requester);
            assertThat(event.getRequestType()).isEqualTo(TeleportRequestType.TPA);
            assertThat(event.getDestination()).isEqualTo(recipient.getLocation());
            assertThat(requests.hasRequest(requester.getUniqueId(), recipient.getUniqueId())).isTrue();
            event.setDestination(redirected);
            event.setCancelled(cancelled);
            if (removeRequest) {
                event.removeRequest();
            }
            return event;
        });

        TpaAcceptCommand command = new TpaAcceptCommand(
            requests, this.tasks, this.notices, this.settings, this.server, this.events
        );
        if (all) {
            command.executeAll(recipient);
        }
        else {
            command.executeTarget(recipient, requester);
        }

        assertThat(requests.hasRequest(requester.getUniqueId(), recipient.getUniqueId()))
            .isEqualTo(cancelled && !removeRequest);
        verify(this.events).callEvent(any(TeleportRequestAcceptEvent.class));
        if (cancelled) {
            verifyNoInteractions(this.tasks, this.notices);
        }
        else {
            verify(this.tasks).createTeleport(
                requester.getUniqueId(),
                PositionAdapter.convert(requester.getLocation()),
                PositionAdapter.convert(redirected),
                this.settings.tpaTimer()
            );
        }
    }

    @Test
    void testCancellingOneRequestDoesNotStopBulkAcceptance() {
        TeleportRequestService requests = this.requests();
        Player first = this.player();
        Player second = this.player();
        Player recipient = this.player();
        requests.createRequest(first.getUniqueId(), recipient.getUniqueId());
        requests.createRequest(second.getUniqueId(), recipient.getUniqueId());
        when(this.events.callEvent(any())).thenAnswer(invocation -> {
            TeleportRequestAcceptEvent event = invocation.getArgument(0);
            event.setCancelled(event.getRequester() == first);
            return event;
        });

        new TpaAcceptCommand(requests, this.tasks, this.notices, this.settings, this.server, this.events)
            .executeAll(recipient);

        assertThat(requests.hasRequest(first.getUniqueId(), recipient.getUniqueId())).isTrue();
        assertThat(requests.hasRequest(second.getUniqueId(), recipient.getUniqueId())).isFalse();
        verify(this.tasks).createTeleport(eq(second.getUniqueId()), any(), any(), any());
        verify(this.tasks, never()).createTeleport(eq(first.getUniqueId()), any(), any(), any());
    }

    @Test
    void testExplicitRemovalDoesNotRemoveRequestToAnotherRecipient() {
        TeleportRequestService requests = this.requests();
        Player requester = this.player();
        Player recipient = this.player();
        Player anotherRecipient = this.player();
        requests.createRequest(requester.getUniqueId(), recipient.getUniqueId());
        when(this.events.callEvent(any())).thenAnswer(invocation -> {
            TeleportRequestAcceptEvent event = invocation.getArgument(0);
            requests.createRequest(requester.getUniqueId(), anotherRecipient.getUniqueId());
            event.removeRequest();
            event.setCancelled(true);
            return event;
        });

        new TpaAcceptCommand(requests, this.tasks, this.notices, this.settings, this.server, this.events)
            .executeTarget(recipient, requester);

        assertThat(requests.hasRequest(requester.getUniqueId(), anotherRecipient.getUniqueId())).isTrue();
        verifyNoInteractions(this.tasks, this.notices);
    }

    @Test
    void testMinimumHeightCheckRunsBeforeEvent() {
        TeleportRequestService requests = this.requests();
        Player requester = this.player();
        Player recipient = this.player();
        when(recipient.getLocation()).thenReturn(new Location(this.world, 0, -61, 0));
        requests.createRequest(requester.getUniqueId(), recipient.getUniqueId());

        new TpaAcceptCommand(requests, this.tasks, this.notices, this.settings, this.server, this.events)
            .executeTarget(recipient, requester);

        verifyNoInteractions(this.events, this.tasks);
        assertThat(requests.hasRequest(requester.getUniqueId(), recipient.getUniqueId())).isTrue();
    }
}
