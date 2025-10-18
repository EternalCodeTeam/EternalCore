package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;

public interface EnchantMessages {
    Notice enchantedItem();
    Notice enchantedTargetPlayerItem();
    Notice enchantedItemByAdmin();
    Notice enchantmentNotFound();
    Notice enchantmentLevelUnsupported();
}
