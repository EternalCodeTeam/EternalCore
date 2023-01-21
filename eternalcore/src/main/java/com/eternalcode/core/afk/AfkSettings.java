package com.eternalcode.core.afk;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface AfkSettings extends DelaySettings {

    int interactionsCountDisableAfk();

    Duration getChatDelay();

    @Override
    default Duration delay() {
        return getChatDelay();
    }

}
