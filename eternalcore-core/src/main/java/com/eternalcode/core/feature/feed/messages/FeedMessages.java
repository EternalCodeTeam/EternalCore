package com.eternalcode.core.feature.feed.messages;

import com.eternalcode.multification.notice.Notice;

public interface FeedMessages {
    Notice fed();
    Notice fedTargetPlayer();
    Notice fedByAdmin();
}

