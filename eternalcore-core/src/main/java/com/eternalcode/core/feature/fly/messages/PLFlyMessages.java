package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFlyMessages extends OkaeriConfig implements FlyMessages {
    
    @Comment({" ", "# {STATE} - Status latania"})
    public Notice flyEnable = Notice.chat("<green>► <white>Latanie zostało {STATE}");
    public Notice flyDisable = Notice.chat("<green>► <white>Latanie zostało {STATE}");
    
    @Comment("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
    public Notice flySetEnable = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");
    public Notice flySetDisable = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");
}
