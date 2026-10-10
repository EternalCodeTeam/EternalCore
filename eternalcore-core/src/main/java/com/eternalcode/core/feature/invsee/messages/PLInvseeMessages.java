package com.eternalcode.core.feature.invsee.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLInvseeMessages extends OkaeriConfig implements InvseeMessages {
    Notice cannotInspectSelf = Notice.chat("<red>✘ <dark_red>Nie możesz sprawdzić samego siebie!");
}
