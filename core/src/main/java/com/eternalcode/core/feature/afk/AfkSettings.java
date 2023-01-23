package com.eternalcode.core.feature.afk;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface AfkSettings extends DelaySettings {

    int interactionsCountDisableAfk();

    Duration getAfkDelay();

    @Override
    default Duration delay() {
        return getAfkDelay();
    }

}
