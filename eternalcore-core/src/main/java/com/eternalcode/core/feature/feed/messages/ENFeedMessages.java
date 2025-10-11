package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFeedMessages extends OkaeriConfig implements FeedMessages {
    public Notice received = Notice.chat("<green>► <white>You've been fed!");

    @Comment("# {PLAYER} - Player who has been fed")
    public Notice given = Notice.chat("<green>► <white>You've fed the <green>{PLAYER}");
}

