package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTeleportOfflineMessages extends OkaeriConfig implements TeleportOfflineMessages {

    @Comment({" ", "# {PLAYER} - Gracz, który nie grał wcześniej na serwerze"})
    public Notice offlinePlayerNotPlayedBefore = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie grał wcześniej na tym serwerze!");
}
