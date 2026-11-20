package com.eternalcode.core.feature.teleportrequest.self;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.invalidusage.InvalidUsageException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TpaHereActionCommandTest {

    private static final Duration TPA_TIMER = Duration.ofSeconds(5);

    private final World world = mock(World.class);

    private TeleportRequestSettings settings;
    private TeleportHereRequestService requestService;
    private TeleportTaskService teleportTaskService;
    private NoticeService noticeService;
    private EternalCoreBroadcast<?, ?, ?> broadcast;
    private Server server;
    private TpaHereActionCommand command;

    private Player player;
    private Player firstRequester;
    private Player secondRequester;

    @BeforeEach
    void setUp() {
        when(this.world.getName()).thenReturn("world");

        this.settings = mock(TeleportRequestSettings.class);
        when(this.settings.tpaRequestExpire()).thenReturn(Duration.ofMinutes(1));
        when(this.settings.tpaTimer()).thenReturn(TPA_TIMER);

        this.requestService = new TeleportHereRequestService(this.settings);
        this.teleportTaskService = mock(TeleportTaskService.class);
        this.noticeService = mock(NoticeService.class);
        this.broadcast = mock(EternalCoreBroadcast.class, RETURNS_SELF);
        when(this.noticeService.create()).thenAnswer(invocation -> this.broadcast);
        this.server = mock(Server.class);

        this.command = new TpaHereActionCommand(this.requestService, this.teleportTaskService, this.settings, this.noticeService, this.server);

        this.player = this.mockPlayer("Player", 0);
        this.firstRequester = this.mockPlayer("First", 100);
        this.secondRequester = this.mockPlayer("Second", 200);
    }

    @Test
    void shouldAcceptLatestRequestWhenNoPlayerSpecified() throws InterruptedException {
        this.requestService.createRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId());
        Thread.sleep(5);
        this.requestService.createRequest(this.secondRequester.getUniqueId(), this.player.getUniqueId());

        this.command.accept(this.player, Optional.empty());

        this.verifyPlayerTeleportedTo(200);
        assertThat(this.requestService.hasRequest(this.secondRequester.getUniqueId(), this.player.getUniqueId())).isFalse();
        assertThat(this.requestService.hasRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId())).isTrue();
        verify(this.broadcast).player(this.player.getUniqueId());
        verify(this.broadcast).player(this.secondRequester.getUniqueId());
    }

    @Test
    void shouldAcceptSpecifiedRequest() throws InterruptedException {
        this.requestService.createRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId());
        Thread.sleep(5);
        this.requestService.createRequest(this.secondRequester.getUniqueId(), this.player.getUniqueId());

        this.command.accept(this.player, Optional.of(this.firstRequester));

        this.verifyPlayerTeleportedTo(100);
        assertThat(this.requestService.hasRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId())).isFalse();
        assertThat(this.requestService.hasRequest(this.secondRequester.getUniqueId(), this.player.getUniqueId())).isTrue();
    }

    @Test
    void shouldRequireExplicitNameWhenConfigured() {
        when(this.settings.requireExplicitNameInTpa()).thenReturn(true);
        this.requestService.createRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId());

        assertThatThrownBy(() -> this.command.accept(this.player, Optional.empty()))
            .isInstanceOf(InvalidUsageException.class);

        verify(this.teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        assertThat(this.requestService.hasRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId())).isTrue();
    }

    @Test
    void shouldNotifyWhenNoRequest() {
        this.command.accept(this.player, Optional.empty());

        verify(this.teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        verify(this.noticeService).player(eq(this.player.getUniqueId()), any());
    }

    @Test
    void shouldNotifyWhenLatestRequesterIsOffline() {
        this.requestService.createRequest(UUID.randomUUID(), this.player.getUniqueId());

        this.command.accept(this.player, Optional.empty());

        verify(this.teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        verify(this.noticeService).player(eq(this.player.getUniqueId()), any());
    }

    @Test
    void shouldDenySpecifiedRequest() {
        this.requestService.createRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId());

        this.command.executeTarget(this.player, this.firstRequester);

        verify(this.teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        assertThat(this.requestService.hasRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId())).isFalse();
        verify(this.broadcast).player(this.firstRequester.getUniqueId());
    }

    @Test
    void shouldDenyAllRequests() {
        this.requestService.createRequest(this.firstRequester.getUniqueId(), this.player.getUniqueId());
        this.requestService.createRequest(this.secondRequester.getUniqueId(), this.player.getUniqueId());

        this.command.executeAll(this.player);

        verify(this.teleportTaskService, never()).createTeleport(any(), any(), any(), any());
        assertThat(this.requestService.findRequests(this.player.getUniqueId())).isEmpty();
        verify(this.broadcast).player(this.firstRequester.getUniqueId());
        verify(this.broadcast).player(this.secondRequester.getUniqueId());
    }

    private void verifyPlayerTeleportedTo(double destinationX) {
        ArgumentCaptor<Position> destination = ArgumentCaptor.forClass(Position.class);

        verify(this.teleportTaskService).createTeleport(eq(this.player.getUniqueId()), any(), destination.capture(), eq(TPA_TIMER));
        assertThat(destination.getValue().x()).isEqualTo(destinationX);
    }

    private Player mockPlayer(String name, double x) {
        Player player = mock(Player.class);
        UUID uniqueId = UUID.randomUUID();

        when(player.getName()).thenReturn(name);
        when(player.getUniqueId()).thenReturn(uniqueId);
        when(player.getLocation()).thenAnswer(invocation -> new Location(this.world, x, 0, 0));
        when(this.server.getPlayer(uniqueId)).thenReturn(player);

        return player;
    }

}
