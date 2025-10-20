package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLButcherMessages extends OkaeriConfig implements ButcherMessages {
    public Notice killed = Notice.chat("<green>► <white>Zabiłeś <green>{KILLED} <white>mobów!");
    public Notice safeChunksLimitExceeded = Notice.chat("<red>✘ <dark_red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    public Notice invalidChunkNumber = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
}
