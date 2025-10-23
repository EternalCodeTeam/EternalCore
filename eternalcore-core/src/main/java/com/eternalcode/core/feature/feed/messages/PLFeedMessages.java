package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFeedMessages extends OkaeriConfig implements FeedMessages {
    Notice fed = Notice.chat("<green>► <white>Zostałeś nakarmiony!");
    Notice fedTargetPlayer = Notice.chat("<green>► <white>Nakarmiłeś gracza <green>{PLAYER}");
    Notice fedByAdmin = Notice.chat("<green>► <white>Administrator <green>{ADMIN} <white>nakarmił cię!");
}

