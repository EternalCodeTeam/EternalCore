package com.eternalcode.core.feature.punishment.messages;

import com.eternalcode.multification.notice.Notice;

public interface PunishmentMessages {

    // Ban
    Notice banBroadcast();
    Notice banBroadcastSilent();
    Notice banCannotBanAdmin();
    Notice banInvalidReason();
    Notice banSuccessPrivate();
    Notice banAlreadyBanned();

    // Banip
    Notice banIpSuccessPrivate();
    Notice banIpNoAddress();

    // Unban
    Notice unbanBroadcast();
    Notice unbanSuccessPrivate();
    Notice unbanNotBanned();

    // Unbanip
    Notice unbanIpBroadcast();
    Notice unbanIpSuccessPrivate();
    Notice unbanIpNotBanned();

    // Kick
    Notice kickBroadcast();
    Notice kickBroadcastSilent();
    Notice kickCannotKickAdmin();
    Notice kickInvalidReason();
    Notice kickSuccessPrivate();
    Notice kickNotOnline();
    Notice kickAllBroadcast();

    // Mute
    Notice muteBroadcast();
    Notice muteBroadcastSilent();
    Notice muteCannotMuteAdmin();
    Notice muteInvalidReason();
    Notice muteSuccessPrivate();
    Notice muteAlreadyMuted();
    Notice muteBlockedChat();

    // Unmute
    Notice unmuteBroadcast();
    Notice unmuteSuccessPrivate();
    Notice unmuteNotMuted();

    // Warn
    Notice warnBroadcast();
    Notice warnBroadcastSilent();
    Notice warnCannotWarnAdmin();
    Notice warnInvalidReason();
    Notice warnSuccessPrivate();

    Notice altAccountsFound();
    Notice altAccountsNone();
}
