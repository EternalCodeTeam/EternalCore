/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.notification.AudienceProvider;
import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.chat.notification.BukkitAudienceProvider;
import com.eternalcode.core.chat.notification.NotificationType;
import com.eternalcode.core.command.argument.AmountArgument;
import com.eternalcode.core.command.argument.GameModeArgument;
import com.eternalcode.core.command.argument.MaterialArgument;
import com.eternalcode.core.command.argument.MessageActionArgument;
import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.command.argument.StringPlayerArgument;
import com.eternalcode.core.command.bind.PlayerSenderBind;
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
import com.eternalcode.core.command.implementations.ListCommand;
import com.eternalcode.core.command.implementations.NameCommand;
import com.eternalcode.core.command.implementations.OnlineCommand;
import com.eternalcode.core.command.implementations.PingCommand;
import com.eternalcode.core.command.implementations.RepairCommand;
import com.eternalcode.core.command.implementations.ScoreboardCommand;
import com.eternalcode.core.command.implementations.SetSpawnCommand;
import com.eternalcode.core.command.implementations.SkullCommand;
import com.eternalcode.core.command.implementations.SpawnCommand;
import com.eternalcode.core.command.implementations.SpeedCommand;
import com.eternalcode.core.command.implementations.StonecutterCommand;
import com.eternalcode.core.command.implementations.TeleportCommand;
import com.eternalcode.core.command.implementations.TposCommand;
import com.eternalcode.core.command.implementations.WhoIsCommand;
import com.eternalcode.core.command.implementations.WorkbenchCommand;
import com.eternalcode.core.command.message.PermissionMessage;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.CommandsConfiguration;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.configuration.lang.en.ENMessagesConfiguration;
import com.eternalcode.core.configuration.lang.pl.PLMessagesConfiguration;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.listener.player.PlayerChatListener;
import com.eternalcode.core.listener.player.PlayerCommandPreprocessListener;
import com.eternalcode.core.listener.player.PlayerDeathListener;
import com.eternalcode.core.listener.player.PlayerJoinListener;
import com.eternalcode.core.listener.player.PlayerQuitListener;
import com.eternalcode.core.listener.scoreboard.ScoreboardListener;
import com.eternalcode.core.listener.sign.SignChangeListener;
import com.eternalcode.core.listener.user.CreateUserListener;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.user.UserManager;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Plugin(name = "EternalCore", version = "1.0.2-APLHA")
@Author("EternalCodeTeam")
@ApiVersion(ApiVersion.Target.v1_17)
@Description("Essential plugin for your server!")
@LogPrefix("EternalCore")

public class EternalCore extends JavaPlugin {

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    @Getter private LanguageManager languageManager;
    @Getter private ConfigurationManager configurationManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private AudienceProvider audienceProvider;
    @Getter private AudiencesService audiencesService;
    @Getter private TeleportManager teleportManager;
    @Getter private BukkitAudiences audiences;
    @Getter private LiteCommands liteCommands;
    @Getter private MiniMessage miniMessage;
    @Getter private ChatManager chatManager;
    @Getter private UserManager userManager;
    @Getter private BukkitUserProvider userProvider;
    @Getter private Scheduler scheduler;
    private boolean isPaper = false;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        this.softwareCheck();

        /** Scheduler */

        this.scheduler = new BukkitSchedulerImpl(this);

        /** Configuration */

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadAndRenderConfigs();

        PluginConfiguration config = configurationManager.getPluginConfiguration();
        LocationsConfiguration locations = configurationManager.getLocationsConfiguration();
        CommandsConfiguration commands = configurationManager.getCommandsConfiguration();

        /** Configuration (Language) */

        File langFolder = new File(this.getDataFolder(), "lang");
        Map<Language, Messages> defaultImplementations = new ImmutableMap.Builder<Language, Messages>()
            .put(Language.EN, new ENMessagesConfiguration(langFolder, "en_messages.yml"))
            .put(Language.PL, new PLMessagesConfiguration(langFolder, "pl_messages.yml"))
            .build();

        List<Messages> messages = PandaStream.of(config.chat.languages)
            .map(lang -> defaultImplementations.getOrDefault(lang, new ENMessagesConfiguration(langFolder, lang.getLang() + "_messages.yml")))
            .toList();

