package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.AbstractTranslation;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class ENTranslation extends AbstractTranslation {

    ENTranslation(Language language) {
        super(language);
    }

    ENTranslation() {
        this(Language.EN);
    }

    @Description({
        "#",
        "# This file is responsible for the English translation in eternalcore.",
        "#",
        "# If you need help with setup or have any questions related to EternalCore,",
        "# join us in our discord, or write a request in the \"Issues\" tab on GitHub",
        "#",
        "# Issues: https://github.com/EternalCodeTeam/EternalCore/issues",
        "# Discord: https://discord.gg/FQ7jmGBd6c",
        "# Website: https://eternalcode.pl/",
        "# SourceCode: https://github.com/EternalCodeTeam/EternalCore",
    })

    @Description({
        " ",
        "# You can use MiniMessages formatting everywhere, or standard &7, &e etc.",
        "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
        "# You can use the web generator to generate and preview messages: https://webui.adventure.kyori.net/",
        "#",
        "# You can without any restrictions send to, whether the message is to be sent in the chat, actionbar, or both, how",
        "# add prefix before text:",
        "# [CHAT] - Message will be sent to chat",
        "# [ACTIONBAR] - Message will be sent to actionbar",
        "# [TITLE] - Message will be sent as title",
        "# [SUBTITLE] - Message will be sent as subtitle",
        "# [DISABLED] - Message will not be sent",
        "#",
        "# You can also combine prefixes as follows:",
        "# [CHAT, ACTIONBAR] - Message will be sent to chat and actionbar",
        "# [CHAT, ACTIONBAR, TITLE] - Message will be sent to chat, actionbar and title",
        "#",
        "# If you don't enter any prefix, the message will be sent simply in the chat",
        "#",
        "# All options except EternalCore messages are described in the config.yml file",
        " "
    })

    @Description("# This section is responsible for all messages used during bad of a command argument")
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - Required permission")
        public Notification permissionMessage = Notification.chat("<dark_gray>» <red>You don't have permission to perform this command! <gray>({PERMISSIONS})");

        @Description({ " ", "# {USAGE} - Correct usage" })
        public Notification usageMessage = Notification.chat("<dark_gray>» <yellow>Correct usage: <gray>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<dark_gray>» <yellow>Correct usage:");
        public Notification usageMessageEntry = Notification.chat("<dark_gray>» <gray>{USAGE}");

        @Description(" ")
        public Notification offlinePlayer = Notification.chat("<dark_gray>» <red>This player is currently offline!");
        public Notification onlyPlayer = Notification.chat("<dark_gray>» <red>Command is only for players!");
        public Notification numberBiggerThanOrEqualZero = Notification.chat("<dark_gray>» <red>The number must be greater than or equal to 0!");
        public Notification noItem = Notification.chat("<dark_gray>» <red>You need item to use this command!");
        public Notification noMaterial = Notification.chat("<dark_gray>» <red>This item doesn't exist");
        public Notification noArgument = Notification.chat("<dark_gray>» <red>This argument doesn't exist");
        public Notification noDamaged = Notification.chat("<dark_gray>» <red>This item can't be repaired");
        public Notification noDamagedItems = Notification.chat("<dark_gray>» <red>You need damaged items to use this command!");
        public Notification noEnchantment = Notification.chat("<dark_gray>» <red>This enchantment doesn't exist");
        public Notification noValidEnchantmentLevel = Notification.chat("<dark_gray>» <red>This enchantment level is not supported!");
        public Notification invalidTimeFormat = Notification.chat("<dark_gray>» <red>Invalid time format!");
        public Notification worldDoesntExist = Notification.chat("<dark_gray>» <red>This world doesn't exist!");
        public Notification youMustGiveWorldName = Notification.chat("<dark_gray>» <red>You must provide a world name!");
        public Notification incorrectLocation = Notification.chat("<dark_gray>» <red>Incorrect location!");
    }

    @Description({
        " ",
        "# This answer is responsible for the general formatting of some values",
        "# The purpose of the section is to reduce the repetition of some messages."
    })
    public ENFormatSection format = new ENFormatSection();

    @Getter
    @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Description({
        " ",
        "# This section is responsible for the player support chat /helpop",
    })
    public ENHelpOpSection helpOp = new ENHelpOpSection();

    @Getter
    @Contextual
    public static class ENHelpOpSection implements HelpOpSection {
        @Description("# {PLAYER} - Player who send message on /helpop, {TEXT} - message")
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<dark_gray>: <white>{TEXT}");
        @Description(" ")
        public Notification send = Notification.chat("<dark_gray>» <green>This message has been successfully sent to administration");
        @Description("# {TIME} - Time to next use (cooldown)")
        public Notification helpOpDelay = Notification.chat("<dark_gray>» <red>You can use this command for: <gold>{TIME}");
    }

    @Description({
        " ",
        "# This section is responsible for the communication between administration",
    })
    public ENAdminChatSection adminChat = new ENAdminChatSection();

    @Getter
    @Contextual
    public static class ENAdminChatSection implements AdminChatSection {
        @Description({ "# {PLAYER} - Player who sent message on adminchat, {TEXT} - message" })
        public Notification format = Notification.chat("<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");
    }

    @Description({
        " ",
        "# This section is responsible for messages related to teleportation",
    })
    public ENTeleportSection teleport = new ENTeleportSection();

    @Getter
    @Contextual
    public static class ENTeleportSection implements TeleportSection {
        // teleport
        @Description({ "# {PLAYER} - Teleported players" })
        public Notification teleportedToPlayer = Notification.chat("<dark_gray>» <green>Successfully teleported to {PLAYER}!");

        @Description({ "# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred" })
        public Notification teleportedPlayerToPlayer = Notification.chat("<dark_gray>» <green>Successfully teleported {PLAYER} to {ARG-PLAYER}!");

        // Task
        @Description({ "# {TIME} - Teleportation time" })
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleporting in <white>{TIME}");
        @Description(" ")
        public Notification teleported = Notification.chat("<dark_gray>» <green>Teleported!");
        public Notification teleporting = Notification.chat("<dark_gray>» <green>Teleporting...");
        public Notification teleportTaskCanceled = Notification.chat("<dark_gray>» <red>You've moved, teleportation canceled!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<dark_gray>» <red>You are in teleport!");

        // Coordinates XYZ
        @Description({ " ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate" })
        public Notification teleportedToCoordinates = Notification.chat("<dark_gray>» <gold>Teleported to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");
        @Description({ " ", "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate" })
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<dark_gray>» <gold>Teleported <red>{PLAYER} <gold>to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");

        // Back
        @Description(" ")
        public Notification teleportedToLastLocation = Notification.chat("<dark_gray>» <gray>Teleported to the last location!");
        @Description({ " ", "# {PLAYER} - Player who has been teleported" })
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<dark_gray>» <gray>Teleported {PLAYER} to the last location!");
        @Description(" ")
        public Notification lastLocationNoExist = Notification.chat("<dark_gray>» <red>Last location is not exist!");
    }


    public ENChatSection chat = new ENChatSection();

    @Getter
    @Contextual
    public static class ENChatSection implements ChatSection {
        @Description({ "# {PLAYER} - Player who performed the actions for the chat" })
        public Notification disabled = Notification.chat("<dark_gray>» <red>Chat has been disabled by {PLAYER}!");
        public Notification enabled = Notification.chat("<dark_gray>» <green>The chat has been enabled by {PLAYER}!");
        public Notification cleared = Notification.chat("<dark_gray>» <gold>Chat has been cleared by {PLAYER}!");

        @Description(" ")
        public Notification alreadyDisabled = Notification.chat("<dark_gray>» <green>Chat already off!");
        public Notification alreadyEnabled = Notification.chat("<dark_gray>» <green>Chat already on!");

        @Description({ " ", "# {SLOWMODE} - Time for next message" })
        public Notification slowModeSet = Notification.chat("<dark_gray>» <green>Slowmode set to: {SLOWMODE}");

        @Description({ " ", "# {TIME} - Time to next use (cooldown)" })
        public Notification slowMode = Notification.chat("<dark_gray>» <red>You can write the next message for: <gold>{TIME}");

        @Description(" ")
        public Notification disabledChatInfo = Notification.chat("<dark_gray>» <red>Chat is currently disabled!");

        @Description({ " ", "# {BROADCAST} - Broadcast" })
        public String alertMessageFormat = "<red><bold>BROADCAST: <gray>{BROADCAST}";

        @Description(" ")
        public Notification commandNotFound = Notification.chat("<dark_gray>» <red>Command <yellow>{COMMAND} <red>doesn't exists!");
    }

    @Description({
        " ",
        "# This section is responsible for handling tpa requests,",
        "# It gives you the ability to edit queries of this type",
    })
    public ENTpaSection tpa = new ENTpaSection();

    @Getter
    @Contextual
    public static class ENTpaSection implements TpaSection {
        public Notification tpaSelfMessage = Notification.chat("<dark_gray>» <red>You can't teleport to yourself!");
        public Notification tpaAlreadySentMessage = Notification.chat("<dark_gray>» <red>You have already sent a teleportation request!");
        public Notification tpaSentMessage = Notification.chat("<dark_gray>» <green>You have sent a request for teleportation to a player: <gray>{PLAYER}<green>!");

        @Description({
            " ",
            "# We used MiniMessages formatting in these messages",
            "# The current request acceptance message allows the player to click on it to accept the teleport request with MiniMessages!",
            "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({ " ", "# {PLAYER} - Player who sent the request to another player" })
        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<dark_gray>» <green>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!"),
            Notification.chat("<hover:show_text:'<green>Accept request for teleports?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept! <gray>(Click)</gray></click></gold></hover>"),
            Notification.chat("<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red><green>to deny! <gray>(Click)</gray></click></gold></hover>")
        );

        @Description(" ")
        public Notification tpaDenyNoRequestMessage = Notification.chat("<dark_gray>» <red>You do not have a teleport request from this player!");

        @Description({ " ", "# {PLAYER} - Player who sent a request to teleport to another player" })
        public Notification tpaDenyDoneMessage = Notification.chat("<dark_gray>» <red>You have declined a teleport request from a player: <gray>{PLAYER}<red>!");

        @Description({ " ", "# {PLAYER} - Player who declines the tpa request" })
        public Notification tpaDenyReceivedMessage = Notification.chat("<dark_gray>» <red>Player: <gray>{PLAYER} rejected your teleport request!");

        @Description(" ")
        public Notification tpaDenyAllDenied = Notification.chat("<dark_gray>» <red>All players have denied your teleport request!");

        @Description({ " ", "# {PLAYER} - Player who sent tpa request to another player" })
        public Notification tpaAcceptMessage = Notification.chat("<dark_gray>» <green>You have accepted the teleportation from the player: <gray>{PLAYER}<green>!");

        @Description(" ")
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<dark_gray>» <red>This player has not sent you a teleport request!");

        @Description({ " ", "# {PLAYER} - Player who sent a request to teleport to another player" })
        public Notification tpaAcceptReceivedMessage = Notification.chat("<dark_gray>» <green>Player: <gray>{PLAYER} <green>accepted your teleportation request!");

        @Description(" ")
        public Notification tpaAcceptAllAccepted = Notification.chat("<dark_gray>» <green>All players have accepted your teleport request!");
    }

    @Description({
        " ",
        "# This section is responsible for setting and editing fast travel points - warp",
    })
    public ENWarpSection warp = new ENWarpSection();

    @Getter
    @Contextual
    public static class ENWarpSection implements WarpSection {
        @Description("# {WARP} - Warp name")
        public Notification warpAlreadyExists = Notification.chat("<dark_gray>» <red>Warp <yellow>{WARP} <red>already exists!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Warp {WARP} has been created.");
        public Notification remove = Notification.chat("<dark_gray>» <gray>Warp {WARP} has been deleted.");

        @Description(" ")
        public Notification notExist = Notification.chat("<dark_gray>» <red>This warp doesn't exist");
    }


    @Description({
        " ",
        "# The following section is responsible for setting and editing personal fast travel points - home",
    })
    public ENHomeSection home = new ENHomeSection();

    @Getter
    @Contextual
    public static class ENHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<dark_gray>» <red>This home doesn't exist");

        @Description({ " ", "# {HOME} - Home name" })
        public Notification create = Notification.chat("<dark_gray>» <gray>Home {HOME} has been created.");
        public Notification delete = Notification.chat("<dark_gray>» <gray>Home {HOME} has been deleted.");
        public Notification limit = Notification.chat("<dark_gray>» <red>You have reached the limit of homes! Your limit is {LIMIT}.");

        @Description({ " ", "# {LIMIT} - Homes limit" })
        public Notification overrideHomeLocation = Notification.chat("<dark_gray>» <gray>Home {HOME} has been overridden.");
    }

    @Description({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENPrivateSection privateChat = new ENPrivateSection();

    @Getter
    @Contextual
    public static class ENPrivateSection implements PrivateChatSection {
        public Notification noReply = Notification.chat("<dark_gray>» <red>You have no one to reply!");

        @Description("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({ " ", "# {SENDER} - Message sender, {MESSAGE} - Message" })
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notification socialSpyEnable = Notification.chat("<dark_gray>» <green>SocialSpy has been enabled!");
        public Notification socialSpyDisable = Notification.chat("<dark_gray>» <red>SocialSpy has been disabled!");

        @Description({ " ", "# {PLAYER} - Ignored player" })
        public Notification ignorePlayer = Notification.chat("<dark_gray>» <red>{PLAYER} <gray>player has been ignored!");

        @Description(" ")
        public Notification ignoreAll = Notification.chat("<dark_gray>» <red>All players have been ignored!");
        public Notification cantIgnoreYourself = Notification.chat("<dark_gray>» <red>You can't ignore yourself!");

        @Description({ " ", "# {PLAYER} - Ignored player." })
        public Notification alreadyIgnorePlayer = Notification.chat("<dark_gray>» <red>You already ignore this player!");

        @Description("# {PLAYER} - Unignored player")
        public Notification unIgnorePlayer = Notification.chat("<dark_gray>» <red>{PLAYER} <gray>player has been uningored!");

        @Description(" ")
        public Notification unIgnoreAll = Notification.chat("<dark_gray>» <red>All players have been uningored!");
        public Notification cantUnIgnoreYourself = Notification.chat("<dark_gray>» <red>You can't unignore yourself!");

        @Description({ " ", "# {PLAYER} - Ignored player" })
        public Notification notIgnorePlayer = Notification.chat("<dark_gray>» <red>You don't ignore this player, so you can unignore him!");
    }

    @Description({
        " ",
        "# Section responsible for AFK."
    })
    public ENAfkSection afk = new ENAfkSection();

    @Getter
    @Contextual
    public static class ENAfkSection implements AfkSection {
        @Description("# {PLAYER} - Player who is in AFK")
        public Notification afkOn = Notification.chat("<dark_gray>» <gray>{PLAYER} is AFK!");
        public Notification afkOff = Notification.chat("<dark_gray>» <gray>{PLAYER} is no more AFK!");

        @Description({ " ", "# {TIME} - Time after the player can execute the command." })
        public Notification afkDelay = Notification.chat("<dark_gray>» <red>You can use this command only after <gold>{TIME}!");
    }

    @Description({
        " ",
        "# Section responsible for various server events."
    })
    public ENEventSection event = new ENEventSection();

    @Getter
    @Contextual
    public static class ENEventSection implements EventSection {
        @Description({
            " # EternalCore will pick a random message for the list below, every time the player do a various action.",
            " # If the {KILLER} will be, for example a console, it will pick the unknownPlayerDeath message."
        })
        public String unknownPlayerDeath = "unknown";

        @Description("# {PLAYER} - Killed player, {KILLER} - Killer")
        public List<Notification> deathMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>died!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>was killed by <gray>{KILLER}!")
        );

        @Description("# {PLAYER} - Player who joined")
        public List<Notification> joinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>joined the server!"),
            Notification.chat("<dark_gray>» <gray>Welcome to the server <green>{PLAYER}<gray>!")
        );

        @Description("# {PLAYER} - Player who joined.")
        public List<Notification> firstJoinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>joined the server for the first time!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>welcome to the server for the first time!")
        );

        @Description("# {PLAYER} - Player who left")
        public List<Notification> quitMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>logged off the server!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>left the server!")
        );

        @Description({ " ", "# {PLAYER} - Player who joined" })
        public Notification welcomeTitle = Notification.chat("<yellow>EternalCode.pl");
        public Notification welcomeSubtitle = Notification.chat("<yellow>Welcome back to the server!");
    }


    @Description({ " ", "# Section responsible for inventories-related stuff." })
    public ENInventorySection inventory = new ENInventorySection();

    @Getter
    @Contextual
    public static class ENInventorySection implements InventorySection {
        public Notification inventoryClearMessage = Notification.chat("<dark_gray>» <red>Your inventory has been cleared!");

        @Description({ " ", "# {PLAYER} - Target player" })
        public Notification inventoryClearMessageBy = Notification.chat("<dark_gray>» <red>Player {PLAYER} inventory cleared");

        @Description(" ")
        public Notification cantOpenYourInventory = Notification.chat("<dark_gray>» <red>You can't open your own inventory!");
        public String disposalTitle = "<white><bold>Trash";
    }

    @Description({
        " ",
        "# This section is responsible for player-related stuff and interactions with them."
    })
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    @Contextual
    public static class ENPlayerSection implements PlayerSection {
        public Notification feedMessage = Notification.chat("<dark_gray>» <green>You've been fed!");

        @Description(" # {PLAYER} - Player who has been fed")
        public Notification feedMessageBy = Notification.chat("<dark_gray>» <green>You've fed the {PLAYER}");

        @Description(" ")
        public Notification healMessage = Notification.chat("<dark_gray>» <green>You've been healed!");

        @Description("# {PLAYER} - Healed player")
        public Notification healMessageBy = Notification.chat("<dark_gray>» <red>Healed <gold>{PLAYER}");

        @Description(" ")
        public Notification killSelf = Notification.chat("<dark_gray>» <red>You killed yourself!");
        @Description("# {PLAYER} - Killed player")
        public Notification killedMessage = Notification.chat("<dark_gray>» <red>Killed {PLAYER}");

        @Description(" ")
        public Notification speedBetweenZeroAndTen = Notification.chat("<dark_gray>» <red>Enter speed from 0 to 10!");

        @Description("# {SPEED} - Speed value")
        public Notification speedSet = Notification.chat("<dark_gray>» <red>Speed is set to {SPEED}");

        @Description("# {PLAYER} - Target player, {SPEED} - Target player speed value")
        public Notification speedSetBy = Notification.chat("<dark_gray>» <red>Speed for {PLAYER} is set to {SPEED}");

        @Description({ " ", "# {STATE} - Godmode status" })
        public Notification godMessage = Notification.chat("<dark_gray>» <red>God is now {STATE}");

        @Description({ " ", "# {PLAYER} - Target player, {STATE} - Target player godmode status" })
        public Notification godSetMessage = Notification.chat("<dark_gray>» <red>Player <gold>{PLAYER} god is now: {STATE}");

        @Description({ " ", "# {STATE} - Fly status" })
        public Notification flyMessage = Notification.chat("<dark_gray>» <red>Fly is now {STATE}");

        @Description("# {PLAYER} - Target player, {STATE} - Target player fly status")
        public Notification flySetMessage = Notification.chat("<dark_gray>» <red>Fly for <gold>{PLAYER} <red>is now {STATE}");

        @Description({ " ", "# {PING} - Current ping" })
        public Notification pingMessage = Notification.chat("<dark_gray>» <red>Your ping is: <gold>{PING}ms");

        @Description("# {PLAYER} - Target player, {PING} - Ping of target player")
        public Notification pingOtherMessage = Notification.chat("<dark_gray>» <red>Ping of the <gold>{PLAYER} <red>is: <gold>{PING}ms");

        @Description(" ")
        public Notification gameModeNotCorrect = Notification.chat("<dark_gray>» <red>Not a valid gamemode type");

        @Description("# {GAMEMODE} - Gamemode name")
        public Notification gameModeMessage = Notification.chat("<dark_gray>» <red>Gamemode now is set to: {GAMEMODE}");

        @Description("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
        public Notification gameModeSetMessage = Notification.chat("<dark_gray>» <red>Gamemode for <gold>{PLAYER} <red>now is set to: <gold>{GAMEMODE}");

        @Description({ " ", "# {ONLINE} - Number of online players" })
        public Notification onlinePlayersCountMessage = Notification.chat("<dark_gray>» <gold>On server now is: <white>{ONLINE} <gold>players!");

        @Description("# {ONLINE} - Current online players, {PLAYERS} - Player list")
        public Notification onlinePlayersMessage = Notification.chat("<dark_gray>» <gold>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}");

        public List<String> fullServerSlots = List.of(
            " ",
            "<dark_gray>» <red>Server is full!",
            "<dark_gray>» <red>Buy rank on our site!"
        );

        @Description({
            " ",
            "# {PLAYER} - Player name",
            "# {UUID} - Player UUID",
            "# {IP} - Player IP",
            "# {WALK-SPEED} - Player walk speed",
            "# {SPEED} - Player fly speed",
            "# {PING} - Player ping",
            "# {LEVEL} - Player level",
            "# {HEALTH} - Player health",
            "# {FOOD} - Player food level"
        })
        public List<String> whoisCommand = List.of("<dark_gray>» <gray>Target name: <white>{PLAYER}",
            "<dark_gray>» <gray>Target UUID: <white>{UUID}",
            "<dark_gray>» <gray>Target address: <white>{IP}",
            "<dark_gray>» <gray>Target walk speed: <white>{WALK-SPEED}",
            "<dark_gray>» <gray>Target fly speed: <white>{SPEED}",
            "<dark_gray>» <gray>Target ping: <white>{PING}ms",
            "<dark_gray>» <gray>Target level: <white>{LEVEL}",
            "<dark_gray>» <gray>Target health: <white>{HEALTH}",
            "<dark_gray>» <gray>Target food level: <white>{FOOD}"
        );
    }

    @Description({ " ", "# This section is responsible for spawn-related stuff." })
    public ENSpawnSection spawn = new ENSpawnSection();

    @Getter
    @Contextual
    public static class ENSpawnSection implements SpawnSection {
        public Notification spawnSet = Notification.chat("<dark_gray>» <green>Spawn set!");
        public Notification spawnNoSet = Notification.chat("<dark_gray>» <red>Spawn is not set!");
        @Description({ " ", "# {PLAYER} - Player who teleported you to spawn" })
        public Notification spawnTeleportedBy = Notification.chat("<dark_gray>» <red>You have been teleported to spawn by {PLAYER}!");
        @Description({ " ", "# {PLAYER} - Teleported player" })
        public Notification spawnTeleportedOther = Notification.chat("<dark_gray>» <red>You teleported player {PLAYER} to spawn!");
    }

    @Description({
        " ",
        "# This section is responsible for messages regarding item attributes."
    })
    public ENItemSection item = new ENItemSection();

    @Getter
    @Contextual
    public static class ENItemSection implements ItemSection {
        @Description("# {ITEM_NAME} - New item name")
        public Notification itemChangeNameMessage = Notification.chat("<dark_gray>» <gray>Name has been changed to: <red>{ITEM_NAME}");

        @Description(" ")
        public Notification itemClearNameMessage = Notification.chat("<dark_gray>» <gray>Name has been cleared!");

        @Description({ " ", "# {ITEM_LORE} - New item lore" })
        public Notification itemChangeLoreMessage = Notification.chat("<dark_gray>» <gray>Lore has been changed to: <red>{ITEM_LORE}");

        @Description(" ")
        public Notification itemClearLoreMessage = Notification.chat("<dark_gray>» <gray>Lore has been cleared!");

        @Description({ " ", "# {ITEM_FLAG} - Flag name" })
        public Notification itemFlagRemovedMessage = Notification.chat("<dark_gray>» <gray>Flag {ITEM_FLAG} has been removed!");
        public Notification itemFlagAddedMessage = Notification.chat("<dark_gray>» <gray>Flag {ITEM_FLAG} has been added!");

        @Description(" ")
        public Notification itemFlagClearedMessage = Notification.chat("<dark_gray>» <gray>Flags have been cleared!");

        @Description({ " ", "# {ITEM} - Name of received item" })
        public Notification giveReceived = Notification.chat("<dark_gray>» <red>You have received: <gold>{ITEM}");

        @Description({ " ", "# {PLAYER} - Name of item receiver, {ITEM} - the item" })
        public Notification giveGiven = Notification.chat("<dark_gray>» <red>Player <gold>{PLAYER} <red>has received <gold>{ITEM}");

        @Description(" ")
        public Notification giveNotItem = Notification.chat("<dark_gray>» <red>Not a valid obtainable item!");
        public Notification repairMessage = Notification.chat("<dark_gray>» <green>Repaired!");

        @Description({ " ", "# {SKULL} - Name of the skull owner" })
        public Notification skullMessage = Notification.chat("<dark_gray>» <green>Player {SKULL} heads received");

        @Description(" ")
        public Notification enchantedMessage = Notification.chat("<dark_gray>» <gold>Item in hand is enchanted!");
    }


    @Description({ " ", "# Messages sent on time and weather change." })
    public ENTimeAndWeatherMessageSection timeAndWeather = new ENTimeAndWeatherMessageSection();

    @Getter
    @Contextual
    public static class ENTimeAndWeatherMessageSection implements TimeAndWeatherSection {
        @Description("# {WORLD} - World name")
        public Notification timeSetDay = Notification.chat("<dark_gray>» <green>Time set to day in the <yellow>{WORLD}!");
        public Notification timeSetNight = Notification.chat("<dark_gray>» <green>Time set to night in the <yellow>{WORLD}!");

        @Description("# {TIME} - Changed time in ticks")
        public Notification timeSet = Notification.chat("<dark_gray>» <green>Time set to <yellow>{TIME}");
        public Notification timeAdd = Notification.chat("<dark_gray>» <green>Time added <yellow>{TIME}");

        @Description("# {WORLD} - World name")
        public Notification weatherSetRain = Notification.chat("<dark_gray>» <green>Weather set to rain in the <yellow>{WORLD}!");
        public Notification weatherSetSun = Notification.chat("<dark_gray>» <green>Weather set to sun in the <yellow>{WORLD}!");
        public Notification weatherSetThunder = Notification.chat("<dark_gray>» <green>Weather set to thunder in the <yellow>{WORLD}!");
    }

    @Description({ " ", "# Messages responsible for containers" })
    public ENContainerSection container = new ENContainerSection();

    @Getter
    @Contextual
    public static class ENContainerSection implements ContainerSection {

        @Description({
            "# These messages are sent when the player opens a container",
            "# {PLAYER} - Player who opened the container"
        })

        public Notification genericContainerOpened = Notification.disabled("");

        public Notification genericContainerOpenedBy = Notification.disabled("<dark_gray>» <green>The specified container has been opened by {PLAYER}!");
        public Notification genericContainerOpenedFor = Notification.disabled("<dark_gray>» <green>The specified container has been opened for {PLAYER}!");
    }

    @Description({ " ", "# Information sent, when the language is changed to English" })
    public ENLanguageSection language = new ENLanguageSection();

    @Getter
    @Contextual
    public static class ENLanguageSection implements LanguageSection {
        public Notification languageChanged = Notification.chat("<dark_gray>» <gold>Language changed to <red>English<gold>!");

    }

}
