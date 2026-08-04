package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.commons.bukkit.position.Position;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeathTeleportServiceTest {

    private static final DeathTeleportSettings SETTINGS = new DeathTeleportSettings() {
        @Override
        public boolean deathTeleportEnabled() {
            return true;
        }

        @Override
        public Duration deathTeleportDelay() {
            return Duration.ofSeconds(3);
        }

        @Override
        public Duration deathLocationCacheDuration() {
            return Duration.ofHours(1);
        }
    };

    private static Position position() {
        return new Position(1.0, 64.0, 2.0, 0.0F, 0.0F, "world");
    }

    @Test
    void shouldReturnEmptyWhenNoDeathLocationMarked() {
        DeathTeleportService service = new DeathTeleportService(SETTINGS);

        assertThat(service.getDeathLocation(UUID.randomUUID())).isEmpty();
    }

    @Test
    void shouldStoreAndReturnDeathLocation() {
        DeathTeleportService service = new DeathTeleportService(SETTINGS);
        UUID player = UUID.randomUUID();
        Position position = position();

        service.markDeathLocation(player, position);

        assertThat(service.getDeathLocation(player)).contains(position);
    }

    @Test
    void shouldClearDeathLocation() {
        DeathTeleportService service = new DeathTeleportService(SETTINGS);
        UUID player = UUID.randomUUID();

        service.markDeathLocation(player, position());
        service.clearDeathLocation(player);

        assertThat(service.getDeathLocation(player)).isEmpty();
    }
}