        Messages defaultMessages = PandaStream.of(messages)
            .find(m -> m.getLanguage().equals(config.chat.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        this.languageManager = new LanguageManager(defaultMessages);

        for (Messages message : messages) {
            this.configurationManager.loadAndRender(message);
            this.languageManager.loadLanguage(message.getLanguage(), message);
        }

        /** Adventure */

        this.audiences = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.get(); //TODO: Nowe MiniMessage przeszkadza w debugowaniu na paper :(

        /** Services */ // TODO: Przenieść wyżej serwisy (Poprawić architekturę, aby zależności szły w dobre strony)

        this.scoreboardManager = new ScoreboardManager(this, this.configurationManager);
        this.scoreboardManager.updateTask();

        this.chatManager = new ChatManager(config.chat);
        this.teleportManager = new TeleportManager();

        this.userManager = new UserManager();
        this.userProvider = new BukkitUserProvider(userManager); //TODO: Czasowe rozwiazanie, do poprawy (do usuniecia)

        this.audienceProvider = new BukkitAudienceProvider(this.userManager, server, this.audiences);
        this.audiencesService = new AudiencesService(this.languageManager, this.audienceProvider, this.miniMessage);

        /** Commands */

        this.liteCommands = LiteBukkitFactory.builder(server, "EternalCore")
            .argument(String.class, new StringPlayerArgument(server))
            .argument(Integer.class, new AmountArgument(languageManager, userProvider))
            .argument(Player.class, new PlayerArgument(userProvider, languageManager, server))
            .argument(Option.class, new PlayerArgument(userProvider, languageManager, server).toOptionHandler())
            .argument(Material.class, new MaterialArgument(userProvider, languageManager))
            .argument(GameMode.class, new GameModeArgument(userProvider, languageManager))
            .argument(NotificationType.class, new MessageActionArgument(userProvider, languageManager))

            .bind(Player.class, new PlayerSenderBind(languageManager))
            .bind(ConfigurationManager.class, () -> this.configurationManager)
            .bind(LanguageManager.class, () -> languageManager)
            .bind(PluginConfiguration.class, () -> config)
            .bind(LocationsConfiguration.class, () -> locations)
            .bind(TeleportManager.class, () -> this.teleportManager)
            .bind(EternalCore.class, () -> this)
            .bind(UserManager.class, () -> this.userManager)
            .bind(ScoreboardManager.class, () -> this.scoreboardManager)
            .bind(AudiencesService.class, () -> this.audiencesService)
            .bind(MiniMessage.class, () -> this.miniMessage)
            .bind(ChatManager.class, () -> this.chatManager)
            .bind(Scheduler.class, () -> this.scheduler)

            .placeholders(commands.commandsSection.commands.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v::getValue)))
            .message(ValidationInfo.NO_PERMISSION, new PermissionMessage(userProvider, languageManager))

            .command(
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
                SpawnCommand.class,
                PingCommand.class,
                OnlineCommand.class,
                ListCommand.class,
                TposCommand.class,
                NameCommand.class
            )
            .register();

        /** Listeners */

        // Register events
        PandaStream.of(
            new PlayerChatListener(this.chatManager, audiencesService, this.configurationManager, server),
            new PlayerJoinListener(this.configurationManager, audiencesService, server),
            new PlayerQuitListener(this.configurationManager, server),
            new CreateUserListener(this.userManager),
            new ScoreboardListener(config, this.scoreboardManager),
            new PlayerCommandPreprocessListener(this.audiencesService, this.configurationManager, server),
            new SignChangeListener(miniMessage),
            new PlayerDeathListener(this.configurationManager),
            new TeleportListeners(this.audiencesService, this.teleportManager)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        /** Tasks */

        TeleportTask task = new TeleportTask(this.audiencesService, this.teleportManager, server);
        this.scheduler.runTaskTimer(task, 10, 10);

        // bStats metrics
        // Metrics metrics = new Metrics(this, 13964);
        // metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Successfully loaded EternalCore in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatformManager().unregisterCommands();

        PluginConfiguration config = this.configurationManager.getPluginConfiguration();

        this.configurationManager.render(config);
    }

    private void softwareCheck() {
        try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            this.isPaper = true;
        } catch (ClassNotFoundException classNotFoundException) {
            this.getLogger().warning("Your server running on unsupported software, use paper minecraft software and other paper 1.17x forks");
            this.getLogger().warning("Download paper from https://papermc.io/downloads");
            this.getLogger().warning("WARRING: Supported minecraft version is 1.17-1.18x");
        }

        if (this.isPaper) {
            this.getLogger().info("Your server running on supported software, congratulations!");
            this.getLogger().info("Server version: " + this.getServer().getVersion());
        }

        switch (VERSION) {
            case "v1_8_R1", "v1_8_R2", "v1_8_R3", "v1_9_R1", "v1_9_R2", "v1_10_R1", "v1_11_R1", "v1_12_R1", "v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1", "v1_16_R1" -> this.getLogger().info("EternalCore no longer supports your version, be aware that there may be bugs!");
        }
    }

}
