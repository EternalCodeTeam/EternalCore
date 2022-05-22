package com.eternalcode.core.configuration.lang;

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
public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public PLArgumentSection argument = new PLArgumentSection();
    public PLFormatSection format = new PLFormatSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLWarpSection warp = new PLWarpSection();
    public PLPrivateMessage privateMessage = new PLPrivateMessage();
    public PLTpaSection tpa = new PLTpaSection();
    public PLOtherMessages other = new PLOtherMessages();

    @Override
    public Language getLanguage() {
        return Language.PL;
    }

    @Getter @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public String permissionMessage = "&4Blad: &cNie masz uprawnien do tej komendy! &7({PERMISSIONS})";
        public String usageMessage = "&8» &ePoprawne uzycie: &7{USAGE}";
        public String offlinePlayer = "&4Blad: &cTen gracz jest offline!";
        public String onlyPlayer = "&4Blad: &cKomenda tylko dla graczy!";
        public String notNumber = "&4Blad: &cArgument nie jest liczba!";
        public String numberBiggerThanOrEqualZero = "&4Blad: &cLiczba musi byc rowna lub wieksza 0!";
        public String noItem = "&4Blad: &cMusisz miec item w rece!";
        public String noMaterial = "&4Blad: &cTaki material nie istnieje!";
        public String noArgument = "&4Blad: &cTaki argument nie istnieje!";
        public String noDamaged = "&4Blad: &cTen przedmiot nie moze być naprawiony!";
        public String noDamagedItems = "&4Blad: &cMusisz miec uszkodzone przedmioty!";
        public String noEnchantment = "&4Blad: &cTaki enchant nie istnieje!";
        public String noValidEnchantmentLevel = "&4Blad: &cTen poziom enchantu nie jest wspierany!";
    }

    @Getter @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "&awlaczone";
        public String disable = "&cwylaczone";
    }

    @Getter @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aWiadomość została wysłana do administracji";
        public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";
    }

    @Getter @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public String format = "&8[&4Administracja&8] &c{NICK}&8: &f{TEXT}";
    }

    @Getter @Contextual
    public static class PLTeleportSection implements TeleportSection {
        public String actionBarMessage = "&aTeleportacja za &f{TIME}";
        public String cancel = "&4Blad: &cRuszyłeś się, teleportacja przerwana!";
        public String teleported = "&8» &aPrzeteleportowano!";
        public String teleporting = "&8» &aTeleportuje...";
        public String haveTeleport = "&4Blad: &cTeleportujesz się już!";

        public String successfullyTeleported = "&8» &aPrzeteleportowano do {PLAYER}!";
        public String successfullyTeleportedPlayer = "&8» &aPrzeteleportowano {PLAYER} do {ARG-PLAYER}!";
        public String tposMessage = "&8» &6Przeteleportowano na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String tposByMessage = "&8» &6Przeteleportowano &c{PLAYER} &6na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
    }

    @Getter @Contextual
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

    @Getter @Contextual
    public static class PLTpaSection implements TpaSection {
        public String tpaSelfMessage = "&4Blad: &cNie mozesz siebie teleportowac!";
        public String tpaAlreadySentMessage = "&4Blad: &cWyslales juz prosbe o teleportacje!";
        public String tpaSentMessage = "&8» &aWyslales prosbe o teleportacje do gracza: &7{PLAYER}&a!";
        public String tpaReceivedMessage =
            """
             &8» &aOtrzymales prosbe o teleportacje od gracza: &7{PLAYER}&a!
             &8» &6/tpaccept {PLAYER} &aaby zaakceptowac!
             &8» &6/tpdeny {PLAYER} &aaby odrzucic!
             """;

        public String tpaDenyNoRequestMessage = "&4Blad: &cNie masz prosby o teleportacje od tego gracza!";
        public String tpaDenyNoRequestMessageAll = "&4Blad: &cNie masz zadnych prosb o teleportacje!";
        public String tpaDenyDoneMessage = "&8» &cOdrzuciles prosbe o teleportacje od gracza: &7{PLAYER}&c!";
        public String tpaDenyReceivedMessage = "&8» &cGracz: {PLAYER} odrzucil twoja prosbe o teleportacje!";
        public String tpaDenyAllDenied = "&8» &cWszystkie prosby o teleportacje zostaly odrzucone!";

        public String tpaAcceptMessage = "&8» &aZaakceptowales teleportacje od gracza: &7{PLAYER}&a!";
        public String tpaAcceptNoRequestMessage = "&4Blad: &cTen gracz nie wyslal ci prosby o teleportacje!";
        public String tpaAcceptNoRequestMessageAll = "&4Blad: &cNie masz zadnych prosb o teleportacje!";
        public String tpaAcceptReceivedMessage = "&8» &aGracz: &7{PLAYER} &azaakceptowal twoja prosbe o teleportacje!";
        public String tpaAcceptAllAccepted = "&8» &aWszystkie prosby o teleportacje zostaly zaakceptowane!";
    }

    @Getter @Contextual
    public static class PLWarpSection implements WarpSection {
        public String availableList = "&8» Lista warpów: {WARPS}";
        public String notExist = "&8» &cNie odnaleziono takiego warpa!";
        public String noPermission = "&8» &cNie masz uprawnien do tego warpa!";
        public String disabled = "&8» &cTen warp jest aktualnie wyłączony!";
        public String create = "&8 » &7Stworzono warpa {name}!";
        public String remove = "&8 » &7Usunięto warpa {name}!";
    }

    @Getter @Contextual
    public static class PLPrivateMessage implements PrivateMessage {
        public String noReply = "&8 » &cYou have no one to reply!";
        public String sendFormat = "&8[&7Ty -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String receiveFormat = "&8[&7{SENDER} -> &fTy&8]&7: &f{MESSAGE}";
    }

    @Getter @Contextual
    public static class PLOtherMessages implements OtherMessages {
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
        public String nameMessage = "&8» &6Nowa nazwa itemu: &c{NAME}";
        public String enchantedMessage = "&8» &6Item w rece zostal zenchantowany!";
        public String languageChanged = "&8» &6Zmieniono język na &cPolski&6!";

        public List<String> whoisCommand = List.of("&8» &7Gracz: &f{PLAYER}",
            "&8» &7UUID: &f{UUID}",
            "&8» &7IP: &f{IP}",
            "&8» &7Szybkość chodzenia: &f{WALK-SPEED}",
            "&8» &7Szybkość latania: &f{SPEED}",
            "&8» &7Opóźnienie: &f{PING}ms",
            "&8» &7Poziom: &f{LEVEL}",
            "&8» &7Zdrowie: &f{HEALTH}",
            "&8» &7Poziom najedzenia: &f{FOOD}"
        );
    }

}
