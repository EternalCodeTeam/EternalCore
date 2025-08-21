package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;

public interface JailMessages {
    Notice locationSet();
    Notice locationRemoved();
    Notice locationNotSet();
    Notice locationOverride();

    Notice detainBroadcast();
    Notice detainPrivate();
    Notice detainCountdown();
    Notice detainOverride();
    Notice detainAdmin();

    Notice releaseBroadcast();
    Notice releasePrivate();
    Notice releaseAll();
    Notice releaseNoPlayers();
    Notice isNotPrisoner();

    Notice listHeader();
    Notice listEmpty();
    Notice listPlayerEntry();

    Notice cannotUseCommand();
}
