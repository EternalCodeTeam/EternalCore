package com.eternalcode.core.feature.afk;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface AfkSettings extends DelaySettings {

    boolean autoAfk();

    int interactionsCountDisableAfk();

    Duration getAfkDelay();

    Duration getAfkInactivityTime();

    @Override
    default Duration delay() {
        return this.getAfkDelay();
    }

}
