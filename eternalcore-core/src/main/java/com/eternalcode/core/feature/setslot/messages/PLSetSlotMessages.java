package com.eternalcode.core.feature.setslot.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSetSlotMessages extends OkaeriConfig implements SetSlotMessages {
    Notice slotSaved = Notice.chat("<green>► <white>Sloty serwera zostały ustawione na <green>{SLOTS} <white>i zapisane!");
}
