package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENButcherMessages extends OkaeriConfig implements ButcherMessages {

    @Comment({" ", "# {KILLED} - Number of killed mobs"})
    public Notice butcherCommand = Notice.chat("<green>► <white>You killed <green>{KILLED} <white>mobs!");

    @Comment({" ", "# {SAFE_CHUNKS} - The number of safe chunks"})
    public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
}
