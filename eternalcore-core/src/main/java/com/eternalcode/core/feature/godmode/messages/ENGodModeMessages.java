package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGodModeMessages extends OkaeriConfig implements GodModeMessages {
    
    @Comment("# {STATE} - Invulnerability status")
    public Notice godModeEnabled = Notice.chat("<green>► <white>Invulnerability is now {STATE}");
    public Notice godModeDisabled = Notice.chat("<green>► <white>Invulnerability is now {STATE}");

    @Comment("# {STATE} - Target player invulnerability status")
    public Notice godModeEnabledForTarget = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>invulnerability is now: {STATE}");
    public Notice godModeDisabledForTarget = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>invulnerability is now: {STATE}");
}

