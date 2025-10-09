package com.eternalcode.core.feature.heal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENHealMessages extends OkaeriConfig implements HealMessages {
    
    public Notice healMessage = Notice.chat("<green>► <white>You've been healed!");

    @Comment("# {PLAYER} - Healed player")
    public Notice healMessageBy = Notice.chat("<green>► <white>Healed <green>{PLAYER}");
}
