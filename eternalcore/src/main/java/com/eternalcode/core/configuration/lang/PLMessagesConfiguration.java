package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.configuration.ReloadableMessages;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLMessagesConfiguration implements ReloadableMessages {

    @Override
    public Language getLanguage() {
        return Language.PL;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + "pl_messages.yml");
    }

    public PLArgumentSection argument = new PLArgumentSection();
    public PLFormatSection format = new PLFormatSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLWarpSection warp = new PLWarpSection();
    public PLHomeSection home = new PLHomeSection();
    public PLPrivateMessageSection privateMessage = new PLPrivateMessageSection();
    public PLTpaSection tpa = new PLTpaSection();
    public PLOtherMessages other = new PLOtherMessages();
    public PLAfkSection afk = new PLAfkSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public String permissionMessage = "&4Błąd: &cNie masz uprawnień do tej komendy! &7({PERMISSIONS})";
        public String usageMessage = "&8» &ePoprawne użycie: &7{USAGE}";
        public String usageMessageHead = "&8» &ePoprawne użycie:";
        public String usageMessageEntry = "&8» &7{USAGE}";
        public String offlinePlayer = "&4Błąd: &cTen gracz jest offline!";
        public String onlyPlayer = "&4Błąd: &cKomenda tylko dla graczy!";
        public String notNumber = "&4Błąd: &cArgument nie jest liczba!";
        public String numberBiggerThanOrEqualZero = "&4Błąd: &cLiczba musi byc równa lub większa 0!";
        public String noItem = "&4Błąd: &cMusisz mieć item w ręce!";
        public String noMaterial = "&4Błąd: &cTaki material nie istnieje!";
        public String noArgument = "&4Błąd: &cTaki argument nie istnieje!";
        public String noDamaged = "&4Błąd: &cTen przedmiot nie może być naprawiony!";
        public String noDamagedItems = "&4Błąd: &cMusisz mieć uszkodzone przedmioty!";
        public String noEnchantment = "&4Błąd: &cTaki enchant nie istnieje!";
        public String noValidEnchantmentLevel = "&4Błąd: &cTen poziom zaklęcia nie jest wspierany!";
    }

    @Getter
    @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "&awłączona";
        public String disable = "&cwyłączona";
    }

    @Getter
    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aWiadomość została wysłana do administracji";
        public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";
    }

    @Getter
    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public String format = "&8[&4Administracja&8] &c{NICK}&8: &f{TEXT}";
    }

    @Getter
    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        // teleport
        public String teleportedToPlayer = "&8» &aPrzeteleportowano do {PLAYER}!";
        public String teleportedPlayerToPlayer = "&8» &aPrzeteleportowano {PLAYER} do {ARG-PLAYER}!";

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("&aTeleportacja za &f{TIME}");
        public String teleported = "&8» &aPrzeteleportowano!";
        public String teleporting = "&8» &aTeleportuje...";
        public String teleportTaskCanceled = "&4Błąd: &cRuszyłeś się, teleportacja przerwana!";
        public String teleportTaskAlreadyExist = "&4Błąd: &cTeleportujesz się już!";

        // Coordinates XYZ
        public String teleportedToCoordinates = "&8» &6Przeteleportowano na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String teleportedSpecifiedPlayerToCoordinates = "&8» &6Przeteleportowano &c{PLAYER} &6na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";

        // Back
        public String teleportedToLastLocation = "&8 » &7Przeteleportowano do poprzedniej lokalizacji!";
        public String teleportedSpecifiedPlayerLastLocation = "&8 » &7Przeteleportowano {PLAYER} do poprzedniej lokalizacji!";
        public String lastLocationNoExist = "&8 » &cNie ma zapisanej poprzedniej lokalizacji!";
    }

    @Getter
    @Contextual
    public static class PLChatSection implements ChatSection {
        public String disabled = "&8» &cCzat został wyłączony przez &6{NICK}&c!";
        public String enabled = "&8» &aCzat został włączony przez &f{NICK}&a!";
        public String cleared = "&8» &6Czat został wyczyszczony przez &c{NICK}&6!";
        public String alreadyDisabled = "&4Błąd: &cCzat jest juz wyłączony!";
        public String alreadyEnabled = "&4Błąd: &cCzat jest juz włączony!";
        public String slowModeSet = "&8» &aSlowmode został ustawiony na {SLOWMODE}";
        public String slowMode = "&8» &cNastępną wiadomość możesz wysłać za: &6{TIME}&c!";
        public String disabledChatInfo = "&8» &cCzat jest aktualnie wyłączony!";
        public String noCommand = "&8» &cKomenda &e{COMMAND} &cnie istnieje!";
    }

    @Getter
    @Contextual
    public static class PLTpaSection implements TpaSection {
        public String tpaSelfMessage = "&4Błąd: &cNie możesz siebie teleportować!";
        public String tpaAlreadySentMessage = "&4Błąd: &cWysłałeś już prose o teleportacje!";
        public String tpaSentMessage = "&8» &aWysłałeś prośbę o teleportacje do gracza: &7{PLAYER}&a!";
        public String tpaReceivedMessage =
            """
             &8» &aOtrzymałeś prośbę o teleportacje od gracza: &7{PLAYER}&a!
             &8» &6/tpaccept {PLAYER} &aaby zaakceptować!
             &8» &6/tpdeny {PLAYER} &aaby odrzucić!
             """;

        public String tpaDenyNoRequestMessage = "&4Błąd: &cNie masz prośby o teleportacje od tego gracza!";
        public String tpaDenyNoRequestMessageAll = "&4Błąd: &cNie masz żadnych próśb o teleportacje!";
        public String tpaDenyDoneMessage = "&8» &cOdrzuciłeś próśb o teleportacje od gracza: &7{PLAYER}&c!";
        public String tpaDenyReceivedMessage = "&8» &cGracz: {PLAYER} odrzucił twoją prośbę o teleportacje!";
        public String tpaDenyAllDenied = "&8» &cWszystkie prośby o teleportacje zostały odrzucone!";

        public String tpaAcceptMessage = "&8» &aZaakceptowałeś teleportacje od gracza: &7{PLAYER}&a!";
        public String tpaAcceptNoRequestMessage = "&4Błąd: &cTen gracz nie wysłał ci prośby o teleportacje!";
        public String tpaAcceptNoRequestMessageAll = "&4Błąd: &cNie masz żadnych próśb o teleportacje!";
        public String tpaAcceptReceivedMessage = "&8» &aGracz: &7{PLAYER} &azaakceptował twoją prośbę o teleportacje!";
        public String tpaAcceptAllAccepted = "&8» &aWszystkie prośby o teleportacje zostały zaakceptowane!";
    }

    @Getter
    @Contextual
    public static class PLWarpSection implements WarpSection {
        public String availableList = "&8» Lista warpów: {WARPS}";
        public String notExist = "&8» &cNie odnaleziono takiego warpa!";
        public String noPermission = "&8» &cNie masz uprawnień do tego warpa!";
        public String disabled = "&8» &cTen warp jest aktualnie wyłączony!";
        public String create = "&8 » &7Stworzono warpa {name}!";
        public String remove = "&8 » &7Usunięto warpa {name}!";
    }

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public String notExist = "&8» &cNie ma takiego domu!";
        public String create = "&8 » &7Stworzono home {home}!";
        public String delete = "&8 » &7Usunięto home {home}!";
    }

    @Getter
    @Contextual
    public static class PLPrivateMessageSection implements PrivateMessageSection {
        public String noReply = "&8 » &cNie masz komu odpowiedzieć";
        public String privateMessageYouToTarget = "&8[&7Ty -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String privateMessageTargetToYou = "&8[&7{SENDER} -> &fTy&8]&7: &f{MESSAGE}";

        public String socialSpyMessage = "&8[&css&8] &8[&7{SENDER} -> &f{TARGET}&8]&7: &f{MESSAGE}";
        public String socialSpyEnable = "&8 » &aSocialSpy został włączony!";
        public String socialSpyDisable = "&8 » &cSocialSpy został wyłączony!";

        public String ignorePlayer = "&8 » &7Zignorowano gracza &c{PLAYER}&7!";
        public String unIgnorePlayer = "&8 » &7Odignorowano gracza &a{PLAYER}&7!";
    }

    @Getter
    @Contextual
    public static class PLAfkSection implements AfkSection {
        public String afkOn = "&8 » &7{player} jest AFK!";
        public String afkOff = "&8 » &7{player} nie jest już AFK!";
    }

    @Getter
    @Contextual
    public static class PLOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "&c&lOGŁOSZENIE: &7{BROADCAST}";
        public String clearMessage = "&8» &aWyczyszczono ekwipunek!";
        public String clearByMessage = "&8» &aWyczyszczono ekwipunek gracza {PLAYER}";
        public String disposalTitle = "&f&lKosz";
        public String foodMessage = "&8» &aZostałeś najedzony!";
        public String foodOtherMessage = "&8» &aNajadłeś gracza {PLAYER}";
        public String healMessage = "&8» &aZostałeś uleczony!";
        public String healedMessage = "&8» &aUleczyłeś gracza {PLAYER}";
        public String nullHatMessage = "&4Błąd: &cNie możesz użyć /hat!";
        public String repairMessage = "&8» &aNaprawiono!";
        public String skullMessage = "&8» &aOtrzymałeś głowę gracza {PLAYER}";

        public String killSelf = "&8» &cPopełniłeś samobójstwo!";
        public String killedMessage = "&8» &cZabito gracza {PLAYER}";

        public String speedBetweenZeroAndTen = "&4Błąd: &cUstaw speed w przedziale 0-10!";
        public String speedSet = "&8» &aUstawiono speed na {SPEED}";
        public String speedSetBy = "&8» &cUstawiono {PLAYER} speeda na {SPEED}";

        public String godMessage = "&8» &aGod został {STATE}";
        public String godSetMessage = "&8» &aGod dla gracza &f{PLAYER} &azostał {STATE}";

        public String flyMessage = "&8» &aLatanie zostało {STATE}";
        public String flySetMessage = "&8» &aLatanie dla gracza &f{PLAYER} &azostało {STATE}";

        public String giveReceived = "&8» &aOtrzymałeś &6{ITEM}";
        public String giveGiven = "&8» &aGracz &f{PLAYER} &aotrzymał: &6{ITEM}";

        public String spawnSet = "&8» &aUstawiono spawn!";
        public String spawnNoSet = "&4Błąd: &cSpawn nie jest ustawiony!";
        public String spawnTeleportedBy = "&8» &aZostałeś przeteleportowany na spawn przez {PLAYER}!";
        public String spawnTeleportedOther = "&8» &aGracz &f{PLAYER} &azostał przeteleportowany na spawn!";

        public String gameModeNotCorrect = "&4Błąd: &cNie prawidłowy typ!";
        public String gameModeMessage = "&8» &aUstawiono tryb gry na: {GAMEMODE}";
        public String gameModeSetMessage = "&8» &aUstawiono tryb gry graczowi &f{PLAYER} &ana: &f{GAMEMODE}";
        public String pingMessage = "&8» &aTwój ping: &f{PING}ms";
        public String pingOtherMessage = "&8» &aGracz &f{PLAYER} &ama: &f{PING}ms";
        public String onlineMessage = "&8» &6Na serwerze jest: &f{ONLINE} &6graczy online!";
        public String listMessage = "&8» &6Na serwerze jest: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";

        public String itemChangeNameMessage = "&8 » &7Nowa nazwa itemu: &c{ITEM_NAME}";
        public String itemClearNameMessage = "&8 » &7Wyczyszczono nazwę itemu!";
        public String itemChangeLoreMessage = "&8 » &7Nowa linia lore: &c{ITEM_LORE}";
        public String itemClearLoreMessage = "&8 » &7Wyczyszczono linie lore!";
        public String itemFlagRemovedMessage = "&8 » &7Usunięto flagę: &c{ITEM_FLAG}";
        public String itemFlagAddedMessage = "&8 » &7Dodano flagę: &c{ITEM_FLAG}";
        public String itemFlagClearedMessage = "&8 » &7Wyczyszczono flagi!";

        public String enchantedMessage = "&8» &6Item w ręce został zaklęty!";
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
