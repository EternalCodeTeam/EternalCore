package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLSpawnMessages implements SpawnMessages {
    public Notice spawnSet = Notice.chat("<green>► <white>Ustawiono spawn!");
    public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn nie został ustawiony!");
    @Description({" ", "# {PLAYER} - Gracz który teleportował cię na spawn"})
    public Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>Zostałeś przeteleportowany na spawn przez gracza <green>{PLAYER}<white>!");
    @Description("# {PLAYER} - Gracz który został przeteleportowany na spawn")
    public Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany na spawn!");
}
