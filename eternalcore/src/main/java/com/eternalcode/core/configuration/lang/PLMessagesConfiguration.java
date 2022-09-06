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
public class PLMessagesConfiguration implements ReloadableMessages {


    @Override
    public Language getLanguage() {
        return Language.PL;
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
    public PLEventMessageSection eventMessages = new PLEventMessageSection();
    public PLOtherMessages other = new PLOtherMessages();
    public PLAfkSection afk = new PLAfkSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public String permissionMessage = "<dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <gray>({PERMISSIONS})";
        public String usageMessage = "<dark_gray>» <yellow>Poprawne użycie: <gray>{USAGE}";
        public String usageMessageHead = "<dark_gray>» <yellow>Poprawne użycie:";
        public String usageMessageEntry = "<dark_gray>» <gray>{USAGE}";
        public String offlinePlayer = "<dark_red>Błąd: <red>Ten gracz jest offline!";
        public String onlyPlayer = "<dark_red>Błąd: <red>Komenda tylko dla graczy!";
        public String notNumber = "<dark_red>Błąd: <red>Argument nie jest liczba!";
        public String numberBiggerThanOrEqualZero = "<dark_red>Błąd: <red>Liczba musi byc równa lub większa 0!";
        public String noItem = "<dark_red>Błąd: <red>Musisz mieć item w ręce!";
        public String noMaterial = "<dark_red>Błąd: <red>Taki material nie istnieje!";
        public String noArgument = "<dark_red>Błąd: <red>Taki argument nie istnieje!";
        public String noDamaged = "<dark_red>Błąd: <red>Ten przedmiot nie może być naprawiony!";
        public String noDamagedItems = "<dark_red>Błąd: <red>Musisz mieć uszkodzone przedmioty!";
        public String noEnchantment = "<dark_red>Błąd: <red>Taki enchant nie istnieje!";
        public String noValidEnchantmentLevel = "<dark_red>Błąd: <red>Ten poziom zaklęcia nie jest wspierany!";
    }

