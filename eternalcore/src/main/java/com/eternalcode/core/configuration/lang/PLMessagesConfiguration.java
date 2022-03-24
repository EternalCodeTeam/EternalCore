package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.configuration.AbstractConfigWithResource;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter @Accessors(fluent = true)
public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public PLArgumentSection argument = new PLArgumentSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLOtherMessages other = new PLOtherMessages();

    @Accessors
    private final Language language = Language.PL;

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLArgumentSection implements ArgumentSection {
        public String permissionMessage = "&4Blad: &cNie masz uprawnien do tej komendy! &7({PERMISSIONS})";
        public String offlinePlayer = "&4Blad: &cTen gracz jest offline!";
        public String onlyPlayer = "&4Blad: &cKomenda tylko dla graczy!";
        public String notNumber = "&4Blad: &cArgument nie jest liczba!";
        public String numberBiggerThanOrEqualZero = "&4Blad: &cLiczba musi byc rowna lub wieksza 0!";
        public String noItem = "&4Blad: &cMusisz miec item w rece!";
        public String noMaterial = "&4Blad: &cTaki material nie istenieje!";
        public String noArgument = "&4Blad: &cTaki argument nie istenieje!";
    }

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLHelpOpSection implements HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aWiadomość została wysłana do administracji";
        public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";
    }

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLAdminChatSection implements AdminChatSection {
        public String format = "&8[&4Administracja&8] &c{NICK}&8: &f{TEXT}";
    }

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLTeleportSection implements TeleportSection {
        public String actionBarMessage = "&aTeleportacja za &f{TIME}";
        public String cancel = "&4Blad: &cRuszyłeś się, teleportacja przerwana!";
        public String teleported = "&8» &aPrzeteleportowano!";
        public String teleporting = "&8» &aTeleportuje...";
        public String haveTeleport = "&4Blad: &cTeleportujesz się już!";
    }

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLChatSection implements ChatSection {
        public String disabled = "&8» &cCzat został wyłączony przez &6{NICK}&c!";
        public String enabled = "&8» &aCzat został włączony przez &f{NICK}&a!";
        public String cleared = "&8» &6Czat zotał wyczyszczony przez &c{NICK}&6!";
        public String alreadyDisabled = "&4Blad: &cCzat jest juz wylaczony!";
        public String alreadyEnabled = "&4Blad: &cCzat jest juz wlaczony!";
        public String slowModeSet = "&8» &aSlowmode został ustawiony na {SLOWMODE}";
        public String slowMode = "&8» &cNastępną wiadomość możesz wysłać za: &6{TIME}&c!";
        public String disabledChatInfo = "&8» &cCzat jest aktualnie wyłaczony!";
        public String noCommand = "&8» &cKomenda &e{COMMAND} &cnie istnieje!";
    }

    @Contextual @Getter @Accessors(fluent = true)
    public static class PLOtherMessages implements OtherMessages {
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
        public String onlineMessage = "&8» &6Na serwerze jest: &f{ONLINE} &6graczy online!";
        public String listMessage = "&8» &6Na serwerze jest: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
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


}
