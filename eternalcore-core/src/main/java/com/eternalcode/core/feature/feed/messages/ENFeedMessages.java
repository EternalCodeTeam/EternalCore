package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFeedMessages extends OkaeriConfig implements FeedMessages {
    public Notice fedYourself = Notice.chat("<green>► <white>You've been fed!");

    @Comment("# {PLAYER} - Player who has been fed")
    public Notice fedTargetPlayer = Notice.chat("<green>► <white>You've fed the <green>{PLAYER}");

    @Comment("# {ADMIN} - the administrator executing the command")
    public Notice fedByAdmin = Notice.chat("<green>► <white>Administrator <green>{ADMIN} <white>fed you!");
}

