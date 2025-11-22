package com.eternalcode.core.feature.disposal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENDisposalMessages extends OkaeriConfig implements DisposalMessages {
    String disposalInventoryTitle = "<gray>Trash";
    Notice disposalOpened = Notice.chat("<color:#9d6eef>► <white>Disposal opened!");
    Notice disposalOpenedForTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Opened a disposal for {PLAYER}!");
}
