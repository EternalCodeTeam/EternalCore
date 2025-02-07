package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENSpawnMessages implements SpawnMessages {
    public Notice spawnSet = Notice.chat("<green>► <white>Spawn set!");
    public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn is not set!");
    @Description({" ", "# {PLAYER} - Player who teleported you to spawn"})
    public Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>You have been teleported to spawn by <green>{PLAYER}<white>!");
    @Description({" ", "# {PLAYER} - Teleported player"})
    public Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>You teleported player <green>{PLAYER} <white>to spawn!");
}
