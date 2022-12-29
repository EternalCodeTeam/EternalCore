package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.ReloadableMessages;
import com.eternalcode.core.language.Language;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class ENMessagesConfiguration implements ReloadableMessages {

    public ENArgumentSection argument = new ENArgumentSection();
    public ENFormatSection format = new ENFormatSection();
    public ENHelpOpSection helpOp = new ENHelpOpSection();
    public ENAdminChatSection adminChat = new ENAdminChatSection();
    public ENTeleportSection teleport = new ENTeleportSection();
    public ENChatSection chat = new ENChatSection();
    public ENTpaSection tpa = new ENTpaSection();
    public ENWarpSection warp = new ENWarpSection();
    public ENHomeSection home = new ENHomeSection();
    public ENPrivateMessageSection privateMessage = new ENPrivateMessageSection();
    public ENAfkSection afk = new ENAfkSection();
    public ENEventMessageSection eventMessages = new ENEventMessageSection();
    public ENOtherMessages other = new ENOtherMessages();

    @Getter @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        public Notification permissionMessage = Notification.chat("<dark_gray>» <red>You don't have permission to perform this command! <gray>({PERMISSIONS})");
        public Notification usageMessage = Notification.chat("<dark_gray>» <yellow>Correct usage: <gray>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<dark_gray>» <yellow>Correct usage:");
        public Notification usageMessageEntry = Notification.chat("<dark_gray>» <gray>{USAGE}");
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
    }

    @Getter @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Getter @Contextual
    public static class ENHelpOpSection implements HelpOpSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{NICK}<dark_gray>: <white>{TEXT}");
        public Notification send = Notification.chat("<dark_gray>» <green>This message has been successfully sent to administration");
        public Notification coolDown = Notification.chat("<dark_gray>» <red>You can use this command for: <gold>{TIME}");
    }

    @Getter @Contextual
    public static class ENAdminChatSection implements AdminChatSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{NICK}<dark_gray>: <white>{TEXT}");
    }

    @Getter @Contextual
    public static class ENTeleportSection implements TeleportSection {
        // teleport
        public Notification teleportedToPlayer = Notification.chat("<dark_gray>» <green>Successfully teleported to {PLAYER}!");
        public Notification teleportedPlayerToPlayer = Notification.chat("<dark_gray>» <green>Successfully teleported {PLAYER} to {ARG-PLAYER}!");

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleporting in <white>{TIME}");
        public Notification teleported = Notification.chat("<dark_gray>» <green>Teleported!");
        public Notification teleporting = Notification.chat("<dark_gray>» <green>Teleporting...");
        public Notification teleportTaskCanceled = Notification.chat("<dark_gray>» <red>You've moved, teleportation canceled!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<dark_gray>» <red>You are in teleport!");

        // Coordinates XYZ
        public Notification teleportedToCoordinates = Notification.chat("<dark_gray>» <gold>Teleported to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<dark_gray>» <gold>Teleported <red>{PLAYER} <gold>to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");

        // Back
        public Notification teleportedToLastLocation = Notification.chat("<dark_gray>» <gray>Teleported to the last location!");
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<dark_gray>» <gray>Teleported {PLAYER} to the last location!");
        public Notification lastLocationNoExist = Notification.chat("<dark_gray>» <red>Last location is not exist!");
    }

    @Getter @Contextual
    public static class ENChatSection implements ChatSection {
        public Notification disabled = Notification.chat("<dark_gray>» <red>Chat has been disabled by {NICK}!");
        public Notification enabled = Notification.chat("<dark_gray>» <green>The chat has been enabled by {NICK}!");
        public Notification cleared = Notification.chat("<dark_gray>» <gold>Chat has been cleared by {NICK}!");
        public Notification alreadyDisabled = Notification.chat("<dark_gray>» <green>Chat already off!");
        public Notification alreadyEnabled = Notification.chat("<dark_gray>» <green>Chat already on!");
        public Notification slowModeSet = Notification.chat("<dark_gray>» <green>Slowmode set to: {SLOWMODE}");
        public Notification slowMode = Notification.chat("<dark_gray>» <red>You can write the next message for: <gold>{TIME}");
        public Notification disabledChatInfo = Notification.chat("<dark_gray>» <red>Chat is currently disabled!");
        public Notification noCommand = Notification.chat("<dark_gray>» <red>Command <yellow>{COMMAND} <red>doesn't exists!");
    }

    @Getter @Contextual
    public static class ENTpaSection implements TpaSection {
        public Notification tpaSelfMessage = Notification.chat("<dark_gray>» <red>You can't teleport to yourself!");
        public Notification tpaAlreadySentMessage = Notification.chat("<dark_gray>» <red>You have already sent a teleportation request!");
        public Notification tpaSentMessage = Notification.chat("<dark_gray>» <green>You have sent a request for teleportation to a player: <gray>{PLAYER}<green>!");

        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<dark_gray>» <green>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!"),
            Notification.chat("<dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept!"),
            Notification.chat("<dark_gray>» <gold>/tpdeny {PLAYER} <green>to deny!")
        );

        public Notification tpaDenyNoRequestMessage = Notification.chat("<dark_gray>» <red>You do not have a teleport request from this player!");
        public Notification tpaDenyDoneMessage = Notification.chat("<dark_gray>» <red>You have declined a teleport request from a player: <gray>{PLAYER}<red>!");
        public Notification tpaDenyReceivedMessage = Notification.chat("<dark_gray>» <red>Player: <gray>{PLAYER} rejected your teleport request!");
        public Notification tpaDenyAllDenied = Notification.chat("<dark_gray>» <red>All players have denied your teleport request!");

        public Notification tpaAcceptMessage = Notification.chat("<dark_gray>» <green>You have accepted the teleportation from the player: <gray>{PLAYER}<green>!");
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<dark_gray>» <red>This player has not sent you a teleport request!");
        public Notification tpaAcceptReceivedMessage = Notification.chat("<dark_gray>» <green>Player: <gray>{PLAYER} <green>accepted your teleportation request!");
        public Notification tpaAcceptAllAccepted = Notification.chat("<dark_gray>» <green>All players have accepted your teleport request!");
    }

    @Getter @Contextual
    public static class ENWarpSection implements WarpSection {
        public Notification warpAlreadyExists = Notification.chat("<dark_gray>» <red>Warp <yellow>{WARP} <red>already exists!");
        public Notification notExist = Notification.chat("<dark_gray>» <red>This warp doesn't exist");
        public Notification create = Notification.chat("<dark_gray>» <gray>Warp {NAME} has been created.");
        public Notification remove = Notification.chat("<dark_gray>» <gray>Warp {NAME} has been deleted.");
    }

    @Getter @Contextual
    public static class ENHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<dark_gray>» <red>This home doesn't exist");
        public Notification create = Notification.chat("<dark_gray>» <gray>Home {HOME} has been created.");
        public Notification delete = Notification.chat("<dark_gray>» <gray>Home {HOME} has been deleted.");
    }

    @Getter @Contextual
    public static class ENPrivateMessageSection implements PrivateMessageSection {
        public Notification noReply = Notification.chat("<dark_gray>» <red>You have no one to reply!");
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification socialSpyEnable = Notification.chat("<dark_gray>» <green>SocialSpy has been enabled!");
        public Notification socialSpyDisable = Notification.chat("<dark_gray>» <red>SocialSpy has been disabled!");

        public Notification ignorePlayer = Notification.chat("<dark_gray>» <red>{PLAYER} <gray>player has been ignored!");
        public Notification unIgnorePlayer = Notification.chat("<dark_gray>» <red>{PLAYER} <gray>player has been uningored!");
    }

    @Getter @Contextual
    public static class ENAfkSection implements AfkSection {
        public Notification afkOn = Notification.chat("<dark_gray>» <gray>{PLAYER} is AFK!");
        public Notification afkOff = Notification.chat("<dark_gray>» <gray>{PLAYER} is not AFK!");
    }

    @Getter
    @Contextual
    public static class ENEventMessageSection implements EventMessagesSection {
        public List<Notification> deathMessage = List.of(Notification.chat("<dark_gray>» <gray>{PLAYER} <red>died!"));

        public List<Notification> joinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>joined the server!"),
            Notification.chat("<dark_gray>» <gray>Welcome to the server <green>{PLAYER}<gray>!")
        );

        public List<Notification> firstJoinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>joined the server for the first time!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>welcome to the server for the first time!")
        );

        public List<Notification> quitMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>logged off the server!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>left the server!")
        );

        public Notification welcomeTitle = Notification.chat("<yellow>EternalCode.pl");

        public Notification welcomeSubtitle = Notification.chat("<yellow>Welcome back to the server!");
    }

    @Getter @Contextual
    public static class ENOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "<red><bold>BROADCAST: <gray>{BROADCAST}";

        public Notification inventoryClearMessage = Notification.chat("<dark_gray>» <red>Your inventory has been cleared!");
        public Notification inventoryClearMessageBy = Notification.chat("<dark_gray>» <red>Player {PLAYER} inventory cleared");

        public String disposalTitle = "<white><bold>Trash";

        public Notification feedMessage = Notification.chat("<dark_gray>» <green>You've been feed!");
        public Notification feedMessageBy = Notification.chat("<dark_gray>» <green>You've fed the {PLAYER}");

        public Notification healMessage = Notification.chat("<dark_gray>» <green>You've been heal!");
        public Notification healMessageBy = Notification.chat("<dark_gray>» <red>Healed <gold>{PLAYER}");

        public Notification repairMessage = Notification.chat("<dark_gray>» <green>Repaired!");
        public Notification skullMessage = Notification.chat("<dark_gray>» <green>Player {NICK} heads received");

        public Notification killSelf = Notification.chat("<dark_gray>» <red>You kill yourself!");
        public Notification killedMessage = Notification.chat("<dark_gray>» <red>Killed {PLAYER}");

        public Notification speedBetweenZeroAndTen = Notification.chat("<dark_gray>» <red>Enter speed from 0 to 10!");
        public Notification speedSet = Notification.chat("<dark_gray>» <red>Speed is set to {SPEED}");
        public Notification speedSetBy = Notification.chat("<dark_gray>» <red>Speed for {PLAYER} is set to {SPEED}");

        public Notification godMessage = Notification.chat("<dark_gray>» <red>God is now {STATE}");
        public Notification godSetMessage = Notification.chat("<dark_gray>» <red>Player <gold>{PLAYER} god is now: {STATE}");

        public Notification flyMessage = Notification.chat("<dark_gray>» <red>Fly is now {STATE}");
        public Notification flySetMessage = Notification.chat("<dark_gray>» <red>Fly for <gold>{PLAYER} <red>is now {STATE}");

        public Notification giveReceived = Notification.chat("<dark_gray>» <red>You have received: <gold>{ITEM}");
        public Notification giveGiven = Notification.chat("<dark_gray>» <red>Player <gold>{PLAYER} <red>has received <gold>{ITEM}");

        public Notification spawnSet = Notification.chat("<dark_gray>» <green>Spawn set!");
        public Notification spawnNoSet = Notification.chat("<dark_gray>» <red>Spawn is not set!");
        public Notification spawnTeleportedBy = Notification.chat("<dark_gray>» <red>You have been teleported to spawn by {PLAYER}!");
        public Notification spawnTeleportedOther = Notification.chat("<dark_gray>» <red>You teleported player {PLAYER} to spawn!");

        public Notification gameModeNotCorrect = Notification.chat("<dark_gray>» <red>Not a valid gamemode type");
        public Notification gameModeMessage = Notification.chat("<dark_gray>» <red>Gamemode now is set to: {GAMEMODE}");
        public Notification gameModeSetMessage = Notification.chat("<dark_gray>» <red>Gamemode for <gold>{PLAYER} <red>now is set to: <gold>{GAMEMODE}");

        public Notification pingMessage = Notification.chat("<dark_gray>» <red>Your ping is: <gold>{PING}ms");
        public Notification pingOtherMessage = Notification.chat("<dark_gray>» <red>Ping of the <gold>{PLAYER} <red>is: <gold>{PING}ms");

        public Notification onlineMessage = Notification.chat("<dark_gray>» <gold>On server now is: <white>{ONLINE} <gold>players!");

        public Notification listMessage = Notification.chat("<dark_gray>» <gold>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}");

        public Notification itemChangeNameMessage = Notification.chat("<dark_gray>» <gray>Name has been changed to: <red>{ITEM_NAME}");
        public Notification itemClearNameMessage = Notification.chat("<dark_gray>» <gray>Name has been cleared!");

        public Notification itemChangeLoreMessage = Notification.chat("<dark_gray>» <gray>Lore has been changed to: <red>{ITEM_LORE}");
        public Notification itemClearLoreMessage = Notification.chat("<dark_gray>» <gray>Lore has been cleared!");

        public Notification itemFlagRemovedMessage = Notification.chat("<dark_gray>» <gray>Flag {ITEM_FLAG} has been removed!");
        public Notification itemFlagAddedMessage = Notification.chat("<dark_gray>» <gray>Flag {ITEM_FLAG} has been added!");
        public Notification itemFlagClearedMessage = Notification.chat("<dark_gray>» <gray>Flags have been cleared!");

        public Notification enchantedMessage = Notification.chat("<dark_gray>» <gold>Item in hand is enchanted!");
        public Notification languageChanged = Notification.chat("<dark_gray>» <gold>Language changed to <red>English<gold>!");

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

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + "en_messages.yml");
    }

    @Override
    public Language getLanguage() {
        return Language.EN;
    }
}
