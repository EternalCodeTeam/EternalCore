/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core;

import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.command.binds.MaterialArgument;
import com.eternalcode.core.command.binds.MessageAction;
import com.eternalcode.core.command.binds.MessageActionArgument;
import com.eternalcode.core.command.binds.PermissionMessage;
import com.eternalcode.core.command.binds.PlayerArgument;
import com.eternalcode.core.command.binds.PlayerSenderBind;
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
import com.eternalcode.core.command.implementations.GiveCommand;
import com.eternalcode.core.command.implementations.GodCommand;
import com.eternalcode.core.command.implementations.GrindstoneCommand;
import com.eternalcode.core.command.implementations.HatCommand;
import com.eternalcode.core.command.implementations.HealCommand;
import com.eternalcode.core.command.implementations.HelpOpCommand;
import com.eternalcode.core.command.implementations.InventoryOpenCommand;
import com.eternalcode.core.command.implementations.KillCommand;
import com.eternalcode.core.command.implementations.RepairCommand;
import com.eternalcode.core.command.implementations.ScoreboardCommand;
import com.eternalcode.core.command.implementations.SetSpawnCommand;
import com.eternalcode.core.command.implementations.SkullCommand;
import com.eternalcode.core.command.implementations.SpawnCommand;
import com.eternalcode.core.command.implementations.SpeedCommand;
import com.eternalcode.core.command.implementations.StonecutterCommand;
import com.eternalcode.core.command.implementations.TeleportCommand;
import com.eternalcode.core.command.implementations.WhoIsCommand;
import com.eternalcode.core.command.implementations.WorkbenchCommand;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.listeners.player.PlayerChatListener;
import com.eternalcode.core.listeners.player.PlayerCommandPreprocessListener;
import com.eternalcode.core.listeners.player.PlayerDeathListener;
import com.eternalcode.core.listeners.player.PlayerJoinListener;
import com.eternalcode.core.listeners.player.PlayerQuitListener;
import com.eternalcode.core.listeners.scoreboard.ScoreboardListener;
import com.eternalcode.core.listeners.sign.SignChangeListener;
import com.eternalcode.core.listeners.user.CreateUserListener;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.utils.ChatUtils;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Plugin(name = "EternalCore", version = "1.0.1-APLHA")
@Author("EternalCodeTeam")
@ApiVersion(ApiVersion.Target.v1_17)
@Description("Essential plugin for your server!")
@LogPrefix("EternalCore")

public class EternalCore extends JavaPlugin {

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    @Getter private ConfigurationManager configurationManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private TeleportManager teleportManager;
    @Getter private static EternalCore instance;
    @Getter private LiteCommands liteCommands;
    @Getter private ChatManager chatManager;
    @Getter private UserManager userManager;
    @Getter private Scheduler scheduler;
    private boolean isPaper = false;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();

        this.softwareCheck();

        instance = this;

        this.scheduler = new BukkitSchedulerImpl(this);
        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadAndRenderConfigs();

        this.scoreboardManager = new ScoreboardManager(this, this.configurationManager);
        this.scoreboardManager.updateTask();

        this.chatManager = new ChatManager(configurationManager.getPluginConfiguration());

        this.teleportManager = new TeleportManager();

        // bStats metrics
        // Metrics metrics = new Metrics(this, 13964);
        // metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        // Services
        this.userManager = new UserManager();

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "EternalCore")
            .argument(Player.class, new PlayerArgument(this.configurationManager.getMessagesConfiguration(), this.getServer()))
            .argument(Option.class, new PlayerArgument(this.configurationManager.getMessagesConfiguration(), this.getServer()).toOptionHandler())
            .argument(MessageAction.class, new MessageActionArgument(this.configurationManager.getMessagesConfiguration()))
            .argument(Material.class, new MaterialArgument(this.configurationManager.getMessagesConfiguration()))
            .bind(Player.class, new PlayerSenderBind(this.configurationManager.getMessagesConfiguration()))
            .bind(ConfigurationManager.class, () -> this.configurationManager)
            .bind(MessagesConfiguration.class, () -> this.configurationManager.getMessagesConfiguration())
            .bind(PluginConfiguration.class, () -> this.configurationManager.getPluginConfiguration())
            .bind(LocationsConfiguration.class, () -> this.configurationManager.getLocationsConfiguration())
            .bind(TeleportManager.class, () -> this.teleportManager)
            .bind(EternalCore.class, () -> this)
            .bind(UserManager.class, () -> this.userManager)
            .bind(Server.class, this.getServer())
            .placeholders(this.configurationManager.getCommandsConfiguration().commandsSection.commands.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v::getValue)))
            .message(ValidationInfo.NO_PERMISSION, new PermissionMessage(this.configurationManager.getMessagesConfiguration()))
            .command(
                AdminChatCommand.class,
                TeleportCommand.class,
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
                HelpOpCommand.class,
                InventoryOpenCommand.class,
                RepairCommand.class,
                GiveCommand.class,
                SetSpawnCommand.class,
                SpawnCommand.class
            )
            .register();

        // Register events
        PandaStream.of(
            new PlayerChatListener(this.chatManager, this.configurationManager, this.getServer()),
            new PlayerJoinListener(this.configurationManager, this.getServer()),
            new PlayerQuitListener(this.configurationManager, this.getServer()),
            new CreateUserListener(this.userManager),
            new ScoreboardListener(this.configurationManager.getPluginConfiguration(), this.scoreboardManager),
            new PlayerCommandPreprocessListener(this.configurationManager, this.getServer()),
            new SignChangeListener(),
            new PlayerDeathListener(this.configurationManager),
            new TeleportListeners(this.configurationManager.getMessagesConfiguration(), this.teleportManager)
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        TeleportTask task = new TeleportTask(this.configurationManager.getMessagesConfiguration(), this.teleportManager, this.getServer());

        this.scheduler.runTaskTimer(task, 10, 10);

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info(ChatUtils.color("&7Successfully loaded EternalCore in " + millis + "ms"));
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatformManager().unregisterCommands();

        PluginConfiguration config = this.configurationManager.getPluginConfiguration();

        config.chat.enabled = chatManager.isChatEnabled();
        config.chat.slowMode = chatManager.getChatDelay();

        this.configurationManager.render(config);
    }

    private void softwareCheck() {
        try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            this.isPaper = true;
        } catch (ClassNotFoundException classNotFoundException) {
            this.getLogger().warning(ChatUtils.color("&c&lYour server running on unsupported software, use paper minecraft software and other paper 1.17x forks"));
            this.getLogger().warning(ChatUtils.color("&c&lDownload paper from https://papermc.io/downloads"));
            this.getLogger().warning(ChatUtils.color("&6&lWARRING&r &6Supported minecraft version is 1.17-1.18x"));
        }

        if (this.isPaper) {
            this.getLogger().info(ChatUtils.color("&a&lYour server running on supported software, congratulations!"));
            this.getLogger().info(ChatUtils.color("&a&lServer version: &7" + Bukkit.getServer().getVersion()));
        }

        switch (VERSION) {
            case "v1_8_R1", "v1_8_R2", "v1_8_R3", "v1_9_R1", "v1_9_R2", "v1_10_R1", "v1_11_R1", "v1_12_R1", "v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1", "v1_16_R1" -> this.getLogger().info("EternalCore no longer supports your version, be aware that there may be bugs!");
        }
    }
}
