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
    Notice banPlayerTriesJoin();

    // Banip
    Notice banIpSuccessPrivate();
    Notice banIpNoAddress();

    // Unban
    Notice unbanBroadcast();
    Notice unbanSuccessPrivate();
    Notice unbanNotBanned();

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
    Notice muteBlockedSign();

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
    Notice warnEscalationBroadcast();

    Notice altAccountsFound();
    Notice altAccountsNone();

    // History
    Notice historyHeaderRecent();
    Notice historyHeaderPlayer();
    Notice historyEntry();
    Notice historyEmpty();
    Notice historyError();
    Notice historyNoPermission();

    Notice punishmentActionError();
}
