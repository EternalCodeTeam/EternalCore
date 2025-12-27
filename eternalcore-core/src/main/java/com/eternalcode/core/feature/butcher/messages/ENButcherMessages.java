package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENButcherMessages extends OkaeriConfig implements ButcherMessages {
    Notice killed = Notice.chat("<color:#9d6eef>► <white>You killed <color:#9d6eef>{KILLED} <white>mobs!");
    Notice safeChunksLimitExceeded = Notice.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    Notice invalidChunkNumber = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
}
