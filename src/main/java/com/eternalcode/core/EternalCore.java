/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core;

import com.eternalcode.core.command.binds.CommandInfoBind;
import com.eternalcode.core.command.implementations.AlertCommand;
import com.eternalcode.core.command.implementations.AnvilCommand;
import com.eternalcode.core.command.implementations.BanCommand;
import com.eternalcode.core.command.implementations.BanIPCommand;
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
import com.eternalcode.core.command.implementations.KillCommand;
import com.eternalcode.core.command.implementations.SkullCommand;
import com.eternalcode.core.command.implementations.SpeedCommand;
import com.eternalcode.core.command.implementations.StonecutterCommand;
import com.eternalcode.core.command.implementations.WhoIsCommand;
import com.eternalcode.core.command.implementations.WorkbenchCommand;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.listeners.PlayerChatListener;
import com.eternalcode.core.listeners.WeatherChangeListener;
import com.eternalcode.core.user.CreateUserListener;
import com.eternalcode.core.user.UserService;
import com.eternalcode.core.utils.ChatUtils;
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

@Plugin(name = "EternalCore", version = "1.0")
@Author("vLucky, Sidd, Dejmi, VelvetDuck, Rollczi, Jakubk15, Igoyek, zitreF, MastersPR0")
@ApiVersion(ApiVersion.Target.v1_17)
@Description("More than Tools!")
@SoftDependency(value = "PlaceholderAPI")
@LogPrefix("[EternalCore]")

public final class EternalCore extends JavaPlugin {

    @Getter
    private static EternalCore instance;
    @Getter private UserService userService;
    @Getter private FunnyCommands funnyCommands;
    @Getter private ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        instance = this;

        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.loadConfigs();
        MessagesConfiguration messagesConfiguration = configurationManager.getMessagesConfiguration();

        // bStats metrics
        // TODO: Jakieś ładne custom charty
        //Metrics metrics = new Metrics(this, 13026);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        // Services
        this.userService = new UserService();
        this.funnyCommands = FunnyCommands.configuration(() -> this)
            .bind(resources -> resources.on(EternalCore.class).assignInstance(this))
            .bind(resources -> resources.on(UserService.class).assignInstance(userService))
            .bind(resources -> resources.on(ConfigurationManager.class).assignInstance(configurationManager))
            .bind(new CommandInfoBind())
            .registerDefaultComponents()
            .permissionHandler((message, permission) -> message.getCommandSender().sendMessage(ChatUtils.color(messagesConfiguration.permissionMessage.replace("{PERMISSION}", permission))))
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
                BanCommand.class,
                BanIPCommand.class
            ).install();

        // Register events
        PandaStream.of(
            new PlayerChatListener(),
            new WeatherChangeListener(),
            new CreateUserListener(this)
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        getLogger().info(ChatUtils.color("&7Successfully loaded EternalCore in " + (System.currentTimeMillis() - startTime) + "ms"));
    }

    @Override
    public void onDisable() {
        funnyCommands.dispose();
    }
}
