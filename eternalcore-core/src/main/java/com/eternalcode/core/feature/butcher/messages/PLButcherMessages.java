package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLButcherMessages extends OkaeriConfig implements ButcherMessages {

    @Comment({" ", "# {KILLED} - Liczba zabitych mobów"})
    public Notice butcherCommand = Notice.chat("<green>► <white>Zabiłeś <green>{KILLED} <white>mobów!");

    @Comment({" ", "# {SAFE_CHUNKS} - Liczba bezpiecznych chunków"})
    public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
}
