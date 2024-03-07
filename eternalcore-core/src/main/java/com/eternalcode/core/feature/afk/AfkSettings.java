package com.eternalcode.core.feature.afk;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface AfkSettings extends DelaySettings {

    boolean markPlayerAsAfk();

    int interactionsCountDisableAfk();

    Duration getAfkDelay();

    Duration getAfkInactivityTime();

    @Override
    default Duration delay() {
        return this.getAfkDelay();
    }

}
