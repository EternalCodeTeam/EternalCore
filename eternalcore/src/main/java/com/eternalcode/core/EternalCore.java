package com.eternalcode.core;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.adventure.AdventureNotificationAnnouncer;
import com.eternalcode.core.chat.adventure.BukkitAudienceProvider;
import com.eternalcode.core.chat.legacy.LegacyColorProcessor;
import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.AudienceProvider;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import com.eternalcode.core.command.argument.AmountArgument;
import com.eternalcode.core.command.argument.EnchantmentArgument;
import com.eternalcode.core.command.argument.GameModeArgument;
import com.eternalcode.core.command.argument.MaterialArgument;
import com.eternalcode.core.command.argument.NoticeTypeArgument;
import com.eternalcode.core.command.argument.PlayerArg;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import com.eternalcode.core.command.argument.PlayerNameArg;
import com.eternalcode.core.command.argument.WarpArgument;
import com.eternalcode.core.command.bind.AudienceBind;
import com.eternalcode.core.command.bind.PlayerBind;
import com.eternalcode.core.command.bind.UserBind;
import com.eternalcode.core.command.implementations.AdminChatCommand;
import com.eternalcode.core.command.implementations.AlertCommand;
import com.eternalcode.core.command.implementations.AnvilCommand;
import com.eternalcode.core.command.implementations.CartographyTableCommand;
import com.eternalcode.core.command.implementations.ChatCommand;
import com.eternalcode.core.command.implementations.ClearCommand;
import com.eternalcode.core.command.implementations.DisposalCommand;
import com.eternalcode.core.command.implementations.EnchantCommand;
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
import com.eternalcode.core.command.implementations.LanguageCommand;
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
import com.eternalcode.core.command.platform.EternalCommandsFactory;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.CommandsConfiguration;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.configuration.lang.ENMessagesConfiguration;
import com.eternalcode.core.configuration.lang.PLMessagesConfiguration;
import com.eternalcode.core.database.CacheWarpRepository;
import com.eternalcode.core.database.Database;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.language.LanguageInventory;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.listener.player.PlayerChatListener;
import com.eternalcode.core.listener.player.PlayerCommandPreprocessListener;
import com.eternalcode.core.listener.player.PlayerDeathListener;
import com.eternalcode.core.listener.player.PlayerJoinListener;
import com.eternalcode.core.listener.player.PlayerQuitListener;
import com.eternalcode.core.listener.scoreboard.ScoreboardListener;
import com.eternalcode.core.listener.sign.SignChangeListener;
import com.eternalcode.core.listener.user.PrepareUserController;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpManager;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.litecommands.valid.messages.UseSchemeFormatting;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EternalCore extends JavaPlugin {

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    /**
     * Services & Managers
     **/
    private static EternalCore instance;
    private Scheduler scheduler;
    private UserManager userManager;
    private HomeManager homeManager;
    private TeleportManager teleportManager;
    private WarpManager warpManager;
    private BukkitUserProvider userProvider;
    private LanguageInventory languageInventory;

    /**
     * Configuration, Language & Chat
     **/
    private ConfigurationManager configurationManager;
    private LanguageManager languageManager;
    private ChatManager chatManager;

    /**
     * Adventure
     **/
    private BukkitAudiences adventureAudiences;
    private MiniMessage miniMessage;

    /**
     * Audiences System
     **/
    private AudienceProvider audienceProvider;
    private NotificationAnnouncer notificationAnnouncer;
    private NoticeService noticeService;

    /**
     * FrameWorks & Libs
     **/
    private Database database;
    private ScoreboardManager scoreboardManager;
    private LiteCommands liteCommands;
    private boolean isSpigot = false;

    public static EternalCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        instance = this;

        this.softwareCheck();

        /* Services */

        this.scheduler = new BukkitSchedulerImpl(this);
        this.userManager = new UserManager();
        this.homeManager = new HomeManager();
        this.teleportManager = new TeleportManager();
        this.warpManager = WarpManager.create(new CacheWarpRepository());
        this.userProvider = new BukkitUserProvider(userManager); // TODO: Czasowe rozwiazanie, do poprawy (do usuniecia)

        /* Configuration */

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.database = new Database(configurationManager, this.getLogger());
        this.configurationManager.loadAndRenderConfigs();

        PluginConfiguration config = configurationManager.getPluginConfiguration();
        LocationsConfiguration locations = configurationManager.getLocationsConfiguration();
        CommandsConfiguration commands = configurationManager.getCommandsConfiguration();
        LanguageConfiguration languageConfig = configurationManager.getInventoryConfiguration();

        /* Language & Chat */

        File langFolder = new File(this.getDataFolder(), "lang");
        Map<Language, Messages> defaultImplementations = new ImmutableMap.Builder<Language, Messages>()
            .put(Language.EN, new ENMessagesConfiguration(langFolder, "en_messages.yml"))
            .put(Language.PL, new PLMessagesConfiguration(langFolder, "pl_messages.yml"))
            .build();

        List<Messages> messages = PandaStream.of(languageConfig.languages)
            .map(lang -> defaultImplementations.getOrDefault(lang, new ENMessagesConfiguration(langFolder, lang.getLang() + "_messages.yml")))
            .toList();

        Messages defaultMessages = PandaStream.of(messages)
            .find(m -> m.getLanguage().equals(languageConfig.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        this.languageManager = new LanguageManager(defaultMessages);

        for (Messages message : messages) {
            this.configurationManager.loadAndRender(message);
            this.languageManager.loadLanguage(message.getLanguage(), message);
        }

        this.chatManager = new ChatManager(config.chat);

        /* Adventure */

        this.adventureAudiences = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

        /* Audiences System */

        this.audienceProvider = new BukkitAudienceProvider(this.userManager, server, this.adventureAudiences);
        this.notificationAnnouncer = new AdventureNotificationAnnouncer(this.adventureAudiences, this.miniMessage);
        this.noticeService = new NoticeService(this.languageManager, this.audienceProvider, this.notificationAnnouncer);

        /* FrameWorks & Libs */
        this.scoreboardManager = new ScoreboardManager(this, this.configurationManager, this.miniMessage);
        this.scoreboardManager.updateTask();

        this.languageInventory = new LanguageInventory(languageConfig.languageSelector, this.noticeService, this.userManager, this.miniMessage);

        this.liteCommands = EternalCommandsFactory.builder(server, "EternalCore", this.audienceProvider, this.notificationAnnouncer)

            // arguments
            .argument(String.class, new PlayerNameArg(server))
            .argument(Integer.class, new AmountArgument(this.languageManager, this.configurationManager, this.userProvider))
            .argument(Player.class, new PlayerArg(this.userProvider, this.languageManager, server))
            .argument(Player.class, new PlayerArgOrSender(this.languageManager, this.userProvider, server))
            .argument(Material.class, new MaterialArgument(this.userProvider, this.languageManager))
            .argument(GameMode.class, new GameModeArgument(this.userProvider, this.languageManager))
            .argument(NoticeType.class, new NoticeTypeArgument(this.userProvider, this.languageManager))
            .argument(Warp.class, new WarpArgument(this.warpManager, this.languageManager, this.userProvider))
            .argument(Enchantment.class, new EnchantmentArgument(this.userProvider, this.languageManager))

            // Optional arguments
            .argument(Option.class, new PlayerArg(this.userProvider, this.languageManager, server).toOptionHandler())
            .argument(Option.class, new PlayerArgOrSender(this.languageManager, this.userProvider, server).toOptionHandler())

            // Dynamic binds
            .parameterBind(Player.class, new PlayerBind(this.languageManager))
            .parameterBind(Audience.class, new AudienceBind(this.userManager))
            .parameterBind(User.class, new UserBind(this.languageManager, this.userManager))

            // Static binds
            .typeBind(EternalCore.class, () -> this)
            .typeBind(ConfigurationManager.class, () -> this.configurationManager)
            .typeBind(LanguageInventory.class, () -> this.languageInventory)
            .typeBind(LanguageManager.class, () -> this.languageManager)
            .typeBind(PluginConfiguration.class, () -> config)
            .typeBind(LocationsConfiguration.class, () -> locations)
            .typeBind(TeleportManager.class, () -> this.teleportManager)
            .typeBind(UserManager.class, () -> this.userManager)
            .typeBind(ScoreboardManager.class, () -> this.scoreboardManager)
            .typeBind(NoticeService.class, () -> this.noticeService)
            .typeBind(MiniMessage.class, () -> this.miniMessage)
            .typeBind(ChatManager.class, () -> this.chatManager)
            .typeBind(Scheduler.class, () -> this.scheduler)

            .placeholders(commands.commandsSection.commands.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v::getValue)))
            .message(ValidationInfo.NO_PERMISSION, new PermissionMessage(this.userProvider, this.languageManager))
            .formattingUseScheme(UseSchemeFormatting.NORMAL)

            .command(
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
                NameCommand.class,
                EnchantCommand.class,
                TeleportCommand.class,
                LanguageCommand.class
            )
            .register();

        /* Listeners */

        PandaStream.of(
            new PlayerChatListener(this.chatManager, noticeService, this.configurationManager, server),
            new PlayerJoinListener(this.configurationManager, this.noticeService, server),
            new PlayerQuitListener(this.configurationManager, this.noticeService, server),
            new PrepareUserController(this.userManager, server),
            new ScoreboardListener(config, this.scoreboardManager),
            new PlayerCommandPreprocessListener(this.noticeService, this.configurationManager, server),
            new SignChangeListener(this.miniMessage),
            new PlayerDeathListener(this.noticeService, this.configurationManager),
            new TeleportListeners(this.noticeService, this.teleportManager)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        /* Tasks */

        TeleportTask task = new TeleportTask(this.noticeService, this.teleportManager, server);
        this.scheduler.runTaskTimer(task, 10, 10);

        this.database.connect();

        // bStats metrics
        Metrics metrics = new Metrics(this, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Successfully loaded EternalCore in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatformManager().unregisterCommands();
        this.database.disconnect();
    }

    private void softwareCheck() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
            this.isSpigot = true;
        } catch (ClassNotFoundException exception) {
            this.getLogger().warning("Your server running on unsupported software, use spigot/paper minecraft software and other spigot/paper 1.17x forks");
            this.getLogger().warning("We recommend using paper, download paper from https://papermc.io/downloads");
            this.getLogger().warning("WARRING: Supported minecraft version is 1.17-1.18x");
        }

        if (this.isSpigot) {
            this.getLogger().info("Your server running on supported software, congratulations!");
            this.getLogger().info("Server version: " + this.getServer().getVersion());
        }

        switch (VERSION) {
            case "v1_17_R1", "v1_18_R1", "v1_18_R2", "v1_19_R1": return;
            default: this.getLogger().warning("EternalCore no longer supports your version, be aware that there may be bugs!");
        }
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public TeleportManager getTeleportManager() {
        return this.teleportManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public BukkitUserProvider getUserProvider() {
        return this.userProvider;
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public ChatManager getChatManager() {
        return this.chatManager;
    }

    public BukkitAudiences getAdventureAudiences() {
        return this.adventureAudiences;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public AudienceProvider getAudienceProvider() {
        return this.audienceProvider;
    }

    public NotificationAnnouncer getNotificationAnnouncer() {
        return this.notificationAnnouncer;
    }

    public NoticeService getNoticeService() {
        return this.noticeService;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public WarpManager getWarpManager() {
        return this.warpManager;
    }

    public LiteCommands getLiteCommands() {
        return this.liteCommands;
    }
}
