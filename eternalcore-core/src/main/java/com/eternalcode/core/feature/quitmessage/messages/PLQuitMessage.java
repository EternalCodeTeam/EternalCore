package com.eternalcode.core.feature.quitmessage.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class PLQuitMessage extends OkaeriConfig implements QuitMessage {

    @Comment({
            "# EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz opuści serwer."
    })
    public List<Notice> playerLeftServer = List.of(
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>wylogował się z serwera!"),
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>rozłączył się nieoczekiwanie!"),
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>opuścił serwer!"));
}
