package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;

public interface MsgMessages {
    Notice noReply();
    Notice msgYouToTarget();
    Notice msgTargetToYou();

    Notice socialSpyMessage();
    Notice socialSpyEnable();
    Notice socialSpyDisable();

    Notice receiverDisabledMessages();
    Notice selfMessagesDisabled();
    Notice selfMessagesEnabled();
    Notice otherMessagesDisabled();
    Notice otherMessagesEnabled();

    Notice ignorePlayer();
    Notice ignoreAll();
    Notice unIgnorePlayer();
    Notice unIgnoreAll();
    Notice alreadyIgnorePlayer();
    Notice notIgnorePlayer();
    Notice cantIgnoreYourself();
    Notice cantUnIgnoreYourself();
}
