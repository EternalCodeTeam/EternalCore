package com.eternalcode.core.feature.broadcast.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBroadcastMessages extends OkaeriConfig implements BroadcastMessages {

    @Comment("# {BROADCAST} - Broadcast")
    public String messageFormat = "<red><bold>BROADCAST:</bold> <gray>{BROADCAST}";

    public Notice queueAdded = Notice.chat("<green>► <white>Message added to the queue!");
    public Notice queueRemovedSingle = Notice.chat("<green>► <white>Removed latest message!");
    public Notice queueRemovedAll = Notice.chat("<green>► <white>Removed all messages!");
    public Notice queueCleared = Notice.chat("<green>► <white>Message queue cleared!");
    public Notice queueEmpty = Notice.chat("<red>✘ <dark_red>The message queue is empty!");
    public Notice queueSent = Notice.chat("<green>► <white>All messages sent from the queue!");

}
