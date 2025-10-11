package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGodModeMessages extends OkaeriConfig implements GodModeMessages {
    
    @Comment({" ", "# {STATE} - Invulnerability status"})
    public Notice enabled = Notice.chat("<green>► <white>Invulnerability is now {STATE}");
    public Notice disabled = Notice.chat("<green>► <white>Invulnerability is now {STATE}");

    @Comment({" ", "# {PLAYER} - Target player, {STATE} - Target player invulnerability status"})
    public Notice enabledFor = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>invulnerability is now: {STATE}");
    public Notice disabledFor = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>invulnerability is now: {STATE}");
}

