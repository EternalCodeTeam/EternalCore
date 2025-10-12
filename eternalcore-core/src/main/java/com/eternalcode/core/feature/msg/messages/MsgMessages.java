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

    Placeholders placeholders();

    interface Placeholders {
        String msgEnabled();
        String msgDisabled();

        String socialSpyEnabled();
        String socialSpyDisabled();
    }

}
