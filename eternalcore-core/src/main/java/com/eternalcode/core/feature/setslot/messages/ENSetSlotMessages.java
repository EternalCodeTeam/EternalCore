package com.eternalcode.core.feature.setslot.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENSetSlotMessages implements SetSlotMessages {
    public Notice slotSaved = Notice.chat("<green>► <white>Server slots have been set to <green>{SLOTS} <white>and saved!");
}
