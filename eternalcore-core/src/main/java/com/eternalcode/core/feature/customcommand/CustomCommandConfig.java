package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.multification.notice.Notice;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;

@ConfigurationFile
public class CustomCommandConfig implements ReloadableConfig {

    @Description({
        "# Custom commands configuration",
        "#",
        "# This file allows you to define your own commands using simple YAML configuration.",
        "# Each command entry supports multiple display formats: chat, title, subtitle, action bar, bossbar and sound.",
        "#",
        "# Example usage:",
        "#   - Display your Discord link with a title and chat message (/discord)",
        "#   - Promote your store using a bossbar (/store)",
        "#   - Show server rules with a subtitle and timed title (/rules)",
        "#",
        "# You can test and generate these easily using:",
        "# https://www.eternalcode.pl/notification-generator"
    })

    @Description({
        " ",
        "# Custom commands",
        "#",
        "# The key is the command name (e.g. \"discord\", \"rules\")",
        "# Each entry can define aliases, title/subtitle with fade in/out, action bars, chat messages and more.",
        "#",
        "# You can define multiple commands freely.",
        "# Example: /vote, /help, /rules, /discord"
    })
    public Map<String, CustomCommand> commands = Map.of(
        "help", new CustomCommand(
            "help",
            List.of("info"),
            Notice.builder()
                .title("<rainbow>Welcome to EternalCore!</rainbow>")
                .subtitle("<gray>Use /help to get started</gray>")
                .times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
                .actionBar("<green>Need support? Try /discord</green>")
                .chat(
                    " ",
                    "<yellow>To add your own commands, edit <aqua>custom-commands.yml</aqua> and restart the server.",
                    "<gray>Use our generator to create nice looking messages:</gray>",
                    "<aqua>https://www.eternalcode.pl/notification-generator</aqua>"
                ).build()
        ),

        "discord", new CustomCommand(
            "discord",
            List.of("dc"),
            Notice.builder()
                .title("<blue>🎧 Discord</blue>")
                .subtitle("<gray>Join the community!</gray>")
                .times(Duration.ofMillis(300), Duration.ofSeconds(2), Duration.ofMillis(300))
                .chat(
                    " ",
                    "<blue>🎧 Join our Discord server:",
                    "<aqua>https://discord.gg/yourserver</aqua>",
                    " ",
                    "<gray>Talk with the team, suggest features, get help.</gray>"
                ).build()
        ),

        "store", new CustomCommand(
            "store",
            List.of("shop"),
            Notice.builder()
                .bossBar(
                    Color.YELLOW, Overlay.NOTCHED_10, Duration.ofSeconds(5), 1.0,
                    "<gold>🛒 Visit our store – support the server & get cool perks!")
                .chat(
                    " ",
                    "<gold>🛒 Visit our server store:",
                    "<aqua>https://store.yourserver.com</aqua>",
                    " ",
                    "<gray>Your support keeps us alive ❤</gray>"
                ).build()
        ),

        "vote", new CustomCommand(
            "vote",
            List.of("votes"),
            Notice.builder()
                .actionBar("<yellow>★ Vote daily and earn rewards!</yellow>")
                .chat(
                    " ",
                    "<yellow>★ Vote for our server and receive daily rewards!",
                    "<aqua>https://yourserver.com/vote</aqua>",
                    " ",
                    "<gray>Each vote helps us grow!</gray>"
                ).build()
        ),

        "rules", new CustomCommand(
            "rules",
            List.of(),
            Notice.builder()
                .title("<red>📜 Server Rules</red>")
                .subtitle("<gray>Read before playing!</gray>")
                .times(Duration.ofMillis(300), Duration.ofSeconds(3), Duration.ofMillis(300))
                .actionBar("<red>No cheating, griefing or toxicity – be respectful!</red>")
                .chat(
                    " ",
                    "<red>📜 Server Rules:",
                    "<aqua>https://yourserver.com/rules</aqua>",
                    " ",
                    "<gray>Breaking rules may result in a ban.</gray>"
                ).build()
        ),

        "map", new CustomCommand(
            "map",
            List.of("dynmap"),
            Notice.builder()
                .chat(
                    " ",
                    "<green>🗺 Live Server Map:",
                    "<aqua>https://map.yourserver.com</aqua>",
                    " ",
                    "<gray>See what others are building in real time!</gray>"
                ).build()
        )
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "custom-commands.yml");
    }
}
