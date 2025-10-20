package com.eternalcode.core.feature.disposal.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLDisposalMessages extends OkaeriConfig implements DisposalMessages {
    String disposalTitle = "<gray>Kosz";
}

