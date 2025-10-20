package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENButcherMessages extends OkaeriConfig implements ButcherMessages {
    public Notice killed = Notice.chat("<green>► <white>You killed <green>{KILLED} <white>mobs!");
    public Notice safeChunksLimitExceeded = Notice.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    public Notice invalidChunkNumber = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
}
