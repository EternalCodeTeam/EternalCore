package com.eternalcode.core.feature.setslot.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSetSlotMessages extends OkaeriConfig implements SetSlotMessages {
    Notice slotSaved = Notice.chat("<green>► <white>Server slots have been set to <green>{SLOTS} <white>and saved!");
}
