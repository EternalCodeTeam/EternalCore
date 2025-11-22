package com.eternalcode.core.feature.fun.endscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)

public class ENEndScreenMessages extends OkaeriConfig implements EndScreenMessages {

    Notice shownToSelf = Notice.chat("<green>► <white>You have shown the end screen to yourself!</white>");

    @Comment("# {PLAYER} - returns player's name")
    Notice shownToOtherPlayer = Notice.chat("<green>► <white>You have shown the end screen to player <green>{PLAYER}!</green>");
}
