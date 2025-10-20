package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLButcherMessages extends OkaeriConfig implements ButcherMessages {
    Notice killed = Notice.chat("<green>► <white>Zabiłeś <green>{KILLED} <white>mobów!");
    Notice safeChunksLimitExceeded = Notice.chat("<red>✘ <dark_red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    Notice invalidChunkNumber = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
}
