package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;

public interface GiveMessages {
    Notice giveReceived();
    Notice giveGiven();
    Notice giveNoSpace();
    Notice giveNotItem();
}
