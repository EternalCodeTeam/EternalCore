package com.eternalcode.core.feature.motd.messages;

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
public class ENMotdMessages extends OkaeriConfig implements MotdMessages {

    @Comment("# Message of the Day (MOTD) content that will be sent to players when they join the server.")
    @Comment("# Out of the box supported placeholders: {PLAYER}, {WORLD}")
    @Comment("# You can add your own placeholders using the PlaceholderAPI.")
    @Comment("# You can check our Notification Generator: https://www.eternalcode.pl/notification-generator")
    Notice motdContent = BukkitNotice.builder()
        .chat(List.of(
                " ",
                " <color:#9d6eef>🠚 <white>Welcome Back, <gradient:#9d6eef:#A1AAFF:#9d6eef>{PLAYER}</gradient>!</white>",
                " ",
                " <white>Useful commands:",
                "  <color:#9d6eef>⏺ <white>/discord - link to the Discord",
                "  <color:#9d6eef>⏺ <white>/help - server help",
                "  <color:#9d6eef>⏺ <white>/dynmap - server map"
            )
        )
        .title("<gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore</gradient>", "<white>Welcome back to the server!")
        .sound(Sound.BLOCK_NOTE_BLOCK_PLING)
        .build();
}
