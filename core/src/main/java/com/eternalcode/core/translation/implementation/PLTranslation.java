package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.AbstractTranslation;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLTranslation extends AbstractTranslation {

    PLTranslation() {
        super(Language.PL);
    }

    public PLArgumentSection argument = new PLArgumentSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public Notification permissionMessage = Notification.chat("<dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <gray>({PERMISSIONS})");
        public Notification usageMessage = Notification.chat("<dark_gray>» <yellow>Poprawne użycie: <gray>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<dark_gray>» <yellow>Poprawne użycie:");
        public Notification usageMessageEntry = Notification.chat("<dark_gray>» <gray>{USAGE}");
        public Notification offlinePlayer = Notification.chat("<dark_red>Błąd: <red>Ten gracz jest obecnie offline!");
        public Notification onlyPlayer = Notification.chat("<dark_red>Błąd: <red>Ta komenda jest dostępna tylko dla graczy!");
        public Notification numberBiggerThanOrEqualZero = Notification.chat("<dark_red>Błąd: <red>Liczba musi być równa lub większa od 0!");
        public Notification noItem = Notification.chat("<dark_red>Błąd: <red>Musisz trzymać przedmiot w dłoni!");
        public Notification noMaterial = Notification.chat("<dark_red>Błąd: <red>Taki materiał nie istnieje!");
        public Notification noArgument = Notification.chat("<dark_red>Błąd: <red>Taki argument nie istnieje!");
        public Notification noDamaged = Notification.chat("<dark_red>Błąd: <red>Ten przedmiot nie może być naprawiony!");
        public Notification noDamagedItems = Notification.chat("<dark_red>Błąd: <red>Musisz posiadać uszkodzone przedmioty!");
        public Notification noEnchantment = Notification.chat("<dark_red>Błąd: <red>Takie zaklęcie nie istnieje!");
        public Notification noValidEnchantmentLevel = Notification.chat("<dark_red>Błąd: <red>Ten poziom zaklęcia nie jest wspierany!");
        public Notification invalidTimeFormat = Notification.chat("<dark_red>Błąd: <red>Nieprawidłowy format czasu!");
        public Notification worldDoesntExist = Notification.chat("<dark_red>Błąd: <red>Taki świat nie istnieje!");
        public Notification youMustGiveWorldName = Notification.chat("<dark_red>Błąd: <red>Musisz podać nazwę świata!");
        public Notification incorrectLocation = Notification.chat("<dark_red>Błąd: <red>Nieprawidłowa lokalizacja!");
    }

    public PLFormatSection format = new PLFormatSection();

    @Getter
    @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "<green>włączona";
        public String disable = "<red>wyłączona";
    }


    public PLHelpOpSection helpOp = new PLHelpOpSection();

    @Getter
    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{NICK}<dark_gray>: <white>{TEXT}");
        public Notification send = Notification.chat("<dark_gray>» <green>Wiadomość została wysłana do administracji");
        public Notification helpOpDelay = Notification.chat("<dark_gray>» <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
    }

    public PLAdminChatSection adminChat = new PLAdminChatSection();

    @Getter
    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{NICK}<dark_gray>: <white>{TEXT}");
    }

    public PLTeleportSection teleport = new PLTeleportSection();

    @Getter
    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        // teleport
        public Notification teleportedToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano do gracza {PLAYER}!");
        public Notification teleportedPlayerToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano gracza {PLAYER} do gracza {ARG-PLAYER}!");

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleportacja za <white>{TIME}");
        public Notification teleported = Notification.of("<dark_gray>» <green>Przeteleportowano!", NoticeType.CHAT, NoticeType.ACTIONBAR);
        public Notification teleporting = Notification.chat("<dark_gray>» <green>Teleportowanie...");
        public Notification teleportTaskCanceled = Notification.chat("<dark_red>Błąd: <red>Ruszyłeś się, teleportacja została przerwana!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<dark_red>Błąd: <red>Teleportujesz się już!");

        // Coordinates XYZ
        public Notification teleportedToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano na współrzędne x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano gracza <red>{PLAYER} <gold>na współrzędne x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");

        // Back
        public Notification teleportedToLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano do ostatniej lokalizacji!");
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano gracza {PLAYER} do ostatniej lokalizacji!");
        public Notification lastLocationNoExist = Notification.chat("<dark_gray>» <red>Nie ma zapisanej ostatniej lokalizacji!");
    }


    public PLChatSection chat = new PLChatSection();

    @Getter
    @Contextual
    public static class PLChatSection implements ChatSection {
        public Notification disabled = Notification.chat("<dark_gray>» <red>Czat został wyłączony przez <gold>{NICK}<red>!");
        public Notification enabled = Notification.chat("<dark_gray>» <green>Czat został włączony przez <white>{NICK}<green>!");
        public Notification cleared = Notification.chat("<dark_gray>» <gold>Czat został wyczyszczony przez <red>{NICK}<gold>!");
        public Notification alreadyDisabled = Notification.chat("<dark_red>Błąd: <red>Czat jest już wyłączony!");
        public Notification alreadyEnabled = Notification.chat("<dark_red>Błąd: <red>Czat jest już włączony!");
        public Notification slowModeSet = Notification.chat("<dark_gray>» <green>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");
        public Notification slowMode = Notification.chat("<dark_gray>» <red>Następną wiadomość możesz wysłać za: <gold>{TIME}<red>!");
        public Notification disabledChatInfo = Notification.chat("<dark_gray>» <red>Czat jest obecnie wyłączony!");
        public Notification noCommand = Notification.chat("<dark_gray>» <red>Komenda <yellow>{COMMAND} <red>nie istnieje!");
        public String alertMessageFormat = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";
    }

    public PLTpaSection tpa = new PLTpaSection();

    @Getter
    @Contextual
    public static class PLTpaSection implements TpaSection {
        public Notification tpaSelfMessage = Notification.chat("<dark_red>Błąd: <red>Nie możesz teleportować się samodzielnie!");
        public Notification tpaAlreadySentMessage = Notification.chat("<dark_red>Błąd: <red>Już wysłałeś prośbę o teleportację!");
        public Notification tpaSentMessage = Notification.chat("<dark_gray>» <green>Wysłałeś prośbę o teleportację do gracza: <gray>{PLAYER}<green>!");

        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<dark_gray>» <green>Otrzymałeś prośbę o teleportację od gracza: <gray>{PLAYER}<green>!"),
            Notification.chat("<dark_gray>» <gold>/tpaccept {PLAYER} <green>by ją zaakceptować!"),
            Notification.chat("<dark_gray>» <gold>/tpdeny {PLAYER} <green>by ją odrzucić!")
        );

        public Notification tpaDenyNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Nie otrzymałeś prośby o teleportację od tego gracza!");
        public Notification tpaDenyDoneMessage = Notification.chat("<dark_gray>» <red>Odrzuciłeś prośbę o teleportację od gracza: <gray>{PLAYER}<red>!");
        public Notification tpaDenyReceivedMessage = Notification.chat("<dark_gray>» <red>Gracz: {PLAYER} odrzucił twoją prośbę o teleportację!");
        public Notification tpaDenyAllDenied = Notification.chat("<dark_gray>» <red>Odrzucono wszystkie prośby o teleportację!");

        public Notification tpaAcceptMessage = Notification.chat("<dark_gray>» <green>Zaakceptowałeś prośbę o teleportację od gracza: <gray>{PLAYER}<green>!");
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Ten gracz nie wysłał do ciebie prośby o teleportację!");
        public Notification tpaAcceptReceivedMessage = Notification.chat("<dark_gray>» <green>Gracz: <gray>{PLAYER} <green>zaakceptował twoją prośbę o teleportację!");
        public Notification tpaAcceptAllAccepted = Notification.chat("<dark_gray>» <green>Zaakceptowano wszystkie prośby o teleportację!");
    }

    public PLWarpSection warp = new PLWarpSection();

    @Getter
    @Contextual
    public static class PLWarpSection implements WarpSection {
        public Notification warpAlreadyExists = Notification.chat("<dark_red>Błąd: <red>Warp o nazwie <yellow>{WARP}</yellow> <dark_red>już istnieje!");
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie odnaleziono takiego warpu!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono warp <yellow>{NAME}</yellow>!");
        public Notification remove = Notification.chat("<dark_gray>» <gray>Usunięto warp <yellow>{NAME}</yellow>!");
    }

    public PLHomeSection home = new PLHomeSection();

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie ma takiego domu!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono home {HOME}!");
        public Notification delete = Notification.chat("<dark_gray>» <gray>Usunięto home {HOME}!");
        public Notification limit = Notification.chat("<dark_gray>» <red>Osiągnąłeś limit domów! Twój limit to {LIMIT}.");
        public Notification overrideHomeLocation = Notification.chat("<dark_gray>» <gray>Nadpisałeś lokalizację domu {HOME}!");
    }

    public PLPrivateChatSection privateChat = new PLPrivateChatSection();

    @Getter
    @Contextual
    public static class PLPrivateChatSection implements PrivateChatSection {
        public Notification noReply = Notification.chat("<dark_gray>» <red>Nie masz z kim rozmawiać");
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification socialSpyEnable = Notification.chat("<dark_gray>» <green>SocialSpy został włączony!");
        public Notification socialSpyDisable = Notification.chat("<dark_gray>» <red>SocialSpy został wyłączony!");

        public Notification ignorePlayer = Notification.chat("<dark_gray>» <gray>Zignorowano gracza <red>{PLAYER}<gray>!");
        public Notification ignoreAll = Notification.chat("<dark_gray>» <gray>Zignorowano wszystkich graczy!");
        public Notification unIgnorePlayer = Notification.chat("<dark_gray>» <gray>Odignorowano gracza <green>{PLAYER}<gray>!");
        public Notification unIgnoreAll = Notification.chat("<dark_gray>» <gray>Odignorowano wszystkich graczy!");
        public Notification cantIgnoreYourself = Notification.chat("<dark_gray>» <red>Nie możesz zignorować samego siebie!");
        public Notification cantUnIgnoreYourself = Notification.chat("<dark_gray>» <red>Nie możesz odignorować samego siebie!");
        public Notification alreadyIgnorePlayer = Notification.chat("<dark_gray>» <red>Gracz <gray>{PLAYER} <red>jest już zignorowany!");
        public Notification notIgnorePlayer = Notification.chat("<dark_gray>» <red>Gracz <gray>{PLAYER} <red>nie jest przez Ciebie zignorowany. Nie możesz go odignorować!");
    }

    public PLAfkSection afk = new PLAfkSection();

    @Getter
    @Contextual
    public static class PLAfkSection implements AfkSection {
        public Notification afkOn = Notification.chat("<dark_gray>» <gray>{PLAYER} jest AFK!");
        public Notification afkOff = Notification.chat("<dark_gray>» <gray>{PLAYER} już nie jest AFK!");
        public Notification afkDelay = Notification.chat("<dark_gray>» <red>Możesz użyć tej komendy dopiero po <gold>{TIME}!");
    }

    public PLEventSection event = new PLEventSection();

    @Getter
    @Contextual
    public static class PLEventSection implements EventSection {
        public String unknownPlayerDeath = "niezidyentyfikowany obiekt bojowy";

        public List<Notification> deathMessage = List.of(
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>zginął przez {KILLER}!"),
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>zginął tragicznie podczas cieżkiej walki!")
        );

        public List<Notification> joinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera!"),
            Notification.chat("<dark_gray>» <gray>Witaj na serwerze <green>{PLAYER}<gray>!")
        );

        public List<Notification> firstJoinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera po raz pierwszy!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>zawitał u nas po raz pierwszy!")
        );

        public List<Notification> quitMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>wylogował się z serwera!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>opuścił serwer!")
        );

        public Notification welcomeTitle = Notification.chat("<yellow>EternalCode.pl");

        public Notification welcomeSubtitle = Notification.chat("<yellow>Witaj ponownie na serwerze!");
    }


    public PLInventorySection inventory = new PLInventorySection();

    @Getter
    @Contextual
    public static class PLInventorySection implements InventorySection {
        public Notification inventoryClearMessage = Notification.chat("<dark_gray>» <green>Wyczyszczono ekwipunek!");
        public Notification inventoryClearMessageBy = Notification.chat("<dark_gray>» <green>Ekwipunek gracza {PLAYER} został wyczyszczony");
        public Notification cantOpenYourInventory = Notification.chat("<dark_gray>» <red>Nie możesz otworzyć swojego ekwipunku!");
        public String disposalTitle = "<white><bold>Kosz";
    }

    public PLPlayerSection player = new PLPlayerSection();

    @Getter
    @Contextual
    public static class PLPlayerSection implements PlayerSection {
        public Notification feedMessage = Notification.chat("<dark_gray>» <green>Zostałeś nakarmiony!");
        public Notification feedMessageBy = Notification.chat("<dark_gray>» <green>Nakarmiłeś gracza {PLAYER}");

        public Notification healMessage = Notification.chat("<dark_gray>» <green>Zostałeś uleczony!");
        public Notification healMessageBy = Notification.chat("<dark_gray>» <green>Uleczyłeś gracza {PLAYER}");

        public Notification killSelf = Notification.chat("<dark_gray>» <red>Popełniłeś samobójstwo!");
        public Notification killedMessage = Notification.chat("<dark_gray>» <red>Zabito gracza {PLAYER}");

        public Notification speedBetweenZeroAndTen = Notification.chat("<dark_red>Błąd: <red>Ustaw prędkość w zakresie 0-10!");
        public Notification speedSet = Notification.chat("<dark_gray>» <green>Ustawiono prędkość na {SPEED}");
        public Notification speedSetBy = Notification.chat("<dark_gray>» <red>Ustawiono prędkość gracza {PLAYER} na {SPEED}");

        public Notification godMessage = Notification.chat("<dark_gray>» <green>Tryb nieśmiertelności został {STATE}");
        public Notification godSetMessage = Notification.chat("<dark_gray>» <green>Tryb nieśmiertelności dla gracza <white>{PLAYER} <green>został {STATE}");

        public Notification flyMessage = Notification.chat("<dark_gray>» <green>Latanie zostało {STATE}");
        public Notification flySetMessage = Notification.chat("<dark_gray>» <green>Latanie dla gracza <white>{PLAYER} <green>zostało {STATE}");

        public Notification pingMessage = Notification.chat("<dark_gray>» <green>Twój ping: <white>{PING}ms");
        public Notification pingOtherMessage = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>ma ping: <white>{PING}ms");

        public Notification gameModeNotCorrect = Notification.chat("<dark_red>Błąd: <red>Niepoprawny typ!");
        public Notification gameModeMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry na: {GAMEMODE}");
        public Notification gameModeSetMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry graczowi <white>{PLAYER} <green>na: <white>{GAMEMODE}");

        public Notification onlineMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <white>{ONLINE} <gold>graczy online!");
        public Notification listMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}");

        public List<String> fullServerSlots = List.of(
            " ",
            "<dark_gray>» <red>Serwer jest pełen!",
            "<dark_gray>» <red>Zakup rangę na naszej stronie!"
        );

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

    public PLSpawnSection spawn = new PLSpawnSection();

    @Getter
    @Contextual
    public static class PLSpawnSection implements SpawnSection {
        public Notification spawnSet = Notification.chat("<dark_gray>» <green>Ustawiono spawn!");
        public Notification spawnNoSet = Notification.chat("<dark_red>Błąd: <red>Spawn nie został ustawiony!");
        public Notification spawnTeleportedBy = Notification.chat("<dark_gray>» <green>Zostałeś przeteleportowany na spawn przez gracza {PLAYER}!");
        public Notification spawnTeleportedOther = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green> został przeteleportowany na spawn!");
    }

    public PLItemSection item = new PLItemSection();

    @Getter
    @Contextual
    public static class PLItemSection implements ItemSection {
        public Notification itemChangeNameMessage = Notification.chat("<dark_gray>» <gray>Nowa nazwa itemu: <red>{ITEM_NAME}");
        public Notification itemClearNameMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono nazwę itemu!");

        public Notification itemChangeLoreMessage = Notification.chat("<dark_gray>» <gray>Nowa linia opisu: <red>{ITEM_LORE}");
        public Notification itemClearLoreMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono linie opisu!");

        public Notification itemFlagRemovedMessage = Notification.chat("<dark_gray>» <gray>Usunięto flagę: <red>{ITEM_FLAG}");
        public Notification itemFlagAddedMessage = Notification.chat("<dark_gray>» <gray>Dodano flagę: <red>{ITEM_FLAG}");
        public Notification itemFlagClearedMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono flagi!");

        public Notification giveReceived = Notification.chat("<dark_gray>» <green>Otrzymałeś: <gold>{ITEM}");
        public Notification giveGiven = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>otrzymał: <gold>{ITEM}");
        public Notification giveNotItem = Notification.chat("<dark_red>Błąd: <red>Podany przedmiot nie istnieje!");

        public Notification repairMessage = Notification.chat("<dark_gray>» <green>Naprawiono!");
        public Notification skullMessage = Notification.chat("<dark_gray>» <green>Otrzymałeś głowę gracza: {PLAYER}");
        public Notification enchantedMessage = Notification.chat("<dark_gray>» <gold>Item w twojej ręce został zaklęty!");
    }

    public PLTimeAndWeatherMessageSection timeAndWeather = new PLTimeAndWeatherMessageSection();

    @Getter
    @Contextual
    public static class PLTimeAndWeatherMessageSection implements TimeAndWeatherSection {
        public Notification timeSetDay = Notification.chat("<dark_gray>» <green>Ustawiono dzień w świecie <yellow>{WORLD}!");
        public Notification timeSetNight = Notification.chat("<dark_gray>» <green>Ustawiono noc w świecie <yellow>{WORLD}!");

        public Notification timeSet = Notification.chat("<dark_gray>» <green>Ustawiono czas na <yellow>{TIME}!");
        public Notification timeAdd = Notification.chat("<dark_gray>» <green>Zmieniono czas o <yellow>{TIME}!");

        public Notification weatherSetRain = Notification.chat("<dark_gray>» <green>Ustawiono deszcz w świecie <yellow>{WORLD}!");
        public Notification weatherSetSun = Notification.chat("<dark_gray>» <green>Ustawiono słoneczną pogodę w świecie <yellow>{WORLD}!");
        public Notification weatherSetThunder = Notification.chat("<dark_gray>» <green>Ustawiono burze w świecie <yellow>{WORLD}!>");
    }

    public PLLanguageSection language = new PLLanguageSection();

    @Getter
    @Contextual
    public static class PLLanguageSection implements LanguageSection {
        public Notification languageChanged = Notification.chat("<dark_gray>» <gold>Zmieniono język na <red>Polski<gold>!");
    }

    public PLContainerSection container = new PLContainerSection();

    @Getter
    @Contextual
    public static class PLContainerSection implements ContainerSection {
        public Notification genericContainerOpened = Notification.disabled("");
        public Notification genericContainerOpenedBy = Notification.chat("<dark_gray>» <green>Otwarto kontener przez gracza {PLAYER}!");
        public Notification genericContainerOpenedFor = Notification.chat("<dark_gray>» <green>Otwarto kontener dla gracza {PLAYER}!");
    }
    
}
