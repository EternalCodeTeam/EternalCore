package com.eternalcode.core.feature.deathteleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeathTeleportToggleServiceImplTest {

    private static class InMemoryRepository implements DeathTeleportToggleRepository {

        private final Map<UUID, DeathTeleportState> states = new HashMap<>();
        private final AtomicInteger reads = new AtomicInteger();

        @Override
        public CompletableFuture<DeathTeleportState> getState(UUID uuid) {
            this.reads.incrementAndGet();
            return CompletableFuture.completedFuture(this.states.getOrDefault(uuid, DeathTeleportState.ENABLED));
        }

        @Override
        public CompletableFuture<Void> setState(UUID uuid, DeathTeleportState state) {
            this.states.put(uuid, state);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Test
    void shouldReturnEnabledByDefault() {
        DeathTeleportToggleServiceImpl service = new DeathTeleportToggleServiceImpl(new InMemoryRepository());
        UUID player = UUID.randomUUID();

        assertThat(service.getState(player).join()).isEqualTo(DeathTeleportState.ENABLED);
    }

    @Test
    void shouldToggleStateFromEnabledToDisabled() {
        DeathTeleportToggleServiceImpl service = new DeathTeleportToggleServiceImpl(new InMemoryRepository());
        UUID player = UUID.randomUUID();

        DeathTeleportState toggled = service.toggleState(player).join();

        assertThat(toggled).isEqualTo(DeathTeleportState.DISABLED);
        assertThat(service.getState(player).join()).isEqualTo(DeathTeleportState.DISABLED);
    }

    @Test
    void shouldToggleBackAndForth() {
        DeathTeleportToggleServiceImpl service = new DeathTeleportToggleServiceImpl(new InMemoryRepository());
        UUID player = UUID.randomUUID();

        assertThat(service.toggleState(player).join()).isEqualTo(DeathTeleportState.DISABLED);
        assertThat(service.toggleState(player).join()).isEqualTo(DeathTeleportState.ENABLED);
    }

    @Test
    void shouldPersistExplicitState() {
        InMemoryRepository repository = new InMemoryRepository();
        DeathTeleportToggleServiceImpl service = new DeathTeleportToggleServiceImpl(repository);
        UUID player = UUID.randomUUID();

        service.setState(player, DeathTeleportState.DISABLED).join();

        assertThat(repository.states.get(player)).isEqualTo(DeathTeleportState.DISABLED);
    }

    @Test
    void shouldCacheStateAfterFirstReadFromRepository() {
        InMemoryRepository repository = new InMemoryRepository();
        DeathTeleportToggleServiceImpl service = new DeathTeleportToggleServiceImpl(repository);
        UUID player = UUID.randomUUID();

        service.getState(player).join();
        service.getState(player).join();

        assertThat(repository.reads.get()).isEqualTo(1);
    }
}
