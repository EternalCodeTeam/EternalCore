package com.eternalcode.core.feature.broadcast.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLBroadcastMessages extends OkaeriConfig implements BroadcastMessages {

    @Comment("# {BROADCAST} - Ogłoszenie")
    public String messageFormat = "<red><bold>OGŁOSZENIE:</bold> <gray>{BROADCAST}";

    public Notice queueAdded = Notice.chat("<green>► <white>Dodano wiadomość do kolejki!");
    public Notice queueRemovedSingle = Notice.chat("<green>► <white>Usunięto ostatnią wiadomość z kolejki!");
    public Notice queueRemovedAll = Notice.chat("<green>► <white>Usunięto wszystkie wiadomości z kolejki!");
    public Notice queueCleared = Notice.chat("<green>► <white>Wyczyszczono kolejkę wiadomości!");
    public Notice queueEmpty = Notice.chat("<red>✘ <dark_red>Kolejka wiadomości jest pusta!");
    public Notice queueSent = Notice.chat("<green>► <white>Wysłano wszystkie wiadomości z kolejki!");

}
