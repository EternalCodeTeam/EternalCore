package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ENMessagesConfiguration extends AbstractConfigWithResource implements Messages {
    private final Language language = Language.EN;

    public ENArgumentSection argument = new ENArgumentSection();
    public ENFormatSection format = new ENFormatSection();
    public ENHelpOpSection helpOp = new ENHelpOpSection();
    public ENAdminChatSection adminChat = new ENAdminChatSection();
    public ENTeleportSection teleport = new ENTeleportSection();
    public ENChatSection chat = new ENChatSection();
    public ENTpaSection tpa = new ENTpaSection();
    public ENWarpSection warp = new ENWarpSection();
    public ENOtherMessages other = new ENOtherMessages();

    public ENMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public ENArgumentSection argument() {
        return this.argument;
    }

    public ENFormatSection format() {
        return this.format;
    }

    public ENHelpOpSection helpOp() {
        return this.helpOp;
    }

    public ENAdminChatSection adminChat() {
        return this.adminChat;
    }

    public ENTeleportSection teleport() {
        return this.teleport;
    }

    public ENChatSection chat() {
        return this.chat;
    }

    public ENTpaSection tpa() {
        return this.tpa;
    }

    public ENWarpSection warp() {
        return this.warp;
    }

    public Language getLanguage() {
        return this.language;
    }

    public ENOtherMessages other() {
        return this.other;
    }

    @Contextual
    public static class ENFormatSection implements Messages.Format {
        public String enable = "&aenabled";
        public String disable = "&cdisabled";

        public String formatEnable() {
            return this.enable;
        }

        public String formatDisable() {
            return this.disable;
        }
    }

    @Contextual
    public static class ENWarpSection implements Messages.WarpSection {

        public String availableList = "&8» List available warps: {WARPS}";
        public String notExist = "&8» &cThis warp doesn't exist";
        public String noPermission = "&8» &cYou don't have permission to this warp!";
        public String disabled = "&8» &cThis warp is currently disabled!";

        public String availableList() {
            return this.availableList;
        }

        public String notExist() {
            return this.notExist;
        }

        public String noPermission() {
            return this.noPermission;
        }

        public String disabled() {
            return this.disabled;
        }
    }

    @Contextual
    public static class ENArgumentSection implements Messages.ArgumentSection {
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

        public String permissionMessage() {
            return this.permissionMessage;
        }

        public String usageMessage() {
            return this.usageMessage;
        }

        public String offlinePlayer() {
            return this.offlinePlayer;
        }

        public String onlyPlayer() {
            return this.onlyPlayer;
        }

        public String notNumber() {
            return this.notNumber;
        }

        public String numberBiggerThanOrEqualZero() {
            return this.numberBiggerThanOrEqualZero;
        }

        public String noItem() {
            return this.noItem;
        }

        public String noMaterial() {
            return this.noMaterial;
        }

        public String noArgument() {
            return this.noArgument;
        }

        public String noDamaged() {
            return this.noDamaged;
        }

        public String noDamagedItems() {
            return this.noDamagedItems;
        }

        public String noEnchantment() {
            return this.noEnchantment;
        }

        public String noValidEnchantmentLevel() {
            return this.noValidEnchantmentLevel;
        }
    }

    @Contextual
    public static class ENHelpOpSection implements Messages.HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aThis message has been successfully sent to administration";
        public String coolDown = "&8» &cYou can use this command for: &6{TIME}";

        public String format() {
            return this.format;
        }

        public String send() {
            return this.send;
        }

        public String coolDown() {
            return this.coolDown;
        }
    }

    @Contextual
    public static class ENAdminChatSection implements Messages.AdminChatSection {
        public String format = "&8[&4AdminChat&8] &c{NICK}&8: &f{TEXT}";

        public String format() {
            return this.format;
        }
    }

    @Contextual
    public static class ENTeleportSection implements Messages.TeleportSection {
        public String actionBarMessage = "&aTeleporting in &f{TIME}";
        public String cancel = "&8» &cYou've moved, teleportation canceled!";
        public String teleported = "&8» &aTeleported!";
        public String teleporting = "&8» &aTeleporting...";
        public String haveTeleport = "&8» &cYou are in teleport!";

        public String actionBarMessage() {
            return this.actionBarMessage;
        }

        public String cancel() {
            return this.cancel;
        }

        public String teleported() {
            return this.teleported;
        }

        public String teleporting() {
            return this.teleporting;
        }

        public String haveTeleport() {
            return this.haveTeleport;
        }
    }

    @Contextual
    public static class ENChatSection implements Messages.ChatSection {
        public String disabled = "&8» &cChat has been disabled by {NICK}!";
        public String enabled = "&8» &aThe chat has been enabled by {NICK}!";
        public String cleared = "&8» &6Chat has been cleared by {NICK}!";
        public String alreadyDisabled = "&8» &aChat already off!";
        public String alreadyEnabled = "&8» &aChat already on!";
        public String slowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
        public String slowMode = "&8» &cYou can write the next message for: &6{TIME}";
        public String disabledChatInfo = "&8» &cChat is currently disabled!";
        public String noCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";

        public String disabled() {
            return this.disabled;
        }

        public String enabled() {
            return this.enabled;
        }

        public String cleared() {
            return this.cleared;
        }

        public String alreadyDisabled() {
            return this.alreadyDisabled;
        }

        public String alreadyEnabled() {
            return this.alreadyEnabled;
        }

        public String slowModeSet() {
            return this.slowModeSet;
        }

        public String slowMode() {
            return this.slowMode;
        }

        public String disabledChatInfo() {
            return this.disabledChatInfo;
        }

        public String noCommand() {
            return this.noCommand;
        }
    }

    @Contextual
    public static class ENTpaSection implements TpaSection {

        public String tpaSelfMessage = "&8» &cYou can't teleport to yourself!";
        public String tpaAlreadySentMessage = "&8» &cYou have already sent a teleportation request!";
        public String tpaSentMessage = "&8» &aYou have sent a request for teleportation to a player: &7{PLAYER}&a!";
        public String tpaRecivedMessage = "&8» &aYou have received a request for teleportation from a player: &7{PLAYER}&a! " +
            "\n &8» &6/tpaccept {PLAYER} &ato accept! " +
            "\n &8» &6/tpdeny {PLAYER} &ato deny!";

        public String tpaDenyNoRequestMessage = "&8» &cYou do not have a teleport request from this player!";
        public String tpaDenyNoRequestAllMessage = "&8» &cYou do not have a teleport requests!";
        public String tpaDenyDoneMessage = "&8» &cYou have declined a teleport request from a player: &7{PLAYER}&c!";
        public String tpaDenyRecivedMessage = "&8» &cPlayer: &7{PLAYER} rejected your teleport request!";
        public String tpaDenyAllDenied = "&8» &cAll players have denied your teleport request!";

        public String tpaAcceptMessage = "&8» &aYou have accepted the teleportation from the player: &7{PLAYER}&a!";
        public String tpaAcceptNoRequestMessage = "&8» &cThis player has not sent you a teleport request!";
        public String tpaAcceptNoRequestAllMessage = "&8» &cYou do not have a teleport requests!";
        public String tpaAcceptRecivedMessage = "&8» &aPlayer: &7{PLAYER} &aaccepted your teleportation request!";
        public String tpaAcceptAllAccepted = "&8» &aAll players have accepted your teleport request!";

        @Override
        public String tpaSelfMessage() {
            return this.tpaSelfMessage;
        }

        @Override
        public String tpaAlreadySentMessage() {
            return this.tpaAlreadySentMessage;
        }

        @Override
        public String tpaSentMessage() {
            return this.tpaSentMessage;
        }

        @Override
        public String tpaRecivedMessage() {
            return this.tpaRecivedMessage;
        }

        @Override
        public String tpaDenyNoRequestMessage() {
            return this.tpaDenyNoRequestMessage;
        }

        @Override
        public String tpaDenyNoRequestMessageAll() {
            return this.tpaDenyNoRequestAllMessage;
        }

        @Override
        public String tpaDenyDoneMessage() {
            return this.tpaDenyDoneMessage;
        }

        @Override
        public String tpaDenyRecivedMessage() {
            return this.tpaDenyRecivedMessage;
        }

        @Override
        public String tpaDenyAllDenied() {
            return this.tpaDenyAllDenied;
        }

        @Override
        public String tpaAcceptMessage() {
            return this.tpaAcceptMessage;
        }

        @Override
        public String tpaAcceptNoRequestMessage() {
            return this.tpaAcceptNoRequestMessage;
        }

        @Override
        public String tpaAcceptNoRequestMessageAll() {
            return this.tpaAcceptNoRequestAllMessage;
        }

        @Override
        public String tpaAcceptRecivedMessage() {
            return this.tpaAcceptRecivedMessage;
        }

        @Override
        public String tpaAcceptAllAccepted() {
            return this.tpaAcceptAllAccepted;
        }
    }

    @Contextual
    public static class ENOtherMessages implements Messages.OtherMessages {
        public String successfullyTeleported = "&8» &aSuccessfully teleported to {PLAYER}!";
        public String successfullyTeleportedPlayer = "&8» &aSuccessfully teleported {PLAYER} to {ARG-PLAYER}!";
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
        public String spawnTeleportedBy = "&8» &cYou have been teleported to spawn by {NICK}!";
        public String spawnTeleportedOther = "&8» &cYou teleported player {NICK} to spawn!";
        public String gameModeNotCorrect = "&8» &cNot a valid gamemode type";
        public String gameModeMessage = "&8» &cGamemode now is set to: {GAMEMODE}";
        public String gameModeSetMessage = "&8» &cGamemode for &6{PLAYER} &cnow is set to: &6{GAMEMODE}";
        public String pingMessage = "&8» &cYour ping is: &6{PING}ms";
        public String pingOtherMessage = "&8» &cPing of the &6{PLAYER} &cis: &6{PING}ms";
        public String onlineMessage = "&8» &6On server now is: &f{ONLINE} &6players!";
        public String listMessage = "&8» &6On server is: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
        public String tposMessage = "&8» &6Teleported to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String tposByMessage = "&8» &6Teleported &c{PLAYER} &6to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String nameMessage = "&8» &6New name is: &6{NAME}";
        public String enchantedMessage = "&8» &6Item in hand is enchanted!";
        public String languageChanged = "&8» &6Language changed to &cEnglish&6!";
        public String privateMessageSendFormat = "&8[&7You -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String privateMessageReceiveFormat = "&8[&7{SENDER} -> &fYou&8]&7: &f{MESSAGE}";

        public List<String> whoisCommand = Arrays.asList("&8» &7Target name: &f{PLAYER}",
            "&8» &7Target UUID: &f{UUID}",
            "&8» &7Target address: &f{IP}",
            "&8» &7Target walk speed: &f{WALK-SPEED}",
            "&8» &7Target fly speed: &f{SPEED}",
            "&8» &7Target ping: &f{PING}ms",
            "&8» &7Target level: &f{LEVEL}",
            "&8» &7Target health: &f{HEALTH}",
            "&8» &7Target food level: &f{FOOD}");

        public String successfullyTeleported() {
            return this.successfullyTeleported;
        }

        public String successfullyTeleportedPlayer() {
            return this.successfullyTeleportedPlayer;
        }

        public String alertMessagePrefix() {
            return this.alertMessagePrefix;
        }

        public String clearMessage() {
            return this.clearMessage;
        }

        public String clearByMessage() {
            return this.clearByMessage;
        }

        public String disposalTitle() {
            return this.disposalTitle;
        }

        public String foodMessage() {
            return this.foodMessage;
        }

        public String foodOtherMessage() {
            return this.foodOtherMessage;
        }

        public String healMessage() {
            return this.healMessage;
        }

        public String healedMessage() {
            return this.healedMessage;
        }

        public String nullHatMessage() {
            return this.nullHatMessage;
        }

        public String repairMessage() {
            return this.repairMessage;
        }

        public String skullMessage() {
            return this.skullMessage;
        }

        public String killSelf() {
            return this.killSelf;
        }

        public String killedMessage() {
            return this.killedMessage;
        }

        public String speedBetweenZeroAndTen() {
            return this.speedBetweenZeroAndTen;
        }

        public String speedSet() {
            return this.speedSet;
        }

        public String speedSetBy() {
            return this.speedSetBy;
        }

        public String godMessage() {
            return this.godMessage;
        }

        public String godSetMessage() {
            return this.godSetMessage;
        }

        public String flyMessage() {
            return this.flyMessage;
        }

        public String flySetMessage() {
            return this.flySetMessage;
        }

        public String giveReceived() {
            return this.giveReceived;
        }

        public String giveGiven() {
            return this.giveGiven;
        }

        public String spawnSet() {
            return this.spawnSet;
        }

        public String spawnNoSet() {
            return this.spawnNoSet;
        }

        public String spawnTeleportedBy() {
            return this.spawnTeleportedBy;
        }

        public String spawnTeleportedOther() {
            return this.spawnTeleportedOther;
        }

        public String gameModeNotCorrect() {
            return this.gameModeNotCorrect;
        }

        public String gameModeMessage() {
            return this.gameModeMessage;
        }

        public String gameModeSetMessage() {
            return this.gameModeSetMessage;
        }

        public String pingMessage() {
            return this.pingMessage;
        }

        public String pingOtherMessage() {
            return this.pingOtherMessage;
        }

        public String onlineMessage() {
            return this.onlineMessage;
        }

        public String listMessage() {
            return this.listMessage;
        }

        public String tposMessage() {
            return this.tposMessage;
        }

        public String tposByMessage() {
            return this.tposByMessage;
        }

        public String nameMessage() {
            return this.nameMessage;
        }

        public List<String> whoisCommand() {
            return this.whoisCommand;
        }

        public String enchantedMessage() {
            return this.enchantedMessage;
        }

        public String languageChanged() {
            return this.languageChanged;
        }

        @Override
        public String privateMessageSendFormat() {
            return this.privateMessageSendFormat;
        }

        @Override
        public String privateMessageReceiveFormat() {
            return this.privateMessageReceiveFormat;
        }
    }
}
