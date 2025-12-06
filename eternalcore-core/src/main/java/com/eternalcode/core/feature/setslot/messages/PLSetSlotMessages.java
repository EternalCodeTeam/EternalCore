package com.eternalcode.core.feature.setslot.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSetSlotMessages extends OkaeriConfig implements SetSlotMessages {
    Notice slotSaved = Notice.chat("<color:#9d6eef>► <white>Sloty serwera zostały ustawione na <color:#9d6eef>{SLOTS} <white>i zapisane!");
}
