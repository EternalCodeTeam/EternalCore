package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;

public interface EnchantMessages {
    Notice enchantedMessage();
    Notice enchantedMessageFor();
    Notice enchantedMessageBy();
    Notice invalidEnchantment();
    Notice invalidEnchantmentLevel();
}
