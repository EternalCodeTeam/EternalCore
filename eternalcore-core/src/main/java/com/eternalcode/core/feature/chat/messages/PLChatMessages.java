package com.eternalcode.core.feature.chat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLChatMessages extends OkaeriConfig implements ChatMessages {
    Notice chatDisabled = Notice.chat("<green>► <white>Czat został wyłączony przez <green>{PLAYER}<white>!");
    Notice chatEnabled = Notice.chat("<green>► <white>Czat został włączony przez <green>{PLAYER}<white>!");
    Notice chatCleared = Notice.chat("<green>► <white>Czat został wyczyszczony przez <green>{PLAYER}<white>!");

    @Comment
    Notice chatAlreadyDisabled = Notice.chat("<red>✘ <dark_red>Czat jest już wyłączony!");
    Notice chatAlreadyEnabled = Notice.chat("<red>✘ <dark_red>Czat jest już włączony!");

    @Comment
    Notice slowModeSet = Notice.chat("<green>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");
    Notice slowModeDisabled = Notice.chat("<green>► <white>Tryb powolnego wysyłania został wyłączony przez <green>{PLAYER}<white>!");
    @Comment({" ", "# {TIME} - Czas powolnego wysyłania wiadomości"})
    Notice slowModeNotReady = Notice.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za <red>{TIME}<dark_red>!");

    @Comment
    Notice chatDisabledInfo = Notice.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");

    @Comment
    Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");
}
