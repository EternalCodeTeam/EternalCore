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
import com.eternalcode.core.command.argument.SpeedTypeArgument;
import com.eternalcode.core.command.argument.StringNicknameArgument;
import com.eternalcode.core.command.argument.UserArgument;
import com.eternalcode.core.command.argument.WarpArgument;
import com.eternalcode.core.command.argument.WorldArgument;
import com.eternalcode.core.command.argument.MobEntityArgument;
import com.eternalcode.core.command.argument.home.ArgHome;
import com.eternalcode.core.command.argument.home.HomeArgument;
import com.eternalcode.core.command.configurator.GameModeConfigurator;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.command.configurator.CommandConfigurator;
import com.eternalcode.core.command.contextual.PlayerContextual;
import com.eternalcode.core.command.contextual.UserContextual;
import com.eternalcode.core.command.contextual.ViewerContextual;
import com.eternalcode.core.command.handler.InvalidUsage;
import com.eternalcode.core.command.handler.NoticeHandler;
import com.eternalcode.core.command.handler.PermissionMessage;
import com.eternalcode.core.configuration.ConfigurationBackupService;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.NoneRepository;
import com.eternalcode.core.database.wrapper.AutoMessageRepositoryOrmLite;
import com.eternalcode.core.database.wrapper.HomeRepositoryOrmLite;
import com.eternalcode.core.database.wrapper.IgnoreRepositoryOrmLite;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.AdminChatCommand;
import com.eternalcode.core.afk.AfkCommand;
import com.eternalcode.core.afk.AfkController;
import com.eternalcode.core.afk.AfkService;
import com.eternalcode.core.afk.AfkTask;
import com.eternalcode.core.feature.automessage.AutoMessageCommand;
import com.eternalcode.core.feature.automessage.AutoMessageRepository;
import com.eternalcode.core.feature.automessage.AutoMessageService;
import com.eternalcode.core.feature.chat.ChatManager;
import com.eternalcode.core.feature.chat.ChatManagerCommand;
import com.eternalcode.core.feature.chat.ChatManagerController;
import com.eternalcode.core.feature.essentials.AlertCommand;
import com.eternalcode.core.feature.essentials.FeedCommand;
import com.eternalcode.core.feature.essentials.FlyCommand;
import com.eternalcode.core.feature.essentials.GodCommand;
import com.eternalcode.core.feature.essentials.HealCommand;
import com.eternalcode.core.feature.essentials.KillCommand;
import com.eternalcode.core.feature.essentials.container.LoomCommand;
import com.eternalcode.core.feature.essentials.container.SmithingTableCommand;
import com.eternalcode.core.feature.essentials.mob.ButcherCommand;
import com.eternalcode.core.feature.essentials.speed.SpeedCommand;
import com.eternalcode.core.feature.essentials.speed.SpeedType;
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
import com.eternalcode.core.feature.essentials.tellraw.TellRawCommand;
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
import com.eternalcode.core.feature.randomteleport.RandomTeleportCommand;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
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
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
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
import com.eternalcode.core.updater.UpdaterController;
import com.eternalcode.core.updater.UpdaterService;
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
    private final EventCaller eventCaller;
    private final RandomTeleportService randomTeleportService;

    /* Adventure */
    private final BukkitAudiences audiencesProvider;
    private final MiniMessage miniMessage;

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
    private final UpdaterService updaterService;

    /* Database */
    private DatabaseManager databaseManager;

    /* depends on Database */
    private final PrivateChatService privateChatService;
    private final WarpManager warpManager;
    private final HomeManager homeManager;
    private final AutoMessageService autoMessageService;

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
        this.eventCaller = new EventCaller(server);

        /* Adventure */
        this.audiencesProvider = BukkitAudiences.create(plugin);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

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
        this.noticeService = NoticeService.adventure(this.audiencesProvider, this.miniMessage, this.scheduler, this.viewerProvider, this.translationManager, this.placeholderRegistry);
        this.afkService = new AfkService(pluginConfiguration.afk, this.noticeService, this.userManager, this.eventCaller);
        this.teleportRequestService = new TeleportRequestService(pluginConfiguration.teleportAsk);
        this.randomTeleportService = new RandomTeleportService(pluginConfiguration.randomTeleport);
        this.updaterService = new UpdaterService(plugin.getDescription());

        /* Database */
        WarpRepository warpRepository = new WarpConfigRepository(this.configurationManager, locationsConfiguration);
        HomeRepository homeRepository;
        IgnoreRepository ignoreRepository;
        AutoMessageRepository autoMessageRepository;

        try {
            this.databaseManager = new DatabaseManager(pluginConfiguration, plugin.getLogger(), plugin.getDataFolder());
            this.databaseManager.connect();

            homeRepository = HomeRepositoryOrmLite.create(this.databaseManager, this.scheduler);
            ignoreRepository = IgnoreRepositoryOrmLite.create(this.databaseManager, this.scheduler);
            autoMessageRepository = AutoMessageRepositoryOrmLite.create(this.databaseManager, this.scheduler);

        }
        catch (Exception exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Can not connect to database! Some functions may not work!");

            NoneRepository noneRepository = new NoneRepository();

            homeRepository = noneRepository;
            ignoreRepository = noneRepository;
            autoMessageRepository = noneRepository;
        }

        /* depends on Database */
        this.privateChatService = new PrivateChatService(this.noticeService, ignoreRepository, this.userManager);
        this.warpManager = WarpManager.create(warpRepository);
        this.homeManager = HomeManager.create(homeRepository);
        this.autoMessageService = new AutoMessageService(autoMessageRepository, pluginConfiguration.autoMessage, this.noticeService, this.scheduler, server);

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
            .argument(NoticeTextType.class,                     new NoticeTypeArgument(this.viewerProvider, this.translationManager))
            .argument(SpeedType.class,                          new SpeedTypeArgument(this.viewerProvider, this.translationManager))
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
            .permissionHandler(new PermissionMessage(this.viewerProvider, this.noticeService))
            .resultHandler(Notice.class, new NoticeHandler(this.viewerProvider, this.noticeService))

            .commandInstance(
                new EternalCoreCommand(this.configurationManager, this.miniMessage),

                // AutoMessage Command
                new AutoMessageCommand(this.autoMessageService, this.noticeService),

                // Home Commands
                new HomeCommand(this.teleportTaskService, this.teleportService, this.noticeService, this.homeManager),
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
                new RandomTeleportCommand(this.noticeService, this.randomTeleportService),

                // Tpa Commands
                new TpaCommand(this.teleportRequestService, this.noticeService),
                new TpaAcceptCommand(this.teleportRequestService, this.teleportTaskService, this.noticeService, pluginConfiguration.teleportAsk, server),
                new TpaDenyCommand(this.teleportRequestService, this.noticeService, server),

                // Spawn & Warp Command
                new SetSpawnCommand(this.configurationManager, locationsConfiguration, this.noticeService),
                new SpawnCommand(locationsConfiguration, pluginConfiguration.teleport, this.noticeService, this.teleportTaskService, this.teleportService),
                new WarpCommand(this.noticeService, this.warpManager, this.teleportTaskService, pluginConfiguration, warpInventory),

                // Inventory Commands
                new EnderchestCommand(this.noticeService),
                new WorkbenchCommand(this.noticeService),
                new AnvilCommand(this.noticeService),
                new CartographyTableCommand(this.noticeService),
                new GrindstoneCommand(this.noticeService),
                new StonecutterCommand(this.noticeService),
                new LoomCommand(this.noticeService),
                new SmithingTableCommand(this.noticeService),
                new DisposalCommand(this.miniMessage, this.translationManager, this.userManager, server, this.noticeService),

                // Private Chat Commands
                new PrivateChatCommands(this.privateChatService, this.noticeService),

                new AdminChatCommand(this.noticeService, server),
                new HelpOpCommand(this.noticeService, pluginConfiguration, server),
                new AlertCommand(this.noticeService),
                new TellRawCommand(this.noticeService),

                // Moderation Commands
                new FlyCommand(this.noticeService),
                new GodCommand(this.noticeService),
                new GameModeCommand(commandConfiguration, this.noticeService),
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
            .commandEditor(GameModeCommand.class, new GameModeConfigurator(commandConfiguration))
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
            new PlayerLoginListener(this.translationManager, this.userManager, this.miniMessage),
            new UpdaterController(pluginConfiguration, this.updaterService, this.audiencesProvider, this.miniMessage)
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

    public EventCaller getEventCaller() {
        return this.eventCaller;
    }

    public WarpManager getWarpManager() {
        return this.warpManager;
    }

    public HomeManager getHomeManager() {
        return this.homeManager;
    }

    public AutoMessageService getAutoMessageService() {
        return this.autoMessageService;
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

    public NoticeService getNoticeService() {
        return this.noticeService;
    }

}
