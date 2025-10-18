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
        " ",
        "# EternalCore will randomly select one of the messages from the list below",
        "# every time a player leaves the server.",
        "# {PLAYER} - Player who left the server."
    })
    public List<Notice> quitMessage = List.of(
        BukkitNotice.builder()
            .actionBar("<dark_red>{PLAYER} <white>logged off the server!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<dark_red>{PLAYER} <white>disconnected unexpectedly!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8f, 1f)
            .build(),

        BukkitNotice.builder()
            .actionBar("<dark_red>{PLAYER} <white>left the server!")
            .sound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.8f, 1f)
            .build()
    );
}
