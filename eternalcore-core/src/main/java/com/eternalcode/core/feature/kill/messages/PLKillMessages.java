package com.eternalcode.core.feature.kill.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLKillMessages extends OkaeriConfig implements KillMessages {

    @Comment("# {PLAYER} - Gracz który został zabity")
    public Notice killed = Notice.chat("<red>► <dark_red>Zabito gracza <red>{PLAYER}");
}
