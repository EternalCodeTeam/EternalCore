package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpawnMessages extends OkaeriConfig implements SpawnMessages {
    public Notice spawnSet = Notice.chat("<green>► <white>Ustawiono spawn!");
    public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn nie został ustawiony!");
    @Comment({" ", "# {PLAYER} - Gracz który teleportował cię na spawn"})
    public Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>Zostałeś przeteleportowany na spawn przez gracza <green>{PLAYER}<white>!");
    @Comment("# {PLAYER} - Gracz który został przeteleportowany na spawn")
    public Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany na spawn!");
}
