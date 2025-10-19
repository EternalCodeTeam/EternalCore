package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENButcherMessages extends OkaeriConfig implements ButcherMessages {

    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
}
