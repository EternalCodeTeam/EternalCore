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

    @Comment("# {PLAYER} - Gracz który teleportował cię na spawn")
    public Notice spawnTeleportedBy =
        Notice.chat("<green>► <white>Zostałeś przeteleportowany na spawn przez gracza <green>{PLAYER}<white>!");

    @Comment("# {PLAYER} - Gracz który został przeteleportowany na spawn")
    public Notice spawnTeleportedOther =
        Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany na spawn!");

    public Notice countDown = Notice.title("<green>Teleportacja na spawn za: <white>{TIME}");

    public Notice start = Notice.chat("<green>► <white>Teleportacja na spawn...");

    public Notice succes = Notice.chat("<green>► <white>Pomyślnie przeteleportowano na spawn!");

    public Notice failureAfterChangeWorld = Notice.chat("<red>✘ <dark_red>Teleportacja przerwana z powodu zmiany świata!");
    public Notice failureAfterTakingDamage = Notice.chat("<red>✘ <dark_red>Teleportacja przerwana z powodu otrzymania obrażen!");
    public Notice failtureAfterMoved = Notice.chat("<red>✘ <dark_red>Teleportacja przerwana z powodu poruszenia się!");
    public Notice failtureAlreadyInTeleport = Notice.chat("<red>✘ <dark_red>Posiadasz już aktywną teleportację!");
}
