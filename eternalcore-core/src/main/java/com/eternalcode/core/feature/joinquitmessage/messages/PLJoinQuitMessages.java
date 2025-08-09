package com.eternalcode.core.feature.joinquitmessage.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLJoinQuitMessages extends OkaeriConfig implements JoinQuitMessages {

    @Comment({
        "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
        "# za każdym razem gdy gracz dołączy do serwera",
        "# {PLAYER} - Gracz który dołączył do serwera",
    })
    public List<Notice> joinMessage = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► <green>{PLAYER} <white>dołączył do serwera!")
            .sound(Sound.BLOCK_BEACON_ACTIVATE, 1.8F, 1F)
            .build(),
        BukkitNotice.builder()
            .actionBar("<green>► <white>Witaj na serwerze <green>{PLAYER}<white>!")
            .sound(Sound.BLOCK_BEACON_ACTIVATE, 1.8F, 1F)
            .build()
    );

    @Comment({
        "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
        "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy",
        "# {PLAYER} - Gracz który dołączył do serwera po raz pierwszy"
    })
    public List<Notice> firstJoinMessage = List.of(
        BukkitNotice.builder()
            .sound(Sound.ENTITY_VILLAGER_CELEBRATE)
            .actionBar("<green>► {PLAYER} <white>dołączył do serwera po raz pierwszy!")
            .build(),
        BukkitNotice.builder()
            .sound(Sound.ENTITY_VILLAGER_CELEBRATE)
            .actionBar("<green>► {PLAYER} <white>zawitał u nas po raz pierwszy!")
            .build()
    );

    @Comment({
        "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
        "# za każdym razem gdy gracz opuści serwer",
        "# {PLAYER} - Gracz który opuścił serwer"
    })
    public List<Notice> quitMessage = List.of(
        BukkitNotice.builder()
            .actionBar("<red>► {PLAYER} <white>opuścił serwer!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8F, 1F)
            .build(),
        BukkitNotice.builder()
            .actionBar("<red>► {PLAYER} <white>wylogował się z serwera!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8F, 1F)
            .build()
    );
}
