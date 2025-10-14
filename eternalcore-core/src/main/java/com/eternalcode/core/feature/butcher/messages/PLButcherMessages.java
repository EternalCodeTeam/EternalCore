package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLButcherMessages extends OkaeriConfig implements ButcherMessages {

    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
}
