package com.eternalcode.core.feature.enchant.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLEnchantMessages extends OkaeriConfig implements EnchantMessages {

    @Comment(" ")
    public Notice enchantedMessage = Notice.chat("<green>► <white>Item w twojej ręce został zaklęty!");
    
    public Notice enchantedMessageFor = Notice.chat("<green>► <white>Item w ręce gracza <green>{PLAYER} <white>został zaklęty!");
    
    public Notice enchantedMessageBy = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>zaklął twój item!");

    public Notice invalidEnchantment = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    public Notice invalidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");
}
