package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFeedMessages extends OkaeriConfig implements FeedMessages {
    Notice fed = Notice.chat("<green>► <white>You've been fed!");
    Notice fedTargetPlayer = Notice.chat("<green>► <white>You've fed the <green>{PLAYER}");
    Notice fedByAdmin = Notice.chat("<green>► <white>Administrator <green>{ADMIN} <white>fed you!");
}

