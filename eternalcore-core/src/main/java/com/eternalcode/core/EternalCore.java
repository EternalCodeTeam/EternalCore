package com.eternalcode.core;

import com.eternalcode.core.bridge.BridgeManager;
import com.eternalcode.core.command.argument.DurationArgument;
import com.eternalcode.core.command.argument.EnchantmentArgument;
import com.eternalcode.core.command.argument.GameModeArgument;
import com.eternalcode.core.command.argument.LocationArgument;
import com.eternalcode.core.command.argument.NoticeTypeArgument;
import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.command.argument.RequesterArgument;
import com.eternalcode.core.command.argument.SpeedArgument;
import com.eternalcode.core.command.argument.StringNicknameArgument;
import com.eternalcode.core.command.argument.UserArgument;
import com.eternalcode.core.command.argument.WarpArgument;
import com.eternalcode.core.command.argument.WorldArgument;
import com.eternalcode.core.command.argument.MobEntityArgument;
import com.eternalcode.core.command.argument.home.ArgHome;
import com.eternalcode.core.command.argument.home.HomeArgument;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.command.configurator.CommandConfigurator;
import com.eternalcode.core.command.contextual.PlayerContextual;
import com.eternalcode.core.command.contextual.UserContextual;
import com.eternalcode.core.command.contextual.ViewerContextual;
import com.eternalcode.core.command.handler.InvalidUsage;
import com.eternalcode.core.command.handler.NotificationHandler;
import com.eternalcode.core.command.handler.PermissionMessage;
import com.eternalcode.core.configuration.ConfigurationBackupService;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.NoneRepository;
import com.eternalcode.core.database.wrapper.HomeRepositoryOrmLite;
import com.eternalcode.core.database.wrapper.IgnoreRepositoryOrmLite;
import com.eternalcode.core.feature.adminchat.AdminChatCommand;
import com.eternalcode.core.feature.afk.AfkCommand;
import com.eternalcode.core.feature.afk.AfkController;
import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.afk.AfkTask;
import com.eternalcode.core.feature.chat.ChatManager;
import com.eternalcode.core.feature.chat.ChatManagerCommand;
import com.eternalcode.core.feature.chat.ChatManagerController;
import com.eternalcode.core.feature.essentials.AlertCommand;
import com.eternalcode.core.feature.essentials.FeedCommand;
import com.eternalcode.core.feature.essentials.FlyCommand;
import com.eternalcode.core.feature.essentials.GodCommand;
import com.eternalcode.core.feature.essentials.HealCommand;
import com.eternalcode.core.feature.essentials.KillCommand;
import com.eternalcode.core.feature.essentials.mob.ButcherCommand;
import com.eternalcode.core.feature.essentials.SpeedCommand;
import com.eternalcode.core.feature.essentials.container.AnvilCommand;
import com.eternalcode.core.feature.essentials.container.CartographyTableCommand;
import com.eternalcode.core.feature.essentials.container.DisposalCommand;
import com.eternalcode.core.feature.essentials.container.EnderchestCommand;
import com.eternalcode.core.feature.essentials.container.GrindstoneCommand;
import com.eternalcode.core.feature.essentials.container.InventoryClearCommand;
import com.eternalcode.core.feature.essentials.container.InventoryOpenCommand;
import com.eternalcode.core.feature.essentials.container.StonecutterCommand;
import com.eternalcode.core.feature.essentials.container.WorkbenchCommand;
import com.eternalcode.core.feature.essentials.gamemode.GameModeCommand;
import com.eternalcode.core.feature.essentials.item.EnchantCommand;
import com.eternalcode.core.feature.essentials.item.GiveCommand;
import com.eternalcode.core.feature.essentials.item.HatCommand;
import com.eternalcode.core.feature.essentials.item.ItemFlagCommand;
import com.eternalcode.core.feature.essentials.item.ItemLoreCommand;
import com.eternalcode.core.feature.essentials.item.ItemNameCommand;
import com.eternalcode.core.feature.essentials.item.RepairCommand;
import com.eternalcode.core.feature.essentials.item.SkullCommand;
import com.eternalcode.core.feature.essentials.mob.MobEntity;
import com.eternalcode.core.feature.essentials.playerinfo.OnlinePlayerCountCommand;
import com.eternalcode.core.feature.essentials.playerinfo.OnlinePlayersListCommand;
import com.eternalcode.core.feature.essentials.playerinfo.PingCommand;
import com.eternalcode.core.feature.essentials.playerinfo.WhoIsCommand;
import com.eternalcode.core.feature.essentials.time.DayCommand;
import com.eternalcode.core.feature.essentials.time.NightCommand;
import com.eternalcode.core.feature.essentials.time.TimeCommand;
import com.eternalcode.core.feature.essentials.weather.RainCommand;
import com.eternalcode.core.feature.essentials.weather.SunCommand;
import com.eternalcode.core.feature.essentials.weather.ThunderCommand;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.feature.home.HomeRepository;
import com.eternalcode.core.feature.home.command.DelHomeCommand;
import com.eternalcode.core.feature.home.command.HomeCommand;
import com.eternalcode.core.feature.home.command.SetHomeCommand;
import com.eternalcode.core.feature.ignore.IgnoreCommand;
import com.eternalcode.core.feature.ignore.IgnoreRepository;
import com.eternalcode.core.feature.ignore.UnIgnoreCommand;
import com.eternalcode.core.feature.privatechat.PrivateChatCommands;
import com.eternalcode.core.feature.privatechat.PrivateChatService;
import com.eternalcode.core.feature.reportchat.HelpOpCommand;
import com.eternalcode.core.feature.spawn.SetSpawnCommand;
import com.eternalcode.core.feature.spawn.SpawnCommand;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpCommand;
import com.eternalcode.core.feature.warp.WarpConfigRepository;
import com.eternalcode.core.feature.warp.WarpInventory;
import com.eternalcode.core.feature.warp.WarpManager;
import com.eternalcode.core.feature.warp.WarpRepository;
import com.eternalcode.core.language.LanguageCommand;
import com.eternalcode.core.language.LanguageInventory;
import com.eternalcode.core.language.config.LanguageConfiguration;
import com.eternalcode.core.listener.player.PlayerChatSoundListener;
import com.eternalcode.core.listener.player.PlayerCommandPreprocessListener;
import com.eternalcode.core.listener.player.PlayerDeathListener;
import com.eternalcode.core.listener.player.PlayerJoinListener;
import com.eternalcode.core.listener.player.PlayerLoginListener;
import com.eternalcode.core.listener.player.PlayerQuitListener;
import com.eternalcode.core.feature.spawn.SpawnRespawnController;
import com.eternalcode.core.listener.sign.SignChangeListener;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.notification.NotificationAnnouncer;
import com.eternalcode.core.notification.adventure.AdventureNotificationAnnouncer;
import com.eternalcode.core.placeholder.PlaceholderBukkitRegistryImpl;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.teleport.TeleportDeathController;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.teleport.command.TeleportBackCommand;
import com.eternalcode.core.teleport.command.TeleportCommand;
import com.eternalcode.core.teleport.command.TeleportHereCommand;
import com.eternalcode.core.teleport.command.TeleportToPositionCommand;
import com.eternalcode.core.teleport.request.TeleportRequestService;
import com.eternalcode.core.teleport.command.TeleportUpCommand;
import com.eternalcode.core.teleport.request.TpaAcceptCommand;
import com.eternalcode.core.teleport.request.TpaCommand;
import com.eternalcode.core.teleport.request.TpaDenyCommand;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.PrepareUserController;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.util.legacy.LegacyColorProcessor;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;

