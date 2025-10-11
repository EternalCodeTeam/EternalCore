package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;

public interface EnchantMessages {
    Notice self();
    Notice other();
    Notice by();
    Notice invalid();
    Notice invalidLevel();
}
