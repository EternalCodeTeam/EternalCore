package com.eternalcode.core.feature.punishment.messages;

import com.eternalcode.core.feature.punishment.ban.Ban;
import com.eternalcode.core.feature.punishment.kick.Kick;
import com.eternalcode.core.feature.punishment.mute.Mute;
import com.eternalcode.core.feature.punishment.warn.Warn;
import com.eternalcode.multification.notice.Notice;

public interface PunishmentMessages {

    // Ban
    Notice banBroadcast();
    Notice banBroadcastSilent();
    Notice banCannotBanAdmin();
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
    Notice unbanIpBannedForOther();
    Notice unbanBroadcastSilent();

    Notice unbanIpBroadcast();
    Notice unbanIpSuccessPrivate();
    Notice unbanIpNotBanned();
    Notice unbanIpBroadcastSilent();

    // Kick
    Notice kickBroadcast();
    Notice kickBroadcastSilent();
    Notice kickCannotKickAdmin();
    Notice kickSuccessPrivate();
    Notice kickAllBroadcast();

    // Mute
    Notice muteBroadcast();
    Notice muteBroadcastSilent();
    Notice muteCannotMuteAdmin();
    Notice muteSuccessPrivate();
    Notice muteAlreadyMuted();
    Notice muteBlockedChat();
    Notice muteBlockedSign();
    Notice muteBlockedCommand();

    // Unmute
    Notice unmuteBroadcast();
    Notice unmuteSuccessPrivate();
    Notice unmuteNotMuted();
    Notice unmuteBroadcastSilent();

    // Warn
    Notice warnBroadcast();
    Notice warnBroadcastSilent();
    Notice warnCannotWarnAdmin();
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

    Notice notificationsEnabled();
    Notice notificationsDisabled();

    Notice punishmentActionError();
}
