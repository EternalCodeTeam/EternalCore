package com.eternalcode.core.feature.privatechat.messages;

import com.eternalcode.multification.notice.Notice;

public interface PrivateChatMessages {
    Notice noReply();
    Notice privateMessageYouToTarget();
    Notice privateMessageTargetToYou();

    Notice socialSpyMessage();
    Notice socialSpyEnable();
    Notice socialSpyDisable();

    Notice ignorePlayer();
    Notice ignoreAll();
    Notice unIgnorePlayer();
    Notice unIgnoreAll();
    Notice alreadyIgnorePlayer();
    Notice notIgnorePlayer();
    Notice cantIgnoreYourself();
    Notice cantUnIgnoreYourself();
}
