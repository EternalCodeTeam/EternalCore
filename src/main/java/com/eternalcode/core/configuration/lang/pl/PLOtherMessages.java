package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.Arrays;
import java.util.List;

@Getter @Accessors(fluent = true)
@Contextual
public class PLOtherMessages implements Messages.OtherMessages {

    public String successfullyReloaded = "&8» &aPrzeładowano plugin!";
    public String successfullyTeleported = "&8» &aPrzeteleportowano do {PLAYER}!";
    public String successfullyTeleportedPlayer = "&8» &aPrzeteleportowano {PLAYER} to {ARG-PLAYER}!";
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
    public String onlineMessage = "&8» &6On server now is: &f{ONLINE} &6players!";
    public String listMessage = "&8» &6On server is: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
    public String tposMessage = "&8» &6Teleported to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
    public String tposByMessage = "&8» &6Teleported &c{PLAYER} &6to location x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
    public String nameMessage = "&8» &6New name is: &6{NAME}";
    public List<String> whoisCommand = Arrays.asList("&8» &7Gracz: &f{PLAYER}",
        "&8» &7UUID: &f{UUID}",
        "&8» &7IP: &f{IP}",
        "&8» &7Szybkość chodzenia: &f{WALK-SPEED}",
        "&8» &7Szybkość latania: &f{SPEED}",
        "&8» &7Opóźnienie: &f{PING}ms",
        "&8» &7Poziom: &f{LEVEL}",
        "&8» &7Zdrowie: &f{HEALTH}",
        "&8» &7Poziom najedzenia: &f{FOOD}");

}
