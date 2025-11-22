package com.eternalcode.core.feature.setslot.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSetSlotMessages extends OkaeriConfig implements SetSlotMessages {
    Notice slotSaved = Notice.chat("<color:#9d6eef>► <white>Server slots have been set to <color:#9d6eef>{SLOTS} <white>and saved!");
}
