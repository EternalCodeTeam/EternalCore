package com.eternalcode.core.feature.kit.usage;

import static com.eternalcode.core.feature.kit.KitTestFactory.kit;
import static com.eternalcode.core.feature.kit.KitTestFactory.recurring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.feature.kit.KitUsage;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitUsageServiceImplTest {

    private final UUID playerUniqueId = UUID.randomUUID();

    private KitUsageStore store;
    private KitService kitService;
    private KitUsageServiceImpl usageService;

    @BeforeEach
    void setUp() {
        this.store = mock(KitUsageStore.class);
        this.kitService = mock(KitService.class);
        this.usageService = new KitUsageServiceImpl(this.store, this.kitService);
    }

    @Test
    void shouldComputeRemainingCooldownFromLastClaim() {
        Kit kit = recurring("daily", Duration.ofHours(24));
        when(this.store.getUsage(this.playerUniqueId, "daily")).thenReturn(new KitUsage(Instant.now().minus(Duration.ofHours(20)), 1));

        Optional<Duration> remaining = this.usageService.getRemainingCooldown(this.playerUniqueId, kit);

        assertThat(remaining).isPresent();
        assertThat(remaining.get()).isBetween(Duration.ofHours(4).minusSeconds(5), Duration.ofHours(4));
    }

    @Test
    void shouldReturnNoCooldownWhenExpiredNeverClaimedOrNotRecurring() {
        Kit expired = recurring("daily", Duration.ofHours(1));
        Kit oneTime = kit("welcome").type(KitType.ONE_TIME).cooldown(Duration.ofDays(1)).build();
        Kit neverClaimed = recurring("never", Duration.ofDays(1));
        when(this.store.getUsage(this.playerUniqueId, "daily")).thenReturn(new KitUsage(Instant.now().minus(Duration.ofHours(2)), 1));
        when(this.store.getUsage(this.playerUniqueId, "welcome")).thenReturn(new KitUsage(Instant.now(), 1));
        when(this.store.getUsage(this.playerUniqueId, "never")).thenReturn(KitUsage.neverClaimed());

        assertThat(this.usageService.getRemainingCooldown(this.playerUniqueId, expired)).isEmpty();
        assertThat(this.usageService.getRemainingCooldown(this.playerUniqueId, oneTime)).isEmpty();
        assertThat(this.usageService.getRemainingCooldown(this.playerUniqueId, neverClaimed)).isEmpty();
    }

    @Test
    void shouldUseCurrentKitCooldownAfterKitWasEdited() {
        Kit shortened = recurring("daily", Duration.ofHours(1));
        when(this.store.getUsage(this.playerUniqueId, "daily")).thenReturn(new KitUsage(Instant.now().minus(Duration.ofHours(2)), 1));

        assertThat(this.usageService.getRemainingCooldown(this.playerUniqueId, shortened)).isEmpty();
    }

    @Test
    void shouldListOnlyActiveCooldownsOfExistingKits() {
        Instant now = Instant.now();
        Kit daily = recurring("daily", Duration.ofDays(1));
        Kit hourly = recurring("hourly", Duration.ofHours(1));
        when(this.store.getUsages(this.playerUniqueId)).thenReturn(Map.of(
            "daily", new KitUsage(now, 1),
            "hourly", new KitUsage(now.minus(Duration.ofHours(2)), 4),
            "deleted", new KitUsage(now, 1)
        ));
        when(this.kitService.findKit("daily")).thenReturn(Optional.of(daily));
        when(this.kitService.findKit("hourly")).thenReturn(Optional.of(hourly));
        when(this.kitService.findKit("deleted")).thenReturn(Optional.empty());

        assertThat(this.usageService.getRemainingCooldowns(this.playerUniqueId)).containsOnlyKeys("daily");
    }
}
