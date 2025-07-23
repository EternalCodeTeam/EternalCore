package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENJailMessages extends OkaeriConfig implements JailMessages {
    @Comment({" ", "# Section responsible for location of jail setup"})
    public Notice jailLocationSet = Notice.chat("<green>► <white>Jail location has been set!");
    public Notice jailLocationRemove = Notice.chat("<red>✘ <dark_red>Jail location has been removed!");
    public Notice jailLocationNotSet = Notice.chat("<red>✘ <dark_red>Jail location is not set!");
    public Notice jailLocationOverride = Notice.chat("<green>► <white>Jail location has been overridden!");

    @Comment({" ", "# Section responsible for detaining players"})
    public Notice jailDetainPrivate = Notice.chat("<green>► <white>You have been jailed!");
    public Notice jailCannotUseCommand = Notice.chat("<red>✘ <dark_red>You can't use this command! You are in jail!");
    @Comment({" ", "# {PLAYER} - Player who has been detained"})
    public Notice jailDetainOverride =
        Notice.chat("<green>► <white>You have overridden the jail for <green>{PLAYER} <white>!");
    @Comment({" ", "# {PLAYER} - Player who has been detained"})
    public Notice jailDetainBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been jailed!");
    @Comment({" ", "# {REMAINING_TIME} - Time left to release"})
    public Notice jailDetainCountdown =
        Notice.actionbar("<red> You are in jail! <gray>Time left: <red>{REMAINING_TIME}!");
    @Comment({" ", "# {PLAYER} - Admin who you can't detain"})
    public Notice jailDetainAdmin =
        Notice.chat("<red>✘ <dark_red>You can't jail <red>{PLAYER} <dark_red>because he is an admin!");

    @Comment({" ", "# Section responsible for releasing players from jail"})
    @Comment({" ", "# {PLAYER} - Player who has been released from jail"})
    public Notice jailReleaseBroadcast =
        Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been granted freedom!");
    public Notice jailReleasePrivate = Notice.actionbar("<green> You have been released from jail!");
    public Notice jailReleaseAll = Notice.chat("<green>► <white>All players have been released from jail!");
    public Notice jailReleaseNoPlayers = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Comment({" ", "# {PLAYER} - Player nickname"})
    public Notice jailIsNotPrisoner = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not a prisoner!");

    @Comment({" ", "# Section responsible for listing players in jail"})
    public Notice jailListHeader = Notice.chat("<green>► <white>Players in jail: ");
    public Notice jailListEmpty = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Comment({" ", "# {PLAYER} - Player who has been detained", "# {REMAINING_TIME} - Time of detention",
                  "# {DETAINED_BY} - Player who detained the player"})
    public Notice jailListPlayerEntry = Notice.chat(
        "<green>► <white>{PLAYER} <gray>(<white>{REMAINING_TIME}<gray>) <white>detained by <green>{DETAINED_BY} <white>!");
}