class EternalCore implements EternalCoreApi {

    /* Services */
    private final Scheduler scheduler;
    private final UserManager userManager;
    private final BukkitViewerProvider viewerProvider;
    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;

    /* Adventure */
    private final BukkitAudiences audiencesProvider;
    private final MiniMessage miniMessage;
    private final AdventureNotificationAnnouncer notificationAnnouncer;

    /* Configuration */
    private final ConfigurationManager configurationManager;

    /* depends on Configuration */
    private final PlaceholderRegistry placeholderRegistry;
    private final BridgeManager bridgeManager;
    private final ChatManager chatManager;
    private final NoticeService noticeService;
    private final TranslationManager translationManager;
    private final AfkService afkService;
    private final TeleportRequestService teleportRequestService;

    /* Database */
    private DatabaseManager databaseManager;

    /* depends on Database */
    private final PrivateChatService privateChatService;
    private final WarpManager warpManager;
    private final HomeManager homeManager;

    /* Frameworks & Libraries */
    private final LiteCommands<CommandSender> liteCommands;
    private final SkullAPI skullAPI;

    public EternalCore(Plugin plugin) {
        EternalCoreApiProvider.initialize(this);

        Stopwatch started = Stopwatch.createStarted();
        Server server = plugin.getServer();

        this.softwareCheck(plugin.getLogger());

        /* Services */
        this.scheduler = new BukkitSchedulerImpl(plugin);
        this.userManager = new UserManager();
        this.viewerProvider = new BukkitViewerProvider(this.userManager, server);
        this.teleportService = new TeleportService();
        this.teleportTaskService = new TeleportTaskService();

        /* Adventure */
        this.audiencesProvider = BukkitAudiences.create(plugin);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

        this.notificationAnnouncer = new AdventureNotificationAnnouncer(this.audiencesProvider, this.miniMessage);

        /* Configuration */
        ConfigurationBackupService configurationBackupService = new ConfigurationBackupService(plugin.getDataFolder());
        this.configurationManager = new ConfigurationManager(configurationBackupService, plugin.getDataFolder());

        PluginConfiguration pluginConfiguration = this.configurationManager.load(new PluginConfiguration());
        LocationsConfiguration locationsConfiguration = this.configurationManager.load(new LocationsConfiguration());
        LanguageConfiguration languageConfiguration = this.configurationManager.load(new LanguageConfiguration());
        PlaceholdersConfiguration placeholdersConfiguration = this.configurationManager.load(new PlaceholdersConfiguration());

        CommandConfiguration commandConfiguration = this.configurationManager.load(new CommandConfiguration());

        /* depends on Configuration */
        this.placeholderRegistry = new PlaceholderBukkitRegistryImpl(server);
        this.placeholderRegistry.registerPlaceholderReplacer(text -> {
            for (Map.Entry<String, String> entry : placeholdersConfiguration.placeholders.entrySet()) {
                text = text.replace(entry.getKey(), entry.getValue());
            }

            return text;
        });

        this.bridgeManager = new BridgeManager(this.placeholderRegistry, server, plugin.getLogger());
        this.bridgeManager.init();

        this.chatManager = new ChatManager(pluginConfiguration.chat);
        this.translationManager = TranslationManager.create(this.configurationManager, languageConfiguration);
        this.noticeService = new NoticeService(this.scheduler, this.translationManager, this.viewerProvider, this.notificationAnnouncer, this.placeholderRegistry);
        this.afkService = new AfkService(pluginConfiguration.afk, this.noticeService, this.userManager);
        this.teleportRequestService = new TeleportRequestService(pluginConfiguration.teleportAsk);

        /* Database */
        WarpRepository warpRepository = new WarpConfigRepository(this.configurationManager, locationsConfiguration);
        HomeRepository homeRepository;
        IgnoreRepository ignoreRepository;

        try {
            this.databaseManager = new DatabaseManager(pluginConfiguration, plugin.getLogger(), plugin.getDataFolder());
            this.databaseManager.connect();

            homeRepository = HomeRepositoryOrmLite.create(this.databaseManager, this.scheduler);
            ignoreRepository = IgnoreRepositoryOrmLite.create(this.databaseManager, this.scheduler);

        }
        catch (Exception exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Can not connect to database! Some functions may not work!");

            NoneRepository noneRepository = new NoneRepository();

            homeRepository = noneRepository;
            ignoreRepository = noneRepository;
        }

        /* depends on Database */
        this.privateChatService = new PrivateChatService(this.noticeService, ignoreRepository, this.userManager);
        this.warpManager = WarpManager.create(warpRepository);
        this.homeManager = HomeManager.create(homeRepository);

        LanguageInventory languageInventory = new LanguageInventory(languageConfiguration, this.noticeService, this.userManager, this.miniMessage);
        WarpInventory warpInventory = new WarpInventory(this.teleportTaskService, this.translationManager, this.warpManager, this.miniMessage);

        /* Frameworks & Libraries */
        this.skullAPI = LiteSkullFactory.builder()
            .bukkitScheduler(plugin)
            .build();

        this.liteCommands = LiteBukkitAdventurePlatformFactory.builder(server, "eternalcore", false, this.audiencesProvider, this.miniMessage)

            // Arguments (include optional)
            .argument(String.class, StringNicknameArgument.KEY, new StringNicknameArgument(server))
            .argument(GameMode.class,                           new GameModeArgument(this.viewerProvider, this.translationManager, commandConfiguration.argument))
            .argument(NoticeType.class,                         new NoticeTypeArgument(this.viewerProvider, this.translationManager))
            .argument(Warp.class,                               new WarpArgument(this.warpManager, this.translationManager, this.viewerProvider))
            .argument(Enchantment.class,                        new EnchantmentArgument(this.viewerProvider, this.translationManager))
            .argument(User.class,                               new UserArgument(this.viewerProvider, this.translationManager, server, this.userManager))
            .argument(Player.class,                             new PlayerArgument(this.viewerProvider, this.translationManager, server))
            .argument(Player.class, RequesterArgument.KEY,      new RequesterArgument(this.teleportRequestService, this.translationManager, this.viewerProvider, server))
            .argument(Duration.class, DurationArgument.KEY,     new DurationArgument(this.viewerProvider, this.translationManager))
            .argument(Integer.class, SpeedArgument.KEY,         new SpeedArgument(this.viewerProvider, this.translationManager))
            .argument(MobEntity.class,                          new MobEntityArgument(this.viewerProvider, this.translationManager))

            // multilevel Arguments (include optional)
            .argumentMultilevel(Location.class, new LocationArgument(this.translationManager, this.viewerProvider))

            // Native Argument (no optional)
            .argument(ArgHome.class, Home.class, new HomeArgument(this.homeManager, this.viewerProvider, this.translationManager))
            .argument(Arg.class, World.class,    new WorldArgument(server, this.translationManager, this.viewerProvider))

            // Dynamic binds
            .contextualBind(Player.class,   new PlayerContextual(this.translationManager))
            .contextualBind(Viewer.class,   new ViewerContextual(this.viewerProvider))
            .contextualBind(User.class,     new UserContextual(this.translationManager, this.userManager))

            .invalidUsageHandler(new InvalidUsage(this.viewerProvider, this.noticeService))
            .permissionHandler(new PermissionMessage(this.viewerProvider, this.audiencesProvider, this.translationManager, this.miniMessage))
            .resultHandler(Notification.class, new NotificationHandler(this.viewerProvider, this.noticeService))

            .commandInstance(
                new EternalCoreCommand(this.configurationManager, this.miniMessage),

                // Home Commands
                new HomeCommand(this.teleportTaskService, this.teleportService),
                new SetHomeCommand(this.homeManager, this.noticeService, pluginConfiguration),
                new DelHomeCommand(this.homeManager, this.noticeService),

                // Item Commands
                new ItemNameCommand(this.noticeService, this.miniMessage),
                new ItemLoreCommand(this.noticeService, this.miniMessage),
                new ItemFlagCommand(this.noticeService),

                // Weather Commands
                new SunCommand(this.noticeService),
                new ThunderCommand(this.noticeService),
                new RainCommand(this.noticeService),

                // Time Commands
                new TimeCommand(this.noticeService),
                new DayCommand(this.noticeService),
                new NightCommand(this.noticeService),

                // Tp Commands
                new TeleportCommand(this.noticeService, this.teleportService),
                new TeleportToPositionCommand(this.noticeService, this.teleportService),
                new TeleportHereCommand(this.noticeService, this.teleportService),
                new TeleportBackCommand(this.teleportService, this.noticeService),
                new TeleportUpCommand(this.teleportService, this.noticeService),

                // Tpa Commands
                new TpaCommand(this.teleportRequestService, this.noticeService),
                new TpaAcceptCommand(this.teleportRequestService, this.teleportTaskService, this.noticeService, pluginConfiguration.teleportAsk, server),
                new TpaDenyCommand(this.teleportRequestService, this.noticeService, server),

                // Spawn & Warp Command
                new SetSpawnCommand(this.configurationManager, locationsConfiguration, this.noticeService),
                new SpawnCommand(locationsConfiguration, this.noticeService, this.teleportTaskService, this.teleportService),
                new WarpCommand(this.noticeService, this.warpManager, this.teleportTaskService, pluginConfiguration, warpInventory),

                // Inventory Commands
                new EnderchestCommand(this.noticeService),
                new WorkbenchCommand(this.noticeService),
                new AnvilCommand(this.noticeService),
                new CartographyTableCommand(this.noticeService),
                new GrindstoneCommand(this.noticeService),
                new StonecutterCommand(this.noticeService),
                new DisposalCommand(this.miniMessage, this.translationManager, this.userManager, server, this.noticeService),

                // Private Chat Commands
                new PrivateChatCommands(this.privateChatService, this.noticeService),

                new AdminChatCommand(this.noticeService, server),
                new HelpOpCommand(this.noticeService, pluginConfiguration, server),
                new AlertCommand(this.noticeService),

                // Moderation Commands
                new FlyCommand(this.noticeService),
                new GodCommand(this.noticeService),
                new GameModeCommand(this.noticeService),
                new SpeedCommand(this.noticeService),
                new GiveCommand(this.noticeService, pluginConfiguration),
                new EnchantCommand(pluginConfiguration, this.noticeService),
                new RepairCommand(this.noticeService),
                new HealCommand(this.noticeService),
                new FeedCommand(this.noticeService),
                new KillCommand(this.noticeService),
                new InventoryClearCommand(this.noticeService),
                new InventoryOpenCommand(server, this.noticeService),
                new ButcherCommand(this.noticeService, pluginConfiguration),

                // Info Commands
                new OnlinePlayerCountCommand(this.noticeService, server),
                new OnlinePlayersListCommand(pluginConfiguration, this.noticeService, server),
                new WhoIsCommand(this.noticeService),
                new PingCommand(this.noticeService),

                // Misc Commands
                new HatCommand(this.noticeService),
                new AfkCommand(this.noticeService, pluginConfiguration, this.afkService),
                new SkullCommand(this.noticeService, this.skullAPI),
                new LanguageCommand(languageInventory),
                new IgnoreCommand(ignoreRepository, this.noticeService),
                new UnIgnoreCommand(ignoreRepository, this.noticeService),

                ChatManagerCommand.create(this.chatManager, this.noticeService, pluginConfiguration.chat.linesToClear)
            )
            .commandGlobalEditor(new CommandConfigurator(commandConfiguration))
            .register();

        /* Listeners */

        Stream.of(
            new TeleportDeathController(this.teleportService),
            new PlayerChatSoundListener(pluginConfiguration, server),
            new PlayerJoinListener(pluginConfiguration, this.noticeService, server),
            new PlayerQuitListener(pluginConfiguration, this.noticeService, server),
            new ChatManagerController(this.chatManager, this.noticeService),
            new PrepareUserController(this.userManager, server),
            new PlayerCommandPreprocessListener(this.noticeService, pluginConfiguration, server),
            new SignChangeListener(this.miniMessage),
            new PlayerDeathListener(this.noticeService),
            new SpawnRespawnController(this.teleportService, pluginConfiguration, locationsConfiguration),
            new TeleportListeners(this.noticeService, this.teleportTaskService),
            new AfkController(this.afkService),
            new PlayerLoginListener(this.translationManager, this.userManager, this.miniMessage)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, plugin));

