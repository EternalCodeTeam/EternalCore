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
        public Notification permissionMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <red>({PERMISSIONS})");

        @Description({ " ", "# {USAGE} - Wyświetla poprawne użycie komendy" })
        public Notification usageMessage = Notification.chat("<gold>✘ <white>Poprawne użycie: <gold>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<gold>✘ <white>Poprawne użycie:");
        public Notification usageMessageEntry = Notification.chat("<gold>✘ <white>{USAGE}");
        @Description(" ")
        public Notification offlinePlayer = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ten gracz jest obecnie offline!");
        public Notification onlyPlayer = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ta komenda jest dostępna tylko dla graczy!");
        public Notification numberBiggerThanOrEqualZero = Notification.chat("<red>✘ <dark_red>Błąd: <red>Liczba musi być równa lub większa od 0!");
        public Notification noItem = Notification.chat("<red>✘ <dark_red>Błąd: <red>Musisz trzymać przedmiot w dłoni!");
        public Notification noMaterial = Notification.chat("<red>✘ <dark_red>Błąd: <red>Taki materiał nie istnieje!");
        public Notification noArgument = Notification.chat("<red>✘ <dark_red>Błąd: <red>Taki argument nie istnieje!");
        public Notification noDamaged = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ten przedmiot nie może być naprawiony!");
        public Notification noDamagedItems = Notification.chat("<red>✘ <dark_red>Błąd: <red>Musisz posiadać uszkodzone przedmioty!");
        public Notification noEnchantment = Notification.chat("<red>✘ <dark_red>Błąd: <red>Takie zaklęcie nie istnieje!");
        public Notification noValidEnchantmentLevel = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ten poziom zaklęcia nie jest wspierany!");
        public Notification invalidTimeFormat = Notification.chat("<red>✘ <dark_red>Błąd: <red>Nieprawidłowy format czasu!");
        public Notification worldDoesntExist = Notification.chat("<red>✘ <dark_red>Błąd: <red>Taki świat nie istnieje!");
        public Notification youMustGiveWorldName = Notification.chat("<red>✘ <dark_red>Błąd: <red>Musisz podać nazwę świata!");
        public Notification incorrectLocation = Notification.chat("<red>✘ <dark_red>Błąd: <red>Nieprawidłowa lokalizacja!");
        public Notification incorrectNumberOfChunks = Notification.chat("<red>✘ <dark_red>Błąd: <red>Niepoprawna liczba chunków!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ogólne formatowanie niektórych wartości",
        "# Celem sekcji jest ograniczenie powtarzania się niektórych wiadomości."
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
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<white>: <white>{TEXT}");
        @Description(" ")
        public Notification send = Notification.chat("<green>► <white>Wiadomość została wysłana do administracji");
        @Description("# {TIME} - Czas do końca blokady (cooldown)")
        public Notification helpOpDelay = Notification.chat("<white>► <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
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
        public Notification teleportedToPlayer = Notification.chat("<green>► <white>Przeteleportowano do gracza <green>{PLAYER}<white>!");

        @Description({ "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz" })
        public Notification teleportedPlayerToPlayer = Notification.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do gracza <green>{ARG-PLAYER}<white>!");

        @Description({ "# {Y} - Koordynat Y najwyżej położonego bloku" })
        public Notification teleportedToHighestBlock = Notification.chat("<green>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

        // Task
        @Description({ "# {TIME} - Czas teleportacji" })
        public Notification teleportTimerFormat = Notification.actionbar("<green>► <white>Teleportacja za <green>{TIME}");
        @Description(" ")
        public Notification teleported = Notification.of("<green>► <white>Przeteleportowano!", NoticeType.CHAT, NoticeType.ACTIONBAR);
        public Notification teleporting = Notification.chat("<green>► <white>Teleportowanie...");
        public Notification teleportTaskCanceled = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ruszyłeś się, teleportacja została przerwana!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<red>✘ <dark_red>Błąd: <red>Teleportujesz się już!");

        // Coordinates XYZ
        @Description({ " ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notification teleportedToCoordinates = Notification.chat("<green>► <white>Przeteleportowano na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Description(" ")
        public Notification teleportedToLastLocation = Notification.chat("<green>► <white>Przeteleportowano do ostatniej lokalizacji!");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany" })
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do ostatniej lokalizacji!");
        @Description(" ")
        public Notification lastLocationNoExist = Notification.chat("<red>✘ <dark_red>Nie ma zapisanej ostatniej lokalizacji!");
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
        public Notification disabled = Notification.chat("<green>► <white>Czat został wyłączony przez <green>{PLAYER}<white>!");
        public Notification enabled = Notification.chat("<green>► <white>Czat został włączony przez <green>{PLAYER}<white>!");
        public Notification cleared = Notification.chat("<green>► <white>Czat został wyczyszczony przez <green>{PLAYER}<white>!");

        @Description(" ")
        public Notification alreadyDisabled = Notification.chat("<red>✘ <dark_red>Błąd: <red>Czat jest już wyłączony!");
        public Notification alreadyEnabled = Notification.chat("<red>✘ <dark_red>Błąd: <red>Czat jest już włączony!");

        @Description({ " ", "# {SLOWMODE} - Czas powolnego wysyłania wiadomości" })
        public Notification slowModeSet = Notification.chat("<green>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");

        @Description({ " ", "# {TIME} - Czas powolnego wysyłania wiadomości" })
        public Notification slowMode = Notification.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za: <red>{TIME}<dark_red>!");

        @Description(" ")
        public Notification disabledChatInfo = Notification.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");


        @Description({ " ", "# {BROADCAST} - Ogłoszenie" })
        public String alertMessageFormat = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";

        @Description(" ")
        public Notification commandNotFound = Notification.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");
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
        public Notification tpaSelfMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Nie możesz teleportować się samodzielnie!");
        public Notification tpaAlreadySentMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Już wysłałeś prośbę o teleportację!");
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportację" })
        public Notification tpaSentMessage = Notification.chat("<green>► <white>Wysłałeś prośbę o teleportację do gracza: <green>{PLAYER}<white>!");

        @Description({
            " ",
            "# W tych wiadomościach użyliśmy formatowania MiniMessages",
            "# Obecna wiadomość od akceptacji prośby umożliwia graczowi kliknięcie w nią, aby zaakceptować prośbę o teleportację dzięki MiniMessages!",
            "# Więcej informacji znajdziesz na stronie: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<green>► <white>Otrzymałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!"),
            Notification.chat("<hover:show_text:'<green>Akceptować prośbę o teleportacje?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>"),
            Notification.chat("<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        );

        @Description(" ")
        public Notification tpaDenyNoRequestMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Nie otrzymałeś prośby o teleportację od tego gracza!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaDenyDoneMessage = Notification.chat("<red>✘ <dark_red>Odrzuciłeś prośbę o teleportację od gracza: <red>{PLAYER}<dark_red>!");

        @Description({ " ", "# {PLAYER} - Gracz który odrzucił prośbę o teleportacje" })
        public Notification tpaDenyReceivedMessage = Notification.chat("<red>► <dark_red>Gracz: <red>{PLAYER} <dark_red>odrzucił twoją prośbę o teleportację!");

        @Description(" ")
        public Notification tpaDenyAllDenied = Notification.chat("<red>► <dark_red>Odrzucono wszystkie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaAcceptMessage = Notification.chat("<green>► <white>Zaakceptowałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!");

        @Description(" ")
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ten gracz nie wysłał do ciebie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notification tpaAcceptReceivedMessage = Notification.chat("<green>► <white>Gracz: <green>{PLAYER} <white>zaakceptował twoją prośbę o teleportację!");

        @Description(" ")
        public Notification tpaAcceptAllAccepted = Notification.chat("<green>► <white>Zaakceptowano wszystkie prośby o teleportację!");
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
        public Notification warpAlreadyExists = Notification.chat("<red>✘ <dark_red>Błąd: <red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
        public Notification create = Notification.chat("<green>► <white>Stworzono warp <green>{WARP}<white>!");
        public Notification remove = Notification.chat("<red>► <white>Usunięto warp <red>{WARP}<white>!");

        @Description(" ")
        public Notification notExist = Notification.chat("<red>► <dark_red>Nie odnaleziono takiego warpu!");
    }

    @Description({
        " ",
        "# Poniższa sekcja odpowiada za ustawianie i edycję osobistych punktów szybkiej podróży - home",
    })
    public PLHomeSection home = new PLHomeSection();

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<red>► <dark_red>Nie ma takiego domu!");

        @Description({ " ", "# {HOME} - Nazwa domu" })
        public Notification create = Notification.chat("<green>► <white>Stworzono home <green>{HOME}<white>!");
        public Notification delete = Notification.chat("<red>► <white>Usunięto home <red>{HOME}<white>!");
        public Notification overrideHomeLocation = Notification.chat("<green>► <white>Nadpisałeś lokalizację domu <green>{HOME}<white>!");

        @Description({ " ", "# {LIMIT} - Limit domów" })
        public Notification limit = Notification.chat("<green>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości prywatnych",
    })
    public PLPrivateChatSection privateChat = new PLPrivateChatSection();

    @Getter
    @Contextual
    public static class PLPrivateChatSection implements PrivateChatSection {
        public Notification noReply = Notification.chat("<red>► <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

        @Description("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({ " ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości" })
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");


        @Description("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notification socialSpyEnable = Notification.chat("<green>► <white>SocialSpy został <green>włączony!");
        public Notification socialSpyDisable = Notification.chat("<red>► <white> SocialSpy został <red>wyłączony!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification ignorePlayer = Notification.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

        @Description(" ")
        public Notification ignoreAll = Notification.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
        public Notification cantIgnoreYourself = Notification.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification alreadyIgnorePlayer = Notification.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification unIgnorePlayer = Notification.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

        @Description(" ")
        public Notification unIgnoreAll = Notification.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");
        public Notification cantUnIgnoreYourself = Notification.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notification notIgnorePlayer = Notification.chat("<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
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
        public Notification afkOn = Notification.chat("<green>► <white>{PLAYER} jest AFK!");
        public Notification afkOff = Notification.chat("<green>► <white>{PLAYER} już nie jest AFK!");

        @Description({ " ", "# {TIME} - Czas po którym gracz może użyć komendy" })
        public Notification afkDelay = Notification.chat("<red>► <dark_red>Możesz użyć tej komendy dopiero po <dark_red>{TIME}!");
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
            Notification.actionbar("<red>► <dark_red>{PLAYER} <red>zginął przez {KILLER}!"),
            Notification.actionbar("<red>► <dark_red>{PLAYER} <red>zginął tragicznie podczas cieżkiej walki!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera",
            "# {PLAYER} - Gracz który dołączył do serwera",
        })
        public List<Notification> joinMessage = List.of(
            Notification.actionbar("<green>► <green>{PLAYER} <white>dołączył do serwera!"),
            Notification.actionbar("<green>► <white>Witaj na serwerze <green>{PLAYER}<white>!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy",
            "# {PLAYER} - Gracz który dołączył do serwera po raz pierwszy"
        })
        public List<Notification> firstJoinMessage = List.of(
            Notification.actionbar("<green>► {PLAYER} <white>dołączył do serwera po raz pierwszy!"),
            Notification.actionbar("<green>► {PLAYER} <white>zawitał u nas po raz pierwszy!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz opuści serwer",
            "# {PLAYER} - Gracz który opuścił serwer"
        })
        public List<Notification> quitMessage = List.of(
            Notification.actionbar("<red>► {PLAYER} <white>wylogował się z serwera!"),
            Notification.actionbar("<red>► {PLAYER} <white>opuścił serwer!")
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
        public Notification inventoryClearMessage = Notification.chat("<green>► <white>Wyczyszczono ekwipunek!");

        @Description({ " ", "# {PLAYER} - Gracz którego ekwipunek został wyczyszczony" })
        public Notification inventoryClearMessageBy = Notification.chat("<green>► <white>Ekwipunek gracza {PLAYER} został wyczyszczony");
        @Description(" ")
        public Notification cantOpenYourInventory = Notification.chat("<red>✘ <dark_red>Nie możesz otworzyć swojego ekwipunku!");
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
        public Notification feedMessage = Notification.chat("<green>► <white>Zostałeś nakarmiony!");
        @Description(" # {PLAYER} - Gracz który został nakarmiony")
        public Notification feedMessageBy = Notification.chat("<green>► <white>Nakarmiłeś gracza <green>{PLAYER}");

        @Description(" ")
        public Notification healMessage = Notification.chat("<green>► <white>Zostałeś uleczony!");
        @Description("# {PLAYER} - Gracz który został uleczony")
        public Notification healMessageBy = Notification.chat("<green>► <white>Uleczyłeś gracza <green>{PLAYER}");

        @Description(" ")
        public Notification killSelf = Notification.chat("<red>► <dark_red>Popełniłeś samobójstwo!");
        @Description("# {PLAYER} - Gracz który został zabity")
        public Notification killedMessage = Notification.chat("<red>► <dark_red>Zabito gracza <red>{PLAYER}");

        @Description(" ")
        public Notification speedBetweenZeroAndTen = Notification.chat("<red>✘ <dark_red>Błąd: <red>Ustaw prędkość w zakresie 0-10!");
        @Description("# {SPEED} - Ustawiona prędkość")
        public Notification speedSet = Notification.chat("<green>► <white>Ustawiono prędkość na <green>{SPEED}");
        @Description("# {PLAYER} - Gracz któremu została ustawiona prędkość, {SPEED} - Ustawiona prędkość")
        public Notification speedSetBy = Notification.chat("<green>► <white>Ustawiono prędkość gracza <green>{PLAYER} <white>na <green>{SPEED}");

        @Description({ " ", "# {STATE} - Status nieśmiertelności" })
        public Notification godMessage = Notification.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {STATE} - Status nieśmiertelności")
        public Notification godSetMessage = Notification.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");

        @Description({ " ", "# {STATE} - Status latania" })
        public Notification flyMessage = Notification.chat("<green>► <white>Latanie zostało {STATE}");
        @Description("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
        public Notification flySetMessage = Notification.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");

        @Description({ " ", "# {PING} - Aktualna ilość pingu." })
        public Notification pingMessage = Notification.chat("<green>► <white>Twój ping: <green>{PING}<white>ms");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {PING} - Aktualna ilość pingu dla gracza.")
        public Notification pingOtherMessage = Notification.chat("<green>► <white>Gracz <green>{PLAYER} <white>ma ping: <green>{PING}<white>ms");

        @Description(" ")
        public Notification gameModeNotCorrect = Notification.chat("<red>✘ <dark_red>Błąd: <red>Niepoprawny typ!");
        @Description("# {GAMEMODE} - Ustawiony tryb gry")
        public Notification gameModeMessage = Notification.chat("<green>► <white>Ustawiono tryb gry na: <green>{GAMEMODE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb gry, {GAMEMODE} - Ustawiony tryb gry dla gracza")
        public Notification gameModeSetMessage = Notification.chat("<green>► <white>Ustawiono tryb gry graczowi <green>{PLAYER} <white>na: <green>{GAMEMODE}");

        @Description({ " ", "# {ONLINE} - Aktualna ilość graczy online" })
        public Notification onlinePlayersCountMessage = Notification.chat("<green>► <white>Na serwerze jest: <green>{ONLINE} <white>graczy online!");
        @Description("# {ONLINE} - Aktualna ilość graczy online, {PLAYERS} - Lista graczy online")
        public Notification onlinePlayersMessage = Notification.chat("<green>► <white>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS} ");

        public List<String> fullServerSlots = List.of(
            " ",
            "<green>► <white>Serwer jest pełen!",
            "<green>► <white>Zakup rangę na naszej stronie!"
        );

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
        public List<String> whoisCommand = List.of("<green>► <white>Gracz: <green>{PLAYER}",
            "<green>► <white>UUID: <green>{UUID}",
            "<green>► <white>IP: <green>{IP}",
            "<green>► <white>Szybkość chodzenia: <green>{WALK-SPEED}",
            "<green>► <white>Szybkość latania: <green>{SPEED}",
            "<green>► <white>Opóźnienie: <green>{PING}<white>ms",
            "<green>► <white>Poziom: <green>{LEVEL}",
            "<green>► <white>Zdrowie: <green>{HEALTH}",
            "<green>► <white>Poziom najedzenia: <green>{FOOD}"
        );

        @Description({ " ", "{KILLED} - Liczba zabitych mobów" })
        public Notification butcherCommand = Notification.chat("<green>► <white>Zabiłeś <gren>{KILLED} <white>mobów!");

        @Description({ " ", "{SAFE_CHUNKS} - Liczba bezpiecznych chunków" })
        public Notification safeChunksMessage = Notification.chat("<red>✘ <dark_red>Błąd: <red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    }

    @Description({ " ", "# Ta sekcja odpowiada za zmianę punktu spawn oraz teleportację graczy na spawn" })
    public PLSpawnSection spawn = new PLSpawnSection();

    @Getter
    @Contextual
    public static class PLSpawnSection implements SpawnSection {
        public Notification spawnSet = Notification.chat("<green>► <white>Ustawiono spawn!");
        public Notification spawnNoSet = Notification.chat("<red>✘ <dark_red>Błąd: <red>Spawn nie został ustawiony!");
        @Description({ " ", "# {PLAYER} - Gracz który teleportował cię na spawn" })
        public Notification spawnTeleportedBy = Notification.chat("<green>► <white>Zostałeś przeteleportowany na spawn przez gracza <green>{PLAYER}<white>!");
        @Description("# {PLAYER} - Gracz który został przeteleportowany na spawn")
        public Notification spawnTeleportedOther = Notification.chat("<green>► <white>Gracz <green>{PLAYER} <white> został przeteleportowany na spawn!");
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
        public Notification itemChangeNameMessage = Notification.chat("<green>► <white>Nowa nazwa itemu: <green>{ITEM_NAME}");
        @Description(" ")
        public Notification itemClearNameMessage = Notification.chat("<green>► <white>Wyczyszczono nazwę itemu!");

        @Description({ " ", "# {ITEM_LORE} - Nowe linia opisu" })
        public Notification itemChangeLoreMessage = Notification.chat("<green>► <white>Nowa linia opisu: <green>{ITEM_LORE}");
        @Description(" ")
        public Notification itemClearLoreMessage = Notification.chat("<green>► <white>Wyczyszczono linie opisu!");

        @Description({ " ", "# {ITEM_FLAG} - Nowa flaga itemu" })
        public Notification itemFlagRemovedMessage = Notification.chat("<green>► <white>Usunięto flagę: <green>{ITEM_FLAG}");
        public Notification itemFlagAddedMessage = Notification.chat("<green>► <white>Dodano flagę: <green>{ITEM_FLAG}");
        @Description(" ")
        public Notification itemFlagClearedMessage = Notification.chat("<green>► <white>Wyczyszczono flagi!");

        @Description({ " ", "# {ITEM} - Nazwa otrzymanego itemu" })
        public Notification giveReceived = Notification.chat("<green>► <white>Otrzymałeś: <green>{ITEM}");

        @Description({ " ", "# {PLAYER} - Osoba której został przydzielony przedmiot, {ITEM} - Nazwa otrzymanego przedmiotu" })
        public Notification giveGiven = Notification.chat("<green>► <white>Gracz <green>{PLAYER} <white>otrzymał: <green>{ITEM}");

        @Description(" ")
        public Notification giveNotItem = Notification.chat("<red>✘ <dark_red>Błąd: <red>Podany przedmiot nie istnieje!");
        public Notification repairMessage = Notification.chat("<green>► <white>Naprawiono!");

        @Description({ " ", "# {SKULL} - Nazwa gracza do którego należy głowa" })
        public Notification skullMessage = Notification.chat("<green>► <white>Otrzymałeś głowę gracza: {SKULL}");

        @Description(" ")
        public Notification enchantedMessage = Notification.chat("<green>► <white>Item w twojej ręce został zaklęty!");
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
        public Notification timeSetDay = Notification.chat("<green>► <white>Ustawiono dzień w świecie <green>{WORLD}<white>!");
        public Notification timeSetNight = Notification.chat("<green>► <white>Ustawiono noc w świecie <green>{WORLD}<white>!");

        @Description({ " ", "# {TIME} - Czas" })
        public Notification timeSet = Notification.chat("<green>► <white>Ustawiono czas na <green>{TIME}<white>!");
        public Notification timeAdd = Notification.chat("<green>► <white>Zmieniono czas o <green>{TIME}<white>!");

        @Description({ " ", "# {WORLD} - Świat w którym ustawiono pogode" })
        public Notification weatherSetRain = Notification.chat("<green>► <white>Ustawiono deszcz w świecie <green>{WORLD}<white>!");
        public Notification weatherSetSun = Notification.chat("<green>► <white>Ustawiono słoneczną pogodę w świecie <green>{WORLD}<white>!");
        public Notification weatherSetThunder = Notification.chat("<green>► <white>Ustawiono burze w świecie <green>{WORLD}<white>!>");
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
        public Notification genericContainerOpenedBy = Notification.chat("<green>► <white>Otwarto kontener przez gracza <green>{PLAYER}<white>!");
        public Notification genericContainerOpenedFor = Notification.chat("<green>► <white>Otwarto kontener dla gracza <green>{PLAYER}<white>!");
    }

    @Description({ " ", "# Informacja zwrotna, gdy gracz zmienia język pluginu na polski" })
    public PLLanguageSection language = new PLLanguageSection();

    @Getter
    @Contextual
    public static class PLLanguageSection implements LanguageSection {
        public Notification languageChanged = Notification.chat("<green>► <white>Zmieniono język na <green>Polski<white>!");
    }

}
