package com.eternalcode.core.feature.kit.claim;

import static com.eternalcode.core.feature.kit.KitTestFactory.kit;
import static com.eternalcode.core.feature.kit.KitTestFactory.recurring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitClaimStatus;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.KitRequirements;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.event.KitClaimEvent;
import com.eternalcode.core.feature.kit.event.PreKitClaimEvent;
import com.eternalcode.core.feature.kit.usage.KitUsageStore;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitClaimServiceImplTest {

    private final UUID playerUniqueId = UUID.randomUUID();

    private KitUsageService usageService;
    private KitUsageStore usageStore;
    private KitItemDelivery itemDelivery;
    private EventCaller eventCaller;
    private Player player;
    private KitClaimServiceImpl claimService;

    @BeforeEach
    void setUp() {
        this.usageService = mock(KitUsageService.class);
        this.usageStore = mock(KitUsageStore.class);
        this.itemDelivery = mock(KitItemDelivery.class);
        this.eventCaller = mock(EventCaller.class);
        this.player = mock(Player.class);

        World world = mock(World.class);
        when(world.getName()).thenReturn("world");
        when(this.player.getUniqueId()).thenReturn(this.playerUniqueId);
        when(this.player.getWorld()).thenReturn(world);
        when(this.player.getGameMode()).thenReturn(GameMode.SURVIVAL);
        when(this.player.hasPermission(anyString())).thenReturn(true);
        when(this.player.hasPermission(KitPermissions.BYPASS_COOLDOWN)).thenReturn(false);
        when(this.usageService.isLoaded(this.playerUniqueId)).thenReturn(true);
        when(this.usageService.getUsage(eq(this.playerUniqueId), any(Kit.class))).thenReturn(KitUsage.neverClaimed());
        when(this.usageService.getRemainingCooldown(eq(this.playerUniqueId), any(Kit.class))).thenReturn(Optional.empty());
        when(this.eventCaller.callEvent(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.claimService = new KitClaimServiceImpl(
            this.usageService,
            this.usageStore,
            new KitRequirementChecker(),
            this.itemDelivery,
            new KitClaimLock(),
            this.eventCaller
        );
    }

    @Test
    void shouldClaimAvailableKit() {
        Kit kit = recurring("daily", Duration.ofDays(1));

        KitClaimResult result = this.claimService.claimKit(this.player, kit);

        assertThat(result.status()).isEqualTo(KitClaimStatus.SUCCESS);
        verify(this.usageStore).recordClaim(eq(this.playerUniqueId), eq("daily"), any(Instant.class));
        verify(this.itemDelivery).deliver(this.player, kit);
        verify(this.itemDelivery).dispatchCommands(this.player, kit);
        verify(this.eventCaller).callEvent(any(KitClaimEvent.class));
    }

    @Test
    void shouldDenyKitWithoutPermission() {
        Kit kit = recurring("vip", Duration.ZERO);
        when(this.player.hasPermission(kit.permission())).thenReturn(false);

        assertThat(this.claimService.claimKit(this.player, kit).status()).isEqualTo(KitClaimStatus.NO_PERMISSION);
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldDenyKitOnCooldownWithRemainingTime() {
        Kit kit = recurring("daily", Duration.ofDays(1));
        when(this.usageService.getRemainingCooldown(this.playerUniqueId, kit)).thenReturn(Optional.of(Duration.ofHours(5)));

        KitClaimResult result = this.claimService.claimKit(this.player, kit);

        assertThat(result.status()).isEqualTo(KitClaimStatus.ON_COOLDOWN);
        assertThat(result.remaining()).isEqualTo(Duration.ofHours(5));
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldIgnoreCooldownWithBypassPermission() {
        Kit kit = recurring("daily", Duration.ofDays(1));
        when(this.usageService.getRemainingCooldown(this.playerUniqueId, kit)).thenReturn(Optional.of(Duration.ofHours(5)));
        when(this.player.hasPermission(KitPermissions.BYPASS_COOLDOWN)).thenReturn(true);

        assertThat(this.claimService.claimKit(this.player, kit).isSuccess()).isTrue();
    }

    @Test
    void shouldAllowOneTimeKitOnlyOnce() {
        Kit kit = kit("welcome").type(KitType.ONE_TIME).build();
        when(this.usageService.getUsage(this.playerUniqueId, kit)).thenReturn(new KitUsage(Instant.now(), 1));

        assertThat(this.claimService.claimKit(this.player, kit).status()).isEqualTo(KitClaimStatus.ALREADY_CLAIMED);
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldAllowOneTimeKitAgainAfterReset() {
        Kit kit = kit("welcome").type(KitType.ONE_TIME).build();
        when(this.usageService.getUsage(this.playerUniqueId, kit)).thenReturn(new KitUsage(null, 1));

        assertThat(this.claimService.claimKit(this.player, kit).isSuccess()).isTrue();
    }

    @Test
    void shouldDenyClaimUntilPlayerDataIsLoaded() {
        Kit kit = recurring("daily", Duration.ofDays(1));
        when(this.usageService.isLoaded(this.playerUniqueId)).thenReturn(false);

        assertThat(this.claimService.claimKit(this.player, kit).status()).isEqualTo(KitClaimStatus.DATA_NOT_LOADED);
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldCheckWorldGameModeAndPlaytimeRequirements() {
        Kit inNether = kit("nether").requirements(KitRequirements.of(Set.of("world_nether"), Set.of(), Duration.ZERO)).build();
        Kit inCreative = kit("creative").requirements(KitRequirements.of(Set.of(), Set.of(GameMode.CREATIVE), Duration.ZERO)).build();
        Kit veteran = kit("veteran").requirements(KitRequirements.of(Set.of(), Set.of(), Duration.ofHours(10))).build();
        when(this.player.getStatistic(Statistic.PLAY_ONE_MINUTE)).thenReturn((int) Duration.ofHours(4).toSeconds() * 20);

        assertThat(this.claimService.claimKit(this.player, inNether).status()).isEqualTo(KitClaimStatus.WRONG_WORLD);
        assertThat(this.claimService.claimKit(this.player, inCreative).status()).isEqualTo(KitClaimStatus.WRONG_GAME_MODE);

        KitClaimResult playtimeResult = this.claimService.claimKit(this.player, veteran);
        assertThat(playtimeResult.status()).isEqualTo(KitClaimStatus.NOT_ENOUGH_PLAYTIME);
        assertThat(playtimeResult.remaining()).isEqualTo(Duration.ofHours(6));
    }

    @Test
    void shouldCancelWhenInventoryIsFullAndBehaviorIsCancel() {
        Kit kit = recurring("daily", Duration.ZERO);
        when(this.itemDelivery.isCancelledWhenFull()).thenReturn(true);
        when(this.itemDelivery.fits(eq(this.player), any())).thenReturn(false);

        assertThat(this.claimService.claimKit(this.player, kit).status()).isEqualTo(KitClaimStatus.INVENTORY_FULL);
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldNotRecordClaimWhenPreEventIsCancelled() {
        Kit kit = recurring("daily", Duration.ofDays(1));
        when(this.eventCaller.callEvent(any(PreKitClaimEvent.class))).thenAnswer(invocation -> {
            PreKitClaimEvent event = invocation.getArgument(0);
            event.setCancelled(true);
            return event;
        });

        assertThat(this.claimService.claimKit(this.player, kit).status()).isEqualTo(KitClaimStatus.CANCELLED);
        this.verifyNothingGiven(kit);
    }

    @Test
    void shouldRejectNestedClaimOfTheSamePlayer() {
        Kit kit = recurring("daily", Duration.ZERO);
        AtomicReference<KitClaimResult> nestedResult = new AtomicReference<>();
        when(this.eventCaller.callEvent(any(PreKitClaimEvent.class))).thenAnswer(invocation -> {
            nestedResult.set(this.claimService.claimKit(this.player, kit));
            return invocation.getArgument(0);
        });

        assertThat(this.claimService.claimKit(this.player, kit).isSuccess()).isTrue();
        assertThat(nestedResult.get().status()).isEqualTo(KitClaimStatus.ALREADY_CLAIMING);
        verify(this.usageStore).recordClaim(eq(this.playerUniqueId), eq("daily"), any(Instant.class));
    }

    @Test
    void shouldGiveKitAsAdminWithoutChecksAndWithoutRecordingClaim() {
        Kit kit = kit("welcome").type(KitType.ONE_TIME).build();
        when(this.player.hasPermission(kit.permission())).thenReturn(false);
        when(this.usageService.getUsage(this.playerUniqueId, kit)).thenReturn(new KitUsage(Instant.now(), 1));
        when(this.usageService.isLoaded(this.playerUniqueId)).thenReturn(false);

        assertThat(this.claimService.giveKit(this.player, kit).isSuccess()).isTrue();
        verify(this.itemDelivery).deliver(this.player, kit);
        verify(this.usageStore, never()).recordClaim(any(), anyString(), any());
    }

    @Test
    void shouldGiveFirstJoinKitWithoutPermissionButOnlyOnce() {
        Kit starter = kit("starter").type(KitType.FIRST_JOIN).build();
        when(this.player.hasPermission(starter.permission())).thenReturn(false);

        assertThat(this.claimService.claimOnFirstJoin(this.player, starter).isSuccess()).isTrue();

        when(this.usageService.getUsage(this.playerUniqueId, starter)).thenReturn(new KitUsage(Instant.now(), 1));
        assertThat(this.claimService.claimOnFirstJoin(this.player, starter).status()).isEqualTo(KitClaimStatus.ALREADY_CLAIMED);
    }

    @Test
    void shouldReportStatusWithoutClaimingInCanClaim() {
        Kit kit = recurring("daily", Duration.ofDays(1));

        assertThat(this.claimService.canClaim(this.player, kit).isSuccess()).isTrue();
        this.verifyNothingGiven(kit);
        verify(this.eventCaller, never()).callEvent(any(Event.class));
    }

    private void verifyNothingGiven(Kit kit) {
        verify(this.itemDelivery, never()).deliver(any(), any());
        verify(this.itemDelivery, never()).dispatchCommands(any(), any());
        verify(this.usageStore, never()).recordClaim(any(), anyString(), any());
    }
}
