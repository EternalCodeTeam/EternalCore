package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFeedMessages extends OkaeriConfig implements FeedMessages {
    Notice fed = Notice.chat("<color:#9d6eef>► <white>You've been fed!");
    Notice fedTargetPlayer = Notice.chat("<color:#9d6eef>► <white>You've fed the <color:#9d6eef>{PLAYER}");
    Notice fedByAdmin = Notice.chat("<color:#9d6eef>► <white>Administrator <color:#9d6eef>{ADMIN} <white>fed you!");
}

