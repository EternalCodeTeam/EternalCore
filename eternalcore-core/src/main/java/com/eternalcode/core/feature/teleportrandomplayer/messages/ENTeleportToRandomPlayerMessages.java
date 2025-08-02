package com.eternalcode.core.feature.teleportrandomplayer.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTeleportToRandomPlayerMessages extends OkaeriConfig implements TeleportToRandomPlayerMessages {
    public Notice randomPlayerNotFound =
        Notice.chat("<red>✘ <dark_red>No player found to teleport!");

    public Notice teleportedToRandomPlayer =
        Notice.chat("<green>► <white>Teleported to random player <green>{PLAYER}<white>!");

    public Notice randomPlayerInRangeNotFound =
        Notice.chat("<red>✘ <dark_red>No player found in range to teleport!");

    public Notice teleportedToRandomPlayerInRange =
        Notice.chat("<green>► <white>Teleported to a random player in range: <green>{PLAYER}<white>!");

    public Notice teleportedToRandomPlayerInRangeNotFound =
        Notice.chat("<red>✘ <dark_red>No player found in range to teleport!");
}
