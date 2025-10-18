package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENJailMessages extends OkaeriConfig implements JailMessages {

    @Comment("# Messages related to jail location setup")
    public Notice locationSet = Notice.chat("<green>► <white>Jail location has been set!");
    public Notice locationRemoved = Notice.chat("<red>✘ <dark_red>Jail location has been removed!");
    public Notice locationNotSet = Notice.chat("<red>✘ <dark_red>Jail location is not set!");
    public Notice locationOverride = Notice.chat("<green>► <white>Jail location has been overridden!");

    @Comment("# Messages related to jailing players")
    public Notice detained = Notice.chat("<green>► <white>You have been jailed!");
    public Notice cannotUseCommand = Notice.chat("<red>✘ <dark_red>You can’t use this command while jailed!");

    @Comment("# {PLAYER} - name of the player who was jailed again")
    public Notice detainOverride = Notice.chat("<green>► <white>You have overridden the jail for <green>{PLAYER}<white>!");

    @Comment("# {PLAYER} - name of the player who was jailed")
    public Notice detainBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been jailed!");

    @Comment("# {REMAINING_TIME} - time left until release")
    public Notice detainCountdown = Notice.actionbar("<red>You are in jail! <gray>Time left: <red>{REMAINING_TIME}!");

    @Comment("# {PLAYER} - admin who cannot be jailed")
    public Notice detainAdmin = Notice.chat("<red>✘ <dark_red>You can’t jail <red>{PLAYER}<dark_red> because they are an admin!");

    @Comment("# Messages related to releasing players from jail")
    @Comment("# {PLAYER} - name of the player who was released")
    public Notice releaseBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been released!");
    public Notice released = Notice.actionbar("<green>You have been released from jail!");
    public Notice releaseAll = Notice.chat("<green>► <white>All players have been released from jail!");
    public Notice releaseNoPlayers = Notice.chat("<red>✘ <dark_red>No players are currently jailed!");

    @Comment("# {PLAYER} - name of the player who is not jailed")
    public Notice isNotPrisoner = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not jailed!");

    @Comment("# Messages related to listing jailed players")
    public Notice listHeader = Notice.chat("<green>► <white>Players currently in jail:");
    public Notice listEmpty = Notice.chat("<red>✘ <dark_red>No players are currently jailed!");

    @Comment("# {PLAYER} - jailed player, {DETAINED_BY} - player who jailed them, {REMAINING_TIME} - time left until release")
    public Notice listPlayerEntry = Notice.chat("<green>► <white>{PLAYER} <gray>(<white>{REMAINING_TIME}<gray>) <white>jailed by <green>{DETAINED_BY}<white>!");
}
