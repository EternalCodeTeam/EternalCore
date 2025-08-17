package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTeleportOfflineMessages extends OkaeriConfig implements TeleportOfflineMessages {

    @Comment({" ", "# {PLAYER} - Player who has never played before"})
    public Notice offlinePlayerNotPlayedBefore = Notice.chat("<red>✘ <dark_red>Player <red>{PLAYER} <dark_red>has never played on this server before!");
}