    @Getter
    @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "<green>włączona";
        public String disable = "<red>wyłączona";
    }

    @Getter
    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public String format = "<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{NICK}<dark_gray>: <white>{TEXT}";
        public String send = "<dark_gray>» <green>Wiadomość została wysłana do administracji";
        public String coolDown = "<dark_gray>» <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!";
    }

    @Getter
    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public String format = "<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{NICK}<dark_gray>: <white>{TEXT}";
    }

    @Getter
    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        // teleport
        public String teleportedToPlayer = "<dark_gray>» <green>Przeteleportowano do {PLAYER}!";
        public String teleportedPlayerToPlayer = "<dark_gray>» <green>Przeteleportowano {PLAYER} do {ARG-PLAYER}!";

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleportacja za <white>{TIME}");
        public String teleported = "<dark_gray>» <green>Przeteleportowano!";
        public String teleporting = "<dark_gray>» <green>Teleportuje...";
        public String teleportTaskCanceled = "<dark_red>Błąd: <red>Ruszyłeś się, teleportacja przerwana!";
        public String teleportTaskAlreadyExist = "<dark_red>Błąd: <red>Teleportujesz się już!";

        // Coordinates XYZ
        public String teleportedToCoordinates = "<dark_gray>» <gold>Przeteleportowano na x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}";
        public String teleportedSpecifiedPlayerToCoordinates = "<dark_gray>» <gold>Przeteleportowano <red>{PLAYER} <gold>na x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}";

        // Back
        public String teleportedToLastLocation = "<dark_gray>» <gray>Przeteleportowano do poprzedniej lokalizacji!";
        public String teleportedSpecifiedPlayerLastLocation = "<dark_gray>» <gray>Przeteleportowano {PLAYER} do poprzedniej lokalizacji!";
        public String lastLocationNoExist = "<dark_gray>» <red>Nie ma zapisanej poprzedniej lokalizacji!";
    }

    @Getter
    @Contextual
    public static class PLChatSection implements ChatSection {
        public String disabled = "<dark_gray>» <red>Czat został wyłączony przez <gold>{NICK}<red>!";
        public String enabled = "<dark_gray>» <green>Czat został włączony przez <white>{NICK}<green>!";
        public String cleared = "<dark_gray>» <gold>Czat został wyczyszczony przez <red>{NICK}<gold>!";
        public String alreadyDisabled = "<dark_red>Błąd: <red>Czat jest juz wyłączony!";
        public String alreadyEnabled = "<dark_red>Błąd: <red>Czat jest juz włączony!";
        public String slowModeSet = "<dark_gray>» <green>Slowmode został ustawiony na {SLOWMODE}";
        public String slowMode = "<dark_gray>» <red>Następną wiadomość możesz wysłać za: <gold>{TIME}<red>!";
        public String disabledChatInfo = "<dark_gray>» <red>Czat jest aktualnie wyłączony!";
        public String noCommand = "<dark_gray>» <red>Komenda <yellow>{COMMAND} <red>nie istnieje!";
    }

    @Getter
    @Contextual
    public static class PLTpaSection implements TpaSection {
        public String tpaSelfMessage = "<dark_red>Błąd: <red>Nie możesz siebie teleportować!";
        public String tpaAlreadySentMessage = "<dark_red>Błąd: <red>Wysłałeś już prose o teleportacje!";
        public String tpaSentMessage = "<dark_gray>» <green>Wysłałeś prośbę o teleportacje do gracza: <gray>{PLAYER}<green>!";

        public List<String> tpaReceivedMessage = List.of(
            "<dark_gray>» <green>Otrzymałeś prośbę o teleportacje od gracza: <gray>{PLAYER}<green>!",
            "<dark_gray>» <gold>/tpaccept {PLAYER} <green>aby zaakceptować!",
            "<dark_gray>» <gold>/tpdeny {PLAYER} <green>aby odrzucić!"
        );

        public String tpaDenyNoRequestMessage = "<dark_red>Błąd: <red>Nie masz prośby o teleportacje od tego gracza!";
        public String tpaDenyNoRequestMessageAll = "<dark_red>Błąd: <red>Nie masz żadnych próśb o teleportacje!";
        public String tpaDenyDoneMessage = "<dark_gray>» <red>Odrzuciłeś próśb o teleportacje od gracza: <gray>{PLAYER}<red>!";
        public String tpaDenyReceivedMessage = "<dark_gray>» <red>Gracz: {PLAYER} odrzucił twoją prośbę o teleportacje!";
        public String tpaDenyAllDenied = "<dark_gray>» <red>Wszystkie prośby o teleportacje zostały odrzucone!";

        public String tpaAcceptMessage = "<dark_gray>» <green>Zaakceptowałeś teleportacje od gracza: <gray>{PLAYER}<green>!";
        public String tpaAcceptNoRequestMessage = "<dark_red>Błąd: <red>Ten gracz nie wysłał ci prośby o teleportacje!";
        public String tpaAcceptNoRequestMessageAll = "<dark_red>Błąd: <red>Nie masz żadnych próśb o teleportacje!";
        public String tpaAcceptReceivedMessage = "<dark_gray>» <green>Gracz: <gray>{PLAYER} <green>zaakceptował twoją prośbę o teleportacje!";
        public String tpaAcceptAllAccepted = "<dark_gray>» <green>Wszystkie prośby o teleportacje zostały zaakceptowane!";
    }

    @Getter
    @Contextual
    public static class PLWarpSection implements WarpSection {
        public String availableList = "<dark_gray>» Lista warpów: {WARPS}";
        public String notExist = "<dark_gray>» <red>Nie odnaleziono takiego warpa!";
        public String noPermission = "<dark_gray>» <red>Nie masz uprawnień do tego warpa!";
        public String disabled = "<dark_gray>» <red>Ten warp jest aktualnie wyłączony!";
        public String create = "<dark_gray>» <gray>Stworzono warpa {name}!";
        public String remove = "<dark_gray>» <gray>Usunięto warpa {name}!";
    }

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public String notExist = "<dark_gray>» <red>Nie ma takiego domu!";
        public String create = "<dark_gray>» <gray>Stworzono home {home}!";
        public String delete = "<dark_gray>» <gray>Usunięto home {home}!";
    }

    @Getter
    @Contextual
    public static class PLPrivateMessageSection implements PrivateMessageSection {
        public String noReply = "<dark_gray>» <red>Nie masz komu odpowiedzieć";
        public String privateMessageYouToTarget = "<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}";
        public String privateMessageTargetToYou = "<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}";

        public String socialSpyMessage = "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}";
        public String socialSpyEnable = "<dark_gray>» <green>SocialSpy został włączony!";
        public String socialSpyDisable = "<dark_gray>» <red>SocialSpy został wyłączony!";

        public String ignorePlayer = "<dark_gray>» <gray>Zignorowano gracza <red>{PLAYER}<gray>!";
        public String unIgnorePlayer = "<dark_gray>» <gray>Odignorowano gracza <green>{PLAYER}<gray>!";
    }

    @Getter
    @Contextual
    public static class PLAfkSection implements AfkSection {
        public String afkOn = "<dark_gray>» <gray>{PLAYER} jest AFK!";
        public String afkOff = "<dark_gray>» <gray>{PLAYER} nie jest już AFK!";
    }

    @Getter
    @Contextual
    public static class PLEventMessageSection implements EventMessagesSection {
        public List<String> deathMessage = List.of("<dark_gray>» <gray>{PLAYER} <red>zginął!");

        public List<String> joinMessage = List.of("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera!", "<dark_gray>» <gray>Witaj na serwerze <green>{PLAYER}<gray>!");

        public List<String> firstJoinMessage = List.of("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera po raz pierwszy!", "<dark_gray>» <gray>{PLAYER} <green>zawitał u nas po raz pierwszy!");

        public List<String> quitMessage = List.of("<dark_gray>» <gray>{PLAYER} <red>wylogował się z serwera!", "<dark_gray>» <gray>{PLAYER} <red>opuścił serwer!");

        public String welcomeTitle = "<yellow>EternalCode.pl";

        public String welcomeSubtitle = "<yellow>Witaj ponownie na serwerze!";
    }

    @Getter
    @Contextual
    public static class PLOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";
        public String clearMessage = "<dark_gray>» <green>Wyczyszczono ekwipunek!";
        public String clearByMessage = "<dark_gray>» <green>Wyczyszczono ekwipunek gracza {PLAYER}";
        public String disposalTitle = "<white><bold>Kosz";
        public String foodMessage = "<dark_gray>» <green>Zostałeś najedzony!";
        public String foodOtherMessage = "<dark_gray>» <green>Najadłeś gracza {PLAYER}";
        public String healMessage = "<dark_gray>» <green>Zostałeś uleczony!";
        public String healedMessage = "<dark_gray>» <green>Uleczyłeś gracza {PLAYER}";
        public String nullHatMessage = "<dark_red>Błąd: <red>Nie możesz użyć /hat!";
        public String repairMessage = "<dark_gray>» <green>Naprawiono!";
        public String skullMessage = "<dark_gray>» <green>Otrzymałeś głowę gracza {PLAYER}";

        public String killSelf = "<dark_gray>» <red>Popełniłeś samobójstwo!";
        public String killedMessage = "<dark_gray>» <red>Zabito gracza {PLAYER}";

        public String speedBetweenZeroAndTen = "<dark_red>Błąd: <red>Ustaw speed w przedziale 0-10!";
        public String speedSet = "<dark_gray>» <green>Ustawiono speed na {SPEED}";
        public String speedSetBy = "<dark_gray>» <red>Ustawiono {PLAYER} speeda na {SPEED}";

        public String godMessage = "<dark_gray>» <green>God został {STATE}";
        public String godSetMessage = "<dark_gray>» <green>God dla gracza <white>{PLAYER} <green>został {STATE}";

        public String flyMessage = "<dark_gray>» <green>Latanie zostało {STATE}";
        public String flySetMessage = "<dark_gray>» <green>Latanie dla gracza <white>{PLAYER} <green>zostało {STATE}";

        public String giveReceived = "<dark_gray>» <green>Otrzymałeś <gold>{ITEM}";
        public String giveGiven = "<dark_gray>» <green>Gracz <white>{PLAYER} <green>otrzymał: <gold>{ITEM}";

        public String spawnSet = "<dark_gray>» <green>Ustawiono spawn!";
        public String spawnNoSet = "<dark_red>Błąd: <red>Spawn nie jest ustawiony!";
        public String spawnTeleportedBy = "<dark_gray>» <green>Zostałeś przeteleportowany na spawn przez {PLAYER}!";
        public String spawnTeleportedOther = "<dark_gray>» <green>Gracz <white>{PLAYER} <green>został przeteleportowany na spawn!";

        public String gameModeNotCorrect = "<dark_red>Błąd: <red>Nie prawidłowy typ!";
        public String gameModeMessage = "<dark_gray>» <green>Ustawiono tryb gry na: {GAMEMODE}";
        public String gameModeSetMessage = "<dark_gray>» <green>Ustawiono tryb gry graczowi <white>{PLAYER} <green>na: <white>{GAMEMODE}";
        public String pingMessage = "<dark_gray>» <green>Twój ping: <white>{PING}ms";
        public String pingOtherMessage = "<dark_gray>» <green>Gracz <white>{PLAYER} <green>ma: <white>{PING}ms";
        public String onlineMessage = "<dark_gray>» <gold>Na serwerze jest: <white>{ONLINE} <gold>graczy online!";
        public String listMessage = "<dark_gray>» <gold>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}";

        public String itemChangeNameMessage = "<dark_gray>» <gray>Nowa nazwa itemu: <red>{ITEM_NAME}";
        public String itemClearNameMessage = "<dark_gray>» <gray>Wyczyszczono nazwę itemu!";
        public String itemChangeLoreMessage = "<dark_gray>» <gray>Nowa linia lore: <red>{ITEM_LORE}";
        public String itemClearLoreMessage = "<dark_gray>» <gray>Wyczyszczono linie lore!";
        public String itemFlagRemovedMessage = "<dark_gray>» <gray>Usunięto flagę: <red>{ITEM_FLAG}";
        public String itemFlagAddedMessage = "<dark_gray>» <gray>Dodano flagę: <red>{ITEM_FLAG}";
        public String itemFlagClearedMessage = "<dark_gray>» <gray>Wyczyszczono flagi!";

        public String enchantedMessage = "<dark_gray>» <gold>Item w ręce został zaklęty!";
        public String languageChanged = "<dark_gray>» <gold>Zmieniono język na <red>Polski<gold>!";

        public List<String> whoisCommand = List.of("<dark_gray>» <gray>Gracz: <white>{PLAYER}",
            "<dark_gray>» <gray>UUID: <white>{UUID}",
            "<dark_gray>» <gray>IP: <white>{IP}",
            "<dark_gray>» <gray>Szybkość chodzenia: <white>{WALK-SPEED}",
            "<dark_gray>» <gray>Szybkość latania: <white>{SPEED}",
            "<dark_gray>» <gray>Opóźnienie: <white>{PING}ms",
            "<dark_gray>» <gray>Poziom: <white>{LEVEL}",
            "<dark_gray>» <gray>Zdrowie: <white>{HEALTH}",
            "<dark_gray>» <gray>Poziom najedzenia: <white>{FOOD}"
        );
    }


    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + "pl_messages.yml");
    }
}
