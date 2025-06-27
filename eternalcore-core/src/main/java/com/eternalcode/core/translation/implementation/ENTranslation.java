package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.bridge.litecommand.argument.messages.ENArgumentMessages;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.adminchat.messages.ENAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.ENAfkMessages;
import com.eternalcode.core.feature.automessage.messages.ENAutoMessageMessages;
import com.eternalcode.core.feature.helpop.messages.ENHelpOpMessages;
import com.eternalcode.core.feature.home.messages.ENHomeMessages;
import com.eternalcode.core.feature.itemedit.messages.ENItemEditMessages;
import com.eternalcode.core.feature.jail.messages.ENJailMessages;
import com.eternalcode.core.feature.privatechat.messages.ENPrivateMessages;
import com.eternalcode.core.feature.randomteleport.messages.ENRandomTeleportMessages;
import com.eternalcode.core.feature.seen.messages.ENSeenMessages;
import com.eternalcode.core.feature.setslot.messages.ENSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.ENSignEditorMessages;
import com.eternalcode.core.feature.spawn.messages.ENSpawnMessages;
import com.eternalcode.core.feature.sudo.messages.ENSudoMessages;
import com.eternalcode.core.feature.teleportrequest.messages.ENTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.ENTimeAndWeatherMessages;
import com.eternalcode.core.feature.warp.messages.ENWarpMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.multification.notice.Notice;
import java.util.Locale;
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

    public ENTranslation(String languageCode) {
        super(languageCode);
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
    public ENArgumentMessages argument = new ENArgumentMessages();

    @Description({
        " ",
        "# This answer is responsible for the general formatting of some values",
        "# The purpose of the section is to reduce the repetition of some messages."
    })
    public ENFormatSection format = new ENFormatSection();

    @Description("# This section is responsible for all messages related to item editing.")
    public ENItemEditMessages itemEdit = new ENItemEditMessages();

    @Description({
        " ",
        "# This section is responsible for the messages of the /signeditor command",
    })
    public ENSignEditorMessages signEditor = new ENSignEditorMessages();

    @Override
    public String getLanguage() {
        return this.languageCode;
    }

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
    public ENHelpOpMessages helpOp = new ENHelpOpMessages();

    @Description({
        " ",
        "# This section is responsible for the messages of the /seen command"
    })
    public ENSeenMessages seen = new ENSeenMessages();

    @Description({
        " ",
        "# This section is responsible for the communication between administration",
    })
    public ENAdminChatMessages adminChat = new ENAdminChatMessages();

    @Description({
        " ",
        "# This section is responsible for the messages of the /sudo command",
    })
    public ENSudoMessages sudo = new ENSudoMessages();

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
    public ENRandomTeleportMessages randomTeleport = new ENRandomTeleportMessages();

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

        @Description(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Command <red>{COMMAND} <dark_red>doesn't exists!");

        @Description({" ", "# {PLAYER} - Player who received the message", "# {MESSAGE} - message content", "# {TYPE} - message type"})
        public Notice tellrawInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>{PLAYER} <white>with the content: {MESSAGE}");
        public Notice tellrawAllInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>all <white>with the content: {MESSAGE}");

        public Notice tellrawSaved = Notice.chat("<green>► <white>Message saved in queue!");
        public Notice tellrawNoSaved = Notice.chat("<red>✘ <dark_red>No messages saved in queue!");
        public Notice tellrawMultipleSent = Notice.chat("<green>► <white>Messages sent! Message que has been cleared!");
        public Notice tellrawCleared = Notice.chat("<green>► <white>Message queue cleared!");

        @Description({" ", "# {BROADCAST} - Broadcast"})
        public String alertMessageFormat = "<red><bold>BROADCAST:</bold> <gray>{BROADCAST}";
        public Notice alertQueueAdded = Notice.chat("<green>► <white>Message added to the queue!");
        public Notice alertQueueRemovedSingle = Notice.chat("<green>► <white>Removed latest message!");
        public Notice alertQueueRemovedAll = Notice.chat("<green>► <white>Removed all messages!");
        public Notice alertQueueCleared = Notice.chat("<green>► <white>Message queue cleared!");
        public Notice alertQueueEmpty = Notice.chat("<red>✘ <dark_red>The message queue is empty!");
        public Notice alertQueueSent = Notice.chat("<green>► <white>All messages sent from the queue!");

    }

    @Description({
        " ",
        "# This section is responsible for handling tpa requests,",
        "# It gives you the ability to edit queries of this type",
    })
    public ENTeleportRequestMessages tpa = new ENTeleportRequestMessages();

    @Description({
        " ",
        "# This section is responsible for setting and editing fast travel points - warp",
    })
    public ENWarpMessages warp = new ENWarpMessages();

    @Description({
        " ",
        "# The following section is responsible for setting and editing personal fast travel points - home",
    })
    public ENHomeMessages home = new ENHomeMessages();

    @Description({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENPrivateMessages privateChat = new ENPrivateMessages();

    @Description({
        " ",
        "# Section responsible for AFK."
    })
    public ENAfkMessages afk = new ENAfkMessages();

    @Description({
        " ",
        "# Section responsible for various server events."
    })
    public ENEventSection event = new ENEventSection();

    @Getter
    @Contextual
    public static class ENEventSection implements EventSection {
        @Description({
            "# {PLAYER} - Killed player",
            "# {KILLER} - Killer (only for PvP deaths)"
        })
        public List<Notice> deathMessage = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}!")
        );

        @Description({
            "# Messages shown when a player dies from specific damage causes",
            "# {PLAYER} - Killed player",
            "# {CAUSE} - Death cause (e.g., FALL, VOID)",
            "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
        })
        public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
            EntityDamageEvent.DamageCause.VOID, Collections.singletonList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell into the void!")
            ),
            EntityDamageEvent.DamageCause.FALL, Arrays.asList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell from a high place!"),
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell off a deadly cliff!")
            )
        );

        @Description("# {PLAYER} - Player who died from an unknown cause")
        public List<Notice> unknownDeathCause = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died under mysterious circumstances!")
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
    public ENSpawnMessages spawn = new ENSpawnMessages();

    @Description({
        " ",
        "# This section is responsible for messages regarding item attributes."
    })
    public ENItemSection item = new ENItemSection();

    @Getter
    @Contextual
    public static class ENItemSection implements ItemSection {
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
    public ENTimeAndWeatherMessages timeAndWeather = new ENTimeAndWeatherMessages();

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

    @Description({" ", "# Set's max players on the server, the messages for the /setslot command"})
    public ENSetSlotMessages setSlot = new ENSetSlotMessages();

    @Description({" ", "# Auto message"})
    public ENAutoMessageMessages autoMessage = new ENAutoMessageMessages();

    @Description({" ", "# This section is responsible for handling jail-related stuff."})
    public ENJailMessages jailSection = new ENJailMessages();
}
