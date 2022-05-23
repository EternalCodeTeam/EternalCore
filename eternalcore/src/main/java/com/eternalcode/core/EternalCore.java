package com.eternalcode.core;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.PrivateChatService;
import com.eternalcode.core.chat.adventure.AdventureNotificationAnnouncer;
import com.eternalcode.core.command.argument.LocationArgument;
import com.eternalcode.core.command.argument.WorldArgument;
import com.eternalcode.core.command.implementation.ReplyCommand;
import com.eternalcode.core.command.implementation.SocialSpyCommand;
import com.eternalcode.core.command.implementation.TpHereCommand;
import com.eternalcode.core.command.implementation.WarpCommand;
import com.eternalcode.core.command.implementation.time.DayCommand;
import com.eternalcode.core.command.implementation.time.NightCommand;
import com.eternalcode.core.command.implementation.time.TimeCommand;
import com.eternalcode.core.command.implementation.weather.RainCommand;
import com.eternalcode.core.command.implementation.weather.SunCommand;
import com.eternalcode.core.command.implementation.weather.ThunderCommand;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.chat.legacy.LegacyColorProcessor;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
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
import com.eternalcode.core.command.bind.AudienceContextual;
import com.eternalcode.core.command.bind.PlayerContextual;
import com.eternalcode.core.command.bind.UserContextual;
import com.eternalcode.core.command.handler.ComponentResultHandler;
import com.eternalcode.core.command.handler.InvalidUsage;
import com.eternalcode.core.command.handler.PermissionMessage;
import com.eternalcode.core.command.handler.StringResultHandler;
import com.eternalcode.core.command.implementation.AdminChatCommand;
import com.eternalcode.core.command.implementation.AlertCommand;
import com.eternalcode.core.command.implementation.AnvilCommand;
import com.eternalcode.core.command.implementation.CartographyTableCommand;
import com.eternalcode.core.command.implementation.ChatCommand;
import com.eternalcode.core.command.implementation.ClearCommand;
import com.eternalcode.core.command.implementation.DisposalCommand;
import com.eternalcode.core.command.implementation.EnchantCommand;
import com.eternalcode.core.command.implementation.EnderchestCommand;
import com.eternalcode.core.command.implementation.EternalCoreCommand;
import com.eternalcode.core.command.implementation.FeedCommand;
import com.eternalcode.core.command.implementation.FlyCommand;
import com.eternalcode.core.command.implementation.GamemodeCommand;
import com.eternalcode.core.command.implementation.GiveCommand;
import com.eternalcode.core.command.implementation.GodCommand;
import com.eternalcode.core.command.implementation.GrindstoneCommand;
import com.eternalcode.core.command.implementation.HatCommand;
import com.eternalcode.core.command.implementation.HealCommand;
import com.eternalcode.core.command.implementation.HelpOpCommand;
import com.eternalcode.core.command.implementation.InventoryOpenCommand;
import com.eternalcode.core.command.implementation.KillCommand;
import com.eternalcode.core.command.implementation.LanguageCommand;
import com.eternalcode.core.command.implementation.ListCommand;
import com.eternalcode.core.command.implementation.PrivateMessageCommand;
import com.eternalcode.core.command.implementation.ItemNameCommand;
import com.eternalcode.core.command.implementation.OnlineCommand;
import com.eternalcode.core.command.implementation.PingCommand;
import com.eternalcode.core.command.implementation.RepairCommand;
import com.eternalcode.core.command.implementation.SetSpawnCommand;
import com.eternalcode.core.command.implementation.SkullCommand;
import com.eternalcode.core.command.implementation.SpawnCommand;
import com.eternalcode.core.command.implementation.SpeedCommand;
import com.eternalcode.core.command.implementation.StonecutterCommand;
import com.eternalcode.core.command.implementation.TeleportCommand;
import com.eternalcode.core.command.implementation.TpaAcceptCommand;
import com.eternalcode.core.command.implementation.TpaCommand;
import com.eternalcode.core.command.implementation.TpaDenyCommand;
import com.eternalcode.core.command.implementation.TposCommand;
import com.eternalcode.core.command.implementation.WhoIsCommand;
import com.eternalcode.core.command.implementation.WorkbenchCommand;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.CommandsConfiguration;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.configuration.lang.ENMessagesConfiguration;
import com.eternalcode.core.configuration.lang.PLMessagesConfiguration;
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
import com.eternalcode.core.listener.sign.SignChangeListener;
import com.eternalcode.core.user.PrepareUserController;
import com.eternalcode.core.scheduler.BukkitSchedulerImpl;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.teleport.TeleportListeners;
import com.eternalcode.core.teleport.TeleportRequestService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTask;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpConfigRepo;
import com.eternalcode.core.warps.WarpManager;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.scheme.SchemeFormat;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import panda.std.stream.PandaStream;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EternalCore extends JavaPlugin {

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    /**
     * Services & Managers
     **/
    private static EternalCore instance;
    private Scheduler scheduler;
    private UserManager userManager;
    private HomeManager homeManager;
    private TeleportService teleportService;
    private WarpManager warpManager;
    private BukkitUserProvider userProvider;

    /**
     * Configuration, Language & Chat
     **/
    private ConfigurationManager configurationManager;
    private LanguageManager languageManager;
    private ChatManager chatManager;

    /**
     * Services & Managers (dependent on configuration)
     **/
    private Database database;
    private TeleportRequestService teleportRequestService;

    /**
     * Adventure
     **/
    private BukkitAudiences adventureAudiences;
    private MiniMessage miniMessage;

    /**
     * Audiences System
     **/
    private BukkitViewerProvider viewerProvider;
    private NotificationAnnouncer notificationAnnouncer;
    private NoticeService noticeService;
    private PrivateChatService privateChatService;

    /**
     * FrameWorks & Libs
     **/
    private LanguageInventory languageInventory;
    private LiteCommands<CommandSender> liteCommands;
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
        this.teleportService = new TeleportService();
        this.userProvider = new BukkitUserProvider(this.userManager); // TODO: Czasowe rozwiazanie, do poprawy (do usuniecia)

        /* Configuration */

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
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

        /* Services & Managers (dependent on configuration) */

        this.warpManager = WarpManager.create(new WarpConfigRepo(configurationManager, locations));
        this.database = new Database(this.configurationManager, this.getLogger());
        this.teleportRequestService = new TeleportRequestService(config);

        /* Adventure */

        this.adventureAudiences = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

        /* Audiences System */

        this.viewerProvider = new BukkitViewerProvider(this.userManager, server, this.adventureAudiences);
        this.notificationAnnouncer = new AdventureNotificationAnnouncer(this.adventureAudiences, this.miniMessage);
        this.noticeService = new NoticeService(this.languageManager, this.viewerProvider, this.notificationAnnouncer);
        this.privateChatService = new PrivateChatService(server, this.noticeService);

        /* FrameWorks & Libs */
        this.languageInventory = new LanguageInventory(languageConfig.languageSelector, this.noticeService, this.userManager, this.miniMessage);

        this.liteCommands = LiteBukkitFactory.builder(server, "EternalCore")

            // Handlers
            .resultHandler(String.class, new StringResultHandler(this.adventureAudiences, this.miniMessage))
            .resultHandler(Component.class, new ComponentResultHandler(this.adventureAudiences))

            // Arguments (include optional)
            .argument(String.class, "player",   new PlayerNameArg(server))
            .argument(Integer.class,                new AmountArgument(this.languageManager, config, this.userProvider))
            .argument(Material.class,               new MaterialArgument(this.userProvider, this.languageManager))
            .argument(GameMode.class,               new GameModeArgument(this.userProvider, this.languageManager))
            .argument(NoticeType.class,             new NoticeTypeArgument(this.userProvider, this.languageManager))
            .argument(Warp.class,                   new WarpArgument(this.warpManager, this.languageManager, this.userProvider))
            .argument(Enchantment.class,            new EnchantmentArgument(this.userProvider, this.languageManager))
            .argument(World.class,                  new WorldArgument(server))
            .argument(Player.class,                 new PlayerArgument(this.userProvider, this.languageManager, server))
            .argument(Player.class, "request",  new RequesterArgument(this.teleportRequestService, this.languageManager, this.userProvider, server))

            // multilevel Arguments (include optional)
            .argumentMultilevel(Location.class,               new LocationArgument())

            // Native Argument (no optional)
            .argument(Arg.class, Player.class, "or_sender", new PlayerArgOrSender(this.languageManager, this.viewerProvider, server))

            // Dynamic binds
            .contextualBind(Player.class,            new PlayerContextual(this.languageManager))
            .contextualBind(Viewer.class,            new AudienceContextual(this.viewerProvider))
            .contextualBind(User.class,              new UserContextual(this.languageManager, this.userManager))

            // Static binds
            .typeBind(EternalCore.class,            () -> this)
            .typeBind(ConfigurationManager.class,   () -> this.configurationManager)
            .typeBind(LanguageInventory.class,      () -> this.languageInventory)
            .typeBind(LanguageManager.class,        () -> this.languageManager)
            .typeBind(PluginConfiguration.class,    () -> config)
            .typeBind(LocationsConfiguration.class, () -> locations)
            .typeBind(TeleportService.class,        () -> this.teleportService)
            .typeBind(UserManager.class,            () -> this.userManager)
            .typeBind(TeleportRequestService.class, () -> this.teleportRequestService)
            .typeBind(NoticeService.class,          () -> this.noticeService)
            .typeBind(MiniMessage.class,            () -> this.miniMessage)
            .typeBind(ChatManager.class,            () -> this.chatManager)
            .typeBind(PrivateChatService.class,     () -> this.privateChatService)
            .typeBind(Scheduler.class,              () -> this.scheduler)
            .typeBind(WarpManager.class,            () -> this.warpManager)

            //.permissionMessage(new PermissionHandler(this.userProvider, this.languageManager))
            .invalidUsageHandler(new InvalidUsage(this.miniMessage, this.adventureAudiences, this.userProvider, this.languageManager))
            .schemeFormat(SchemeFormat.ARGUMENT_ANGLED_OPTIONAL_SQUARE)
            .permissionHandler(new PermissionMessage(this.userProvider, this.adventureAudiences, this.languageManager, this.miniMessage))

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
                ItemNameCommand.class,
                EnchantCommand.class,
                TeleportCommand.class,
                TpHereCommand.class,
                LanguageCommand.class,
                PrivateMessageCommand.class,
                ReplyCommand.class,
                TpaCommand.class,
                TpaAcceptCommand.class,
                TpaDenyCommand.class,
                WarpCommand.class,
                SocialSpyCommand.class,
                DayCommand.class,
                NightCommand.class,
                TimeCommand.class,
                SunCommand.class,
                ThunderCommand.class,
                RainCommand.class
            )

            .register();

        /* Listeners */

        PandaStream.of(
            new PlayerChatListener(this.chatManager, noticeService, this.configurationManager, server),
            new PlayerJoinListener(this.configurationManager, this.noticeService, server),
            new PlayerQuitListener(this.configurationManager, this.noticeService, server),
            new PrepareUserController(this.userManager, server),
            new PlayerCommandPreprocessListener(this.noticeService, this.configurationManager, server),
            new SignChangeListener(this.miniMessage),
            new PlayerDeathListener(this.noticeService, this.configurationManager),
            new TeleportListeners(this.noticeService, this.teleportService)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        /* Tasks */

        TeleportTask task = new TeleportTask(this.noticeService, this.teleportService, server);
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
        this.liteCommands.getPlatform().unregisterAll();
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

    public UserManager getUserManager() {
        return this.userManager;
    }

    public HomeManager getHomeManager() {
        return this.homeManager;
    }

    public TeleportService getTeleportService() {
        return this.teleportService;
    }

    public WarpManager getWarpManager() {
        return this.warpManager;
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

    public Database getDatabase() {
        return this.database;
    }

    public TeleportRequestService getTeleportRequestService() {
        return this.teleportRequestService;
    }

    public BukkitAudiences getAdventureAudiences() {
        return this.adventureAudiences;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public ViewerProvider getAudienceProvider() {
        return this.viewerProvider;
    }

    public NotificationAnnouncer getNotificationAnnouncer() {
        return this.notificationAnnouncer;
    }

    public NoticeService getNoticeService() {
        return this.noticeService;
    }

    public LanguageInventory getLanguageInventory() {
        return this.languageInventory;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }

    public boolean isSpigot() {
        return this.isSpigot;
    }

}
