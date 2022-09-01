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

    @Override
    public Language getLanguage() {
        return Language.EN;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + "en_messages.yml");
    }

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
    public ENOtherMessages other = new ENOtherMessages();

    @Getter @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        public String permissionMessage = "<dark_gray>» <red>You don't have permission to perform this command! <gray>({PERMISSIONS})";
        public String usageMessage = "<dark_gray>» <yellow>Correct usage: <gray>{USAGE}";
        public String usageMessageHead = "<dark_gray>» <yellow>Correct usage:";
        public String usageMessageEntry = "<dark_gray>» <gray>{USAGE}";
        public String offlinePlayer = "<dark_gray>» <red>This player is currently offline!";
        public String onlyPlayer = "<dark_gray>» <red>Command is only for players!";
        public String notNumber = "<dark_gray>» <red>Argument isn't a number!";
        public String numberBiggerThanOrEqualZero = "<dark_gray>» <red>The number must be greater than or equal to 0!";
        public String noItem = "<dark_gray>» <red>You need item to use this command!";
        public String noMaterial = "<dark_gray>» <red>This item doesn't exist";
        public String noArgument = "<dark_gray>» <red>This argument doesn't exist";
        public String noDamaged = "<dark_gray>» <red>This item can't be repaired";
        public String noDamagedItems = "<dark_gray>» <red>You need damaged items to use this command!";
        public String noEnchantment = "<dark_gray>» <red>This enchantment doesn't exist";
        public String noValidEnchantmentLevel = "<dark_gray>» <red>This enchantment level is not supported!";
    }

    @Getter @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Getter @Contextual
    public static class ENHelpOpSection implements HelpOpSection {
        public String format = "<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{NICK}<dark_gray>: <white>{TEXT}";
        public String send = "<dark_gray>» <green>This message has been successfully sent to administration";
        public String coolDown = "<dark_gray>» <red>You can use this command for: <gold>{TIME}";
    }

    @Getter @Contextual
    public static class ENAdminChatSection implements AdminChatSection {
        public String format = "<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{NICK}<dark_gray>: <white>{TEXT}";
    }

    @Getter @Contextual
    public static class ENTeleportSection implements TeleportSection {
        // teleport
        public String teleportedToPlayer = "<dark_gray>» <green>Successfully teleported to {PLAYER}!";
        public String teleportedPlayerToPlayer = "<dark_gray>» <green>Successfully teleported {PLAYER} to {ARG-PLAYER}!";

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleporting in <white>{TIME}");
        public String teleported = "<dark_gray>» <green>Teleported!";
        public String teleporting = "<dark_gray>» <green>Teleporting...";
        public String teleportTaskCanceled = "<dark_gray>» <red>You've moved, teleportation canceled!";
        public String teleportTaskAlreadyExist = "<dark_gray>» <red>You are in teleport!";

        // Coordinates XYZ
        public String teleportedToCoordinates = "<dark_gray>» <gold>Teleported to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}";
        public String teleportedSpecifiedPlayerToCoordinates = "<dark_gray>» <gold>Teleported <red>{PLAYER} <gold>to location x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}";

        // Back
        public String teleportedToLastLocation = "<dark_gray>» <gray>Teleported to the last location!";
        public String teleportedSpecifiedPlayerLastLocation = "<dark_gray>» <gray>Teleported {PLAYER} to the last location!";
        public String lastLocationNoExist = "<dark_gray>» <red>Last location is not exist!";
    }

    @Getter @Contextual
    public static class ENChatSection implements ChatSection {
        public String disabled = "<dark_gray>» <red>Chat has been disabled by {NICK}!";
        public String enabled = "<dark_gray>» <green>The chat has been enabled by {NICK}!";
        public String cleared = "<dark_gray>» <gold>Chat has been cleared by {NICK}!";
        public String alreadyDisabled = "<dark_gray>» <green>Chat already off!";
        public String alreadyEnabled = "<dark_gray>» <green>Chat already on!";
        public String slowModeSet = "<dark_gray>» <green>Slowmode set to: {SLOWMODE}";
        public String slowMode = "<dark_gray>» <red>You can write the next message for: <gold>{TIME}";
        public String disabledChatInfo = "<dark_gray>» <red>Chat is currently disabled!";
        public String noCommand = "<dark_gray>» <red>Command <yellow>{COMMAND} <red>doesn't exists!";
    }

    @Getter @Contextual
    public static class ENTpaSection implements TpaSection {

        public String tpaSelfMessage = "<dark_gray>» <red>You can't teleport to yourself!";
        public String tpaAlreadySentMessage = "<dark_gray>» <red>You have already sent a teleportation request!";
        public String tpaSentMessage = "<dark_gray>» <green>You have sent a request for teleportation to a player: <gray>{PLAYER}<green>!";
        public String tpaReceivedMessage =
            """
             <dark_gray>» <green>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!
             <dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept!
             <dark_gray>» <gold>/tpdeny {PLAYER} <green>to deny!
            """;

        public String tpaDenyNoRequestMessage = "<dark_gray>» <red>You do not have a teleport request from this player!";
        public String tpaDenyNoRequestMessageAll = "<dark_gray>» <red>You do not have a teleport requests!";
        public String tpaDenyDoneMessage = "<dark_gray>» <red>You have declined a teleport request from a player: <gray>{PLAYER}<red>!";
        public String tpaDenyReceivedMessage = "<dark_gray>» <red>Player: <gray>{PLAYER} rejected your teleport request!";
        public String tpaDenyAllDenied = "<dark_gray>» <red>All players have denied your teleport request!";

        public String tpaAcceptMessage = "<dark_gray>» <green>You have accepted the teleportation from the player: <gray>{PLAYER}<green>!";
        public String tpaAcceptNoRequestMessage = "<dark_gray>» <red>This player has not sent you a teleport request!";
        public String tpaAcceptNoRequestMessageAll = "<dark_gray>» <red>You do not have a teleport requests!";
        public String tpaAcceptReceivedMessage = "<dark_gray>» <green>Player: <gray>{PLAYER} <green>accepted your teleportation request!";
        public String tpaAcceptAllAccepted = "<dark_gray>» <green>All players have accepted your teleport request!";
    }

    @Getter @Contextual
    public static class ENWarpSection implements WarpSection {
        public String availableList = "<dark_gray>» List available warps: {WARPS}";
        public String notExist = "<dark_gray>» <red>This warp doesn't exist";
        public String noPermission = "<dark_gray>» <red>You don't have permission to this warp!";
        public String disabled = "<dark_gray>» <red>This warp is currently disabled!";
        public String create = "<dark_gray>» <gray>Warp {name} has been created.";
        public String remove = "<dark_gray>» <gray>Warp {name} has been deleted.";
    }

    @Getter @Contextual
    public static class ENHomeSection implements HomeSection {
        public String notExist = "<dark_gray>» <red>This home doesn't exist";
        public String create = "<dark_gray>» <gray>Home {home} has been created.";
        public String delete = "<dark_gray>» <gray>Home {home} has been deleted.";
    }

    @Getter @Contextual
    public static class ENPrivateMessageSection implements PrivateMessageSection {
        public String noReply = "<dark_gray>» <red>You have no one to reply!";
        public String privateMessageYouToTarget = "<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}";
        public String privateMessageTargetToYou = "<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}";

        public String socialSpyMessage = "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}";
        public String socialSpyEnable = "<dark_gray>» <green>SocialSpy has been enabled!";
        public String socialSpyDisable = "<dark_gray>» <red>SocialSpy has been disabled!";

        public String ignorePlayer = "<dark_gray>» <red>{PLAYER} <gray>player has been ignored!";
        public String unIgnorePlayer = "<dark_gray>» <red>{PLAYER} <gray>player has been uningored!";
    }

    @Getter @Contextual
    public static class ENAfkSection implements AfkSection {
        public String afkOn = "<dark_gray>» <gray>{player} is AFK!";
        public String afkOff = "<dark_gray>» <gray>{player} is not AFK!";
    }

    @Getter @Contextual
    public static class ENOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "<red><bold>BROADCAST: <gray>{BROADCAST}";
        public String clearMessage = "<dark_gray>» <red>Your inventory has been cleared!";
        public String clearByMessage = "<dark_gray>» <red>Player {PLAYER} inventory cleared";
        public String disposalTitle = "<white><bold>Trash";
        public String foodMessage = "<dark_gray>» <green>You've been feed!";
        public String foodOtherMessage = "<dark_gray>» <green>You've fed the {PLAYER}";
        public String healMessage = "<dark_gray>» <green>You've been heal!";
        public String healedMessage = "<dark_gray>» <red>Healed <gold>{PLAYER}";
        public String nullHatMessage = "<dark_gray>» <red>You cannot use the /hat command.";
        public String repairMessage = "<dark_gray>» <green>Repaired!";
        public String skullMessage = "<dark_gray>» <green>Player {NICK} heads received";

        public String killSelf = "<dark_gray>» <red>You kill yourself!";
        public String killedMessage = "<dark_gray>» <red>Killed {PLAYER}";

        public String speedBetweenZeroAndTen = "<dark_gray>» <red>Enter speed from 0 to 10!";
        public String speedSet = "<dark_gray>» <red>Speed is set to {SPEED}";
        public String speedSetBy = "<dark_gray>» <red>Speed for {PLAYER} is set to {SPEED}";

        public String godMessage = "<dark_gray>» <red>God is now {STATE}";
        public String godSetMessage = "<dark_gray>» <red>Player <gold>{PLAYER} god is now: {STATE}";

        public String flyMessage = "<dark_gray>» <red>Fly is now {STATE}";
        public String flySetMessage = "<dark_gray>» <red>Fly for <gold>{PLAYER} <red>is now {STATE}";

        public String giveReceived = "<dark_gray>» <red>You have received: <gold>{ITEM}";
        public String giveGiven = "<dark_gray>» <red>Player <gold>{PLAYER} <red>has received <gold>{ITEM}";

        public String spawnSet = "<dark_gray>» <green>Spawn set!";
        public String spawnNoSet = "<dark_gray>» <red>Spawn is not set!";
        public String spawnTeleportedBy = "<dark_gray>» <red>You have been teleported to spawn by {PLAYER}!";
        public String spawnTeleportedOther = "<dark_gray>» <red>You teleported player {PLAYER} to spawn!";

        public String gameModeNotCorrect = "<dark_gray>» <red>Not a valid gamemode type";
        public String gameModeMessage = "<dark_gray>» <red>Gamemode now is set to: {GAMEMODE}";
        public String gameModeSetMessage = "<dark_gray>» <red>Gamemode for <gold>{PLAYER} <red>now is set to: <gold>{GAMEMODE}";
        public String pingMessage = "<dark_gray>» <red>Your ping is: <gold>{PING}ms";
        public String pingOtherMessage = "<dark_gray>» <red>Ping of the <gold>{PLAYER} <red>is: <gold>{PING}ms";
        public String onlineMessage = "<dark_gray>» <gold>On server now is: <white>{ONLINE} <gold>players!";
        public String listMessage = "<dark_gray>» <gold>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}";

        public String itemChangeNameMessage = "<dark_gray>» <gray>Name has been changed to: <red>{ITEM_NAME}";
        public String itemClearNameMessage = "<dark_gray>» <gray>Name has been cleared!";
        public String itemChangeLoreMessage = "<dark_gray>» <gray>Lore has been changed to: <red>{ITEM_LORE}";
        public String itemClearLoreMessage = "<dark_gray>» <gray>Lore has been cleared!";
        public String itemFlagRemovedMessage = "<dark_gray>» <gray>Flag {ITEM_FLAG} has been removed!";
        public String itemFlagAddedMessage = "<dark_gray>» <gray>Flag {ITEM_FLAG} has been added!";
        public String itemFlagClearedMessage = "<dark_gray>» <gray>Flags have been cleared!";

        public String enchantedMessage = "<dark_gray>» <gold>Item in hand is enchanted!";
        public String languageChanged = "<dark_gray>» <gold>Language changed to <red>English<gold>!";

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
}
