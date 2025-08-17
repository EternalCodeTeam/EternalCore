package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSpawnMessages extends OkaeriConfig implements SpawnMessages {

    public Notice spawnSet = Notice.chat("<green>► <white>Spawn set!");
    public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn is not set!");

    @Comment("# {PLAYER} - Player who teleported you to spawn")
    public Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>You have been teleported to spawn by <green>{PLAYER}<white>!");

    @Comment("# {PLAYER} - Teleported player")
    public Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>You teleported player <green>{PLAYER} <white>to spawn!");

    public Notice countDown = Notice.title("<green>Teleporting to spawn in: <white>{TIME}");

    public Notice start = Notice.chat("<green>► <white>Teleporting to spawn...");

    public Notice succes = Notice.chat("<green>► <white>Successfully teleported to spawn!");

    public Notice failureAfterChangeWorld = Notice.chat("<red>✘ <dark_red>Teleportation cancelled due to world change!");
    public Notice failureAfterTakingDamage = Notice.chat("<red>✘ <dark_red>Teleportation cancelled due to taking damage!");
    public Notice failtureAfterMoved = Notice.chat("<red>✘ <dark_red>Teleportation cancelled due to movement!");
    public Notice failtureAlreadyInTeleport = Notice.chat("<red>✘ <dark_red>You now have active teleportation!!");
}
