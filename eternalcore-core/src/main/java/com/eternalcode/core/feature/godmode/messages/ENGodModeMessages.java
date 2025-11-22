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
    Notice godModeEnabled = Notice.chat("<color:#9d6eef>► <white>Invulnerability is now {STATE}");
    Notice godModeDisabled = Notice.chat("<color:#9d6eef>► <white>Invulnerability is now {STATE}");

    @Comment("# {STATE} - Target player invulnerability status")
    Notice godModeEnabledForTarget = Notice.chat("<color:#9d6eef>► <white>Player <color:#9d6eef>{PLAYER} <white>invulnerability is now: {STATE}");
    Notice godModeDisabledForTarget = Notice.chat("<color:#9d6eef>► <white>Player <color:#9d6eef>{PLAYER} <white>invulnerability is now: {STATE}");
}

