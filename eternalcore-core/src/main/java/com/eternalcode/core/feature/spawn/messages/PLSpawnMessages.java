package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpawnMessages extends OkaeriConfig implements SpawnMessages {

    Notice spawnSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono spawn!");
    Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn nie został ustawiony!");

    @Comment("# {PLAYER} - Gracz który teleportował cię na spawn")
    Notice spawnTeleportedBy =
        Notice.chat("<color:#9d6eef>► <white>Zostałeś przeteleportowany na spawn przez gracza <color:#9d6eef>{PLAYER}<white>!");

    @Comment("# {PLAYER} - Gracz który został przeteleportowany na spawn")
    Notice spawnTeleportedOther =
        Notice.chat("<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>został przeteleportowany na spawn!");
}
