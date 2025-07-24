package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import java.io.File;
import java.time.Duration;
import java.util.List;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;

@ConfigurationFile
@Header({
        " ",
        "Custom commands configuration",
        "",
        "This file allows you to define your own commands using simple YAML configuration.",
        "Each command entry supports multiple display formats: chat, title, subtitle, action bar, bossbar and sound.",
        "",
        "Example usage:",
        "- Display your Discord link with a title and chat message (/discord)",
        "- Promote your store using a bossbar (/store)",
        "- Show server rules with a subtitle and timed title (/rules)",
        "",
        "You can test and generate these easily using:",
        "https://www.eternalcode.pl/notification-generator"
})
public class CustomCommandConfig extends AbstractConfigurationFile {

    @Comment({
            " ",
            "# Custom commands",
            "#",
            "# Each command defines its name, aliases and message configuration.",
            "#",
            "# WARNING: You must restart the server after editing this file for changes to take effect.",
    })
    public List<CustomCommand> commands = List.of(
            CustomCommand.of(
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
            CustomCommand.of(
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
            CustomCommand.of(
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
            CustomCommand.of(
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
            CustomCommand.of(
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
            CustomCommand.of(
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
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "custom-commands.yml");
    }
}
