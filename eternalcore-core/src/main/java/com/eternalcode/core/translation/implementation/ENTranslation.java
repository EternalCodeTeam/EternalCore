package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.bridge.litecommand.argument.messages.ENArgumentMessages;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.adminchat.messages.ENAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.ENAfkMessages;
import com.eternalcode.core.feature.automessage.messages.ENAutoMessageMessages;
import com.eternalcode.core.feature.burn.messages.ENBurnMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.ENDemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.ENElderGuardianMessages;
import com.eternalcode.core.feature.helpop.messages.ENHelpOpMessages;
import com.eternalcode.core.feature.home.messages.ENHomeMessages;
import com.eternalcode.core.feature.itemedit.messages.ENItemEditMessages;
import com.eternalcode.core.feature.jail.messages.ENJailMessages;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.msg.messages.ENMsgMessages;
import com.eternalcode.core.feature.randomteleport.messages.ENRandomTeleportMessages;
import com.eternalcode.core.feature.seen.messages.ENSeenMessages;
import com.eternalcode.core.feature.setslot.messages.ENSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.ENSignEditorMessages;
import com.eternalcode.core.feature.spawn.messages.ENSpawnMessages;
import com.eternalcode.core.feature.sudo.messages.ENSudoMessages;
import com.eternalcode.core.feature.teleportrequest.messages.ENTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.ENTimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.ENVanishMessages;
import com.eternalcode.core.feature.warp.messages.ENWarpMessages;
import com.eternalcode.core.feature.near.messages.ENNearMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class ENTranslation extends AbstractTranslation {

    public ENTranslation(Language language) {
        super(language);
    }

    public ENTranslation() {
        this(Language.EN);
    }

    @Comment({
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

    @Comment({
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
    @Comment("# This section is responsible for all messages used during bad of a command argument")
    public ENArgumentMessages argument = new ENArgumentMessages();

    @Comment({
        " ",
        "# This answer is responsible for the general formatting of some values",
        "# The purpose of the section is to reduce the repetition of some messages."
    })
    public ENFormatSection format = new ENFormatSection();

    @Comment("# This section is responsible for all messages related to item editing.")
    public ENItemEditMessages itemEdit = new ENItemEditMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /signeditor command",
    })
    public ENSignEditorMessages signEditor = new ENSignEditorMessages();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "lang" + File.separator + "en_messages.yml");
    }

    @Getter
    public static class ENFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Comment({
        " ",
        "# This section is responsible for the player support chat /helpop",
    })
    public ENHelpOpMessages helpOp = new ENHelpOpMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /seen command"
    })
    public ENSeenMessages seen = new ENSeenMessages();

    @Comment({
        " ",
        "# This section is responsible for the communication between administration",
    })
    public ENAdminChatMessages adminChat = new ENAdminChatMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /sudo command",
    })
    public ENSudoMessages sudo = new ENSudoMessages();

    @Comment({
        " ",
        "# This section is responsible for messages related to teleportation",
    })
    public ENTeleportSection teleport = new ENTeleportSection();

    @Getter
    public static class ENTeleportSection extends OkaeriConfig implements TeleportSection {
        // teleport
        @Comment({"# {PLAYER} - Teleported players"})
        public Notice teleportedToPlayer = Notice.chat("<green>► <white>Successfully teleported to <green>{PLAYER}<white>!");

        @Comment({"# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred"})
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>► <white>Successfully teleported <green>{PLAYER} <white>to <green>{ARG-PLAYER}<white>!");

        @Comment({"# {Y} - Y coordinate of the highest block"})
        public Notice teleportedToHighestBlock = Notice.chat("<green>► <white>Teleported successfully to the highest block! (Y: {Y})");

        @Comment(" ")
        public Notice teleportedAllToPlayer = Notice.chat("<green>► <white>All players have been teleported to you!");

        // Task
        @Comment({"# {TIME} - Teleportation time"})
        public Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleporting in <green>{TIME}");
        @Comment(" ")
        public Notice teleported = Notice.chat("<green>► <white>Teleported!");
        public Notice teleporting = Notice.chat("<green>► <white>Teleporting...");
        public Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>You've moved, teleportation canceled!");
        public Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>You are in teleport!");

        // Coordinates XYZ
        @Comment({" ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedToCoordinates = Notice.chat("<green>► <white>Teleported to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Comment({" ", "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>► <white>Teleported <green>{PLAYER} <white>to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Comment(" ")
        public Notice teleportedToLastLocation = Notice.chat("<green>► <white>Teleported to the last location!");
        @Comment({" ", "# {PLAYER} - Player who has been teleported"})
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Teleported <green>{PLAYER} <white>to the last location!");
        @Comment(" ")
        public Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Last location is not exist!");

        @Comment(" ")
        public Notice randomPlayerNotFound = Notice.chat("<red>✘ <dark_red>No player found to teleport!");
        @Comment({" ", "# {PLAYER} - The player you were teleported"})
        public Notice teleportedToRandomPlayer = Notice.chat("<green>► <white>Teleported to random player <green>{PLAYER}<white>!");
    }

    @Comment({
        " ",
        "# This section is responsible for messages related to random teleport",
    })
    public ENRandomTeleportMessages randomTeleport = new ENRandomTeleportMessages();

    public ENChatSection chat = new ENChatSection();

    @Getter
    public static class ENChatSection extends OkaeriConfig implements ChatSection {
        @Comment({"# {PLAYER} - Player who performed the actions for the chat"})
        public Notice disabled = Notice.chat("<green>► <white>Chat has been disabled by <green>{PLAYER}<white>!");
        public Notice enabled = Notice.chat("<green>► <white>The chat has been enabled by <green>{PLAYER}<white>!");
        public Notice cleared = Notice.chat("<green>► <white>Chat has been cleared by <green>{PLAYER}<white>!");

        @Comment(" ")
        public Notice alreadyDisabled = Notice.chat("<red>✘ <dark_red>Chat already off!");
        public Notice alreadyEnabled = Notice.chat("<red>✘ <dark_red>Chat already on!");

        @Comment({" ", "# {SLOWMODE} - Time for next message"})
        public Notice slowModeSet = Notice.chat("<green>► <white>SlowMode set to: {SLOWMODE}");

        @Comment({" ", "# {PLAYER} - Player who performed the actions for the chat"})
        public Notice slowModeOff = Notice.chat("<green>► <white>SlowMode has been disabled by <green>{PLAYER}<white>!");

        @Comment({" ", "# {TIME} - Time to next use (cooldown)"})
        public Notice slowMode = Notice.chat("<red>✘ <dark_red>You can write the next message for: <red>{TIME}<dark_red>!");

        @Comment(" ")
        public Notice disabledChatInfo = Notice.chat("<red>✘ <dark_red>Chat is currently disabled!");

        @Comment(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Command <red>{COMMAND} <dark_red>doesn't exists!");

        @Comment({" ", "# {PLAYER} - Player who received the message", "# {MESSAGE} - message content", "# {TYPE} - message type"})
        public Notice tellrawInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>{PLAYER} <white>with the content: {MESSAGE}");
        public Notice tellrawAllInfo = Notice.chat("<green>► <white>A message of type <green>{TYPE} <white>was sent to <green>all <white>with the content: {MESSAGE}");

        public Notice tellrawSaved = Notice.chat("<green>► <white>Message saved in queue!");
        public Notice tellrawNoSaved = Notice.chat("<red>✘ <dark_red>No messages saved in queue!");
        public Notice tellrawMultipleSent = Notice.chat("<green>► <white>Messages sent! Message que has been cleared!");
        public Notice tellrawCleared = Notice.chat("<green>► <white>Message queue cleared!");

        @Comment({" ", "# {BROADCAST} - Broadcast"})
        public String alertMessageFormat = "<red><bold>BROADCAST:</bold> <gray>{BROADCAST}";
        public Notice alertQueueAdded = Notice.chat("<green>► <white>Message added to the queue!");
        public Notice alertQueueRemovedSingle = Notice.chat("<green>► <white>Removed latest message!");
        public Notice alertQueueRemovedAll = Notice.chat("<green>► <white>Removed all messages!");
        public Notice alertQueueCleared = Notice.chat("<green>► <white>Message queue cleared!");
        public Notice alertQueueEmpty = Notice.chat("<red>✘ <dark_red>The message queue is empty!");
        public Notice alertQueueSent = Notice.chat("<green>► <white>All messages sent from the queue!");

    }

    @Comment({
        " ",
        "# This section is responsible for handling tpa requests,",
        "# It gives you the ability to edit queries of this type",
    })
    public ENTeleportRequestMessages tpa = new ENTeleportRequestMessages();

    @Comment({
        " ",
        "# This section is responsible for setting and editing fast travel points - warp",
    })
    public ENWarpMessages warp = new ENWarpMessages();

    @Comment({
        " ",
        "# The following section is responsible for setting and editing personal fast travel points - home",
    })
    public ENHomeMessages home = new ENHomeMessages();

    @Comment({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENMsgMessages msg = new ENMsgMessages();

    @Comment({
        " ",
        "# Section responsible for AFK."
    })
    public ENAfkMessages afk = new ENAfkMessages();

    @Comment({
        " ",
        "# Section responsible for various server events."
    })
    public ENEventSection event = new ENEventSection();

    @Getter
    public static class ENEventSection extends OkaeriConfig implements EventSection {
        @Comment({
            "# {PLAYER} - Killed player",
            "# {KILLER} - Killer (only for PvP deaths)"
        })
        public List<Notice> deathMessage = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}!")
        );

        @Comment({
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

        @Comment("# {PLAYER} - Player who died from an unknown cause")
        public List<Notice> unknownDeathCause = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died under mysterious circumstances!")
        );

        @Comment({"", "# {PLAYER} - Player who joined"})
        public List<Notice> joinMessage = List.of(
            Notice.actionbar("<green>► <green>{PLAYER} <white>joined the server!"),
            Notice.actionbar("<green>► <white>Welcome to the server <green>{PLAYER}<white>!")
        );

        @Comment("# {PLAYER} - Player who joined.")
        public List<Notice> firstJoinMessage = List.of(
            Notice.chat("<green>► {PLAYER} <white>joined the server for the first time!"),
            Notice.chat("<green>► {PLAYER} <white>welcome to the server for the first time!")
        );

        @Comment("# {PLAYER} - Player who left")
        public List<Notice> quitMessage = List.of(
            Notice.actionbar("<red>► {PLAYER} <white>logged off the server!"),
            Notice.actionbar("<red>► {PLAYER} <white>left the server!")
        );

        @Comment({" ", "# {PLAYER} - Player who joined"})
        public Notice welcome = Notice.title("<yellow>EternalCode.pl", "<yellow>Welcome back to the server!");
    }


    @Comment({" ", "# Section responsible for inventories-related stuff."})
    public ENInventorySection inventory = new ENInventorySection();

    @Getter
    public static class ENInventorySection extends OkaeriConfig implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>► <white>Your inventory has been cleared!");

        @Comment({" ", "# {PLAYER} - Target player"})
        public Notice inventoryClearMessageBy = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>inventory cleared");

        @Comment(" ")
        public String disposalTitle = "<white><bold>Trash";
    }

    @Comment({
        " ",
        "# This section is responsible for player-related stuff and interactions with them."
    })
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    public static class ENPlayerSection extends OkaeriConfig implements PlayerSection {
        public Notice feedMessage = Notice.chat("<green>► <white>You've been fed!");

        @Comment(" # {PLAYER} - Player who has been fed")
        public Notice feedMessageBy = Notice.chat("<green>► <white>You've fed the <green>{PLAYER}");

        @Comment(" ")
        public Notice healMessage = Notice.chat("<green>► <white>You've been healed!");

        @Comment("# {PLAYER} - Healed player")
        public Notice healMessageBy = Notice.chat("<green>► <white>Healed <green>{PLAYER}");

        @Comment(" ")
        public Notice killSelf = Notice.chat("<red>► <dark_red>You killed yourself!");
        @Comment("# {PLAYER} - Killed player")
        public Notice killedMessage = Notice.chat("<red>► <dark_red>Killed <red>{PLAYER}");

        @Comment(" ")
        public Notice speedBetweenZeroAndTen = Notice.chat("<red>✘ <dark_red>Enter speed from 0 to 10!");
        public Notice speedTypeNotCorrect = Notice.chat("<red>✘ <dark_red>Invalid speed type!");

        @Comment("# {SPEED} - Walk or fly speed value")
        public Notice speedWalkSet = Notice.chat("<green>► <white>Walking speed is set to <green>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>► <white>Flying speed is set to <green>{SPEED}");

        @Comment("# {PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        public Notice speedWalkSetBy = Notice.chat("<green>► <white>Walking speed for <green>{PLAYER} <white>is set to <green>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>► <white>Flying speed for <green>{PLAYER} <white>is set to <green>{SPEED}");

        @Comment({" ", "# {STATE} - Godmode status"})
        public Notice godEnable = Notice.chat("<green>► <white>God is now {STATE}");
        public Notice godDisable = Notice.chat("<green>► <white>God is now {STATE}");

        @Comment({" ", "# {PLAYER} - Target player, {STATE} - Target player godmode status"})
        public Notice godSetEnable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");
        public Notice godSetDisable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");

        @Comment({" ", "# {STATE} - Fly status"})
        public Notice flyEnable = Notice.chat("<green>► <white>Fly is now {STATE}");
        public Notice flyDisable = Notice.chat("<green>► <white>Fly is now {STATE}");

        @Comment("# {PLAYER} - Target player, {STATE} - Target player fly status")
        public Notice flySetEnable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
        public Notice flySetDisable = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");

        @Comment({" ", "# {PING} - Current ping"})
        public Notice pingMessage = Notice.chat("<green>► <white>Your ping is: <green>{PING}<white>ms");

        @Comment("# {PLAYER} - Target player, {PING} - Ping of target player")
        public Notice pingOtherMessage = Notice.chat("<green>► <white>Ping of the <green>{PLAYER} <white>is: <green>{PING}<white>ms");

        @Comment(" ")
        public Notice gameModeNotCorrect = Notice.chat("<red>✘ <dark_red>Not a valid gamemode type");

        @Comment("# {GAMEMODE} - Gamemode name")
        public Notice gameModeMessage = Notice.chat("<green>► <white>Gamemode now is set to: <green>{GAMEMODE}");

        @Comment("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
        public Notice gameModeSetMessage = Notice.chat("<green>► <white>Gamemode for <green>{PLAYER} <white>now is set to: <green>{GAMEMODE}");

        @Comment({" ", "# {ONLINE} - Number of online players"})
        public Notice onlinePlayersCountMessage = Notice.chat("<green>► <white>On server now is: <green>{ONLINE} <white>players!");

        @Comment("# {ONLINE} - Current online players, {PLAYERS} - Player list")
        public Notice onlinePlayersMessage = Notice.chat("<green>► <white>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS}");

        public List<String> fullServerSlots = List.of(
            " ",
            "<red>✘ <dark_red>Server is full!",
            "<red>✘ <dark_red>Buy rank on our site!"
        );

        @Comment({
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

        @Comment({" ", "# {KILLED} - Number of killed mobs"})
        public Notice butcherCommand = Notice.chat("<green>► <white>You killed <green>{KILLED} <white>mobs!");

        @Comment({" ", "# {SAFE_CHUNKS} - The number of safe chunks"})
        public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>You have exceeded the number of safe chunks <red>{SAFE_CHUNKS}");
    }

    @Comment({" ", "# This section is responsible for spawn-related stuff."})
    public ENSpawnMessages spawn = new ENSpawnMessages();

    @Comment({
        " ",
        "# This section is responsible for messages regarding item attributes."
    })
    public ENItemSection item = new ENItemSection();

    @Getter
    public static class ENItemSection extends OkaeriConfig implements ItemSection {
        @Comment({" ", "# {ITEM} - Name of received item"})
        public Notice giveReceived = Notice.chat("<green>► <white>You have received: <green>{ITEM}");

        @Comment({" ", "# {PLAYER} - Name of item receiver, {ITEM} - the item"})
        public Notice giveGiven = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has received <green>{ITEM}");
        public Notice giveNoSpace = Notice.chat("<red>✘ <dark_red>Not enough space in inventory!");

        @Comment(" ")
        public Notice giveNotItem = Notice.chat("<green>► <white>Not a valid obtainable item!");
        public Notice repairMessage = Notice.chat("<green>► <white>Repaired held item!");
        public Notice repairAllMessage = Notice.chat("<green>► <white>Repaired all items!");

        @Comment({" ", "# {TIME} - Time to next use (cooldown)"})
        public Notice repairDelayMessage = Notice.chat("<red>✘ <dark_red>You can use this command after <red>{TIME}!");

        @Comment({" ", "# {SKULL} - Name of the skull owner"})
        public Notice skullMessage = Notice.chat("<green>► <white>Player <green>{SKULL} <white>heads received");

        @Comment(" ")
        public Notice enchantedMessage = Notice.chat("<green>► <white>Item in hand is enchanted!");
        public Notice enchantedMessageFor = Notice.chat("<green>► <white>Item in hand of <green>{PLAYER} <white>is enchanted!");
        public Notice enchantedMessageBy = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>enchanted your item!");
    }

    @Comment({" ", "# Messages sent on time and weather change."})
    public ENTimeAndWeatherMessages timeAndWeather = new ENTimeAndWeatherMessages();

    @Comment({" ", "# Messages responsible for containers"})
    public ENContainerSection container = new ENContainerSection();

    @Getter
    public static class ENContainerSection extends OkaeriConfig implements ContainerSection {

        @Comment({
            "# These messages are sent when the player opens a container",
            "# {PLAYER} - Player who opened the container"
        })

        public Notice genericContainerOpened = Notice.empty();

        public Notice genericContainerOpenedBy = Notice.chat("<green>► <white>The specified container has been opened by <green>{PLAYER}<white>!");
        public Notice genericContainerOpenedFor = Notice.chat("<green>► <white>The specified container has been opened for <green>{PLAYER}<white>!");
    }

    @Comment({" ", "# Information sent, when the language is changed to English"})
    public ENLanguageSection language = new ENLanguageSection();

    @Getter
    public static class ENLanguageSection extends OkaeriConfig implements LanguageSection {
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

    @Comment({" ", "# Set's max players on the server, the messages for the /setslot command"})
    public ENSetSlotMessages setSlot = new ENSetSlotMessages();

    @Comment({" ", "# Auto message"})
    public ENAutoMessageMessages autoMessage = new ENAutoMessageMessages();

    @Comment({" ", "# This section is responsible for handling jail-related stuff."})
    public ENJailMessages jailSection = new ENJailMessages();

    @Comment({" ", "# This section is responsible for elder guardian messages."})
    public ENElderGuardianMessages elderGuardian = new ENElderGuardianMessages();

    @Comment({" ", "# This section is responsible for demo screen messages."})
    public ENDemoScreenMessages demoScreen = new ENDemoScreenMessages();

    @Comment({" ", "# This section is responsible for '/burn' command messages."})
    public ENBurnMessages burn = new ENBurnMessages();
  
    @Comment({" ", "# This section is responsible for vanish-related stuff."})
    public ENVanishMessages vanish = new ENVanishMessages();

    @Comment({" ", "# This section is responsible for '/near' command messages."})
    public ENNearMessages near = new ENNearMessages();
}
