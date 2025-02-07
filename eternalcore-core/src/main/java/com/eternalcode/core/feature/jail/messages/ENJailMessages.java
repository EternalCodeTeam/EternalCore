package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENJailMessages implements JailMessages {
    @Description({" ", "# Section responsible for location of jail setup"})
    public Notice jailLocationSet = Notice.chat("<green>► <white>Jail location has been set!");
    public Notice jailLocationRemove = Notice.chat("<red>✘ <dark_red>Jail location has been removed!");
    public Notice jailLocationNotSet = Notice.chat("<red>✘ <dark_red>Jail location is not set!");
    public Notice jailLocationOverride = Notice.chat("<green>► <white>Jail location has been overridden!");

    @Description({" ", "# Section responsible for detaining players"})
    public Notice jailDetainPrivate = Notice.chat("<green>► <white>You have been jailed!");
    public Notice jailCannotUseCommand = Notice.chat("<red>✘ <dark_red>You can't use this command! You are in jail!");
    @Description({" ", "# {PLAYER} - Player who has been detained"})
    public Notice jailDetainOverride =
        Notice.chat("<green>► <white>You have overridden the jail for <green>{PLAYER} <white>!");
    @Description({" ", "# {PLAYER} - Player who has been detained"})
    public Notice jailDetainBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been jailed!");
    @Description({" ", "# {REMAINING_TIME} - Time left to release"})
    public Notice jailDetainCountdown =
        Notice.actionbar("<red> You are in jail! <gray>Time left: <red>{REMAINING_TIME}!");
    @Description({" ", "# {PLAYER} - Admin who you can't detain"})
    public Notice jailDetainAdmin =
        Notice.chat("<red>✘ <dark_red>You can't jail <red>{PLAYER} <dark_red>because he is an admin!");

    @Description({" ", "# Section responsible for releasing players from jail"})
    @Description({" ", "# {PLAYER} - Player who has been released from jail"})
    public Notice jailReleaseBroadcast =
        Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been granted freedom!");
    public Notice jailReleasePrivate = Notice.actionbar("<green> You have been released from jail!");
    public Notice jailReleaseAll = Notice.chat("<green>► <white>All players have been released from jail!");
    public Notice jailReleaseNoPlayers = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Description({" ", "# {PLAYER} - Player nickname"})
    public Notice jailIsNotPrisoner = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not a prisoner!");

    @Description({" ", "# Section responsible for listing players in jail"})
    public Notice jailListHeader = Notice.chat("<green>► <white>Players in jail: ");
    public Notice jailListEmpty = Notice.chat("<red>✘ <dark_red>No players found in jail!");
    @Description({" ", "# {PLAYER} - Player who has been detained", "# {REMAINING_TIME} - Time of detention",
                  "# {DETAINED_BY} - Player who detained the player"})
    public Notice jailListPlayerEntry = Notice.chat(
        "<green>► <white>{PLAYER} <gray>(<white>{REMAINING_TIME}<gray>) <white>detained by <green>{DETAINED_BY} <white>!");
}
