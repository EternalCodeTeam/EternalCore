package com.eternalcode.core.feature.joinmessage.messages;

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
public class PLJoinMessage extends OkaeriConfig implements JoinMessage {

    @Comment({
        "# EternalCore będzie losował losową wiadomość z poniższej listy",
        "# za każdym razem gdy gracz dołączy do serwera.",
    })
    public List<Notice> playerJoinedServer = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>dołączył do serwera!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<green>► <white>Witaj na serwerze <green>{PLAYER}<white>!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build()
    );

    @Comment({
        "# EternalCore będzie losował losową wiadomość z poniższej listy",
        "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy.",
    })
    public List<Notice> playerJoinedServerFirstTime = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>dołączył do serwera po raz pierwszy!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>zawitał u nas po raz pierwszy!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build()
    );
}
