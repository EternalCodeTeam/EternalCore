package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENEnchantMessages extends OkaeriConfig implements EnchantMessages {

    @Comment(" ")
    public Notice enchantedMessage = Notice.chat("<green>► <white>Item in hand is enchanted!");
    
    public Notice enchantedMessageFor = Notice.chat("<green>► <white>Item in hand of <green>{PLAYER} <white>is enchanted!");
    
    public Notice enchantedMessageBy = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>enchanted your item!");

    public Notice invalidEnchantment = Notice.chat("<red>✘ <dark_red>Invalid enchantment provided!");
    public Notice invalidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
}
