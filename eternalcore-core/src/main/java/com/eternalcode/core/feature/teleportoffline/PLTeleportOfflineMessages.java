package com.eternalcode.core.feature.teleportoffline;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTeleportOfflineMessages extends OkaeriConfig implements TeleportOfflineMessages {

    @Comment({ " ", "# {PLAYER} - Gracz, który nie grał wcześniej na serwerze" })
    Notice offlinePlayerNotPlayedBefore = Notice
            .chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie grał wcześniej na tym serwerze!");
    Notice lastLocationNotFound = Notice
            .chat("<red>✘ <dark_red>Ostatnia lokalizacja gracza <red>{PLAYER} <dark_red>nie została znaleziona!");
    Notice teleportedToPlayerLastLocation = Notice.chat(
            "<color:#9d6eef>► <white>Teleportowano do ostatniej lokalizacji gracza <color:#9d6eef>{PLAYER}<white>!");
}
