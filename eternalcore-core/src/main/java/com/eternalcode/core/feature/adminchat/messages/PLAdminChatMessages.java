package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLAdminChatMessages extends OkaeriConfig implements AdminChatMessages {

    @Comment("# {PLAYER} - nazwa gracza, {TEXT} - treść wiadomości")
    Notice format = Notice.chat(
            "<dark_gray>[<color:#9d6eef>Admin<dark_gray>] <gradient:#9d6eef:#A1AAFF>{PLAYER}</gradient><dark_gray>: <white>{TEXT}");

    Notice enabled = Notice.chat("<color:#9d6eef>► <white>Włączono trwały czat administracji!");

    Notice disabled = Notice.chat("<color:#9d6eef>► <white>Wyłączono trwały czat administracji!");

    Notice enabledReminder = Notice.actionbar("<color:#9d6eef>Trwały czat administracji jest włączony!");
}
