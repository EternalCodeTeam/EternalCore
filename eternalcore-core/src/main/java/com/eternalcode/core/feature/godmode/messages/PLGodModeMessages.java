package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGodModeMessages extends OkaeriConfig implements GodModeMessages {

    @Comment("# {STATE} - Status nieśmiertelności")
    public Notice godModeEnabled = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");
    public Notice godModeDisabled = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");

    @Comment(" {STATE} - Status nieśmiertelności")
    public Notice godModeEnabledForTarget = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");
    public Notice godModeDisabledForTarget = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");
}

