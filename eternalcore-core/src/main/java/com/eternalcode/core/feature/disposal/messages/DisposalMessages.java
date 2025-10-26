package com.eternalcode.core.feature.disposal.messages;

import com.eternalcode.multification.notice.Notice;

public interface DisposalMessages {
    String disposalInventoryTitle();
    Notice disposalOpened();
    Notice disposalOpenedForTargetPlayer();
}

