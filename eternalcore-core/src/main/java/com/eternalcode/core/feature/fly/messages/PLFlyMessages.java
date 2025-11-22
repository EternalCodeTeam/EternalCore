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
    Notice flyEnabled = Notice.chat("<color:#9d6eef>► <white>Latanie zostało {STATE}");
    Notice flyDisabled = Notice.chat("<color:#9d6eef>► <white>Latanie zostało {STATE}");
    
    @Comment("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
    Notice flyEnabledForTarget = Notice.chat("<color:#9d6eef>► <white>Latanie dla gracza <color:#9d6eef>{PLAYER} <white>zostało {STATE}");
    Notice flyDisabledForTarget = Notice.chat("<color:#9d6eef>► <white>Latanie dla gracza <color:#9d6eef>{PLAYER} <white>zostało {STATE}");
}

