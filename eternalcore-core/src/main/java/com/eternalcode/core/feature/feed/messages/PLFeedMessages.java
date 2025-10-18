package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFeedMessages extends OkaeriConfig implements FeedMessages {
    public Notice fedYourself = Notice.chat("<green>► <white>Zostałeś nakarmiony!");

    @Comment("# {PLAYER} - Gracz który został nakarmiony")
    public Notice fedTargetPlayer = Notice.chat("<green>► <white>Nakarmiłeś gracza <green>{PLAYER}");

    @Comment("# {ADMIN} - administrator wykonujący komendę")
    public Notice fedByAdmin = Notice.chat("<green>► <white>Administrator <green>{ADMIN} <white>nakarmił cię!");
}

