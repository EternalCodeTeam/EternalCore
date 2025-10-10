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
public Notice self = Notice.chat("<green>► <white>Item w twojej ręce został zaklęty!");
public Notice other = Notice.chat("<green>► <white>Item w ręce gracza <green>{PLAYER} <white>został zaklęty!");
public Notice by = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>zaklął twój item!");

    public Notice invalid = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    public Notice invalidLevel = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");
}
