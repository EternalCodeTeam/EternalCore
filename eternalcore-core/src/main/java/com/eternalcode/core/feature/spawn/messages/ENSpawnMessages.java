package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSpawnMessages extends OkaeriConfig implements SpawnMessages {

    Notice spawnSet = Notice.chat("<green>► <white>Spawn set!");
    Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn is not set!");

    @Comment("# {PLAYER} - Player who teleported you to spawn")
    Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>You have been teleported to spawn by <green>{PLAYER}<white>!");

    @Comment("# {PLAYER} - Teleported player")
    Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>You teleported player <green>{PLAYER} <white>to spawn!");
}
