package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.AbstractTranslation;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLTranslation extends AbstractTranslation {

    PLTranslation() {
        super(Language.PL);
    }

    @Description({
        "#",
        "# Ten plik odpowiada za polskie tłumaczenie w eternalcore.",
        "#",
        "# Jeśli potrzebujesz pomocy z konfiguracją lub masz jakieś pytania dotyczące EternalCore, dołącz do naszego discorda",
        "# bądź napisz zgłoszenie w zakładce \"Issues\" na GitHubie.",
        "#",
        "# Issues: https://github.com/EternalCodeTeam/EternalCore/issues",
        "# Discord: https://discord.gg/FQ7jmGBd6c",
        "# Strona internetowa: https://eternalcode.pl/",
        "# Kod źródłowy: https://github.com/EternalCodeTeam/EternalCore",
    })

    @Description({
        " ",
        "# Wszędzie możesz użyć formatowania MiniMessages, bądź standardowego &7, &e itd.",
        "# Więcej informacji na: https://docs.adventure.kyori.net/minimessage/format.html",
        "# Możesz użyć wirtualnego generatora do generowania i podglądu wiadomości: https://webui.adventure.kyori.net/",
        "#",
        "# Możesz bez żadnych ograniczeń edytować to, czy wiadomość ma się wysłać na czacie, actionbarze, bądź na jednym i drugim w następujący sposób",
        "# dodaj przed wiadomością następujące prefixy:",
        "# [CHAT] - Wiadomość będzie wysyłana na czacie",
        "# [ACTIONBAR] - Wiadomość będzie wysyłana na actionbarze",
        "# [TITLE] - Wiadomość będzie wysyłana jako tytuł",
        "# [SUBTITLE] - Wiadomość będzie wysyłana jako podtytuł",
        "# [DISABLED] - Wiadomość nie będzie wysyłana",
        "#",
        "# Możesz również łączyć prefixy w następujący sposób:",
        "# [CHAT, ACTIONBAR] - Wiadomość będzie wysyłana na czacie i actionbarze",
        "# [CHAT, ACTIONBAR, TITLE] - Wiadomość będzie wysyłana na czacie, actionbarze, title",
        "#",
        "# Jeśli nie podasz żadnego prefixu, wiadomość będzie wysyłana po prostu na czacie",
        "#",
        "# Wszystkie opcje poza wiadomościami EternalCore są opisane w pliku config.yml",
        " "
    })

    @Description("# Ta sekcja odpowiada za wszystkie wiadomości wykorzystywane podczas złego użycia argumentu komendy")
    public PLArgumentSection argument = new PLArgumentSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - Wyświetla wymagane uprawnienia")
        public Notification permissionMessage = Notification.chat("<dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <gray>({PERMISSIONS})");

        @Description("# {USAGE} - Wyświetla poprawne użycie komendy")
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

    @Description({
        " ",
        "# Ta sekcja odpowiada za ogólne formatowanie niektórych wartości",
        "# Jego celem jest ograniczenie powtarzania się niektórych wiadomości."
    })
    public PLFormatSection format = new PLFormatSection();

    @Getter
    @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "<green>włączony/a";
        public String disable = "<red>wyłączony/a";
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za czat pomocy dla graczy, który jest widoczny dla administracji"
    })
    public PLHelpOpSection helpOp = new PLHelpOpSection();

    @Getter
    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        @Description({ "# {PLAYER} - Gracz który wysłał wiadomość na helpop, {TEXT} - Treść wysłanej wiadomości" })
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<dark_gray>: <white>{TEXT}");
        @Description(" ")
        public Notification send = Notification.chat("<dark_gray>» <green>Wiadomość została wysłana do administracji");
        @Description("# {TIME} - Czas do końca blokady (cooldown)")
        public Notification helpOpDelay = Notification.chat("<dark_gray>» <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za czat komunikacji między administracją"
    })
    public PLAdminChatSection adminChat = new PLAdminChatSection();

    @Getter
    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        @Description({ "# {PLAYER} - Gracz który wysłał wiadomość na czacie administracji, {TEXT} - Treść wysłanej wiadomości" })
        public Notification format = Notification.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za komunikaty związane z teleportacją",
    })
    public PLTeleportSection teleport = new PLTeleportSection();

    @Getter
    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        // teleport
        @Description({ "# {PLAYER} - Gracz który został teleportowany" })
        public Notification teleportedToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano do gracza {PLAYER}!");

        @Description({ "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz" })
        public Notification teleportedPlayerToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano gracza {PLAYER} do gracza {ARG-PLAYER}!");

        // Task
        @Description({ "# {TIME} - Czas teleportacji" })
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleportacja za <white>{TIME}");
        @Description(" ")
        public Notification teleported = Notification.of("<dark_gray>» <green>Przeteleportowano!", NoticeType.CHAT, NoticeType.ACTIONBAR);
        public Notification teleporting = Notification.chat("<dark_gray>» <green>Teleportowanie...");
        public Notification teleportTaskCanceled = Notification.chat("<dark_red>Błąd: <red>Ruszyłeś się, teleportacja została przerwana!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<dark_red>Błąd: <red>Teleportujesz się już!");

        // Coordinates XYZ
        @Description({ " ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notification teleportedToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano na współrzędne x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano gracza <red>{PLAYER} <gold>na współrzędne x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");

        // Back
        @Description(" ")
        public Notification teleportedToLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano do ostatniej lokalizacji!");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany" })
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano gracza {PLAYER} do ostatniej lokalizacji!");
        @Description(" ")
        public Notification lastLocationNoExist = Notification.chat("<dark_gray>» <red>Nie ma zapisanej ostatniej lokalizacji!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za edycję ustawień czatu",
    })
    public PLChatSection chat = new PLChatSection();

    @Getter
    @Contextual
    public static class PLChatSection implements ChatSection {
        @Description({ "# {PLAYER} - Gracz który wykonał akcje dla czatu" })
        public Notification disabled = Notification.chat("<dark_gray>» <red>Czat został wyłączony przez <gold>{PLAYER}<red>!");
        public Notification enabled = Notification.chat("<dark_gray>» <green>Czat został włączony przez <white>{PLAYER}<green>!");
        public Notification cleared = Notification.chat("<dark_gray>» <gold>Czat został wyczyszczony przez <red>{PLAYER}<gold>!");

        @Description(" ")
        public Notification alreadyDisabled = Notification.chat("<dark_red>Błąd: <red>Czat jest już wyłączony!");
        public Notification alreadyEnabled = Notification.chat("<dark_red>Błąd: <red>Czat jest już włączony!");

        @Description({ " ", "# {SLOWMODE} - Czas powolnego wysyłania wiadomości" })
        public Notification slowModeSet = Notification.chat("<dark_gray>» <green>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");

        @Description({ " ", "# {TIME} - Czas powolnego wysyłania wiadomości" })
        public Notification slowMode = Notification.chat("<dark_gray>» <red>Następną wiadomość możesz wysłać za: <gold>{TIME}<red>!");

        @Description(" ")
        public Notification disabledChatInfo = Notification.chat("<dark_gray>» <red>Czat jest obecnie wyłączony!");


        @Description({ " ", "# {BROADCAST} - Ogłoszenie" })
        public String alertMessageFormat = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";

        // TODO: Zmienić, usunąć?
        @Description(" ")
        public Notification noCommand = Notification.chat("<dark_gray>» <red>Komenda <yellow>{COMMAND} <red>nie istnieje!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za obsługę zapytań tpa,",
        "# Daje możliwość edycji dotyczących tego typu zapytań ",
    })
    public PLTpaSection tpa = new PLTpaSection();

    @Getter
    @Contextual
    public static class PLTpaSection implements TpaSection {
        public Notification tpaSelfMessage = Notification.chat("<dark_red>Błąd: <red>Nie możesz teleportować się samodzielnie!");
        public Notification tpaAlreadySentMessage = Notification.chat("<dark_red>Błąd: <red>Już wysłałeś prośbę o teleportację!");
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportację" })
        public Notification tpaSentMessage = Notification.chat("<dark_gray>» <green>Wysłałeś prośbę o teleportację do gracza: <gray>{PLAYER}<green>!");

        @Description({
            " ",
            "# W tych wiadomościach użyliśmy formatowania MiniMessages",
            "# Obecna wiadomość od akceptacji prośby umożliwia graczowi kliknięcie w nią, aby zaakceptować prośbę o teleportację dzięki MiniMessages!",
            "# Więcej informacji znajdziesz na stronie: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<dark_gray>» <green>Otrzymałeś prośbę o teleportację od gracza: <gray>{PLAYER}<green>!"),
            Notification.chat("<hover:show_text:'<green>Akceptować prośbę o teleportacje?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>"),
            Notification.chat("<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        );

        @Description(" ")
        public Notification tpaDenyNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Nie otrzymałeś prośby o teleportację od tego gracza!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaDenyDoneMessage = Notification.chat("<dark_gray>» <red>Odrzuciłeś prośbę o teleportację od gracza: <gray>{PLAYER}<red>!");

        @Description({ " ", "# {PLAYER} - Gracz który odrzucił prośbę o teleportacje" })
        public Notification tpaDenyReceivedMessage = Notification.chat("<dark_gray>» <red>Gracz: {PLAYER} odrzucił twoją prośbę o teleportację!");

        @Description(" ")
        public Notification tpaDenyAllDenied = Notification.chat("<dark_gray>» <red>Odrzucono wszystkie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaAcceptMessage = Notification.chat("<dark_gray>» <green>Zaakceptowałeś prośbę o teleportację od gracza: <gray>{PLAYER}<green>!");

        @Description(" ")
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Ten gracz nie wysłał do ciebie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaAcceptReceivedMessage = Notification.chat("<dark_gray>» <green>Gracz: <gray>{PLAYER} <green>zaakceptował twoją prośbę o teleportację!");

        @Description(" ")
        public Notification tpaAcceptAllAccepted = Notification.chat("<dark_gray>» <green>Zaakceptowano wszystkie prośby o teleportację!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję punktów szybkiej podróży - warp",
    })
    public PLWarpSection warp = new PLWarpSection();

    @Getter
    @Contextual
    public static class PLWarpSection implements WarpSection {
        @Description("# {WARP} - Nazwa warpu")
        public Notification warpAlreadyExists = Notification.chat("<dark_red>Błąd: <red>Warp o nazwie <yellow>{WARP}</yellow> <dark_red>już istnieje!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono warp <yellow>{WARP}</yellow>!");
        public Notification remove = Notification.chat("<dark_gray>» <gray>Usunięto warp <yellow>{WARP}</yellow>!");

        @Description(" ")
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie odnaleziono takiego warpu!");
    }

    @Description({
        " ",
        "# Poniższa sekcja odpowiada za ustawianie i edycję osobistych punktów szybkiej podróży - home",
    })
    public PLHomeSection home = new PLHomeSection();

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie ma takiego domu!");

        @Description({ " ", "# {HOME} - Nazwa domu" })
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono home {HOME}!");
        public Notification delete = Notification.chat("<dark_gray>» <gray>Usunięto home {HOME}!");
        public Notification overrideHomeLocation = Notification.chat("<dark_gray>» <gray>Nadpisałeś lokalizację domu {HOME}!");

        @Description({ " ", "# {LIMIT} - Limit domów" })
        public Notification limit = Notification.chat("<dark_gray>» <red>Osiągnąłeś limit domów! Twój limit to {LIMIT}.");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości prywatnych",
    })
    public PLPrivateChatSection privateChat = new PLPrivateChatSection();

    @Getter
    @Contextual
    public static class PLPrivateChatSection implements PrivateChatSection {
        public Notification noReply = Notification.chat("<dark_gray>» <red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

        @Description("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({ " ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości" })
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");


        @Description("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notification socialSpyEnable = Notification.chat("<dark_gray>» <green>SocialSpy został włączony!");
        public Notification socialSpyDisable = Notification.chat("<dark_gray>» <red>SocialSpy został wyłączony!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification ignorePlayer = Notification.chat("<dark_gray>» <gray>Zignorowano gracza <red>{PLAYER}<gray>!");

        @Description(" ")
        public Notification ignoreAll = Notification.chat("<dark_gray>» <gray>Zignorowano wszystkich graczy!");
        public Notification cantIgnoreYourself = Notification.chat("<dark_gray>» <red>Nie możesz zignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification alreadyIgnorePlayer = Notification.chat("<dark_gray>» <red>Gracz <gray>{PLAYER} <red>jest już zignorowany!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification unIgnorePlayer = Notification.chat("<dark_gray>» <gray>Od ignorowano gracza <green>{PLAYER}<gray>!");

        @Description(" ")
        public Notification unIgnoreAll = Notification.chat("<dark_gray>» <gray>Od ignorowano wszystkich graczy!");
        public Notification cantUnIgnoreYourself = Notification.chat("<dark_gray>» <red>Nie możesz od ignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification notIgnorePlayer = Notification.chat("<dark_gray>» <red>Gracz <gray>{PLAYER} <red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości AFK",
    })
    public PLAfkSection afk = new PLAfkSection();

    @Getter
    @Contextual
    public static class PLAfkSection implements AfkSection {
        @Description("# {PLAYER} - Gracz ")
        public Notification afkOn = Notification.chat("<dark_gray>» <gray>{PLAYER} jest AFK!");
        public Notification afkOff = Notification.chat("<dark_gray>» <gray>{PLAYER} już nie jest AFK!");

        @Description({ " ", "# {TIME} - Czas po którym gracz może użyć komendy" })
        public Notification afkDelay = Notification.chat("<dark_gray>» <red>Możesz użyć tej komendy dopiero po <gold>{TIME}!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości o zdarzeniach gracza",
    })
    public PLEventSection event = new PLEventSection();

    @Getter
    @Contextual
    public static class PLEventSection implements EventSection {
        @Description({
            "# EternalCore będzie losować losową wiadomość z poniższej listy, za każdym razem gdy gracz zginie.",
            "# Jeżeli {KILLER} będzie np. konsolą, albo przyczyną naturalną (upadek, lava itd.) zwróci w tym placeholderze wiadomość z unknownPlayerDeath",
        })
        public String unknownPlayerDeath = "niezidentyfikowany obiekt bojowy";

        @Description("# {PLAYER} - Gracz który został uśmiercony, {KILLER} - Gracz który zabił gracza")
        public List<Notification> deathMessage = List.of(
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>zginął przez {KILLER}!"),
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>zginął tragicznie podczas cieżkiej walki!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera",
            "# {PLAYER} - Gracz który dołączył do serwera",
        })
        public List<Notification> joinMessage = List.of(
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera!"),
            Notification.actionbar("<dark_gray>» <gray>Witaj na serwerze <green>{PLAYER}<gray>!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy",
            "# {PLAYER} - Gracz który dołączył do serwera po raz pierwszy"
        })
        public List<Notification> firstJoinMessage = List.of(
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera po raz pierwszy!"),
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <green>zawitał u nas po raz pierwszy!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz opuści serwer",
            "# {PLAYER} - Gracz który opuścił serwer"
        })
        public List<Notification> quitMessage = List.of(
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>wylogował się z serwera!"),
            Notification.actionbar("<dark_gray>» <gray>{PLAYER} <red>opuścił serwer!")
        );

        @Description({ " ", "# {PLAYER} - Gracz który dołączył do serwera" })
        public Notification welcomeTitle = Notification.title("<yellow>{PLAYER}");
        public Notification welcomeSubtitle = Notification.subtitle("<yellow>Witaj ponownie na serwerze!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za przeglądanie ekwipunku gracza"
    })
    public PLInventorySection inventory = new PLInventorySection();

    @Getter
    @Contextual
    public static class PLInventorySection implements InventorySection {
        public Notification inventoryClearMessage = Notification.chat("<dark_gray>» <green>Wyczyszczono ekwipunek!");

        @Description({ " ", "# {PLAYER} - Gracz którego ekwipunek został wyczyszczony" })
        public Notification inventoryClearMessageBy = Notification.chat("<dark_gray>» <green>Ekwipunek gracza {PLAYER} został wyczyszczony");
        @Description(" ")
        public Notification cantOpenYourInventory = Notification.chat("<dark_gray>» <red>Nie możesz otworzyć swojego ekwipunku!");
        public String disposalTitle = "<white><bold>Kosz";
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za interakcję z graczami za pomocą komend",
    })
    public PLPlayerSection player = new PLPlayerSection();

    @Getter
    @Contextual
    public static class PLPlayerSection implements PlayerSection {
        public Notification feedMessage = Notification.chat("<dark_gray>» <green>Zostałeś nakarmiony!");
        @Description(" # {PLAYER} - Gracz który został nakarmiony")
        public Notification feedMessageBy = Notification.chat("<dark_gray>» <green>Nakarmiłeś gracza {PLAYER}");

        @Description(" ")
        public Notification healMessage = Notification.chat("<dark_gray>» <green>Zostałeś uleczony!");
        @Description("# {PLAYER} - Gracz który został uleczony")
        public Notification healMessageBy = Notification.chat("<dark_gray>» <green>Uleczyłeś gracza {PLAYER}");

        @Description(" ")
        public Notification killSelf = Notification.chat("<dark_gray>» <red>Popełniłeś samobójstwo!");
        @Description("# {PLAYER} - Gracz który został zabity")
        public Notification killedMessage = Notification.chat("<dark_gray>» <red>Zabito gracza {PLAYER}");

        @Description(" ")
        public Notification speedBetweenZeroAndTen = Notification.chat("<dark_red>Błąd: <red>Ustaw prędkość w zakresie 0-10!");
        @Description("# {SPEED} - Ustawiona prędkość")
        public Notification speedSet = Notification.chat("<dark_gray>» <green>Ustawiono prędkość na {SPEED}");
        @Description("# {PLAYER} - Gracz któremu została ustawiona prędkość, {SPEED} - Ustawiona prędkość")
        public Notification speedSetBy = Notification.chat("<dark_gray>» <red>Ustawiono prędkość gracza {PLAYER} na {SPEED}");

        @Description({ " ", "# {STATE} - Status nieśmiertelności" })
        public Notification godMessage = Notification.chat("<dark_gray>» <green>Tryb nieśmiertelności został {STATE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {STATE} - Status nieśmiertelności")
        public Notification godSetMessage = Notification.chat("<dark_gray>» <green>Tryb nieśmiertelności dla gracza <white>{PLAYER} <green>został {STATE}");

        @Description({ " ", "# {STATE} - Status latania" })
        public Notification flyMessage = Notification.chat("<dark_gray>» <green>Latanie zostało {STATE}");
        @Description("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
        public Notification flySetMessage = Notification.chat("<dark_gray>» <green>Latanie dla gracza <white>{PLAYER} <green>zostało {STATE}");

        @Description({ " ", "# {PING} - Aktualna ilość pingu." })
        public Notification pingMessage = Notification.chat("<dark_gray>» <green>Twój ping: <white>{PING}ms");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {PING} - Aktualna ilość pingu dla gracza.")
        public Notification pingOtherMessage = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>ma ping: <white>{PING}ms");

        @Description(" ")
        public Notification gameModeNotCorrect = Notification.chat("<dark_red>Błąd: <red>Niepoprawny typ!");
        @Description("# {GAMEMODE} - Ustawiony tryb gry")
        public Notification gameModeMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry na: {GAMEMODE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb gry, {GAMEMODE} - Ustawiony tryb gry dla gracza")
        public Notification gameModeSetMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry graczowi <white>{PLAYER} <green>na: <white>{GAMEMODE}");

        @Description({ " ", "# {ONLINE} - Aktualna ilość graczy online" })
        public Notification onlineMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <white>{ONLINE} <gold>graczy online!");
        @Description("# {ONLINE} - Aktualna ilość graczy online, {PLAYERS} - Lista graczy online")
        public Notification onlineListMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS} ");

        @Description({
            " ",
            "# {PLAYER} - nazwa gracza",
            "# {UUID} - UUID gracza",
            "# {IP} - IP gracza",
            "# {WALK-SPEED} - prędkość chodzenia gracza",
            "# {SPEED} - prędkość latania gracza",
            "# {PING} - ping gracza",
            "# {LEVEL} - poziom gracza",
            "# {HEALTH} - zdrowie gracza",
            "# {FOOD} - poziom najedzenia gracza"
        })
        public List<String> whoisCommand = List.of(
            "<dark_gray>» <gray>Gracz: <white>{PLAYER}",
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

    @Description({ " ", "# Ta sekcja odpowiada za zmianę punktu spawn oraz teleportację graczy na spawn" })
    public PLSpawnSection spawn = new PLSpawnSection();

    @Getter
    @Contextual
    public static class PLSpawnSection implements SpawnSection {
        public Notification spawnSet = Notification.chat("<dark_gray>» <green>Ustawiono spawn!");
        public Notification spawnNoSet = Notification.chat("<dark_red>Błąd: <red>Spawn nie został ustawiony!");
        @Description({ " ", "# {PLAYER} - Gracz który teleportował cię na spawn" })
        public Notification spawnTeleportedBy = Notification.chat("<dark_gray>» <green>Zostałeś przeteleportowany na spawn przez gracza {PLAYER}!");
        @Description("# {PLAYER} - Gracz który został przeteleportowany na spawn")
        public Notification spawnTeleportedOther = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green> został przeteleportowany na spawn!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za zmianę nazwy oraz opisu itemu",
        "# Wszystkie zmienne są opcjonalne, możesz je wyłączyć w ustawieniach"
    })
    public PLItemSection item = new PLItemSection();

    @Getter
    @Contextual
    public static class PLItemSection implements ItemSection {
        @Description("# {ITEM_NAME} - Nowa nazwa itemu")
        public Notification itemChangeNameMessage = Notification.chat("<dark_gray>» <gray>Nowa nazwa itemu: <red>{ITEM_NAME}");
        @Description(" ")
        public Notification itemClearNameMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono nazwę itemu!");

        @Description({ " ", "# {ITEM_LORE} - Nowe linia opisu" })
        public Notification itemChangeLoreMessage = Notification.chat("<dark_gray>» <gray>Nowa linia opisu: <red>{ITEM_LORE}");
        @Description(" ")
        public Notification itemClearLoreMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono linie opisu!");

        @Description({ " ", "# {ITEM_FLAG} - Nowa flaga itemu" })
        public Notification itemFlagRemovedMessage = Notification.chat("<dark_gray>» <gray>Usunięto flagę: <red>{ITEM_FLAG}");
        public Notification itemFlagAddedMessage = Notification.chat("<dark_gray>» <gray>Dodano flagę: <red>{ITEM_FLAG}");
        @Description(" ")
        public Notification itemFlagClearedMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono flagi!");

        @Description({ " ", "# {ITEM} - Nazwa otrzymanego itemu" })
        public Notification giveReceived = Notification.chat("<dark_gray>» <green>Otrzymałeś: <gold>{ITEM}");

        @Description({ " ", "# {PLAYER} - Osoba której został przydzielony przedmiot, {ITEM} - Nazwa otrzymanego przedmiotu" })
        public Notification giveGiven = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>otrzymał: <gold>{ITEM}");

        @Description(" ")
        public Notification giveNotItem = Notification.chat("<dark_red>Błąd: <red>Podany przedmiot nie istnieje!");
        public Notification repairMessage = Notification.chat("<dark_gray>» <green>Naprawiono!");

        @Description({ " ", "# {SKULL} - Nazwa gracza do którego należy głowa" })
        public Notification skullMessage = Notification.chat("<dark_gray>» <green>Otrzymałeś głowę gracza: {SKULL}");

        @Description(" ")
        public Notification enchantedMessage = Notification.chat("<dark_gray>» <gold>Item w twojej ręce został zaklęty!");
    }

    @Description({
        " ",
        "# Komunikaty odpowiedzialne za ustawianie czasu i pogody",
    })
    public PLTimeAndWeatherMessageSection timeAndWeather = new PLTimeAndWeatherMessageSection();

    @Getter
    @Contextual
    public static class PLTimeAndWeatherMessageSection implements TimeAndWeatherSection {
        @Description("# {WORLD} - Nazwa świata w którym zmieniono czas")
        public Notification timeSetDay = Notification.chat("<dark_gray>» <green>Ustawiono dzień w świecie <yellow>{WORLD}!");
        public Notification timeSetNight = Notification.chat("<dark_gray>» <green>Ustawiono noc w świecie <yellow>{WORLD}!");

        @Description({ " ", "# {TIME} - Czas" })
        public Notification timeSet = Notification.chat("<dark_gray>» <green>Ustawiono czas na <yellow>{TIME}!");
        public Notification timeAdd = Notification.chat("<dark_gray>» <green>Zmieniono czas o <yellow>{TIME}!");

        @Description({ " ", "# {WORLD} - Świat w którym ustawiono pogode" })
        public Notification weatherSetRain = Notification.chat("<dark_gray>» <green>Ustawiono deszcz w świecie <yellow>{WORLD}!");
        public Notification weatherSetSun = Notification.chat("<dark_gray>» <green>Ustawiono słoneczną pogodę w świecie <yellow>{WORLD}!");
        public Notification weatherSetThunder = Notification.chat("<dark_gray>» <green>Ustawiono burze w świecie <yellow>{WORLD}!>");
    }

    @Description({
        " ",
        "# Komunikaty odpowiedzialne za kontenery",
    })
    public PLContainerSection container = new PLContainerSection();

    @Getter
    @Contextual
    public static class PLContainerSection implements ContainerSection {

        @Description({
            "# Wiadomości z tej sekcji służą do konfiguracji wiadomości z kontenerów",
            "# Kontenery to inaczej skrzynie, workbench itp.",
            "# W tym przypadku wykorzystujemy to do informowania graczy o otwarciu kontenera np. przy komendzie /anvil",
            "# {PLAYER} - Gracz który otworzył kontener"
        })

        public Notification genericContainerOpened = Notification.disabled("");
        public Notification genericContainerOpenedBy = Notification.chat("<dark_gray>» <green>Otwarto kontener przez gracza {PLAYER}!");
        public Notification genericContainerOpenedFor = Notification.chat("<dark_gray>» <green>Otwarto kontener dla gracza {PLAYER}!");
    }

    @Description({ " ", "# Informacja zwrotna, gdy gracz zmienia język pluginu na polski" })
    public PLLanguageSection language = new PLLanguageSection();

    @Getter
    @Contextual
    public static class PLLanguageSection implements LanguageSection {
        public Notification languageChanged = Notification.chat("<dark_gray>» <gold>Zmieniono język na <red>Polski<gold>!");
    }

}
