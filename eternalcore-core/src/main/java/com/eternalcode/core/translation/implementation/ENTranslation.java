package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.feature.warp.config.WarpConfigItem;
import com.eternalcode.core.feature.warp.config.WarpInventoryItem;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.AbstractTranslation;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        public Notification permissionMessage = Notification.chat("<red>✘ <dark_red>You don't have permission to perform this command! <red>({PERMISSIONS})");

        @Description({ " ", "# {USAGE} - Correct usage" })
        public Notification usageMessage = Notification.chat("<gold>✘ <white>Correct usage: <gold>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<green>► <white>Correct usage:");
        public Notification usageMessageEntry = Notification.chat("<green>► <white>{USAGE}");

        @Description(" ")
        public Notification offlinePlayer = Notification.chat("<red>✘ <dark_red>This player is currently offline!");
        public Notification onlyPlayer = Notification.chat("<red>✘ <dark_red>Command is only for players!");
        public Notification numberBiggerThanOrEqualZero = Notification.chat("<red>✘ <dark_red>The number must be greater than or equal to 0!");
        public Notification noItem = Notification.chat("<red>✘ <dark_red>You need item to use this command!");
        public Notification noMaterial = Notification.chat("<red>✘ <dark_red>This item doesn't exist");
        public Notification noArgument = Notification.chat("<red>✘ <dark_red>This argument doesn't exist");
        public Notification noDamaged = Notification.chat("<red>✘ <dark_red>This item can't be repaired");
        public Notification noDamagedItems = Notification.chat("<red>✘ <dark_red>You need damaged items to use this command!");
        public Notification noEnchantment = Notification.chat("<red>✘ <dark_red>This enchantment doesn't exist");
        public Notification noValidEnchantmentLevel = Notification.chat("<red>✘ <dark_red>This enchantment level is not supported!");
        public Notification invalidTimeFormat = Notification.chat("<red>✘ <dark_red>Invalid time format!");
        public Notification worldDoesntExist = Notification.chat("<red>✘ <dark_red>This world doesn't exist!");
        public Notification youMustGiveWorldName = Notification.chat("<red>✘ <dark_red>You must provide a world name!");
        public Notification incorrectLocation = Notification.chat("<red>✘ <dark_red>Incorrect location!");
        public Notification incorrectNumberOfChunks = Notification.chat("<red>✘ <dark_red>Incorrect number of chunks!");
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
        public Notification send = Notification.chat("<green>► <white>This message has been successfully sent to administration");
        @Description("# {TIME} - Time to next use (cooldown)")
        public Notification helpOpDelay = Notification.chat("<gold>✘ <red>You can use this command for: <gold>{TIME}");
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
        public Notification teleportedToPlayer = Notification.chat("<green>► <white>Successfully teleported to <green>{PLAYER}<white>!");

        @Description({ "# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred" })
        public Notification teleportedPlayerToPlayer = Notification.chat("<green>► <white>Successfully teleported <green>{PLAYER} <white>to <green>{ARG-PLAYER}<white>!");

        @Description({ "# {Y} - Y coordinate of the highest block" })
        public Notification teleportedToHighestBlock = Notification.chat("<green>► <white>Teleported successfully to the highest block! (Y: {Y})");

        // Task
        @Description({ "# {TIME} - Teleportation time" })
        public Notification teleportTimerFormat = Notification.actionbar("<green>► <white>Teleporting in <green>{TIME}");
        @Description(" ")
        public Notification teleported = Notification.chat("<green>► <white>Teleported!");
        public Notification teleporting = Notification.chat("<green>► <white>Teleporting...");
        public Notification teleportTaskCanceled = Notification.chat("<red>✘ <dark_red>You've moved, teleportation canceled!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<red>✘ <dark_red>You are in teleport!");

        // Coordinates XYZ
        @Description({ " ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate" })
        public Notification teleportedToCoordinates = Notification.chat("<green>► <white>Teleported to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Description({ " ", "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate" })
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<green>► <white>Teleported <green>{PLAYER} <white>to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Description(" ")
        public Notification teleportedToLastLocation = Notification.chat("<green>► <white>Teleported to the last location!");
        @Description({ " ", "# {PLAYER} - Player who has been teleported" })
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<green>► <white>Teleported <green>{PLAYER} <white>to the last location!");
        @Description(" ")
        public Notification lastLocationNoExist = Notification.chat("<red>✘ <dark_red>Last location is not exist!");
    }


    public ENChatSection chat = new ENChatSection();

    @Getter
    @Contextual
    public static class ENChatSection implements ChatSection {
        @Description({ "# {PLAYER} - Player who performed the actions for the chat" })
        public Notification disabled = Notification.chat("<green>► <white>Chat has been disabled by <green>{PLAYER}<white>!");
        public Notification enabled = Notification.chat("<green>► <white>The chat has been enabled by <green>{PLAYER}<white>!");
        public Notification cleared = Notification.chat("<green>► <white>Chat has been cleared by <green>{PLAYER}<white>!");

        @Description(" ")
        public Notification alreadyDisabled = Notification.chat("<red>✘ <dark_red>Chat already off!");
        public Notification alreadyEnabled = Notification.chat("<red>✘ <dark_red>Chat already on!");

        @Description({ " ", "# {SLOWMODE} - Time for next message" })
        public Notification slowModeSet = Notification.chat("<green>► <white>Slowmode set to: {SLOWMODE}");

        @Description({ " ", "# {TIME} - Time to next use (cooldown)" })
        public Notification slowMode = Notification.chat("<red>✘ <dark_red>You can write the next message for: <red>{TIME}<dark_red>!");

        @Description(" ")
        public Notification disabledChatInfo = Notification.chat("<red>✘ <dark_red>Chat is currently disabled!");

        @Description({ " ", "# {BROADCAST} - Broadcast" })
        public String alertMessageFormat = "<red><bold>BROADCAST: <gray>{BROADCAST}";

        @Description(" ")
        public Notification commandNotFound = Notification.chat("<red>✘ <dark_red>Command <red>{COMMAND} <dark_red>doesn't exists!");
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
        public Notification tpaSelfMessage = Notification.chat("<red>✘ <dark_red>You can't teleport to yourself!");
        public Notification tpaAlreadySentMessage = Notification.chat("<red>✘ <dark_red>You have already sent a teleportation request!");
        public Notification tpaSentMessage = Notification.chat("<green>► <white>You have sent a request for teleportation to a player: <green>{PLAYER}<white>!");

        @Description({
            " ",
            "# We used MiniMessages formatting in these messages",
            "# The current request acceptance message allows the player to click on it to accept the teleport request with MiniMessages!",
            "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({ " ", "# {PLAYER} - Player who sent the request to another player" })
        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<green>► <white>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!"),
            Notification.chat("<hover:show_text:'<green>Accept request for teleports?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept! <gray>(Click)</gray></click></gold></hover>"),
            Notification.chat("<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red><green>to deny! <gray>(Click)</gray></click></gold></hover>")
        );

        @Description(" ")
        public Notification tpaDenyNoRequestMessage = Notification.chat("<red>✘ <dark_red>You do not have a teleport request from this player!");

        @Description({ " ", "# {PLAYER} - Player who sent a request to teleport to another player" })
        public Notification tpaDenyDoneMessage = Notification.chat("<red>✘ <dark_red>You have declined a teleport request from a player: <red>{PLAYER}<dark_red>!");

        @Description({ " ", "# {PLAYER} - Player who declines the tpa request" })
        public Notification tpaDenyReceivedMessage = Notification.chat("<red>✘ <dark_red>Player: <red>{PLAYER} <dark_red>rejected your teleport request!");

        @Description(" ")
        public Notification tpaDenyAllDenied = Notification.chat("<red>► <dark_red>All players have denied your teleport request!");

        @Description({ " ", "# {PLAYER} - Player who sent tpa request to another player" })
        public Notification tpaAcceptMessage = Notification.chat("<green>► <white>You have accepted the teleportation from the player: <green>{PLAYER}<white>!");

        @Description(" ")
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<red>✘ <dark_red>This player has not sent you a teleport request!");

        @Description({ " ", "# {PLAYER} - Player who sent a request to teleport to another player" })
        public Notification tpaAcceptReceivedMessage = Notification.chat("<green>► <white>Player: <green>{PLAYER} <white>accepted your teleportation request!");

        @Description(" ")
        public Notification tpaAcceptAllAccepted = Notification.chat("<green>► <white>All players have accepted your teleport request!");
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
        public Notification warpAlreadyExists = Notification.chat("<red>✘ <dark_red>Warp <red>{WARP} <dark_red>already exists!");
        public Notification create = Notification.chat("<green>► <white>Warp <green>{WARP} <white>has been created.");
        public Notification remove = Notification.chat("<red>► <white>Warp <red>{WARP} <white>has been deleted.");
        public Notification notExist = Notification.chat("<red>► <dark_red>This warp doesn't exist");
        @Description({ " ", "# {WARPS} - List of warps (separated by commas)" })
        public Notification available = Notification.chat("<green>► <white>Available warps: <green>{WARPS}");


        @Description({ " ", "# Settings for warp inventory" })
        public ENWarpInventory warpInventory = new ENWarpInventory();

        @Getter
        @Contextual
        public static class ENWarpInventory implements WarpInventorySection {
            public String title = "<dark_gray>» <green>Available warps:";
            public int rows = 3;

            public Map<String, WarpInventoryItem> items = Map.of("default", WarpInventoryItem.builder()
                .withWarpName("default")
                .withWarpItem(WarpConfigItem.builder()
                    .withName("&8» &6Warp: &fdefault")
                    .withLore(Collections.singletonList("<gray>Click to teleport!"))
                    .withMaterial(Material.ENDER_PEARL)
                    .withSlot(10)
                    .withGlow(true)
                    .build())
                .build());

            public ENBorderSection border = new ENBorderSection();

            @Getter
            @Contextual
            public static class ENBorderSection implements BorderSection {
                public boolean enabled = true;

                public Material material = Material.GRAY_STAINED_GLASS_PANE;

                public BorderSection.FillType fillType = BorderSection.FillType.BORDER;

                public String name = "";

                public List<String> lore = Collections.emptyList();
            }
        }
    }

    @Description({
        " ",
        "# The following section is responsible for setting and editing personal fast travel points - home",
    })
    public ENHomeSection home = new ENHomeSection();

    @Getter
    @Contextual
    public static class ENHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<red>► <dark_red>This home doesn't exist");

        @Description({ " ", "# {HOME} - Home name" })
        public Notification create = Notification.chat("<green>► <white>Home <green>{HOME} <white>has been created.");
        public Notification delete = Notification.chat("<red>► <white>Home <red>{HOME} <white>has been deleted.");
        public Notification overrideHomeLocation = Notification.chat("<green>► <white>Home <green>{HOME} <white>has been overridden.");
        @Description({ " ", "# {LIMIT} - Homes limit" })
        public Notification limit = Notification.chat("<red>► <white>You have reached the limit of homes! Your limit is <red>{LIMIT}<white>.");

    }

    @Description({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENPrivateSection privateChat = new ENPrivateSection();

    @Getter
    @Contextual
    public static class ENPrivateSection implements PrivateChatSection {
        public Notification noReply = Notification.chat("<red>► <dark_red>You have no one to reply!");

        @Description("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({ " ", "# {SENDER} - Message sender, {MESSAGE} - Message" })
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notification socialSpyEnable = Notification.chat("<green>► <white>SocialSpy has been <green>enabled!");
        public Notification socialSpyDisable = Notification.chat("<red>► <white> SocialSpy has been <red>disabled!");

        @Description({ " ", "# {PLAYER} - Ignored player" })
        public Notification ignorePlayer = Notification.chat("<green>► {PLAYER} <white>player has been ignored!");

        @Description(" ")
        public Notification ignoreAll = Notification.chat("<red>► <dark_red>All players have been ignored!");
        public Notification cantIgnoreYourself = Notification.chat("<red>► <dark_red>You can't ignore yourself!");

        @Description({ " ", "# {PLAYER} - Ignored player." })
        public Notification alreadyIgnorePlayer = Notification.chat("<red>► <dark_red>You already ignore this player!");

        @Description("# {PLAYER} - Unignored player")
        public Notification unIgnorePlayer = Notification.chat("<red>► <dark_red>{PLAYER} <red>player has been uningored!");

        @Description(" ")
        public Notification unIgnoreAll = Notification.chat("<red>► <dark_red>All players have been uningored!");
        public Notification cantUnIgnoreYourself = Notification.chat("<red>► <dark_red>You can't unignore yourself!");

        @Description({ " ", "# {PLAYER} - Ignored player" })
        public Notification notIgnorePlayer = Notification.chat("<red>► <dark_red>>You don't ignore this player, so you can unignore him!");
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
        public Notification afkOn = Notification.chat("<green>► <white>{PLAYER} is AFK!");
        public Notification afkOff = Notification.chat("<green>► <white>{PLAYER} is no more AFK!");

        @Description({ " ", "# {TIME} - Time after the player can execute the command." })
        public Notification afkDelay = Notification.chat("<red>► <dark_red>You can use this command only after <red>{TIME}!");
    }

    @Description({
        " ",
        "# Section responsible for various server events."
    })
    public ENEventSection event = new ENEventSection();

    @Getter
    @Contextual
    public static class ENEventSection implements EventSection {
        @Description("# {PLAYER} - Killed player, {KILLER} - Killer")
        public List<Notification> deathMessage = List.of(
            Notification.chat("<white>☠ <dark_red>{PLAYER} <red>died!"),
            Notification.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}!")
        );

        @Description({
            "# EternalCore will pick a random message for the list below, every time the player do a various action.",
            "# If the {KILLER} not be found, the message will be picked from list if contains cause.",
            "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
        })
        public Map<EntityDamageEvent.DamageCause, List<Notification>> deathMessageByDamageCause = Map.of(
            EntityDamageEvent.DamageCause.VOID, Collections.singletonList(Notification.chat("<white>☠ <dark_red>{PLAYER} <red>fell into the void!")),
            EntityDamageEvent.DamageCause.FALL, Arrays.asList(
                Notification.chat("<white>☠ <dark_red>{PLAYER} <red>fell from a high place!"),
                Notification.chat("<white>☠ <dark_red>{PLAYER} <red>fell off a deadly cliff!")
            )
        );

        @Description({ "", "# {PLAYER} - Player who joined" })
        public List<Notification> joinMessage = List.of(
            Notification.chat("<green>► <green>{PLAYER} <white>joined the server!"),
            Notification.chat("<green>► <white>Welcome to the server <green>{PLAYER}<white>!")
        );

        @Description("# {PLAYER} - Player who joined.")
        public List<Notification> firstJoinMessage = List.of(
            Notification.chat("<green>► {PLAYER} <white>joined the server for the first time!"),
            Notification.chat("<green>► {PLAYER} <white>welcome to the server for the first time!")
        );

        @Description("# {PLAYER} - Player who left")
        public List<Notification> quitMessage = List.of(
            Notification.chat("<red>► {PLAYER} <white>logged off the server!"),
            Notification.chat("<red>► {PLAYER} <white>left the server!")
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
        public Notification inventoryClearMessage = Notification.chat("<green>► <white>Your inventory has been cleared!");

        @Description({ " ", "# {PLAYER} - Target player" })
        public Notification inventoryClearMessageBy = Notification.chat("<green>► <white>Player <green>{PLAYER} <white>inventory cleared");

        @Description(" ")
        public Notification cantOpenYourInventory = Notification.chat("<red>► <dark_red>You can't open your own inventory!");
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
        public Notification feedMessage = Notification.chat("<green>► <white>You've been fed!");

        @Description(" # {PLAYER} - Player who has been fed")
        public Notification feedMessageBy = Notification.chat("<green>► <white>You've fed the <green>{PLAYER}");

        @Description(" ")
        public Notification healMessage = Notification.chat("<green>► <white>You've been healed!");

        @Description("# {PLAYER} - Healed player")
        public Notification healMessageBy = Notification.chat("<green>► <white>Healed <green>{PLAYER}");

        @Description(" ")
        public Notification killSelf = Notification.chat("<red>► <dark_red>You killed yourself!");
        @Description("# {PLAYER} - Killed player")
        public Notification killedMessage = Notification.chat("<red>► <dark_red>Killed <red>{PLAYER}");

        @Description(" ")
        public Notification speedBetweenZeroAndTen = Notification.chat("<red>✘ <dark_red>Enter speed from 0 to 10!");
        public Notification speedTypeNotCorrect = Notification.chat("<red>✘ <dark_red>Invalid speed type!");

        @Description("# {SPEED} - Walk or fly speed value")
        public Notification speedWalkSet = Notification.chat("<green>► <white>Walking speed is set to <green>{SPEED}");
        public Notification speedFlySet = Notification.chat("<green>► <white>Flying speed is set to <green>{SPEED}");

        @Description("# {PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        public Notification speedWalkSetBy = Notification.chat("<green>► <white>Walking speed for <green>{PLAYER} <white>is set to <green>{SPEED}");
        public Notification speedFlySetBy = Notification.chat("<green>► <white>Flying speed for <green>{PLAYER} <white>is set to <green>{SPEED}");

        @Description({ " ", "# {STATE} - Godmode status" })
        public Notification godMessage = Notification.chat("<green>► <white>God is now {STATE}");

        @Description({ " ", "# {PLAYER} - Target player, {STATE} - Target player godmode status" })
        public Notification godSetMessage = Notification.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");

        @Description({ " ", "# {STATE} - Fly status" })
        public Notification flyMessage = Notification.chat("<green>► <white>Fly is now {STATE}");

        @Description("# {PLAYER} - Target player, {STATE} - Target player fly status")
        public Notification flySetMessage = Notification.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");

        @Description({ " ", "# {PING} - Current ping" })
        public Notification pingMessage = Notification.chat("<green>► <white>Your ping is: <green>{PING}<white>ms");

        @Description("# {PLAYER} - Target player, {PING} - Ping of target player")
        public Notification pingOtherMessage = Notification.chat("<green>► <white>Ping of the <green>{PLAYER} <white>is: <green>{PING}<white>ms");

        @Description(" ")
        public Notification gameModeNotCorrect = Notification.chat("<red>✘ <dark_red>Not a valid gamemode type");

        @Description("# {GAMEMODE} - Gamemode name")
        public Notification gameModeMessage = Notification.chat("<green>► <white>Gamemode now is set to: <green>{GAMEMODE}");

        @Description("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
        public Notification gameModeSetMessage = Notification.chat("<green>► <white>Gamemode for <green>{PLAYER} <white>now is set to: <green>{GAMEMODE}");

        @Description({ " ", "# {ONLINE} - Number of online players" })
        public Notification onlinePlayersCountMessage = Notification.chat("<green>► <white>On server now is: <green>{ONLINE} <white>players!");

        @Description("# {ONLINE} - Current online players, {PLAYERS} - Player list")
        public Notification onlinePlayersMessage = Notification.chat("<green>► <white>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS}");

        public List<String> fullServerSlots = List.of(
            " ",
            "<red>✘ <dark_red>Server is full!",
            "<red>✘ <dark_red>Buy rank on our site!"
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
        public List<String> whoisCommand = List.of("<green>► <white>Target name: <green>{PLAYER}",
            "<green>► <white>Target UUID: <green>{UUID}",
            "<green>► <white>Target address: <green>{IP}",
            "<green>► <white>Target walk speed: <green>{WALK-SPEED}",
            "<green>► <white>Target fly speed: <green>{SPEED}",
            "<green>► <white>Target ping: <green>{PING}<white>ms",
            "<green>► <white>Target level: <green>{LEVEL}",
            "<green>► <white>Target health: <green>{HEALTH}",
            "<green>► <white>Target food level: <green>{FOOD}"
        );

        @Description({ " ", "# {KILLED} - Number of killed mobs" })
        public Notification butcherCommand = Notification.chat("<green>► <white>You killed <green>{KILLED} <white>mobs!");

        @Description({ " ", "# {SAFE_CHUNKS} - The number of safe chunks" })
        public Notification safeChunksMessage = Notification.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    }

    @Description({ " ", "# This section is responsible for spawn-related stuff." })
    public ENSpawnSection spawn = new ENSpawnSection();

    @Getter
    @Contextual
    public static class ENSpawnSection implements SpawnSection {
        public Notification spawnSet = Notification.chat("<green>► <white>Spawn set!");
        public Notification spawnNoSet = Notification.chat("<red>✘ <dark_red>Spawn is not set!");
        @Description({ " ", "# {PLAYER} - Player who teleported you to spawn" })
        public Notification spawnTeleportedBy = Notification.chat("<green>► <white>You have been teleported to spawn by <green>{PLAYER}<white>!");
        @Description({ " ", "# {PLAYER} - Teleported player" })
        public Notification spawnTeleportedOther = Notification.chat("<green>► <white>You teleported player <green>{PLAYER} <white>to spawn!");
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
        public Notification itemChangeNameMessage = Notification.chat("<green>► <white>Name has been changed to: <green>{ITEM_NAME}");

        @Description(" ")
        public Notification itemClearNameMessage = Notification.chat("<green>► <white>Name has been cleared!");

        @Description({ " ", "# {ITEM_LORE} - New item lore" })
        public Notification itemChangeLoreMessage = Notification.chat("<green>► <white>Lore has been changed to: <green>{ITEM_LORE}");

        @Description(" ")
        public Notification itemClearLoreMessage = Notification.chat("<green>► <white>Lore has been cleared!");

        @Description({ " ", "# {ITEM_FLAG} - Flag name" })
        public Notification itemFlagRemovedMessage = Notification.chat("<green>► <white>Flag <green>{ITEM_FLAG} <white>has been removed!");
        public Notification itemFlagAddedMessage = Notification.chat("<green>► <white>Flag <green>{ITEM_FLAG} <white>has been added!");

        @Description(" ")
        public Notification itemFlagClearedMessage = Notification.chat("<green>► <white>Flags have been cleared!");

        @Description({ " ", "# {ITEM} - Name of received item" })
        public Notification giveReceived = Notification.chat("<green>► <white>You have received: <green>{ITEM}");

        @Description({ " ", "# {PLAYER} - Name of item receiver, {ITEM} - the item" })
        public Notification giveGiven = Notification.chat("<green>► <white>Player <green>{PLAYER} <white>has received <green>{ITEM}");

        @Description(" ")
        public Notification giveNotItem = Notification.chat("<green>► <white>Not a valid obtainable item!");
        public Notification repairMessage = Notification.chat("<green>► <white>Repaired!");

        @Description({ " ", "# {SKULL} - Name of the skull owner" })
        public Notification skullMessage = Notification.chat("<green>► <white>Player <green>{SKULL} <white>heads received");

        @Description(" ")
        public Notification enchantedMessage = Notification.chat("<green>► <white>Item in hand is enchanted!");
    }


    @Description({ " ", "# Messages sent on time and weather change." })
    public ENTimeAndWeatherMessageSection timeAndWeather = new ENTimeAndWeatherMessageSection();

    @Getter
    @Contextual
    public static class ENTimeAndWeatherMessageSection implements TimeAndWeatherSection {
        @Description("# {WORLD} - World name")
        public Notification timeSetDay = Notification.chat("<green>► <white>Time set to day in the <green>{WORLD}<white>!");
        public Notification timeSetNight = Notification.chat("<green>► <white>Time set to night in the <green>{WORLD}<white>!");

        @Description("# {TIME} - Changed time in ticks")
        public Notification timeSet = Notification.chat("<green>► <white>Time set to <green>{TIME}");
        public Notification timeAdd = Notification.chat("<green>► <white>Time added <green>{TIME}");

        @Description("# {WORLD} - World name")
        public Notification weatherSetRain = Notification.chat("<green>► <white>Weather set to rain in the <green>{WORLD}<white>!");
        public Notification weatherSetSun = Notification.chat("<green>► <white>Weather set to sun in the <green>{WORLD}<white>!");
        public Notification weatherSetThunder = Notification.chat("<green>► <white>Weather set to thunder in the <green>{WORLD}<white>!");
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

        public Notification genericContainerOpenedBy = Notification.disabled("<green>► <white>The specified container has been opened by <green>{PLAYER}<white>!");
        public Notification genericContainerOpenedFor = Notification.disabled("<green>► <white>The specified container has been opened for <green>{PLAYER}<white>!");
    }

    @Description({ " ", "# Information sent, when the language is changed to English" })
    public ENLanguageSection language = new ENLanguageSection();

    @Getter
    @Contextual
    public static class ENLanguageSection implements LanguageSection {
        public Notification languageChanged = Notification.chat("<green>► <white>Language changed to <green>English<white>!");

    }
}
