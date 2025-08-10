package com.eternalcode.core.feature.afk;

import java.time.Duration;

public interface AfkSettings {

    boolean autoAfk();

    int interactionsCountDisableAfk();

    Duration afkCommandDelay();

    Duration afkInactivityTime();

    boolean kickOnAfk();

}
