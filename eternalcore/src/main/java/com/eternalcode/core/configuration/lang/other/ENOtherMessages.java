package com.eternalcode.core.configuration.lang.other;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.Arrays;
import java.util.List;

@Contextual
public class ENOtherMessages implements Messages.OtherMessages {
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

}
