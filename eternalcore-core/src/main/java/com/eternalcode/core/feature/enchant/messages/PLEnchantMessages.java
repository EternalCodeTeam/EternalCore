package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLEnchantMessages extends OkaeriConfig implements EnchantMessages {

    Notice enchantedItem = Notice.chat("<color:#9d6eef>► <white>Item w twojej ręce został zaklęty!");
    Notice enchantedTargetPlayerItem = Notice.chat("<color:#9d6eef>► <white>Item w ręce gracza <color:#9d6eef>{PLAYER} <white>został zaklęty!");
    Notice enchantedItemByAdmin = Notice.chat("<color:#9d6eef>► <white>Administrator <color:#9d6eef>{PLAYER} <white>zaklął twój item!");
    Notice enchantmentNotFound = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    Notice enchantmentLevelUnsupported = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");

}
