package com.eternalcode.core.feature.servercapacity.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLServerCapacityMessages implements ServerCapacityMessages {
    public Notice slotSaved = Notice.chat("<green>► <white>Sloty serwera zostały ustawione na <green>{SLOTS} <white>i zapisane!");
}
