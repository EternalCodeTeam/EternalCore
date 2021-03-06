package com.eternalcode.core;

import com.eternalcode.core.afk.AfkCommand;
import com.eternalcode.core.afk.AfkController;
import com.eternalcode.core.afk.AfkMessagesController;
import com.eternalcode.core.afk.AfkService;
import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.feature.ignore.IgnoreCommand;
import com.eternalcode.core.chat.feature.ignore.IgnoreRepository;
import com.eternalcode.core.chat.feature.ignore.UnIgnoreCommand;
import com.eternalcode.core.chat.feature.privatechat.PrivateChatPresenter;
import com.eternalcode.core.chat.feature.privatechat.PrivateChatService;
import com.eternalcode.core.chat.adventure.AdventureNotificationAnnouncer;
import com.eternalcode.core.chat.placeholder.PlaceholderRegistry;
import com.eternalcode.core.command.argument.LocationArgument;
import com.eternalcode.core.command.argument.UserArgument;
import com.eternalcode.core.command.argument.WorldArgument;
import com.eternalcode.core.chat.feature.privatechat.PrivateChatReplyCommand;
import com.eternalcode.core.chat.feature.privatechat.PrivateChatSocialSpyCommand;
import com.eternalcode.core.command.handler.InvalidUsage;
import com.eternalcode.core.command.implementation.GameModeCommand;
import com.eternalcode.core.command.implementation.item.ItemFlagCommand;
import com.eternalcode.core.command.implementation.item.ItemLoreCommand;
import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.database.NoneRepository;
import com.eternalcode.core.database.wrapper.IgnoreRepositoryOrmLite;
import com.eternalcode.core.home.HomeRepository;
import com.eternalcode.core.publish.LocalPublisher;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.teleport.TeleportDeathController;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.command.TeleportBackCommand;
import com.eternalcode.core.teleport.command.TeleportHereCommand;
import com.eternalcode.core.warp.WarpCommand;
import com.eternalcode.core.command.implementation.time.DayCommand;
import com.eternalcode.core.command.implementation.time.NightCommand;
import com.eternalcode.core.command.implementation.time.TimeCommand;
import com.eternalcode.core.command.implementation.weather.RainCommand;
import com.eternalcode.core.command.implementation.weather.SunCommand;
import com.eternalcode.core.command.implementation.weather.ThunderCommand;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.HomeRepositoryOrmLite;
import com.eternalcode.core.home.Home;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.home.command.ArgHome;
import com.eternalcode.core.home.command.DelHomeCommand;
import com.eternalcode.core.home.command.HomeArgument;
import com.eternalcode.core.home.command.HomeCommand;
import com.eternalcode.core.home.command.SetHomeCommand;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.util.legacy.LegacyColorProcessor;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import com.eternalcode.core.command.argument.AmountArgument;
import com.eternalcode.core.command.argument.EnchantmentArgument;
import com.eternalcode.core.command.argument.GameModeArgument;
import com.eternalcode.core.command.argument.MaterialArgument;
import com.eternalcode.core.command.argument.NoticeTypeArgument;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.command.argument.PlayerNameArg;
import com.eternalcode.core.command.argument.RequesterArgument;
import com.eternalcode.core.command.argument.WarpArgument;
import com.eternalcode.core.command.contextual.ViewerContextual;
import com.eternalcode.core.command.contextual.PlayerContextual;
import com.eternalcode.core.command.contextual.UserContextual;
import com.eternalcode.core.command.handler.PermissionMessage;
import com.eternalcode.core.chat.feature.adminchat.AdminChatCommand;
import com.eternalcode.core.command.implementation.AlertCommand;
import com.eternalcode.core.command.implementation.inventory.AnvilCommand;
import com.eternalcode.core.command.implementation.inventory.CartographyTableCommand;
import com.eternalcode.core.chat.ChatManagerCommand;
import com.eternalcode.core.command.implementation.inventory.InventoryClearCommand;
import com.eternalcode.core.command.implementation.inventory.DisposalCommand;
import com.eternalcode.core.command.implementation.EnchantCommand;
import com.eternalcode.core.command.implementation.inventory.EnderchestCommand;
import com.eternalcode.core.command.implementation.FeedCommand;
import com.eternalcode.core.command.implementation.FlyCommand;
import com.eternalcode.core.command.implementation.GiveCommand;
import com.eternalcode.core.command.implementation.GodCommand;
import com.eternalcode.core.command.implementation.inventory.GrindstoneCommand;
import com.eternalcode.core.command.implementation.HatCommand;
import com.eternalcode.core.command.implementation.HealCommand;
import com.eternalcode.core.chat.feature.reportchat.HelpOpCommand;
import com.eternalcode.core.command.implementation.inventory.InventoryOpenCommand;
import com.eternalcode.core.command.implementation.KillCommand;
import com.eternalcode.core.language.LanguageCommand;
import com.eternalcode.core.command.implementation.info.OnlinePlayersListCommand;
import com.eternalcode.core.chat.feature.privatechat.PrivateChatCommand;
import com.eternalcode.core.command.implementation.item.ItemNameCommand;
import com.eternalcode.core.command.implementation.info.OnlinePlayerCountCommand;
import com.eternalcode.core.command.implementation.info.PingCommand;
import com.eternalcode.core.command.implementation.RepairCommand;
import com.eternalcode.core.command.implementation.spawn.SetSpawnCommand;
import com.eternalcode.core.command.implementation.SkullCommand;
import com.eternalcode.core.command.implementation.spawn.SpawnCommand;
import com.eternalcode.core.command.implementation.SpeedCommand;
import com.eternalcode.core.command.implementation.inventory.StonecutterCommand;
import com.eternalcode.core.teleport.command.TeleportCommand;
import com.eternalcode.core.teleport.request.TpaAcceptCommand;
import com.eternalcode.core.teleport.request.TpaCommand;
import com.eternalcode.core.teleport.request.TpaDenyCommand;
import com.eternalcode.core.teleport.command.TeleportToPositionCommand;
import com.eternalcode.core.command.implementation.info.WhoIsCommand;
import com.eternalcode.core.command.implementation.inventory.WorkbenchCommand;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.language.LanguageInventory;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.listener.player.PlayerChatListener;
import com.eternalcode.core.listener.player.PlayerCommandPreprocessListener;
import com.eternalcode.core.listener.player.PlayerDeathListener;
import com.eternalcode.core.listener.player.PlayerJoinListener;
import com.eternalcode.core.listener.player.PlayerQuitListener;
import com.eternalcode.core.listener.sign.SignChangeListener;
import com.eternalcode.core.user.PrepareUserController;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.request.TeleportRequestService;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.warp.Warp;
import com.eternalcode.core.warp.WarpConfigRepository;
import com.eternalcode.core.warp.WarpManager;
import com.eternalcode.core.warp.WarpRepository;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.liteskull.LiteSkullFactory;
import dev.rollczi.liteskull.api.SkullAPI;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class EternalCore extends JavaPlugin {

    private static EternalCore instance;

    /**
     * Scheduler & Publisher
     **/
    private Scheduler scheduler;
    private Publisher publisher;

    /**
     * Configuration & Database
     **/
    private ConfigurationManager configurationManager;
    private DatabaseManager databaseManager;

    /**
     * Services & Managers
     **/
    private UserManager userManager;
    private PlaceholderRegistry placeholderRegistry;

    private TeleportService teleportService;
    private TeleportTaskService teleportTaskService;

    private WarpManager warpManager;
    private HomeManager homeManager;
    private AfkService afkService;
    private TeleportRequestService teleportRequestService;

    private LanguageManager languageManager;
    private ChatManager chatManager;
    private PrivateChatService privateChatService;

    /**
     * Adventure
     **/
    private BukkitAudiences audiencesProvider;
    private MiniMessage miniMessage;

    /**
     * Viewer & Notice
     **/
    private BukkitViewerProvider viewerProvider;
    private BukkitUserProvider userProvider;

    private NotificationAnnouncer notificationAnnouncer;
    private NoticeService noticeService;

    /**
     * FrameWorks & Libs
     **/
    private LanguageInventory languageInventory;
    private LiteCommands<CommandSender> liteCommands;
    private SkullAPI skullAPI;

    public static EternalCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        instance = this;

        this.softwareCheck();

        this.scheduler = new BukkitSchedulerImpl(this);
        this.publisher = new LocalPublisher();

        /* Configuration */

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadAndRenderConfigs();

        PluginConfiguration config = configurationManager.getPluginConfiguration();
        LocationsConfiguration locations = configurationManager.getLocationsConfiguration();
        LanguageConfiguration languageConfig = configurationManager.getLanguageConfiguration();
        PlaceholdersConfiguration placeholdersConfig = configurationManager.getPlaceholdersConfiguration();

        /* Database */

        WarpRepository warpRepository = new WarpConfigRepository(this.configurationManager, locations);
        HomeRepository homeRepository;
        IgnoreRepository ignoreRepository;

        try {
            this.databaseManager = new DatabaseManager(config, this.getLogger(), this.getDataFolder());
            this.databaseManager.connect();

            homeRepository = HomeRepositoryOrmLite.create(this.databaseManager, this.scheduler);
            ignoreRepository = IgnoreRepositoryOrmLite.create(this.databaseManager, this.scheduler);

        }
        catch (Exception exception) {
            exception.printStackTrace();
            this.getLogger().severe("Can not connect to database! Some functions may not work!");

            NoneRepository noneRepository = new NoneRepository();

            homeRepository = noneRepository;
            ignoreRepository = noneRepository;
        }

        /* Services & Managers  */

        this.userManager = new UserManager();
        this.placeholderRegistry = new PlaceholderRegistry();
        this.placeholderRegistry.stack(text -> {
            for (Map.Entry<String, String> entry : placeholdersConfig.placeholders.entrySet()) {
                text = text.replace(entry.getKey(), entry.getValue());
            }

            return text;
        });

        this.teleportService = new TeleportService();
        this.teleportTaskService = new TeleportTaskService();

        this.afkService = new AfkService(config.afk, this.publisher);
        this.warpManager = WarpManager.create(warpRepository);
        this.homeManager = HomeManager.create(homeRepository);
        this.teleportRequestService = new TeleportRequestService(config.otherSettings);

        this.languageManager = LanguageManager.create(this.configurationManager, this.getDataFolder());
        this.chatManager = new ChatManager(config.chat);

        /* Adventure */

        this.audiencesProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

        /* Audiences System */

        this.viewerProvider = new BukkitViewerProvider(this.userManager, server);
        this.userProvider = new BukkitUserProvider(this.userManager); // TODO: Czasowe rozwiazanie, do poprawy (do usuniecia)

        this.notificationAnnouncer = new AdventureNotificationAnnouncer(this.audiencesProvider, this.miniMessage);
        this.noticeService = new NoticeService(this.languageManager, this.viewerProvider, this.notificationAnnouncer, placeholderRegistry);
        this.privateChatService = new PrivateChatService(this.noticeService, ignoreRepository, this.publisher, this.userManager);

        /* FrameWorks & Libs */
        this.languageInventory = new LanguageInventory(languageConfig.languageSelector, this.noticeService, this.userManager, this.miniMessage);

        this.skullAPI = LiteSkullFactory.builder()
            .bukkitScheduler(this)
            .build();

        this.liteCommands = LiteBukkitFactory.builder(server, "EternalCore")
            // Arguments (include optional)
            .argument(String.class, "player",   new PlayerNameArg(server))
            .argument(Integer.class,                new AmountArgument(this.languageManager, config, viewerProvider))
            .argument(Material.class,               new MaterialArgument(this.userProvider, this.languageManager))
            .argument(GameMode.class,               new GameModeArgument(this.userProvider, this.languageManager))
            .argument(NoticeType.class,             new NoticeTypeArgument(this.userProvider, this.languageManager))
            .argument(Warp.class,                   new WarpArgument(this.warpManager, this.languageManager, this.userProvider))
            .argument(Enchantment.class,            new EnchantmentArgument(this.viewerProvider, this.languageManager))
            .argument(World.class,                  new WorldArgument(server))
            .argument(User.class,                   new UserArgument(this.viewerProvider, this.languageManager, server, this.userManager))
            .argument(Player.class,                 new PlayerArgument(this.viewerProvider, this.languageManager, server))
            .argument(Player.class, "request",  new RequesterArgument(this.teleportRequestService, this.languageManager, this.userProvider, server))

            // multilevel Arguments (include optional)
            .argumentMultilevel(Location.class,               new LocationArgument())

            // Native Argument (no optional)
            .argument(ArgHome.class, Home.class,                new HomeArgument(homeManager, userProvider, languageManager))
            .argument(Arg.class, Player.class, "or_sender", new PlayerArgOrSender(this.languageManager, this.viewerProvider, server))

            // Dynamic binds
            .contextualBind(Player.class,            new PlayerContextual(this.languageManager))
            .contextualBind(Viewer.class,            new ViewerContextual(this.viewerProvider))
            .contextualBind(User.class,              new UserContextual(this.languageManager, this.userManager))

            // Static binds
            .typeBind(EternalCore.class,            () -> this)
            .typeBind(ConfigurationManager.class,   () -> this.configurationManager)
            .typeBind(LanguageInventory.class,      () -> this.languageInventory)
            .typeBind(LanguageManager.class,        () -> this.languageManager)
            .typeBind(TeleportTaskService.class,    () -> this.teleportTaskService)
            .typeBind(UserManager.class,            () -> this.userManager)
            .typeBind(TeleportRequestService.class, () -> this.teleportRequestService)
            .typeBind(NoticeService.class,          () -> this.noticeService)
            .typeBind(MiniMessage.class,            () -> this.miniMessage)
            .typeBind(ChatManager.class,            () -> this.chatManager)
            .typeBind(PrivateChatService.class,     () -> this.privateChatService)
            .typeBind(Scheduler.class,              () -> this.scheduler)
            .typeBind(WarpManager.class,            () -> this.warpManager)
            .typeBind(HomeManager.class,            () -> this.homeManager)
            .typeBind(AfkService.class,             () -> this.afkService)
            .typeBind(SkullAPI.class,               () -> this.skullAPI)

            .typeBind(PluginConfiguration.class,    () -> config)
            .typeBind(LocationsConfiguration.class, () -> locations)
            .typeBind(PluginConfiguration.OtherSettings.class, () -> config.otherSettings)

            .invalidUsageHandler(new InvalidUsage(this.viewerProvider, this.noticeService))
            .permissionHandler(new PermissionMessage(this.userProvider, this.audiencesProvider, this.languageManager, this.miniMessage))

            .commandInstance(
                new IgnoreCommand(ignoreRepository, noticeService),
                new UnIgnoreCommand(ignoreRepository, noticeService)
            )
            .command(
                EternalCoreCommand.class,

                // Home Commands
                HomeCommand.class,
                SetHomeCommand.class,
                DelHomeCommand.class,

                // Item Commands
                ItemNameCommand.class,
                ItemLoreCommand.class,
                ItemFlagCommand.class,

                // Weather Commands
                SunCommand.class,
                ThunderCommand.class,
                RainCommand.class,

                // Time Commands
                TimeCommand.class,
                DayCommand.class,
                NightCommand.class,

                // Teleport Commands
                TeleportCommand.class,
                TeleportToPositionCommand.class,
                TeleportHereCommand.class,
                TeleportBackCommand.class,

                // Tpa Commands
                TpaCommand.class,
                TpaDenyCommand.class,
                TpaAcceptCommand.class,

                // Spawn & Warp Command
                SetSpawnCommand.class,
                SpawnCommand.class,
                WarpCommand.class,

                // Inventory Commands
                EnderchestCommand.class,
                WorkbenchCommand.class,
                AnvilCommand.class,
                CartographyTableCommand.class,
                GrindstoneCommand.class,
                StonecutterCommand.class,
                DisposalCommand.class,

                // Private Chat Commands
                PrivateChatCommand.class,
                PrivateChatReplyCommand.class,
                PrivateChatSocialSpyCommand.class,

                // Admin Chat Commands
                AdminChatCommand.class,
                HelpOpCommand.class,
                AlertCommand.class,
                ChatManagerCommand.class,

                // Moderation Commands
                FlyCommand.class,
                GodCommand.class,
                GameModeCommand.class,
                SpeedCommand.class,
                GiveCommand.class,
                EnchantCommand.class,
                RepairCommand.class,
                HealCommand.class,
                FeedCommand.class,
                KillCommand.class,
                InventoryClearCommand.class,
                InventoryOpenCommand.class,

                // Info Commands
                OnlinePlayerCountCommand.class,
                OnlinePlayersListCommand.class,
                WhoIsCommand.class,
                PingCommand.class,

                // Misc Commands
                HatCommand.class,
                AfkCommand.class,
                SkullCommand.class,
                LanguageCommand.class
            )

            .register();

        /* Listeners */

        Stream.of(
            new TeleportDeathController(this.teleportService),
            new PlayerChatListener(this.chatManager, noticeService, this.configurationManager, server),
            new PlayerJoinListener(this.configurationManager, this.noticeService, server),
            new PlayerQuitListener(this.configurationManager, this.noticeService, server),
            new PrepareUserController(this.userManager, server),
            new PlayerCommandPreprocessListener(this.noticeService, this.configurationManager, server),
            new SignChangeListener(this.miniMessage),
            new PlayerDeathListener(this.noticeService, this.configurationManager),
            new TeleportListeners(this.noticeService, this.teleportTaskService),
            new AfkController(this.afkService)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        /* Subscribers */

        this.publisher.subscribe(new AfkMessagesController(this.noticeService, this.userManager));
        this.publisher.subscribe(new PrivateChatPresenter(this.noticeService));

        /* Tasks */

        TeleportTask task = new TeleportTask(this.noticeService, this.teleportTaskService, teleportService, server);
        this.scheduler.timerSync(task, Duration.ofMillis(200), Duration.ofMillis(200));

        // bStats metrics
        Metrics metrics = new Metrics(this, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Successfully loaded EternalCore in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatform().unregisterAll();
        this.databaseManager.close();
    }

    private void softwareCheck() {
        Logger logger = this.getLogger();
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isSpigot()) {
            logger.warning("Your server running on unsupported software, use spigot/paper minecraft software and other spigot/paper 1.19x forks");
            logger.warning("We recommend using paper, download paper from https://papermc.io/downloads");
            logger.warning("WARRING: Supported minecraft version is 1.17-1.19x");
            return;
        }

        if (!environment.isVersion(17)) {
            logger.warning("EternalCore no longer supports your version, be aware that there may be bugs!");
            return;
        }

        logger.info("Your server running on supported software, congratulations!");
        logger.info("Server version: " + this.getServer().getVersion());
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public AfkService getAfkService() {
        return afkService;
    }

    public TeleportService getTeleportService() {
        return teleportService;
    }

    public TeleportTaskService getTeleportTaskService() {
        return teleportTaskService;
    }

    public TeleportRequestService getTeleportRequestService() {
        return teleportRequestService;
    }

    public BukkitAudiences getAudiencesProvider() {
        return audiencesProvider;
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public BukkitViewerProvider getViewerProvider() {
        return viewerProvider;
    }

    public BukkitUserProvider getUserProvider() {
        return userProvider;
    }

    public NotificationAnnouncer getNotificationAnnouncer() {
        return notificationAnnouncer;
    }

    public NoticeService getNoticeService() {
        return noticeService;
    }

    public PrivateChatService getPrivateChatService() {
        return privateChatService;
    }

    public LanguageInventory getLanguageInventory() {
        return languageInventory;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return liteCommands;
    }

}
