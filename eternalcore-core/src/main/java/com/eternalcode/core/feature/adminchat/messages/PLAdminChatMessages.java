package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLAdminChatMessages implements AdminChatMessages {

    @Description({"# {PLAYER} - Gracz który wysłał wiadomość na czacie administracji, {TEXT} - Treść wysłanej wiadomości"})
    public Notice format = Notice.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");

    public Notice enabled = Notice.chat("<green>► <white>Włączono trwały czat administracji!");

    public Notice disabled = Notice.chat("<green>► <white>Wyłączono trwały czat administracji!");

    public Notice enabledReminder = Notice.actionbar("<white>[AdminChat] <gray>> <green>włączony");

}
