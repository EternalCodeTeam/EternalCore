package com.eternalcode.core.feature.disposal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENDisposalMessages extends OkaeriConfig implements DisposalMessages {
    String disposalTitle = "<gray>Trash";
    Notice disposalOpened = Notice.chat("<green>► <white>Disposal opened!");
    Notice disposalForTargetOpened = Notice.chat("<green>► <white>Opened a disposal for {PLAYER}!");
}
