/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core;

import com.eternalcode.core.command.binds.CommandInfoBind;
import com.eternalcode.core.command.implementations.AdminChatCommand;
import com.eternalcode.core.command.implementations.AlertCommand;
import com.eternalcode.core.command.implementations.AnvilCommand;
import com.eternalcode.core.command.implementations.CartographyTableCommand;
import com.eternalcode.core.command.implementations.ChatCommand;
import com.eternalcode.core.command.implementations.ClearCommand;
import com.eternalcode.core.command.implementations.DisposalCommand;
import com.eternalcode.core.command.implementations.EnderchestCommand;
import com.eternalcode.core.command.implementations.EternalCoreCommand;
import com.eternalcode.core.command.implementations.FeedCommand;
import com.eternalcode.core.command.implementations.FlyCommand;
import com.eternalcode.core.command.implementations.GamemodeCommand;
import com.eternalcode.core.command.implementations.GcCommand;
import com.eternalcode.core.command.implementations.GodCommand;
import com.eternalcode.core.command.implementations.GrindstoneCommand;
import com.eternalcode.core.command.implementations.HatCommand;
import com.eternalcode.core.command.implementations.HealCommand;
import com.eternalcode.core.command.implementations.HelpOpCommand;
import com.eternalcode.core.command.implementations.KillCommand;
import com.eternalcode.core.command.implementations.ScoreboardCommand;
import com.eternalcode.core.command.implementations.SkullCommand;
import com.eternalcode.core.command.implementations.SpeedCommand;
import com.eternalcode.core.command.implementations.StonecutterCommand;
import com.eternalcode.core.command.implementations.WhoIsCommand;
import com.eternalcode.core.command.implementations.WorkbenchCommand;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.listeners.PlayerBasicListeners;
import com.eternalcode.core.listeners.PlayerChatListener;
import com.eternalcode.core.scoreboard.ScoreboardListener;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import com.eternalcode.core.user.CreateUserListener;
import com.eternalcode.core.user.UserService;
import com.eternalcode.core.utils.ChatUtils;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import net.dzikoysk.funnycommands.FunnyCommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import panda.std.stream.PandaStream;
import java.util.concurrent.TimeUnit;

@Plugin(name = "EternalCore", version = "1.0")
@Author("EternalCodeTeam")
@ApiVersion(ApiVersion.Target.v1_17)
@Description("Essential plugin for your server!")
@SoftDependency(value = "PlaceholderAPI")
@LogPrefix("EternalCore")

public final class EternalCore extends JavaPlugin {

    @Getter private static EternalCore instance;
    @Getter private UserService userService;
    @Getter private FunnyCommands funnyCommands;
    @Getter private ConfigurationManager configurationManager;
    @Getter private ScoreboardManager scoreboardManager;
    private boolean isPaper = false;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();

        this.softwareCheck();

        instance = this;

        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.loadConfigs();

        this.scoreboardManager = new ScoreboardManager(this);
        this.scoreboardManager.updateTask();

        MessagesConfiguration config = configurationManager.getMessagesConfiguration();

        // bStats metrics
        // TODO: Jakieś ładne custom charty
        // Metrics metrics = new Metrics(this, 13026);
        // metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        // Services
        this.userService = new UserService();
        this.funnyCommands = FunnyCommands.configuration(() -> this)
            .bind(resources -> resources.on(EternalCore.class).assignInstance(this))
            .bind(resources -> resources.on(UserService.class).assignInstance(userService))
            .bind(resources -> resources.on(ConfigurationManager.class).assignInstance(configurationManager))
            .bind(resources -> resources.on(ScoreboardManager.class).assignInstance(scoreboardManager))
            .bind(new CommandInfoBind())
            .registerDefaultComponents()
            .permissionHandler((message, permission) -> message.getCommandSender().sendMessage(ChatUtils.color(config.permissionMessage.replace("{PERMISSION}", permission))))
            .commands(
                AlertCommand.class,
                AnvilCommand.class,
                CartographyTableCommand.class,
                ChatCommand.class,
                ClearCommand.class,
                DisposalCommand.class,
                EnderchestCommand.class,
                FeedCommand.class,
                FlyCommand.class,
                GamemodeCommand.class,
                GcCommand.class,
                GodCommand.class,
                GrindstoneCommand.class,
                HatCommand.class,
                HealCommand.class,
                KillCommand.class,
                SkullCommand.class,
                SpeedCommand.class,
                StonecutterCommand.class,
                WhoIsCommand.class,
                WorkbenchCommand.class,
                EternalCoreCommand.class,
                ScoreboardCommand.class,
                AdminChatCommand.class,
                HelpOpCommand.class
            ).install();

        // Register events
        PandaStream.of(
            new PlayerChatListener(),
            new PlayerBasicListeners(configurationManager),
            new CreateUserListener(this),
            new ScoreboardListener(this)
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info(ChatUtils.color("&7Successfully loaded EternalCore in " + millis + "ms"));
    }

    @Override
    public void onDisable() {
        funnyCommands.dispose();
    }

    private void softwareCheck() {
        try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            isPaper = true;
        } catch (ClassNotFoundException classNotFoundException) {
            getLogger().warning(ChatUtils.color("&c&lYour server running on unsupported software, use paper minecraft software and other paper 1.13x forks"));
            getLogger().warning(ChatUtils.color("&c&lDownload paper from https://papermc.io/downloads"));
            getLogger().warning(ChatUtils.color("&6&lWARRING&r &6Supported minecraft version is 1.13-1.18x"));
        }

        if (isPaper) {
            getLogger().info(ChatUtils.color("&a&lYour server running on supported software, congratulations!"));
            getLogger().info(ChatUtils.color("&a&lServer version: &7" + Bukkit.getServer().getVersion()));
        }
    }
}
