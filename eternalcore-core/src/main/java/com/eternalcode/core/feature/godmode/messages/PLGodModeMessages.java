package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGodModeMessages extends OkaeriConfig implements GodModeMessages {
    
    @Comment({" ", "# {STATE} - Status nieśmiertelności"})
    public Notice godEnable = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");
    public Notice godDisable = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");

    @Comment("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {STATE} - Status nieśmiertelności")
    public Notice godSetEnable = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");
    public Notice godSetDisable = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");
}
