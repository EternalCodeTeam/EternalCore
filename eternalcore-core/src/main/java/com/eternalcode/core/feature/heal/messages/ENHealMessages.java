package com.eternalcode.core.feature.heal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENHealMessages extends OkaeriConfig implements HealMessages {
    Notice healed = Notice.chat("<green>► <white>You've been healed!");
    Notice healedTargetPlayer = Notice.chat("<green>► <white>Healed <green>{PLAYER}");
}

