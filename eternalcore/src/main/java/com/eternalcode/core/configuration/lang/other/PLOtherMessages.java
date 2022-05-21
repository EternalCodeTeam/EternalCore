package com.eternalcode.core.configuration.lang.other;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.Arrays;
import java.util.List;

@Contextual
public class PLOtherMessages implements Messages.OtherMessages {
    public String successfullyTeleported = "&8» &aPrzeteleportowano do {PLAYER}!";
    public String successfullyTeleportedPlayer = "&8» &aPrzeteleportowano {PLAYER} do {ARG-PLAYER}!";
    public String alertMessagePrefix = "&c&lOGLOSZENIE: &7{BROADCAST}";
    public String clearMessage = "&8» &aWyczyszczono ekwipunek!";
    public String clearByMessage = "&8» &aWyczyszczono ekwipunek gracza {PLAYER}";
    public String disposalTitle = "&f&lKosz";
    public String foodMessage = "&8» &aZostałeś najedzony!";
    public String foodOtherMessage = "&8» &aNajadłeś gracza {PLAYER}";
    public String healMessage = "&8» &aZostałeś uleczony!";
    public String healedMessage = "&8» &aUleczyłeś gracza {PLAYER}";
    public String nullHatMessage = "&4Blad: &cNie możesz użyć /hat!";
    public String repairMessage = "&8» &aNaprawiono!";
    public String skullMessage = "&8» &aOtrzymałeś głowę gracza {PLAYER}";
    public String killSelf = "&8» &cPopełniłeś samobójstwo!";
    public String killedMessage = "&8» &cZabito gracza {PLAYER}";
    public String speedBetweenZeroAndTen = "&4Blad: &cUstaw speed w przedziale 0-10!";
    public String speedSet = "&8» &aUstawiono speed na {SPEED}";
    public String speedSetBy = "&8» &cUstawiono {PLAYER} speeda na {SPEED}";
    public String godMessage = "&8» &aGod został {STATE}";
    public String godSetMessage = "&8» &aGod dla gracza &f{PLAYER} &azostał {STATE}";
    public String flyMessage = "&8» &aLatanie zostało {STATE}";
    public String flySetMessage = "&8» &aLatanie dla gracza &f{PLAYER} &azostało {STATE}";
    public String giveReceived = "&8» &aOtrzymałeś &6{ITEM}";
    public String giveGiven = "&8» &aGracz &f{PLAYER} &aotrzymał: &6{ITEM}";
    public String spawnSet = "&8» &aUstawiono spawn!";
    public String spawnNoSet = "&4Blad: &cSpawn nie jest ustawiony!";
    public String spawnTeleportedBy = "&8» &aZostałeś przeteleportowany na spawn przez {NICK}!";
    public String spawnTeleportedOther = "&8» &aGracz &f{NICK} &azostał przeteleportowany na spawn!";
    public String gameModeNotCorrect = "&4Blad: &cNie prawidłowy typ!";
    public String gameModeMessage = "&8» &aUstawiono tryb gry na: {GAMEMODE}";
    public String gameModeSetMessage = "&8» &aUstawiono tryb gry graczowi &f{PLAYER} &ana: &f{GAMEMODE}";
    public String pingMessage = "&8» &aTwój ping: &f{PING}ms";
    public String pingOtherMessage = "&8» &aGracz &f{PLAYER} &ama: &f{PING}ms";
    public String onlineMessage = "&8» &6Na serwerze jest: &f{ONLINE} &6graczy online!";
    public String listMessage = "&8» &6Na serwerze jest: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
    public String tposMessage = "&8» &6Przeteleportowano na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
    public String tposByMessage = "&8» &6Przeteleportowano &c{PLAYER} &6na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
    public String nameMessage = "&8» &6Nowa nazwa itemu: &c{NAME}";
    public String enchantedMessage = "&8» &6Item w rece zostal zenchantowany!";
    public String languageChanged = "&8» &6Zmieniono język na &cPolski&6!";

    public List<String> whoisCommand = Arrays.asList("&8» &7Gracz: &f{PLAYER}",
        "&8» &7UUID: &f{UUID}",
        "&8» &7IP: &f{IP}",
        "&8» &7Szybkość chodzenia: &f{WALK-SPEED}",
        "&8» &7Szybkość latania: &f{SPEED}",
        "&8» &7Opóźnienie: &f{PING}ms",
        "&8» &7Poziom: &f{LEVEL}",
        "&8» &7Zdrowie: &f{HEALTH}",
        "&8» &7Poziom najedzenia: &f{FOOD}");

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
