package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLEnchantMessages extends OkaeriConfig implements EnchantMessages {

    Notice enchantedItem = Notice.chat("<green>► <white>Item w twojej ręce został zaklęty!");
    Notice enchantedTargetPlayerItem = Notice.chat("<green>► <white>Item w ręce gracza <green>{PLAYER} <white>został zaklęty!");
    Notice enchantedItemByAdmin = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>zaklął twój item!");
    Notice enchantmentNotFound = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    Notice enchantmentLevelUnsupported = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");

}
