package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;

public interface JailMessages {
    Notice jailLocationSet();
    Notice jailLocationRemove();
    Notice jailLocationNotSet();
    Notice jailLocationOverride();

    Notice jailDetainBroadcast();
    Notice jailDetainPrivate();
    Notice jailDetainCountdown();
    Notice jailDetainOverride();
    Notice jailDetainAdmin();

    Notice jailReleaseBroadcast();
    Notice jailReleasePrivate();
    Notice jailReleaseAll();
    Notice jailReleaseNoPlayers();
    Notice jailIsNotPrisoner();

    Notice jailListHeader();
    Notice jailListEmpty();
    Notice jailListPlayerEntry();

    Notice jailCannotUseCommand();
}
