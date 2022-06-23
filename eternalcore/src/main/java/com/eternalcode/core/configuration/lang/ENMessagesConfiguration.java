package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

import java.io.File;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class ENMessagesConfiguration extends AbstractConfigWithResource implements Messages {

    public ENMessagesConfiguration(File folder, String child) {
        super(folder, child);
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

    @Override
    public Language getLanguage() {
        return Language.EN;
    }

    @Getter @Contextual
    public static class ENArgumentSection implements ArgumentSection {
        public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSIONS})";
        public String usageMessage = "&8» &eCorrect usage: &7{USAGE}";
        public String offlinePlayer = "&8» &cThis player is currently offline!";
        public String onlyPlayer = "&8» &cCommand is only for players!";
        public String notNumber = "&8» &cArgument isnt a number!";
        public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";
        public String noItem = "&8» &cYou need item to use this command!";
        public String noMaterial = "&8» &cThis item doesn't exist";
        public String noArgument = "&8» &cThis argument doesn't exist";
        public String noDamaged = "&8» &cThis item can't be repaired";
        public String noDamagedItems = "&8» &cYou need damaged items to use this command!";
        public String noEnchantment = "&8» &cThis enchantment doesn't exist";
        public String noValidEnchantmentLevel = "&8» &cThis enchantment level is not supported!";
    }

    @Getter @Contextual
    public static class ENFormatSection implements Format {
        public String enable = "&aenabled";
        public String disable = "&cdisabled";
    }

    @Getter @Contextual
    public static class ENHelpOpSection implements HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aThis message has been successfully sent to administration";
        public String coolDown = "&8» &cYou can use this command for: &6{TIME}";
    }

    @Getter @Contextual
    public static class ENAdminChatSection implements AdminChatSection {
        public String format = "&8[&4AdminChat&8] &c{NICK}&8: &f{TEXT}";
    }

    @Getter @Contextual
    public static class ENTeleportSection implements TeleportSection {
        // teleport
        public String teleportedToPlayer = "&8» &aSuccessfully teleported to {PLAYER}!";
        public String teleportedPlayerToPlayer = "&8» &aSuccessfully teleported {PLAYER} to {ARG-PLAYER}!";

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("&aTeleporting in &f{TIME}");
        public String teleported = "&8» &aTeleported!";
        public String teleporting = "&8» &aTeleporting...";
        public String teleportTaskCanceled = "&8» &cYou've moved, teleportation canceled!";
        public String teleportTaskAlreadyExist = "&8» &cYou are in teleport!";

        // Coordinates XYZ
        public String teleportedToCoordinates = "&8» &6Teleported to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String teleportedSpecifiedPlayerToCoordinates = "&8» &6Teleported &c{PLAYER} &6to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";

        // Back
        public String teleportedToLastLocation = "&8 » &7Teleported to the last location!";
        public String teleportedSpecifiedPlayerLastLocation = "&8 » &7Teleported {PLAYER} to the last location!";
        public String lastLocationNoExist = "&8 » &cLast location is not exist!";
    }

    @Getter @Contextual
    public static class ENChatSection implements ChatSection {
        public String disabled = "&8» &cChat has been disabled by {NICK}!";
        public String enabled = "&8» &aThe chat has been enabled by {NICK}!";
        public String cleared = "&8» &6Chat has been cleared by {NICK}!";
        public String alreadyDisabled = "&8» &aChat already off!";
        public String alreadyEnabled = "&8» &aChat already on!";
        public String slowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
        public String slowMode = "&8» &cYou can write the next message for: &6{TIME}";
        public String disabledChatInfo = "&8» &cChat is currently disabled!";
        public String noCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";
    }

    @Getter @Contextual
    public static class ENTpaSection implements TpaSection {

        public String tpaSelfMessage = "&8» &cYou can't teleport to yourself!";
        public String tpaAlreadySentMessage = "&8» &cYou have already sent a teleportation request!";
        public String tpaSentMessage = "&8» &aYou have sent a request for teleportation to a player: &7{PLAYER}&a!";
        public String tpaReceivedMessage =
            """
             &8» &aYou have received a request for teleportation from a player: &7{PLAYER}&a!
             &8» &6/tpaccept {PLAYER} &ato accept!
             &8» &6/tpdeny {PLAYER} &ato deny!
            """;

        public String tpaDenyNoRequestMessage = "&8» &cYou do not have a teleport request from this player!";
        public String tpaDenyNoRequestMessageAll = "&8» &cYou do not have a teleport requests!";
        public String tpaDenyDoneMessage = "&8» &cYou have declined a teleport request from a player: &7{PLAYER}&c!";
        public String tpaDenyReceivedMessage = "&8» &cPlayer: &7{PLAYER} rejected your teleport request!";
        public String tpaDenyAllDenied = "&8» &cAll players have denied your teleport request!";

        public String tpaAcceptMessage = "&8» &aYou have accepted the teleportation from the player: &7{PLAYER}&a!";
        public String tpaAcceptNoRequestMessage = "&8» &cThis player has not sent you a teleport request!";
        public String tpaAcceptNoRequestMessageAll = "&8» &cYou do not have a teleport requests!";
        public String tpaAcceptReceivedMessage = "&8» &aPlayer: &7{PLAYER} &aaccepted your teleportation request!";
        public String tpaAcceptAllAccepted = "&8» &aAll players have accepted your teleport request!";
    }

    @Getter @Contextual
    public static class ENWarpSection implements WarpSection {
        public String availableList = "&8» List available warps: {WARPS}";
        public String notExist = "&8» &cThis warp doesn't exist";
        public String noPermission = "&8» &cYou don't have permission to this warp!";
        public String disabled = "&8» &cThis warp is currently disabled!";
        public String create = "&8 » &7Warp {name} has been created.";
        public String remove = "&8 » &7Warp {name} has been deleted.";
    }

    @Getter @Contextual
    public static class ENHomeSection implements HomeSection {
        public String notExist = "&8» &cThis home doesn't exist";
        public String create = "&8 » &7Home {home} has been created.";
        public String delete = "&8 » &7Home {home} has been deleted.";
    }

    @Getter @Contextual
    public static class ENPrivateMessageSection implements PrivateMessageSection {
        public String noReply = "&8 » &cYou have no one to reply!";
        public String privateMessageYouToTarget = "&8[&7You -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String privateMessageTargetToYou = "&8[&7{SENDER} -> &fYou&8]&7: &f{MESSAGE}";

        public String socialSpyMessage = "&8[&css&8] &8[&7{SENDER} -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String socialSpyEnable = "&8 » &aSocialSpy has been enabled!";
        public String socialSpyDisable = "&8 » &cSocialSpy has been disabled!";

        public String ignorePlayer = "&8 » &c{PLAYER} &7player has been ingored!";
        public String unIgnorePlayer = "&8 » &c{PLAYER} &7player has been uningored!";
    }

    @Getter @Contextual
    public static class ENAfkSection implements AfkSection {
        public String afkOn = "&8 » &7{player} is AFK!";
        public String afkOff = "&8 » &7{player} is not AFK!";
    }

    @Getter @Contextual
    public static class ENOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "&c&lBROADCAST: &7{BROADCAST}";
        public String clearMessage = "&8» &cYour inventory has been cleared!";
        public String clearByMessage = "&8» &cPlayer {PLAYER} inventory cleared";
        public String disposalTitle = "&f&lTrash";
        public String foodMessage = "&8» &aYou've been feed!";
        public String foodOtherMessage = "&8» &aYou've fed the {PLAYER}";
        public String healMessage = "&8» &aYou've been heal!";
        public String healedMessage = "&8» &cHealed &6{PLAYER}";
        public String nullHatMessage = "&8» &cYou cannot use the /hat command.";
        public String repairMessage = "&8» &aRepaired!";
        public String skullMessage = "&8» &aPlayer {NICK} heads received";

        public String killSelf = "&8» &cYou kill yourself!";
        public String killedMessage = "&8» &cKilled {PLAYER}";

        public String speedBetweenZeroAndTen = "&8» &cEnter speed from 0 to 10!";
        public String speedSet = "&8» &cSpeed is set to {SPEED}";
        public String speedSetBy = "&8» &cSpeed for {PLAYER} is set to {SPEED}";

        public String godMessage = "&8» &cGod is now {STATE}";
        public String godSetMessage = "&8» &cPlayer &6{PLAYER} god is now: {STATE}";

        public String flyMessage = "&8» &cFly is now {STATE}";
        public String flySetMessage = "&8» &cFly for &6{PLAYER} &cis now {STATE}";

        public String giveReceived = "&8» &cYou have received: &6{ITEM}";
        public String giveGiven = "&8» &cPlayer &6{PLAYER} &chas received &6{ITEM}";

        public String spawnSet = "&8» &aSpawn set!";
        public String spawnNoSet = "&8» &cSpawn is not set!";
        public String spawnTeleportedBy = "&8» &cYou have been teleported to spawn by {PLAYER}!";
        public String spawnTeleportedOther = "&8» &cYou teleported player {PLAYER} to spawn!";

        public String gameModeNotCorrect = "&8» &cNot a valid gamemode type";
        public String gameModeMessage = "&8» &cGamemode now is set to: {GAMEMODE}";
        public String gameModeSetMessage = "&8» &cGamemode for &6{PLAYER} &cnow is set to: &6{GAMEMODE}";
        public String pingMessage = "&8» &cYour ping is: &6{PING}ms";
        public String pingOtherMessage = "&8» &cPing of the &6{PLAYER} &cis: &6{PING}ms";
        public String onlineMessage = "&8» &6On server now is: &f{ONLINE} &6players!";
        public String listMessage = "&8» &6On server is: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
        public String nameMessage = "&8» &6New name is: &6{NAME}";
        public String enchantedMessage = "&8» &6Item in hand is enchanted!";
        public String languageChanged = "&8» &6Language changed to &cEnglish&6!";

        public List<String> whoisCommand = List.of("&8» &7Target name: &f{PLAYER}",
            "&8» &7Target UUID: &f{UUID}",
            "&8» &7Target address: &f{IP}",
            "&8» &7Target walk speed: &f{WALK-SPEED}",
            "&8» &7Target fly speed: &f{SPEED}",
            "&8» &7Target ping: &f{PING}ms",
            "&8» &7Target level: &f{LEVEL}",
            "&8» &7Target health: &f{HEALTH}",
            "&8» &7Target food level: &f{FOOD}"
        );
    }
}