        /* Tasks */

        TeleportTask task = new TeleportTask(this.noticeService, this.teleportTaskService, this.teleportService, server);
        this.scheduler.timerSync(task, Duration.ofMillis(200), Duration.ofMillis(200));

        AfkTask afkTask = new AfkTask(this.afkService, server);
        this.scheduler.timerSync(afkTask, Duration.ofMinutes(1), Duration.ofMinutes(1));

        // bStats metrics
        Metrics metrics = new Metrics((JavaPlugin) plugin, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        plugin.getLogger().info("Successfully loaded EternalCore in " + millis + "ms");
    }

    public void disable() {
        EternalCoreApiProvider.deinitialize();

        if (this.liteCommands != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }

        if (this.databaseManager != null) {
            this.databaseManager.close();
        }

        if (this.skullAPI != null) {
            this.skullAPI.shutdown();
        }
    }

    private void softwareCheck(Logger logger) {
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
        logger.info("Server version: " + environment.getMinecraftVersion());
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public BridgeManager getBridgeManager() {
        return this.bridgeManager;
    }

    @Override
    public UserManager getUserManager() {
        return this.userManager;
    }

    public PlaceholderRegistry getPlaceholderRegistry() {
        return this.placeholderRegistry;
    }

    public TeleportService getTeleportService() {
        return this.teleportService;
    }

    public TeleportTaskService getTeleportTaskService() {
        return this.teleportTaskService;
    }

    public WarpManager getWarpManager() {
        return this.warpManager;
    }

    public HomeManager getHomeManager() {
        return this.homeManager;
    }

    public AfkService getAfkService() {
        return this.afkService;
    }

    public TeleportRequestService getTeleportRequestService() {
        return this.teleportRequestService;
    }

    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }

    public ChatManager getChatManager() {
        return this.chatManager;
    }

    public PrivateChatService getPrivateChatService() {
        return this.privateChatService;
    }

    public BukkitAudiences getAudiencesProvider() {
        return this.audiencesProvider;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    @Override
    public BukkitViewerProvider getViewerProvider() {
        return this.viewerProvider;
    }

    public NotificationAnnouncer getNotificationAnnouncer() {
        return this.notificationAnnouncer;
    }

    public NoticeService getNoticeService() {
        return this.noticeService;
    }

}
