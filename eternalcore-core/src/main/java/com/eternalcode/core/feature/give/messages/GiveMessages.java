package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;

public interface GiveMessages {
    Notice itemGivenByAdmin();
    Notice itemGivenToTargetPlayer();
    Notice noSpace();
    Notice itemNotFound();
}

