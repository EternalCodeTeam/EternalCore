package com.eternalcode.core.feature.punishment.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENPunishmentMessages extends OkaeriConfig implements PunishmentMessages {

    @Comment({ " ", "# Ban section" })
    @Comment({ " ", "# {PLAYER} - Punished player, {OPERATOR} - Staff member, {REASON} - Reason, {EXPIRES} - Expiration" })
    Notice banBroadcast = Notice.chat("<red>► <white>Player <red>{PLAYER} <white>has been banned by <red>{OPERATOR} <white>for <red>{EXPIRES}<white>! Reason: <gray>{REASON}");
    @Comment({ " ", "# Visible only to players with eternalcore.punishment.messages permission" })
    Notice banBroadcastSilent = Notice.chat("<dark_red>[SILENT BAN] <white>Player <red>{PLAYER} <white>has been banned by <red>{OPERATOR} <white>for <red>{EXPIRES}<white>! Reason: <gray>{REASON}");
    Notice banCannotBanAdmin = Notice.chat("<red>✘ <dark_red>You cannot ban administrator <red>{PLAYER}!");
    Notice banInvalidReason = Notice.chat("<red>✘ <dark_red>Reason must be between {MIN} and {MAX} characters!");
    Notice banSuccessPrivate = Notice.chat("<red>► <white>You banned player <red>{PLAYER}!");
    Notice banAlreadyBanned = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is already banned!");
    Notice banPlayerTriesJoin = Notice.chat("<bold><red>Player {PLAYER} is trying to join the server but is banned!!");

    @Comment({ " ", "# Banip section" })
    Notice banIpSuccessPrivate = Notice.chat("<red>► <white>You banned player <red>{PLAYER} <white>and their IP address!");
    Notice banIpNoAddress = Notice.chat("<red>✘ <dark_red>Could not resolve IP address for player {PLAYER}!");

    @Comment({ " ", "# Unban section" })
    Notice unbanBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been unbanned by <green>{OPERATOR}!");
    Notice unbanSuccessPrivate = Notice.chat("<green>► <white>You unbanned player <green>{PLAYER}!");
    Notice unbanNotBanned = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not banned!");

    Notice unbanIpBroadcast = Notice.chat("<green>► <white>IP address <green>{IP} <white>has been unbanned by <green>{OPERATOR}!");
    Notice unbanIpSuccessPrivate = Notice.chat("<green>► <white>You unbanned IP address <green>{IP}!");
    Notice unbanIpNotBanned = Notice.chat("<red>✘ <dark_red>IP address {IP} is not banned!");

    @Comment({ " ", "# Kick section" })
    Notice kickBroadcast = Notice.chat("<red>► <white>Player <red>{PLAYER} <white>has been kicked by <red>{OPERATOR}<white>! Reason: <gray>{REASON}");
    Notice kickBroadcastSilent = Notice.chat("<dark_red>[SILENT KICK] <white>Player <red>{PLAYER} <white>has been kicked by <red>{OPERATOR}<white>! Reason: <gray>{REASON}");
    Notice kickCannotKickAdmin = Notice.chat("<red>✘ <dark_red>You cannot kick administrator <red>{PLAYER}!");
    Notice kickInvalidReason = Notice.chat("<red>✘ <dark_red>Reason must be between {MIN} and {MAX} characters!");
    Notice kickSuccessPrivate = Notice.chat("<red>► <white>You kicked player <red>{PLAYER}!");
    Notice kickNotOnline = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not online!");
    Notice kickAllBroadcast = Notice.chat("<red>► <white>Server has been cleared by <red>{OPERATOR}<white>! Reason: <gray>{REASON} <white>(<red>{COUNT}<white> players)");

    @Comment({ " ", "# Mute section" })
    Notice muteBroadcast = Notice.chat("<red>► <white>Player <red>{PLAYER} <white>has been muted by <red>{OPERATOR} <white>for <red>{EXPIRES}<white>! Reason: <gray>{REASON}");
    Notice muteBroadcastSilent = Notice.chat("<dark_red>[SILENT MUTE] <white>Player <red>{PLAYER} <white>has been muted by <red>{OPERATOR} <white>for <red>{EXPIRES}<white>! Reason: <gray>{REASON}");
    Notice muteCannotMuteAdmin = Notice.chat("<red>✘ <dark_red>You cannot mute administrator <red>{PLAYER}!");
    Notice muteInvalidReason = Notice.chat("<red>✘ <dark_red>Reason must be between {MIN} and {MAX} characters!");
    Notice muteSuccessPrivate = Notice.chat("<red>► <white>You muted player <red>{PLAYER}!");
    Notice muteAlreadyMuted = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is already muted!");
    Notice muteBlockedChat = Notice.chat("<red>✘ <dark_red>You are muted! Reason: <gray>{REASON} <dark_red>Remaining: <gray>{REMAINING_TIME}");
    Notice muteBlockedSign = Notice.chat("<red>✘ <dark_red>You are muted! Reason: <gray>{REASON} <dark_red>Remaining: <gray>{REMAINING_TIME}");

    @Comment({ " ", "# Unmute section" })
    Notice unmuteBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been unmuted by <green>{OPERATOR}!");
    Notice unmuteSuccessPrivate = Notice.chat("<green>► <white>You unmuted player <green>{PLAYER}!");
    Notice unmuteNotMuted = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not muted!");

    @Comment({ " ", "# Warn section" })
    Notice warnBroadcast = Notice.chat("<yellow>► <white>Player <yellow>{PLAYER} <white>has been warned by <yellow>{OPERATOR}<white>! Reason: <gray>{REASON}");
    Notice warnBroadcastSilent = Notice.chat("<gold>[SILENT WARN] <white>Player <yellow>{PLAYER} <white>has been warned by <yellow>{OPERATOR}<white>! Reason: <gray>{REASON}");
    Notice warnCannotWarnAdmin = Notice.chat("<red>✘ <dark_red>You cannot warn administrator <red>{PLAYER}!");
    Notice warnInvalidReason = Notice.chat("<red>✘ <dark_red>Reason must be between {MIN} and {MAX} characters!");
    Notice warnSuccessPrivate = Notice.chat("<yellow>► <white>You warned player <yellow>{PLAYER}!");
    Notice warnEscalationBroadcast = Notice.chat("<gold>► <white>Player <yellow>{PLAYER} <white>received an automatic punishment (<yellow>{ACTION}<white>, {EXPIRES}) for reaching <yellow>{COUNT} <white>warns!");

    Notice altAccountsFound = Notice.chat("<yellow>► <white>Accounts linked to <yellow>{PLAYER}<white>: <gray>{ACCOUNTS}");
    Notice altAccountsNone = Notice.chat("<yellow>► <white>No accounts linked to <yellow>{PLAYER}");

    @Comment({ " ", "# History section" })
    Notice historyHeaderRecent = Notice.chat("<gray>► <white>Recent server punishments <gray>(page {PAGE}):");
    Notice historyHeaderPlayer = Notice.chat("<gray>► <white>Punishment history for <yellow>{PLAYER} <gray>(page {PAGE}):");
    Notice historyEntry = Notice.chat("<gray>[<white>{DATE}<gray>] <yellow>{ACTION} <white>{PLAYER} <gray>by <white>{OPERATOR} <gray>- <white>{REASON}({EXPIRES})");
    Notice historyEmpty = Notice.chat("<gray>No entries found in the history.");
    Notice historyError = Notice.chat("<red>✘ <dark_red>An error occurred while loading the history. Check the console.");
    Notice historyNoPermission = Notice.chat("<red>✘ <dark_red>You dont have permission to check history!");

    Notice punishmentActionError = Notice.chat("<red>✘ <dark_red>An error occurred while performing this action. Check the console.");
}
