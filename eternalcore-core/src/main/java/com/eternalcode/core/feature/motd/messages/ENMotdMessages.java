package com.eternalcode.core.feature.motd.messages;

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
public class ENMotdMessages extends OkaeriConfig implements MotdMessages {

    @Comment("# Message of the Day (MOTD) content that will be sent to players when they join the server.")
    @Comment("# Out of the box supported placeholders: {PLAYER}, {WORLD}, {TIME}")
    @Comment("# You can add your own placeholders using the PlaceholderAPI.")
    @Comment("# You can check our Notification Generator: https://www.eternalcode.pl/notification-generator")
    public Notice motdContent = BukkitNotice.builder()
        .chat(List.of(
                "<green>Welcome to the server,</green> <gradient:#ee1d1d:#f1b722>{PLAYER}</gradient>",
                "<green>Have a good time playing!</green>",
                "<green>The current time in {WORLD} is: </green><gradient:#2c60d5:#742ccf>{TIME}</gradient> <green>ticks",
                "<green>If you need any help, don't hesitate to ask our staff using the </green><dark_green><click:suggest_command:'/helpop'>/helpop</click> command!</dark_green>"
            )
        )
        .title("<gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore</gradient>", "<white>Welcome back to the server!")
        .title("<gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore</gradient>", "<white>Welcome back to the server!")
        .sound(Sound.BLOCK_NOTE_BLOCK_PLING)
        .build();
}
