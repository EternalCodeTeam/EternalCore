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
    public Notice locationSet = Notice.chat("<green>► <white>Jail location has been set!");
    public Notice locationRemoved = Notice.chat("<red>✘ <dark_red>Jail location has been removed!");
    public Notice locationNotSet = Notice.chat("<red>✘ <dark_red>Jail location is not set!");
    public Notice locationOverride = Notice.chat("<green>► <white>Jail location has been overridden!");

    @Comment({" ", "# Section responsible for detaining players"})
    public Notice detained = Notice.chat("<green>► <white>You have been jailed!");
    public Notice cannotUseCommand = Notice.chat("<red>✘ <dark_red>You can't use this command! You are in jail!");
    @Comment({" ", "# {PLAYER} - Player who has been detained"})
    public Notice detainOverride =
        Notice.chat("<green>► <white>You have overridden the jail for <green>{PLAYER} <white>!");
    @Comment({" ", "# {PLAYER} - Player who has been detained"})
    public Notice detainBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been jailed!");
    @Comment({" ", "# {REMAINING_TIME} - Time left to release"})
    public Notice detainCountdown =
        Notice.actionbar("<red> You are in jail! <gray>Time left: <red>{REMAINING_TIME}!");
    @Comment({" ", "# {PLAYER} - Admin who you can't detain"})
    public Notice detainAdmin =
        Notice.chat("<red>✘ <dark_red>You can't jail <red>{PLAYER} <dark_red>because he is an admin!");

    @Comment({" ", "# Section responsible for releasing players from jail"})
    @Comment({" ", "# {PLAYER} - Player who has been released from jail"})
    public Notice releaseBroadcast =
        Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been granted freedom!");
    public Notice released = Notice.actionbar("<green> You have been released from jail!");
    public Notice releaseAll = Notice.chat("<green>► <white>All players have been released from jail!");
    public Notice releaseNoPlayers = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Comment({" ", "# {PLAYER} - Player nickname"})
    public Notice isNotPrisoner = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not a prisoner!");

    @Comment({" ", "# Section responsible for listing players in jail"})
    public Notice listHeader = Notice.chat("<green>► <white>Players in jail: ");
    public Notice listEmpty = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Comment({" ", "# {PLAYER} - Player who has been detained", "# {REMAINING_TIME} - Time of detention",
                  "# {DETAINED_BY} - Player who detained the player"})
    public Notice listPlayerEntry = Notice.chat(
        "<green>► <white>{PLAYER} <gray>(<white>{REMAINING_TIME}<gray>) <white>detained by <green>{DETAINED_BY} <white>!");
}
