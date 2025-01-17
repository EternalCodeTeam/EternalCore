package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
        "# The new notification system supports various formats and options:",
        "#",
        "# Examples:",
        "#",
        "# example: []",
        "#",
        "# example: \"Hello world\"",
        "#",
        "# example:",
        "#   - \"Hello\"",
        "#   - \"world\"",
        "#",
        "# example:",
        "#   title: \"Hello world\"",
        "#   subtitle: \"Subtitle\"",
        "#   times: \"1s 2s 1s\"",
        "#",
        "# example:",
        "#   subtitle: \"Subtitle\"",
        "#   chat: \"Death message\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   chat:",
        "#     - \"Hello\"",
        "#     - \"world\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html ",
        "#   sound: \"ENTITY_PLAYER_LEVELUP 2.0 1.0\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # Sound categories: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/SoundCategory.html",
        "#   sound: \"ENTITY_PLAYER_LEVELUP WEATHER 2.0 1.0\" # If you want to play a sound in a certain category, for example if a player has the sound category \"WEATHER\" in the game settings set to 0%, the sound will not play.",
        "#",
        "# example:",
        "#   titleHide: true # This clearing the title from the screen, example if other plugin send title you can clear it with this for any action",
        "#",
        " "
    })
    @Description("# This section is responsible for all messages used during bad of a command argument")
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - Required permission")
        public Notice permissionMessage = Notice.chat("<red>✘ <dark_red>You don't have permission to perform this command! <red>({PERMISSIONS})");

        @Description({" ", "# {USAGE} - Correct usage"})
        public Notice usageMessage = Notice.chat("<gold>✘ <white>Correct usage: <gold>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<green>► <white>Correct usage:");
        public Notice usageMessageEntry = Notice.chat("<green>► <white>{USAGE}");

        @Description(" ")
        public Notice missingPlayerName = Notice.chat("<red>✘ <dark_red>You must provide a player name!");
        public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>This player is currently offline!");
        public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Command is only for players!");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>The number must be greater than or equal to 0!");
        public Notice noItem = Notice.chat("<red>✘ <dark_red>You need item to use this command!");
        public Notice noMaterial = Notice.chat("<red>✘ <dark_red>This item doesn't exist");
        public Notice noArgument = Notice.chat("<red>✘ <dark_red>This argument doesn't exist");
        public Notice noDamaged = Notice.chat("<red>✘ <dark_red>This item can't be repaired");
        public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>You need damaged items to use this command!");
        public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>This enchantment doesn't exist");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
        public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>Invalid time format!");
        public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>World <red>{WORLD} <dark_red>doesn't exist!");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
        public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Incorrect location format! <red>({LOCATION})");
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
        public Notice format = Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<dark_gray>: <white>{TEXT}");
        @Description(" ")
        public Notice send = Notice.chat("<green>► <white>This message has been successfully sent to administration");
        @Description("# {TIME} - Time to next use (cooldown)")
        public Notice helpOpDelay = Notice.chat("<gold>✘ <red>You can use this command for: <gold>{TIME}");
    }

    @Description({
        " ",
        "# This section is responsible for the communication between administration",
    })
    public ENAdminChatSection adminChat = new ENAdminChatSection();

    @Getter
    @Contextual
    public static class ENAdminChatSection implements AdminChatSection {
        @Description({"# {PLAYER} - Player who sent message on adminchat, {TEXT} - message"})
        public Notice format = Notice.chat("<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");
    }

    @Description({
        " ",
        "# This section is responsible for the messages of the /sudo command",
    })
    public ENSudoSection sudo = new ENSudoSection();

    @Getter
    @Contextual
    public static class ENSudoSection implements SudoSection {
        @Description({"# {PLAYER} - Player who executed the command, {COMMAND} - Command that the player executed"})
        public Notice sudoMessageSpy = Notice.chat("<dark_gray>[<dark_red>Sudo<dark_gray>] <red>{PLAYER}<dark_gray> executed command: <white>{COMMAND}");
        public Notice sudoMessage = Notice.chat("<green>► <white>You executed command: <green>{COMMAND} <white>on player: <green>{PLAYER}");
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
        @Description({"# {PLAYER} - Teleported players"})
        public Notice teleportedToPlayer = Notice.chat("<green>► <white>Successfully teleported to <green>{PLAYER}<white>!");

        @Description({"# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred"})
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>► <white>Successfully teleported <green>{PLAYER} <white>to <green>{ARG-PLAYER}<white>!");

        @Description({"# {Y} - Y coordinate of the highest block"})
        public Notice teleportedToHighestBlock = Notice.chat("<green>► <white>Teleported successfully to the highest block! (Y: {Y})");

        // Task
        @Description({"# {TIME} - Teleportation time"})
        public Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleporting in <green>{TIME}");
        @Description(" ")
        public Notice teleported = Notice.chat("<green>► <white>Teleported!");
        public Notice teleporting = Notice.chat("<green>► <white>Teleporting...");
        public Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>You've moved, teleportation canceled!");
        public Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>You are in teleport!");

        // Coordinates XYZ
        @Description({" ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedToCoordinates = Notice.chat("<green>► <white>Teleported to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Description({" ", "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>► <white>Teleported <green>{PLAYER} <white>to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Description(" ")
        public Notice teleportedToLastLocation = Notice.chat("<green>► <white>Teleported to the last location!");
        @Description({" ", "# {PLAYER} - Player who has been teleported"})
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Teleported <green>{PLAYER} <white>to the last location!");
        @Description(" ")
        public Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Last location is not exist!");

        @Description(" ")
        public Notice randomPlayerNotFound = Notice.chat("<red>✘ <dark_red>No player found to teleport!");
        @Description({" ", "# {PLAYER} - The player you were teleported"})
        public Notice teleportedToRandomPlayer = Notice.chat("<green>► <white>Teleported to random player <green>{PLAYER}<white>!");
    }

    @Description({
        " ",
        "# This section is responsible for messages related to random teleport",
    })
    public ENRandomTeleportSection randomTeleport = new ENRandomTeleportSection();

    @Getter
    @Contextual
    public static class ENRandomTeleportSection implements RandomTeleportSection {
        public Notice randomTeleportStarted = Notice.builder()
            .chat("<green>► <white>Teleportation to a random location has started!")
            .title("<green>Random teleport")
            .subtitle("<white>Searching, please wait...")
            .build();

        public Notice randomTeleportFailed = Notice.chat("<red>✘ <dark_red>A safe location could not be found, please try again!");


        @Description({"# {PLAYER} - Player who has been teleported, {WORLD} - World name, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedToRandomLocation = Notice.chat("<green>► <white>You have been teleported to a random location!");

        @Description(" ")
        public Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat("<green>► <white>You have teleported <green>{PLAYER} <white>to a random location! His current location is: world: {WORLD}, x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}.");

        @Description({" ", "# {TIME} - Time to next use (cooldown)"})
        public Notice randomTeleportDelay = Notice.chat("<red>✘ <dark_red>You can use random teleport after <red>{TIME}!");

    }

    public ENChatSection chat = new ENChatSection();

    @Getter
    @Contextual
    public static class ENChatSection implements ChatSection {
        @Description({"# {PLAYER} - Player who performed the actions for the chat"})
        public Notice disabled = Notice.chat("<green>► <white>Chat has been disabled by <green>{PLAYER}<white>!");
        public Notice enabled = Notice.chat("<green>► <white>The chat has been enabled by <green>{PLAYER}<white>!");
        public Notice cleared = Notice.chat("<green>► <white>Chat has been cleared by <green>{PLAYER}<white>!");

        @Description(" ")
        public Notice alreadyDisabled = Notice.chat("<red>✘ <dark_red>Chat already off!");
        public Notice alreadyEnabled = Notice.chat("<red>✘ <dark_red>Chat already on!");

        @Description({" ", "# {SLOWMODE} - Time for next message"})
        public Notice slowModeSet = Notice.chat("<green>► <white>SlowMode set to: {SLOWMODE}");

        @Description({" ", "# {PLAYER} - Player who performed the actions for the chat"})
        public Notice slowModeOff = Notice.chat("<green>► <white>SlowMode has been disabled by <green>{PLAYER}<white>!");

        @Description({" ", "# {TIME} - Time to next use (cooldown)"})
        public Notice slowMode = Notice.chat("<red>✘ <dark_red>You can write the next message for: <red>{TIME}<dark_red>!");

        @Description(" ")
        public Notice disabledChatInfo = Notice.chat("<red>✘ <dark_red>Chat is currently disabled!");

        @Description({" ", "# {BROADCAST} - Broadcast"})
        public String alertMessageFormat = "<red><bold>BROADCAST: <gray>{BROADCAST}";

        @Description(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Command <red>{COMMAND} <dark_red>doesn't exists!");

        @Description({" ", "# {PLAYER} - Player who received the message", "# {MESSAGE} - message content", "# {TYPE} - message type"})
        public Notice tellrawInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>{PLAYER} <white>with the content: {MESSAGE}");
        public Notice tellrawAllInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>all <white>with the content: {MESSAGE}");

        public Notice tellrawSaved = Notice.chat("<green>► <white>Message saved in queue!");
        public Notice tellrawNoSaved = Notice.chat("<red>✘ <dark_red>No messages saved in queue!");
        public Notice tellrawMultipleSent = Notice.chat("<green>► <white>Messages sent! Message que has been cleared!");
        public Notice tellrawCleared = Notice.chat("<green>► <white>Message queue cleared!");
        public Notice alertQueueAdded = Notice.chat("<green>► <white>Message added to the queue!");
        public Notice alertQueueRemoved = Notice.chat("<green>► <white>Message removed from the queue!");
        public Notice alertQueueCleared = Notice.chat("<green>► <white>Message queue cleared!");
        public Notice alertQueueEmpty = Notice.chat("<red>✘ <dark_red>Error: <red>The message queue is empty!");
        public Notice alertQueueSent = Notice.chat("<green>► <white>All messages sent from the queue!");

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
        public Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>You can't teleport to yourself!");
        public Notice tpaAlreadySentMessage = Notice.chat("<red>✘ <dark_red>You have already sent a teleportation request!");
        public Notice tpaSentMessage = Notice.chat("<green>► <white>You have sent a request for teleportation to a player: <green>{PLAYER}<white>!");

        @Description({
            " ",
            "# We used MiniMessages formatting in these messages",
            "# The current request acceptance message allows the player to click on it to accept the teleport request with MiniMessages!",
            "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({" ", "# {PLAYER} - Player who sent the request to another player"})
        public Notice tpaReceivedMessage = Notice.builder()
            .chat("<green>► <white>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!")
            .chat("<hover:show_text:'<green>Accept request for teleports?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept! <gray>(Click)</gray></click></gold></hover>")
            .chat("<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red><green>to deny! <gray>(Click)</gray></click></gold></hover>")
            .build();

        @Description(" ")
        public Notice tpaDenyNoRequestMessage = Notice.chat("<red>✘ <dark_red>You do not have a teleport request from this player!");

        @Description({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
        public Notice tpaDenyDoneMessage = Notice.chat("<red>✘ <dark_red>You have declined a teleport request from a player: <red>{PLAYER}<dark_red>!");

        @Description({" ", "# {PLAYER} - Player who declines the tpa request"})
        public Notice tpaDenyReceivedMessage = Notice.chat("<red>✘ <dark_red>Player: <red>{PLAYER} <dark_red>rejected your teleport request!");

        @Description(" ")
        public Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>All players have denied your teleport request!");

        @Description({" ", "# {PLAYER} - Player who sent tpa request to another player"})
        public Notice tpaAcceptMessage = Notice.chat("<green>► <white>You have accepted the teleportation from the player: <green>{PLAYER}<white>!");

        @Description(" ")
        public Notice tpaAcceptNoRequestMessage = Notice.chat("<red>✘ <dark_red>This player has not sent you a teleport request!");

        @Description({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
        public Notice tpaAcceptReceivedMessage = Notice.chat("<green>► <white>Player: <green>{PLAYER} <white>accepted your teleportation request!");

        @Description(" ")
        public Notice tpaAcceptAllAccepted = Notice.chat("<green>► <white>All players have accepted your teleport request!");

        @Description(" ")
        public Notice tpaTargetIgnoresYou = Notice.chat("<green>► <red>{PLAYER} <white>is ignoring you!");
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
        public Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp <red>{WARP} <dark_red>already exists!");
        public Notice create = Notice.chat("<green>► <white>Warp <green>{WARP} <white>has been created.");
        public Notice remove = Notice.chat("<red>► <white>Warp <red>{WARP} <white>has been deleted.");
        public Notice notExist = Notice.chat("<red>► <dark_red>This warp doesn't exist");
        public Notice itemAdded = Notice.chat("<green>► <white>Warp has been added to GUI!");
        public Notice noWarps = Notice.chat("<red>✘ <dark_red>There are no warps!");
        public Notice itemLimit = Notice.chat("<red>✘ <dark_red>You have reached the limit of warps! Your limit is <red>{LIMIT}<dark_red>.");
        public Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have permission to use this warp ({WARP})!");
        public Notice addPermissions = Notice.chat("<green>► <white>Added permissions to warp <green>{WARP}<white>!");
        public Notice removePermission = Notice.chat("<red>► <white>Removed permission <red>{PERMISSION}</red> <white>from warp <red>{WARP}<white>!");
        public Notice noPermissionsProvided = Notice.chat("<red>✘ <dark_red>No permissions provided!");
        public Notice permissionDoesNotExist = Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>doesn't exist!");
        public Notice permissionAlreadyExist = Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>already exists!");
        public Notice noPermissionAssigned = Notice.chat("<red>✘ <red>There are no permissions assigned to this warp!");
        public Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>You must provide a warp name!");
        public Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>You must provide a permission!");

        @Description({" ", "# {WARPS} - List of warps (separated by commas)"})
        public Notice available = Notice.chat("<green>► <white>Available warps: <green>{WARPS}");

        @Description({" ", "# Settings for warp inventory"})
        public ENWarpInventory warpInventory = new ENWarpInventory();

        @Getter
        @Contextual
        public static class ENWarpInventory implements WarpInventorySection {
            public String title = "<dark_gray>» <green>Available warps:";

            @Description({" ", "# Warps located inside GUI inventory can be customized here. More warps will be added on creation with /setwarp command. "})
            public Map<String, WarpInventoryItem> items = new HashMap<>();

            public void setItems(Map<String, WarpInventoryItem> items) {
                this.items = items;
            }

            public ENBorderSection border = new ENBorderSection();
            public ENDecorationItemsSection decorationItems = new ENDecorationItemsSection();

            @Getter
            @Contextual
            public static class ENBorderSection implements BorderSection {

                @Description({" ", "# Changes of border section may affect the appearance of the GUI inventory, after changes adjust slots of existing items."})
                public boolean enabled = true;

                public Material material = Material.GRAY_STAINED_GLASS_PANE;

                public BorderSection.FillType fillType = BorderSection.FillType.BORDER;

                public String name = "";

                public List<String> lore = Collections.emptyList();
            }

            @Getter
            @Contextual
            public static class ENDecorationItemsSection implements DecorationItemsSection {
                public List<ConfigItem> items = List.of();
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
        @Description("# {HOMES} - List of homes (separated by commas)")
        public Notice homeList = Notice.chat("<green>► <white>Available homes: <green>{HOMES}");

        @Description({" ", "# {HOME} - Home name"})
        public Notice create = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created.");
        public Notice delete = Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted.");
        public Notice overrideHomeLocation = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden.");
        @Description({" ", "# {LIMIT} - Homes limit"})
        public Notice limit = Notice.chat("<red>► <white>You have reached the limit of homes! Your limit is <red>{LIMIT}<white>.");
        public Notice noHomesOwned = Notice.chat("<dark_red>✘ <red>You don't have any homes.");

        @Description({" ", "# Placeholders messages"})
        public String noHomesOwnedPlaceholder = "You don't have any homes.";

        @Description({
            " ",
            "# Home Admin Section, you can edit player homes as admin",
            "# {HOME} - Home name, {PLAYER} - Player name, {HOMES} - List of homes (separated by commas)"
        })
        public Notice overrideHomeLocationAsAdmin = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden for <green>{PLAYER}<white>.");
        public Notice playerNoOwnedHomes = Notice.chat("<dark_red>✘ <red>Player <dark_red>{PLAYER} <red>doesn't have any homes.");
        public Notice createAsAdmin = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created for <green>{PLAYER}<white>.");
        public Notice deleteAsAdmin = Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted for <red>{PLAYER}<white>.");
        public Notice homeListAsAdmin = Notice.chat("<green>► <white>Available homes for <green>{PLAYER}<white>: <green>{HOMES}");
    }

    @Description({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENPrivateSection privateChat = new ENPrivateSection();

    @Getter
    @Contextual
    public static class ENPrivateSection implements PrivateChatSection {
        public Notice noReply = Notice.chat("<red>► <dark_red>You have no one to reply!");

        @Description("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
        public Notice privateMessageYouToTarget = Notice.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({" ", "# {SENDER} - Message sender, {MESSAGE} - Message"})
        public Notice privateMessageTargetToYou = Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
        public Notice socialSpyMessage = Notice.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy has been {STATE}<white>!");
        public Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy has been {STATE}<white>!");


        public Notice msgToggledOff = Notice.chat("<red>► <dark_red>This player has disabled private messages!");

        public Notice msgToggleSelfOff = Notice.chat("<green>► <white>Private messages have been <red>disabled<white>!");
        public Notice msgToggleSelfOn = Notice.chat("<green>► <white>Private messages have been <green>enabled<white>!");

        @Description("# {PLAYER} - Player")
        public Notice msgTogglePlayerOff = Notice.chat("<green>► <white>Private messages have been disabled for <green>{PLAYER}<white>!");
        public Notice msgTogglePlayerOn = Notice.chat("<green>► <white>Private messages have been enabled for <green>{PLAYER}<white>!");



        @Description({" ", "# {PLAYER} - Ignored player"})
        public Notice ignorePlayer = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

        @Description(" ")
        public Notice ignoreAll = Notice.chat("<red>► <dark_red>All players have been ignored!");
        public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

        @Description({" ", "# {PLAYER} - Ignored player."})
        public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>You already ignore this player!");

        @Description("# {PLAYER} - Unignored player")
        public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been uningored!");

        @Description(" ")
        public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>All players have been uningored!");
        public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

        @Description({" ", "# {PLAYER} - Ignored player"})
        public Notice notIgnorePlayer = Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
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
        public Notice afkOn = Notice.chat("<green>► <white>{PLAYER} is AFK!");
        public Notice afkOff = Notice.chat("<green>► <white>{PLAYER} is no more AFK!");

        @Description({" ", "# {TIME} - Time after the player can execute the command."})
        public Notice afkDelay = Notice.chat("<red>► <dark_red>You can use this command only after <red>{TIME}!");

        @Description({ " " })
        public String afkKickReason = "<red>You have been kicked due to inactivity!";

        @Description({" ", "# Placeholder used in %eternalcore_afk_formatted% to indicate AFK status"})
        public String afkEnabledPlaceholder = "<red><b>AFK";
        public String afkDisabledPlaceholder = "";
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
        public List<Notice> deathMessage = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}!")
        );

        @Description({
            "# EternalCore will pick a random message for the list below, every time the player do a various action.",
            "# If the {KILLER} not be found, the message will be picked from list if contains cause.",
            "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
        })
        public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
            EntityDamageEvent.DamageCause.VOID, Collections.singletonList(Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell into the void!")),
            EntityDamageEvent.DamageCause.FALL, Arrays.asList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell from a high place!"),
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell off a deadly cliff!")
            )
        );

        public List<Notice> unknownDeathCause = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died!")
        );

        @Description({"", "# {PLAYER} - Player who joined"})
        public List<Notice> joinMessage = List.of(
            Notice.actionbar("<green>► <green>{PLAYER} <white>joined the server!"),
            Notice.actionbar("<green>► <white>Welcome to the server <green>{PLAYER}<white>!")
        );

        @Description("# {PLAYER} - Player who joined.")
        public List<Notice> firstJoinMessage = List.of(
            Notice.chat("<green>► {PLAYER} <white>joined the server for the first time!"),
            Notice.chat("<green>► {PLAYER} <white>welcome to the server for the first time!")
        );

        @Description("# {PLAYER} - Player who left")
        public List<Notice> quitMessage = List.of(
            Notice.actionbar("<red>► {PLAYER} <white>logged off the server!"),
            Notice.actionbar("<red>► {PLAYER} <white>left the server!")
        );

        @Description({" ", "# {PLAYER} - Player who joined"})
        public Notice welcome = Notice.title("<yellow>EternalCode.pl", "<yellow>Welcome back to the server!");
    }


    @Description({" ", "# Section responsible for inventories-related stuff."})
    public ENInventorySection inventory = new ENInventorySection();

    @Getter
    @Contextual
    public static class ENInventorySection implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>► <white>Your inventory has been cleared!");

        @Description({" ", "# {PLAYER} - Target player"})
        public Notice inventoryClearMessageBy = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>inventory cleared");

        @Description(" ")
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
        public Notice feedMessage = Notice.chat("<green>► <white>You've been fed!");

        @Description(" # {PLAYER} - Player who has been fed")
        public Notice feedMessageBy = Notice.chat("<green>► <white>You've fed the <green>{PLAYER}");

        @Description(" ")
        public Notice healMessage = Notice.chat("<green>► <white>You've been healed!");

        @Description("# {PLAYER} - Healed player")
        public Notice healMessageBy = Notice.chat("<green>► <white>Healed <green>{PLAYER}");

        @Description(" ")
        public Notice killSelf = Notice.chat("<red>► <dark_red>You killed yourself!");
        @Description("# {PLAYER} - Killed player")
        public Notice killedMessage = Notice.chat("<red>► <dark_red>Killed <red>{PLAYER}");

        @Description(" ")
        public Notice speedBetweenZeroAndTen = Notice.chat("<red>✘ <dark_red>Enter speed from 0 to 10!");
        public Notice speedTypeNotCorrect = Notice.chat("<red>✘ <dark_red>Invalid speed type!");

        @Description("# {SPEED} - Walk or fly speed value")
        public Notice speedWalkSet = Notice.chat("<green>► <white>Walking speed is set to <green>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>► <white>Flying speed is set to <green>{SPEED}");

        @Description("# {PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        public Notice speedWalkSetBy = Notice.chat("<green>► <white>Walking speed for <green>{PLAYER} <white>is set to <green>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>► <white>Flying speed for <green>{PLAYER} <white>is set to <green>{SPEED}");

        @Description({" ", "# {STATE} - Godmode status"})
        public Notice godEnable = Notice.chat("<green>► <white>God is now {STATE}");
        public Notice godDisable = Notice.chat("<green>► <white>God is now {STATE}");

        @Description({" ", "# {PLAYER} - Target player, {STATE} - Target player godmode status"})
        public Notice godSetEnable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");
        public Notice godSetDisable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");

        @Description({" ", "# {STATE} - Fly status"})
        public Notice flyEnable = Notice.chat("<green>► <white>Fly is now {STATE}");
        public Notice flyDisable = Notice.chat("<green>► <white>Fly is now {STATE}");

        @Description("# {PLAYER} - Target player, {STATE} - Target player fly status")
        public Notice flySetEnable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
        public Notice flySetDisable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");

        @Description({" ", "# {PING} - Current ping"})
        public Notice pingMessage = Notice.chat("<green>► <white>Your ping is: <green>{PING}<white>ms");

        @Description("# {PLAYER} - Target player, {PING} - Ping of target player")
        public Notice pingOtherMessage = Notice.chat("<green>► <white>Ping of the <green>{PLAYER} <white>is: <green>{PING}<white>ms");

        @Description(" ")
        public Notice gameModeNotCorrect = Notice.chat("<red>✘ <dark_red>Not a valid gamemode type");

        @Description("# {GAMEMODE} - Gamemode name")
        public Notice gameModeMessage = Notice.chat("<green>► <white>Gamemode now is set to: <green>{GAMEMODE}");

        @Description("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
        public Notice gameModeSetMessage = Notice.chat("<green>► <white>Gamemode for <green>{PLAYER} <white>now is set to: <green>{GAMEMODE}");

        @Description({" ", "# {ONLINE} - Number of online players"})
        public Notice onlinePlayersCountMessage = Notice.chat("<green>► <white>On server now is: <green>{ONLINE} <white>players!");

        @Description("# {ONLINE} - Current online players, {PLAYERS} - Player list")
        public Notice onlinePlayersMessage = Notice.chat("<green>► <white>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS}");

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

        @Description({" ", "# {KILLED} - Number of killed mobs"})
        public Notice butcherCommand = Notice.chat("<green>► <white>You killed <green>{KILLED} <white>mobs!");

        @Description({" ", "# {SAFE_CHUNKS} - The number of safe chunks"})
        public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    }

    @Description({" ", "# This section is responsible for spawn-related stuff."})
    public ENSpawnSection spawn = new ENSpawnSection();

    @Getter
    @Contextual
    public static class ENSpawnSection implements SpawnSection {
        public Notice spawnSet = Notice.chat("<green>► <white>Spawn set!");
        public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Spawn is not set!");
        @Description({" ", "# {PLAYER} - Player who teleported you to spawn"})
        public Notice spawnTeleportedBy = Notice.chat("<green>► <white>You have been teleported to spawn by <green>{PLAYER}<white>!");
        @Description({" ", "# {PLAYER} - Teleported player"})
        public Notice spawnTeleportedOther = Notice.chat("<green>► <white>You teleported player <green>{PLAYER} <white>to spawn!");
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
        public Notice itemChangeNameMessage = Notice.chat("<green>► <white>Name has been changed to: <green>{ITEM_NAME}");

        @Description(" ")
        public Notice itemClearNameMessage = Notice.chat("<green>► <white>Name has been cleared!");

        @Description({" ", "# {ITEM_LORE} - New item lore"})
        public Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Lore has been changed to: <green>{ITEM_LORE}");

        @Description(" ")
        public Notice itemClearLoreMessage = Notice.chat("<green>► <white>Lore has been cleared!");

        @Description({" ", "# {ITEM_FLAG} - Flag name"})
        public Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Flag <green>{ITEM_FLAG} <white>has been removed!");
        public Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Flag <green>{ITEM_FLAG} <white>has been added!");

        @Description(" ")
        public Notice itemFlagClearedMessage = Notice.chat("<green>► <white>Flags have been cleared!");

        @Description({" ", "# {ITEM} - Name of received item"})
        public Notice giveReceived = Notice.chat("<green>► <white>You have received: <green>{ITEM}");

        @Description({" ", "# {PLAYER} - Name of item receiver, {ITEM} - the item"})
        public Notice giveGiven = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has received <green>{ITEM}");
        public Notice giveNoSpace = Notice.chat("<red>✘ <dark_red>Not enough space in inventory!");

        @Description(" ")
        public Notice giveNotItem = Notice.chat("<green>► <white>Not a valid obtainable item!");
        public Notice repairMessage = Notice.chat("<green>► <white>Repaired held item!");
        public Notice repairAllMessage = Notice.chat("<green>► <white>Repaired all items!");

        @Description({" ", "# {TIME} - Time to next use (cooldown)"})
        public Notice repairDelayMessage = Notice.chat("<red>✘ <dark_red>You can use this command after <red>{TIME}!");

        @Description({" ", "# {SKULL} - Name of the skull owner"})
        public Notice skullMessage = Notice.chat("<green>► <white>Player <green>{SKULL} <white>heads received");

        @Description(" ")
        public Notice enchantedMessage = Notice.chat("<green>► <white>Item in hand is enchanted!");
        public Notice enchantedMessageFor = Notice.chat("<green>► <white>Item in hand of <green>{PLAYER} <white>is enchanted!");
        public Notice enchantedMessageBy = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>enchanted your item!");
    }


    @Description({" ", "# Messages sent on time and weather change."})
    public ENTimeAndWeatherMessageSection timeAndWeather = new ENTimeAndWeatherMessageSection();

    @Getter
    @Contextual
    public static class ENTimeAndWeatherMessageSection implements TimeAndWeatherSection {
        @Description("# {WORLD} - World name")
        public Notice timeSetDay = Notice.chat("<green>► <white>Time set to day in the <green>{WORLD}<white>!");
        public Notice timeSetNight = Notice.chat("<green>► <white>Time set to night in the <green>{WORLD}<white>!");

        @Description("# {TIME} - Changed time in ticks")
        public Notice timeSet = Notice.chat("<green>► <white>Time set to <green>{TIME}");
        public Notice timeAdd = Notice.chat("<green>► <white>Time added <green>{TIME}");

        @Description("# {WORLD} - World name")
        public Notice weatherSetRain = Notice.chat("<green>► <white>Weather set to rain in the <green>{WORLD}<white>!");
        public Notice weatherSetSun = Notice.chat("<green>► <white>Weather set to sun in the <green>{WORLD}<white>!");
        public Notice weatherSetThunder = Notice.chat("<green>► <white>Weather set to thunder in the <green>{WORLD}<white>!");
    }

    @Description({" ", "# Messages responsible for containers"})
    public ENContainerSection container = new ENContainerSection();

    @Getter
    @Contextual
    public static class ENContainerSection implements ContainerSection {

        @Description({
            "# These messages are sent when the player opens a container",
            "# {PLAYER} - Player who opened the container"
        })

        public Notice genericContainerOpened = Notice.empty();

        public Notice genericContainerOpenedBy = Notice.chat("<green>► <white>The specified container has been opened by <green>{PLAYER}<white>!");
        public Notice genericContainerOpenedFor = Notice.chat("<green>► <white>The specified container has been opened for <green>{PLAYER}<white>!");
    }

    @Description({" ", "# Information sent, when the language is changed to English"})
    public ENLanguageSection language = new ENLanguageSection();

    @Getter
    @Contextual
    public static class ENLanguageSection implements LanguageSection {
        public Notice languageChanged = Notice.chat("<green>► <white>Language changed to <green>English<white>!");

        public List<ConfigItem> decorationItems = List.of(
            ConfigItem.builder()
                .withMaterial(Material.SUNFLOWER)
                .withGlow(true)
                .withSlot(40)
                .withName("&7Our discord")
                .withLore(Collections.singletonList("&8» &6https://discord.gg/TRbDApaJaJ"))
                .build()
        );
    }

    @Description({" ", "# Auto message"})
    public ENAutoMessageSection autoMessage = new ENAutoMessageSection();

    @Getter
    @Contextual
    public static class ENAutoMessageSection implements AutoMessageSection {

        @Description({
            "",
            "# If you want to useplaceholder %server_online% you need to install",
            "# PlaceholderAPI plugin and download placeholders for Server",
            "# like /papi ecloud download Server",
        })
        public Map<String, Notice> messages = Map.of(
            "1", BukkitNotice.builder()
                .actionBar("<dark_gray>» <gold>There are <white>%server_online% <gold>people online on the server!")
                .sound(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f)
                .build(),

            "2", BukkitNotice.builder()
                .chat("<dark_gray>» <gold>You need help from an admin?")
                .chat("<dark_gray>» <gold>Type command <white>/helpop <gold>to ask!")
                .chat("<dark_gray>» <green><click:suggest_command:'/helpop'>Click to execute!</click></green>")
                .sound(Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f)
                .build()
        );

        public Notice enabled = Notice.chat("<green>► <white>Enabled auto messages!");
        public Notice disabled = Notice.chat("<green>► <white>Disabled auto messages!");

        @Override
        public Collection<Notice> messages() {
            return this.messages.values();
        }

        @Override
        public Notice enabled() {
            return this.enabled;
        }

        @Override
        public Notice disabled() {
            return this.disabled;
        }
    }


    @Description({" ", "# This section is responsible for handling jail-related stuff."})
    public ENJailSection jailSection = new ENJailSection();

    @Getter
    @Contextual
    public static class ENJailSection implements JailSection {
        @Description({" ", "# Section responsible for location of jail setup"})
        public Notice jailLocationSet = Notice.chat("<green>► <white>Jail location has been set!");
        public Notice jailLocationRemove = Notice.chat("<red>✘ <dark_red>Jail location has been removed!");
        public Notice jailLocationNotSet = Notice.chat("<red>✘ <dark_red>Jail location is not set!");
        public Notice jailLocationOverride = Notice.chat("<green>► <white>Jail location has been overridden!");

        @Description({" ", "# Section responsible for detaining players"})
        public Notice jailDetainPrivate = Notice.chat("<green>► <white>You have been jailed!");
        public Notice jailCannotUseCommand = Notice.chat("<red>✘ <dark_red>You can't use this command! You are in jail!");
        @Description({" ", "# {PLAYER} - Player who has been detained"})
        public Notice jailDetainOverride = Notice.chat("<green>► <white>You have overridden the jail for <green>{PLAYER} <white>!");
        @Description({" ", "# {PLAYER} - Player who has been detained"})
        public Notice jailDetainBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been jailed!");
        @Description({" ", "# {REMAINING_TIME} - Time left to release"})
        public Notice jailDetainCountdown = Notice.actionbar("<red> You are in jail! <gray>Time left: <red>{REMAINING_TIME}!");
        @Description({" ", "# {PLAYER} - Admin who you can't detain"})
        public Notice jailDetainAdmin = Notice.chat("<red>✘ <dark_red>You can't jail <red>{PLAYER} <dark_red>because he is an admin!");

        @Description({" ", "# Section responsible for releasing players from jail"})
        @Description({" ", "# {PLAYER} - Player who has been released from jail"})
        public Notice jailReleaseBroadcast = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been granted freedom!");
        public Notice jailReleasePrivate = Notice.actionbar("<green> You have been released from jail!");
        public Notice jailReleaseAll = Notice.chat("<green>► <white>All players have been released from jail!");
        public Notice jailReleaseNoPlayers = Notice.chat("<red>✘ <dark_red>No players found in jail!");
        @Description({" ", "# {PLAYER} - Player nickname"})
        public Notice jailIsNotPrisoner = Notice.chat("<red>✘ <dark_red>Player {PLAYER} is not a prisoner!");

        @Description({" ", "# Section responsible for listing players in jail"})
        public Notice jailListHeader = Notice.chat("<green>► <white>Players in jail: ");
        public Notice jailListEmpty = Notice.chat("<red>✘ <dark_red>No players found in jail!");
        @Description({" ", "# {PLAYER} - Player who has been detained", "# {REMAINING_TIME} - Time of detention", "# {DETAINED_BY} - Player who detained the player"})
        public Notice jailListPlayerEntry = Notice.chat("<green>► <white>{PLAYER} <gray>(<white>{REMAINING_TIME}<gray>) <white>detained by <green>{DETAINED_BY} <white>!");
    }
}
