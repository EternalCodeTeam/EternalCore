package com.eternalcode.core.feature.kill.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENKillMessages extends OkaeriConfig implements KillMessages {
    
    @Comment("# {PLAYER} - Killed player")
    public Notice killed = Notice.chat("<red>► <dark_red>Killed <red>{PLAYER}");
}

