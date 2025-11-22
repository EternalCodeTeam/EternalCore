package com.eternalcode.core.feature.heal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLHealMessages extends OkaeriConfig implements HealMessages {
    Notice healed = Notice.chat("<green>► <white>Zostałeś uleczony!");
    Notice healedTargetPlayer = Notice.chat("<green>► <white>Uleczyłeś gracza <green>{PLAYER}");
}

