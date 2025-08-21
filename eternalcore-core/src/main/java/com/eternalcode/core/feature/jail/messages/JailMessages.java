package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;

public interface JailMessages {
    Notice locationSet();
    Notice locationRemoved();
    Notice locationNotSet();
    Notice locationOverride();

    Notice detainBroadcast();
    Notice detained();
    Notice detainCountdown();
    Notice detainOverride();
    Notice detainAdmin();

    Notice releaseBroadcast();
    Notice released();
    Notice releaseAll();
    Notice releaseNoPlayers();
    Notice isNotPrisoner();

    Notice listHeader();
    Notice listEmpty();
    Notice listPlayerEntry();

    Notice cannotUseCommand();
}
