package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFlyMessages extends OkaeriConfig implements FlyMessages {
    
    @Comment("# {STATE} - Status latania")
    Notice flyEnabled = Notice.chat("<green>► <white>Latanie zostało {STATE}");
    Notice flyDisabled = Notice.chat("<green>► <white>Latanie zostało {STATE}");
    
    @Comment("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
    Notice flyEnabledForTarget = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");
    Notice flyDisabledForTarget = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");
}

