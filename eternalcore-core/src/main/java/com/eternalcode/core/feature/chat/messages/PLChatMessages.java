package com.eternalcode.core.feature.chat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLChatMessages extends OkaeriConfig implements ChatMessages {
    Notice chatDisabled = Notice.chat("<color:#9d6eef>► <white>Czat został wyłączony przez <color:#9d6eef>{PLAYER}<white>!");
    Notice chatEnabled = Notice.chat("<color:#9d6eef>► <white>Czat został włączony przez <color:#9d6eef>{PLAYER}<white>!");
    Notice chatCleared = Notice.chat("<color:#9d6eef>► <white>Czat został wyczyszczony przez <color:#9d6eef>{PLAYER}<white>!");

    @Comment
    Notice chatAlreadyDisabled = Notice.chat("<red>✘ <dark_red>Czat jest już wyłączony!");
    Notice chatAlreadyEnabled = Notice.chat("<red>✘ <dark_red>Czat jest już włączony!");

    @Comment
    Notice slowModeSet = Notice.chat("<color:#9d6eef>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");
    Notice slowModeDisabled = Notice.chat("<color:#9d6eef>► <white>Tryb powolnego wysyłania został wyłączony przez <color:#9d6eef>{PLAYER}<white>!");
    @Comment({" ", "# {TIME} - Czas powolnego wysyłania wiadomości"})
    Notice slowModeNotReady = Notice.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za <red>{TIME}<dark_red>!");

    @Comment
    Notice chatDisabledInfo = Notice.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");

    @Comment
    Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");
}
