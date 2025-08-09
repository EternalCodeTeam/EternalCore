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
public class ENJoinQuitMessages extends OkaeriConfig implements JoinQuitMessages {

    @Comment("# {PLAYER} - Player who joined")
    public List<Notice> joinMessage = List.of(
        BukkitNotice.builder()
            .actionBar("<green>► <green>{PLAYER} <white>joined the server!")
            .sound(Sound.BLOCK_BEACON_ACTIVATE, 1.8F, 1F)
            .build(),
        BukkitNotice.builder()
            .actionBar("<green>► <white>Welcome to the server <green>{PLAYER}<white>!")
            .sound(Sound.BLOCK_BEACON_ACTIVATE, 1.8F, 1F)
            .build()
    );

    @Comment("# {PLAYER} - Player who joined.")
    public List<Notice> firstJoinMessage = List.of(
        BukkitNotice.builder()
            .sound(Sound.ENTITY_VILLAGER_CELEBRATE)
            .actionBar("<green>► {PLAYER} <white>joined the server for the first time!")
            .build(),
        BukkitNotice.builder()
            .sound(Sound.ENTITY_VILLAGER_CELEBRATE)
            .actionBar("<green>► {PLAYER} <white>welcome to the server for the first time!")
            .build()
    );

    @Comment("# {PLAYER} - Player who left")
    public List<Notice> quitMessage = List.of(
        BukkitNotice.builder()
            .actionBar("<red>► {PLAYER} <white>logged off the server!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8F, 1F)
            .build(),
        BukkitNotice.builder()
            .actionBar("<red>► {PLAYER} <white>left the server!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8F, 1F)
            .build()
    );
}
