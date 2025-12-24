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
public class ENQuitMessage extends OkaeriConfig implements QuitMessage {

    @Comment({
            "# EternalCore will randomly select one of the messages from the list below",
            "# every time a player leaves the server."
    })
    public List<Notice> playerLeftServer = List.of(
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>logged off the server!"),
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>disconnected unexpectedly!"),
            Notice.chat("<color:#9d6eef>► <white>{PLAYER} <color:#9d6eef>left the server!"));
}
