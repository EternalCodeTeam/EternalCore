package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENEnchantMessages extends OkaeriConfig implements EnchantMessages {

    Notice enchantedItem = Notice.chat("<green>► <white>Item in hand is enchanted!");
    Notice enchantedTargetPlayerItem = Notice.chat("<green>► <white>Item in hand of <green>{PLAYER} <white>is enchanted!");
    Notice enchantedItemByAdmin = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>enchanted your item!");
    Notice enchantmentNotFound = Notice.chat("<red>✘ <dark_red>Invalid enchantment provided!");
    Notice enchantmentLevelUnsupported = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
}
