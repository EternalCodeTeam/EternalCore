package com.eternalcode.core.feature.heal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLHealMessages extends OkaeriConfig implements HealMessages {
    
    public Notice healMessage = Notice.chat("<green>► <white>Zostałeś uleczony!");
    
    @Comment("# {PLAYER} - Gracz który został uleczony")
    public Notice healMessageBy = Notice.chat("<green>► <white>Uleczyłeś gracza <green>{PLAYER}");
}
