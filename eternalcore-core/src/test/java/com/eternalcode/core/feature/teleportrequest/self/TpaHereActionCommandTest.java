package com.eternalcode.core.feature.teleportrequest.self;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestAcceptEvent;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestType;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.teleport.TeleportTaskService;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TpaHereActionCommandTest {

    @ParameterizedTest
    @CsvSource({"false, false", "true, false", "true, true"})
    void testAcceptance(boolean cancelled, boolean removeRequest) {
        World world = mock(World.class);
        when(world.getName()).thenReturn("world");
        Player requester = mock(Player.class);
        Player recipient = mock(Player.class);
        when(requester.getUniqueId()).thenReturn(UUID.randomUUID());
        when(recipient.getUniqueId()).thenReturn(UUID.randomUUID());
        when(requester.getLocation()).thenReturn(new Location(world, 10, 64, 10));
        when(recipient.getLocation()).thenReturn(new Location(world, 0, 64, 0));
        Location redirected = new Location(world, 100, 70, 100);

        TeleportRequestSettings settings = mock(TeleportRequestSettings.class);
        when(settings.tpaRequestExpire()).thenReturn(Duration.ofSeconds(80));
        when(settings.tpaTimer()).thenReturn(Duration.ofSeconds(10));
        TeleportHereRequestService requests = new TeleportHereRequestService(settings);
        requests.createRequest(requester.getUniqueId(), recipient.getUniqueId());
        TeleportTaskService tasks = mock(TeleportTaskService.class);
        NoticeService notices = mock(NoticeService.class);
        doReturn(mock(EternalCoreBroadcast.class, RETURNS_SELF)).when(notices).create();
        EventCaller events = mock(EventCaller.class);
        when(events.callEvent(any())).thenAnswer(invocation -> {
            TeleportRequestAcceptEvent event = invocation.getArgument(0);
            assertThat(event.isAsynchronous()).isFalse();
            assertThat(event.getRequester()).isSameAs(requester);
            assertThat(event.getRecipient()).isSameAs(recipient);
            assertThat(event.getTeleportingPlayer()).isSameAs(recipient);
            assertThat(event.getRequestType()).isEqualTo(TeleportRequestType.TPAHERE);
            assertThat(event.getDestination()).isEqualTo(requester.getLocation());
            event.setDestination(redirected);
            event.setCancelled(cancelled);
            if (removeRequest) {
                event.removeRequest();
            }
            return event;
        });

        new TpaHereActionCommand(requests, tasks, settings, notices, mock(Server.class), events)
            .accept(recipient, requester);

        assertThat(requests.hasRequest(requester.getUniqueId(), recipient.getUniqueId()))
            .isEqualTo(cancelled && !removeRequest);
        if (cancelled) {
            verifyNoInteractions(tasks, notices);
        }
        else {
            verify(tasks).createTeleport(
                recipient.getUniqueId(),
                PositionAdapter.convert(recipient.getLocation()),
                PositionAdapter.convert(redirected),
                settings.tpaTimer()
            );
        }
    }
}
