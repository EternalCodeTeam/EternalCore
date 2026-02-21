package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENEnchantMessages extends OkaeriConfig implements EnchantMessages {

    Notice enchantedItem = Notice.chat("<color:#9d6eef>► <white>Item in hand is enchanted!");
    Notice enchantedTargetPlayerItem = Notice.chat("<color:#9d6eef>► <white>Item in hand of <color:#9d6eef>{PLAYER} <white>is enchanted!");
    Notice enchantedItemByAdmin = Notice.chat("<color:#9d6eef>► <white>Administrator <color:#9d6eef>{PLAYER} <white>enchanted your item!");
    Notice enchantmentNotFound = Notice.chat("<red>✘ <dark_red>Invalid enchantment provided!");
    Notice enchantmentLevelUnsupported = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
}
