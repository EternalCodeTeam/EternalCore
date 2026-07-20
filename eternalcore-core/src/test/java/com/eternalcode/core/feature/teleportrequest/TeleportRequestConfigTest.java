package com.eternalcode.core.feature.teleportrequest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TeleportRequestConfigTest {

    @Test
    void shouldNotRestrictTpaAcceptYByDefault() {
        TeleportRequestConfig config = new TeleportRequestConfig();

        assertThat(config.minimumTpaAcceptY()).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    void shouldRetainExplicitMinimumTpaAcceptY() {
        TeleportRequestConfig config = new TeleportRequestConfig();
        config.minimumTpaAcceptY = -32;

        assertThat(config.minimumTpaAcceptY()).isEqualTo(-32);
    }
}
