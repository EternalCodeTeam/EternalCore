package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLAdminChatMessages extends OkaeriConfig implements AdminChatMessages {

    @Comment({"# {PLAYER} - Gracz który wysłał wiadomość na czacie administracji, {TEXT} - Treść wysłanej wiadomości"})
    Notice format = Notice.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");

    Notice enabled = Notice.chat("<green>► <white>Włączono trwały czat administracji!");

    Notice disabled = Notice.chat("<green>► <white>Wyłączono trwały czat administracji!");

    Notice enabledReminder = Notice.actionbar("<green> Trwały czat administracji jest włączony!");
}
