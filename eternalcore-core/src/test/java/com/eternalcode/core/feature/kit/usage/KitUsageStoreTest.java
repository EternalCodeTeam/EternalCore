package com.eternalcode.core.feature.kit.usage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.test.MockServer;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitUsageStoreTest {

    private final InMemoryKitUsageRepository repository = new InMemoryKitUsageRepository();
    private final MockServer server = new MockServer();
    private final UUID playerUniqueId = UUID.randomUUID();

    private KitUsageStore store;

    @BeforeEach
    void setUp() {
        this.store = new KitUsageStore(this.repository, this.server.getServer());
    }

    @Test
    void shouldLoadUsagesOfOnlinePlayer() {
        Instant claimedAt = Instant.parse("2026-01-01T10:00:00Z");
        this.repository.usages.put(this.playerUniqueId, new ConcurrentHashMap<>(Map.of("starter", new KitUsage(claimedAt, 1))));
        this.server.joinPlayer("Piotrulla", this.playerUniqueId);

        this.store.load(this.playerUniqueId).join();

        assertThat(this.store.isLoaded(this.playerUniqueId)).isTrue();
        assertThat(this.store.getUsage(this.playerUniqueId, "starter")).isEqualTo(new KitUsage(claimedAt, 1));
        assertThat(this.store.getUsage(this.playerUniqueId, "unknown")).isEqualTo(KitUsage.neverClaimed());
    }

    @Test
    void shouldNotCachePlayerThatLeftBeforeLoadFinished() {
        this.store.load(this.playerUniqueId).join();

        assertThat(this.store.isLoaded(this.playerUniqueId)).isFalse();
    }

    @Test
    void shouldRejectClaimWhenUsagesAreNotLoaded() {
        assertThatThrownBy(() -> this.store.recordClaim(this.playerUniqueId, "starter", Instant.now()))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldRecordClaimInMemoryAndDatabase() {
        this.joinAndLoad();
        Instant claimedAt = Instant.parse("2026-01-01T10:00:00Z");

        this.store.recordClaim(this.playerUniqueId, "daily", claimedAt);
        this.store.recordClaim(this.playerUniqueId, "daily", claimedAt.plusSeconds(60));

        KitUsage expected = new KitUsage(claimedAt.plusSeconds(60), 2);
        assertThat(this.store.getUsage(this.playerUniqueId, "daily")).isEqualTo(expected);
        assertThat(this.repository.usages.get(this.playerUniqueId).get("daily")).isEqualTo(expected);
    }

    @Test
    void shouldResetClaimButKeepClaimCount() {
        this.joinAndLoad();
        this.store.recordClaim(this.playerUniqueId, "vip", Instant.now());

        this.store.resetClaim(this.playerUniqueId, "vip");

        KitUsage usage = this.store.getUsage(this.playerUniqueId, "vip");
        assertThat(usage.isClaimed()).isFalse();
        assertThat(usage.claimCount()).isEqualTo(1);
        assertThat(this.repository.usages.get(this.playerUniqueId).get("vip")).isEqualTo(usage);
    }

    @Test
    void shouldResetClaimOfOfflinePlayerInDatabase() {
        this.repository.usages.put(this.playerUniqueId, new ConcurrentHashMap<>(Map.of("vip", new KitUsage(Instant.now(), 3))));

        this.store.resetClaim(this.playerUniqueId, "vip");

        assertThat(this.repository.usages.get(this.playerUniqueId).get("vip")).isEqualTo(new KitUsage(null, 3));
    }

    @Test
    void shouldWriteInClaimOrderEvenWhenDatabaseIsSlow() {
        this.joinAndLoad();
        CompletableFuture<Void> slowWrite = new CompletableFuture<>();
        this.repository.nextSaveDelay = slowWrite;

        this.store.recordClaim(this.playerUniqueId, "daily", Instant.now());
        this.store.resetClaim(this.playerUniqueId, "daily");

        assertThat(this.repository.operations).isEmpty();

        slowWrite.complete(null);

        assertThat(this.repository.operations).containsExactly("save daily", "reset daily");
        assertThat(this.repository.usages.get(this.playerUniqueId).get("daily").isClaimed()).isFalse();
    }

    @Test
    void shouldLoadOnlyAfterPendingWritesOfRelog() {
        Player player = this.joinAndLoad();
        CompletableFuture<Void> slowWrite = new CompletableFuture<>();
        this.repository.nextSaveDelay = slowWrite;
        this.store.recordClaim(this.playerUniqueId, "daily", Instant.now());

        this.server.quitPlayer(player);
        this.store.unload(this.playerUniqueId);
        this.server.joinPlayer("Piotrulla", this.playerUniqueId);
        CompletableFuture<Void> reload = this.store.load(this.playerUniqueId);

        assertThat(reload).isNotDone();

        slowWrite.complete(null);
        reload.join();

        assertThat(this.store.getUsage(this.playerUniqueId, "daily").isClaimed()).isTrue();
    }

    @Test
    void shouldForgetDeletedKit() {
        this.joinAndLoad();
        this.store.recordClaim(this.playerUniqueId, "old", Instant.now());

        this.store.deleteKit("old");

        assertThat(this.store.getUsages(this.playerUniqueId)).doesNotContainKey("old");
        assertThat(this.repository.usages.get(this.playerUniqueId)).doesNotContainKey("old");
    }

    private Player joinAndLoad() {
        Player player = this.server.joinPlayer("Piotrulla", this.playerUniqueId);
        this.store.load(this.playerUniqueId).join();
        return player;
    }

    private static class InMemoryKitUsageRepository implements KitUsageRepository {

        private final Map<UUID, Map<String, KitUsage>> usages = new ConcurrentHashMap<>();
        private final List<String> operations = new CopyOnWriteArrayList<>();
        private CompletableFuture<Void> nextSaveDelay;

        @Override
        public CompletableFuture<Map<String, KitUsage>> findUsages(UUID playerUniqueId) {
            return CompletableFuture.completedFuture(Map.copyOf(this.usages.getOrDefault(playerUniqueId, Map.of())));
        }

        @Override
        public CompletableFuture<Void> save(UUID playerUniqueId, String kitName, KitUsage usage) {
            CompletableFuture<Void> delay = this.nextSaveDelay == null ? CompletableFuture.completedFuture(null) : this.nextSaveDelay;
            this.nextSaveDelay = null;

            return delay.thenRun(() -> {
                this.operations.add("save " + kitName);
                this.usages.computeIfAbsent(playerUniqueId, key -> new ConcurrentHashMap<>()).put(kitName, usage);
            });
        }

        @Override
        public CompletableFuture<Void> resetClaim(UUID playerUniqueId, String kitName) {
            this.operations.add("reset " + kitName);
            this.usages.computeIfAbsent(playerUniqueId, key -> new ConcurrentHashMap<>()).computeIfPresent(kitName, (name, usage) -> usage.reset());
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public CompletableFuture<Void> deleteByKit(String kitName) {
            this.usages.values().forEach(usages -> usages.remove(kitName));
            return CompletableFuture.completedFuture(null);
        }
    }
}
