package com.eternalcode.core.feature.teleportrandomplayer.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTeleportToRandomPlayerMessages extends OkaeriConfig implements TeleportToRandomPlayerMessages {

    Notice randomPlayerNotFound =
        Notice.chat("<red>✘ <dark_red>No player found to teleport!");

    @Comment("{PLAYER} - The name of the player you have been teleported to")
    Notice teleportedToRandomPlayer =
        Notice.chat("<green>► <white>Teleported to random player <green>{PLAYER}<white>!");

    Notice randomPlayerInRangeNotFound =
        Notice.chat("<red>✘ <dark_red>No player found in range to teleport!");

    @Comment("{PLAYER} - The name of the player you have been teleported to within range")
    Notice teleportedToRandomPlayerInRange =
        Notice.chat("<green>► <white>Teleported to a random player in range: <green>{PLAYER}<white>!");

}
