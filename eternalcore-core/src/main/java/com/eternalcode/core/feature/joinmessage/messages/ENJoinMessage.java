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
public class ENJoinMessage extends OkaeriConfig implements JoinMessage {

    @Comment({
        "# EternalCore will randomly select one of the messages from the list below",
        "# every time a player joins the server.",
    })
    public List<Notice> playerJoinedServer = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>joined the server!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<green>► <white>Welcome to the server <green>{PLAYER}<white>!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build()
    );

    @Comment({
        "# EternalCore will randomly select one of the messages from the list below",
        "# every time a player joins the server for the first time.",
    })
    public List<Notice> playerJoinedServerFirstTime = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>joined the server for the first time!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<green>► {PLAYER} <white>joined us for the first time!")
            .sound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.8f, 1f)
            .build()
    );
}
