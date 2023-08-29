package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.feature.warp.config.WarpConfigItem;
import com.eternalcode.core.feature.warp.config.WarpInventoryItem;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.AbstractTranslation;
import com.j256.ormlite.stmt.query.Not;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        "# Nowy system powiadomień obsługuje różne formaty i opcje:",
        "#",
        "# Przykłady:",
        "#",
        "# example: []",
        "#",
        "# example: \"Hello world\"",
        "#",
        "# example:",
        "#   - \"Hello\"",
        "#   - \"world\"",
        "#",
        "# example:",
        "#   title: \"Hello world\"",
        "#   subtitle: \"Subtitle\"",
        "#   times: \"1s 2s 1s\"",
        "#",
        "# example:",
        "#   subtitle: \"Subtitle\"",
        "#   chat: \"Death message\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   chat:",
        "#     - \"Hello\"",
        "#     - \"world\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html ",
        "#   sound: \"ENTITY_PLAYER_LEVELUP 2.0 1.0\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # Kategorie dźwięków: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/SoundCategory.html",
        "#   sound: \"ENTITY_PLAYER_LEVELUP WEATHER 2.0 1.0\" # Jeśli chcesz odtworzyć dźwięk z określonej kategorii, na przykład jeśli gracz ma kategorię dźwięku \"POGODA\" w ustawieniach gry ustawioną na 0%, dźwięk nie będzie odtwarzany.",
        "#",
        "# example:",
        "#   titleHide: true # Ta opcja czyści title, na przykład, jeśli inny plugin wyśle title, możesz to wyczyścić.",
        "#",
        " "
    })

    @Description("# Ta sekcja odpowiada za wszystkie wiadomości wykorzystywane podczas złego użycia argumentu komendy")
    public PLArgumentSection argument = new PLArgumentSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        @Description("# {PERMISSIONS} - Wyświetla wymagane uprawnienia")
        public Notice permissionMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <red>({PERMISSIONS})");

        @Description({ " ", "# {USAGE} - Wyświetla poprawne użycie komendy" })
        public Notice usageMessage = Notice.chat("<gold>✘ <white>Poprawne użycie: <gold>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<gold>✘ <white>Poprawne użycie:");
        public Notice usageMessageEntry = Notice.chat("<gold>✘ <white>{USAGE}");
        @Description(" ")
        public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ten gracz jest obecnie offline!");
        public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ta komenda jest dostępna tylko dla graczy!");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>Błąd: <red>Liczba musi być równa lub większa od 0!");
        public Notice noItem = Notice.chat("<red>✘ <dark_red>Błąd: <red>Musisz trzymać przedmiot w dłoni!");
        public Notice noMaterial = Notice.chat("<red>✘ <dark_red>Błąd: <red>Taki materiał nie istnieje!");
        public Notice noArgument = Notice.chat("<red>✘ <dark_red>Błąd: <red>Taki argument nie istnieje!");
        public Notice noDamaged = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ten przedmiot nie może być naprawiony!");
        public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>Błąd: <red>Musisz posiadać uszkodzone przedmioty!");
        public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>Błąd: <red>Takie zaklęcie nie istnieje!");
        public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ten poziom zaklęcia nie jest wspierany!");
        public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nieprawidłowy format czasu!");
        public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>Błąd: <red>Taki świat nie istnieje!");
        public Notice youMustGiveWorldName = Notice.chat("<red>✘ <dark_red>Błąd: <red>Musisz podać nazwę świata!");
        public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nieprawidłowa lokalizacja!");
        public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Błąd: <red>Niepoprawna liczba chunków!");
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
        public Notice format = Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<white>: <white>{TEXT}");
        @Description(" ")
        public Notice send = Notice.chat("<green>► <white>Wiadomość została wysłana do administracji");
        @Description("# {TIME} - Czas do końca blokady (cooldown)")
        public Notice helpOpDelay = Notice.chat("<white>► <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
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
        public Notice format = Notice.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");
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
        public Notice teleportedToPlayer = Notice.chat("<green>► <white>Przeteleportowano do gracza <green>{PLAYER}<white>!");

        @Description({ "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz" })
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do gracza <green>{ARG-PLAYER}<white>!");

        @Description({ "# {Y} - Koordynat Y najwyżej położonego bloku" })
        public Notice teleportedToHighestBlock = Notice.chat("<green>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

        // Task
        @Description({ "# {TIME} - Czas teleportacji" })
        public Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleportacja za <green>{TIME}");
        @Description(" ")
        public Notice teleported = Notice.builder()
            .chat("<green>► <white>Przeteleportowano!")
            .actionBar("<green>► <white>Przeteleportowano!")
            .build();

        public Notice teleporting = Notice.chat("<green>► <white>Teleportowanie...");
        public Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ruszyłeś się, teleportacja została przerwana!");
        public Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>Błąd: <red>Teleportujesz się już!");

        // Coordinates XYZ
        @Description({ " ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notice teleportedToCoordinates = Notice.chat("<green>► <white>Przeteleportowano na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Description(" ")
        public Notice teleportedToLastLocation = Notice.chat("<green>► <white>Przeteleportowano do ostatniej lokalizacji!");
        @Description({ " ", "# {PLAYER} - Gracz który został teleportowany" })
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do ostatniej lokalizacji!");
        @Description(" ")
        public Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Nie ma zapisanej ostatniej lokalizacji!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za edycję komunikatów losowej teleportacji",
    })
    public PLRandomTeleportSection randomTeleport = new PLRandomTeleportSection();

    @Getter
    @Contextual
    public static class PLRandomTeleportSection implements RandomTeleportSection {
        @Description(" ")
        public Notice randomTeleportStarted = Notice.chat("<green>► <white>Rozpoczynanie procesu losowania lokalizacji...");
        public Notice randomTeleportFailed = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nie udało się znaleźć bezpiecznej lokalizacji, spróbuj ponownie!!");

        public Notice teleportedToRandomLocation = Notice.chat("<green>► <white>Zostałeś przeteleportowany na losową lokalizację!");

        @Description({"# {PLAYER} - Gracz który został teleportowany, {WORLD} - Świat, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z" })
        public Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat("<green>► <white>Przeteleportowałeś gracza <green>{PLAYER} <white>na losową lokalizację! Jego aktualna lokazlizacja to: świat: {WORLD} x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}.");
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
        public Notice disabled = Notice.chat("<green>► <white>Czat został wyłączony przez <green>{PLAYER}<white>!");
        public Notice enabled = Notice.chat("<green>► <white>Czat został włączony przez <green>{PLAYER}<white>!");
        public Notice cleared = Notice.chat("<green>► <white>Czat został wyczyszczony przez <green>{PLAYER}<white>!");

        @Description(" ")
        public Notice alreadyDisabled = Notice.chat("<red>✘ <dark_red>Błąd: <red>Czat jest już wyłączony!");
        public Notice alreadyEnabled = Notice.chat("<red>✘ <dark_red>Błąd: <red>Czat jest już włączony!");

        @Description({ " ", "# {SLOWMODE} - Czas powolnego wysyłania wiadomości" })
        public Notice slowModeSet = Notice.chat("<green>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");

        @Description({ " ", "# {TIME} - Czas powolnego wysyłania wiadomości" })
        public Notice slowMode = Notice.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za: <red>{TIME}<dark_red>!");

        @Description(" ")
        public Notice disabledChatInfo = Notice.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");


        @Description({ " ", "# {BROADCAST} - Ogłoszenie" })
        public String alertMessageFormat = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";

        @Description(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");

        @Description({" ", "# {PLAYER} - Gracz, który otrzymał wiadomość", "# {MESSAGE} - wiadomość", "# {TYPE} - typ wiadomości"})
        public Notice tellrawInfo = Notice.chat("<green>► <white>Wysłano wiadomość typu <green>{TYPE} <white>do <green>{PLAYER} <white>o treści: {MESSAGE}");
        public Notice tellrawAllInfo = Notice.chat("<green>► <white>Wysłano wiadomość typu <green>{TYPE} <white>do <green>wszystkich <white>o treści: {MESSAGE}");
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
        public Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nie możesz teleportować się samodzielnie!");
        public Notice tpaAlreadySentMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Już wysłałeś prośbę o teleportację!");
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportację" })
        public Notice tpaSentMessage = Notice.chat("<green>► <white>Wysłałeś prośbę o teleportację do gracza: <green>{PLAYER}<white>!");

        @Description({
            " ",
            "# W tych wiadomościach użyliśmy formatowania MiniMessages",
            "# Obecna wiadomość od akceptacji prośby umożliwia graczowi kliknięcie w nią, aby zaakceptować prośbę o teleportację dzięki MiniMessages!",
            "# Więcej informacji znajdziesz na stronie: https://docs.adventure.kyori.net/minimessage/format.html",
        })
        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notice tpaReceivedMessage = Notice.builder()
            .chat("<green>► <white>Otrzymałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!")
            .chat("<hover:show_text:'<green>Akceptować prośbę o teleportacje?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>")
            .chat("<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
            .build();

        @Description(" ")
        public Notice tpaDenyNoRequestMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nie otrzymałeś prośby o teleportację od tego gracza!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notice tpaDenyDoneMessage = Notice.chat("<red>✘ <dark_red>Odrzuciłeś prośbę o teleportację od gracza: <red>{PLAYER}<dark_red>!");

        @Description({ " ", "# {PLAYER} - Gracz który odrzucił prośbę o teleportacje" })
        public Notice tpaDenyReceivedMessage = Notice.chat("<red>► <dark_red>Gracz: <red>{PLAYER} <dark_red>odrzucił twoją prośbę o teleportację!");

        @Description(" ")
        public Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>Odrzucono wszystkie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notice tpaAcceptMessage = Notice.chat("<green>► <white>Zaakceptowałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!");

        @Description(" ")
        public Notice tpaAcceptNoRequestMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ten gracz nie wysłał do ciebie prośby o teleportację!");

        @Description({ " ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza" })
        public Notice tpaAcceptReceivedMessage = Notice.chat("<green>► <white>Gracz: <green>{PLAYER} <white>zaakceptował twoją prośbę o teleportację!");

        @Description(" ")
        public Notice tpaAcceptAllAccepted = Notice.chat("<green>► <white>Zaakceptowano wszystkie prośby o teleportację!");
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
        public Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Błąd: <red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
        public Notice create = Notice.chat("<green>► <white>Stworzono warp <green>{WARP}<white>!");
        public Notice remove = Notice.chat("<red>► <white>Usunięto warp <red>{WARP}<white>!");
        public Notice notExist = Notice.chat("<red>► <dark_red>Nie odnaleziono takiego warpu!");
        @Description({ " ", "# {WARPS} - Lista dostępnych warpów" })
        public Notice available = Notice.chat("<green>► <white>Dostepne warpy: <green>{WARPS}!");

        @Description({ " ", "# Ustawienia gui listy dostępnych warpów" })
        public PLWarpInventory warpInventory = new PLWarpInventory();

        @Getter
        @Contextual
        public static class PLWarpInventory implements WarpInventorySection {
            public String title = "<dark_gray>» <green>Lista dostępnych warpów";
            public int rows = 3;

            public Map<String, WarpInventoryItem> items = Map.of("default", WarpInventoryItem.builder()
                .withWarpName("default")
                .withWarpItem(WarpConfigItem.builder()
                    .withName("&8» &6Warp: &fdomyślny")
                    .withLore(Collections.singletonList("<gray>Kliknij aby się teleportować!"))
                    .withMaterial(Material.PLAYER_HEAD)
                    .withTexture("ewogICJ0aW1lc3RhbXAiIDogMTYyMzI3Mjc5NDQ5MSwKICAicHJvZmlsZUlkIiA6ICJiYzRlZGZiNWYzNmM0OGE3YWM5ZjFhMzlkYzIzZjRmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICI4YWNhNjgwYjIyNDYxMzQwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzEyYzMxODE1N2Y1YzlkYWY4YTc2NzdhMzY2OWY5Nzk4OTQwYWZmMDE0YTY3NGVlMGFmMmE1NzRjYmIyMWI4YzMiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==")
                    .withSlot(10)
                    .build())
                .build());

            public PLBorderSection border = new PLBorderSection();

            @Getter
            @Contextual
            public static class PLBorderSection implements BorderSection {
                public boolean enabled = true;

                public Material material = Material.GRAY_STAINED_GLASS_PANE;

                public BorderSection.FillType fillType = BorderSection.FillType.BORDER;

                public String name = "";

                public List<String> lore = Collections.emptyList();
            }
        }
    }

    @Description({
        " ",
        "# Poniższa sekcja odpowiada za ustawianie i edycję osobistych punktów szybkiej podróży - home",
    })
    public PLHomeSection home = new PLHomeSection();

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public Notice enterName = Notice.chat("<red>► <dark_red>Podaj nazwę domu!");
        public Notice notExist = Notice.chat("<red>► <dark_red>Nie ma takiego domu!");

        @Description({ " ", "# {HOME} - Nazwa domu" })
        public Notice create = Notice.chat("<green>► <white>Stworzono home <green>{HOME}<white>!");
        public Notice delete = Notice.chat("<red>► <white>Usunięto home <red>{HOME}<white>!");
        public Notice overrideHomeLocation = Notice.chat("<green>► <white>Nadpisałeś lokalizację domu <green>{HOME}<white>!");

        @Description({ " ", "# {LIMIT} - Limit domów" })
        public Notice limit = Notice.chat("<green>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości prywatnych",
    })
    public PLPrivateChatSection privateChat = new PLPrivateChatSection();

    @Getter
    @Contextual
    public static class PLPrivateChatSection implements PrivateChatSection {
        public Notice noReply = Notice.chat("<red>► <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

        @Description("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
        public Notice privateMessageYouToTarget = Notice.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description({ " ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości" })
        public Notice privateMessageTargetToYou = Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");


        @Description("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
        public Notice socialSpyMessage = Notice.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

        @Description(" ")
        public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy został <green>włączony!");
        public Notice socialSpyDisable = Notice.chat("<red>► <white> SocialSpy został <red>wyłączony!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notice ignorePlayer = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

        @Description(" ")
        public Notice ignoreAll = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
        public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

        @Description(" ")
        public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");
        public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

        @Description({ " ", "# {PLAYER} - Gracz który jest zignorowany" })
        public Notice notIgnorePlayer = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
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
        public Notice afkOn = Notice.chat("<green>► <white>{PLAYER} jest AFK!");
        public Notice afkOff = Notice.chat("<green>► <white>{PLAYER} już nie jest AFK!");

        @Description({ " ", "# {TIME} - Czas po którym gracz może użyć komendy" })
        public Notice afkDelay = Notice.chat("<red>► <dark_red>Możesz użyć tej komendy dopiero po <dark_red>{TIME}!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości o zdarzeniach gracza",
    })
    public PLEventSection event = new PLEventSection();

    @Getter
    @Contextual
    public static class PLEventSection implements EventSection {
        @Description("# {PLAYER} - Gracz który został uśmiercony, {KILLER} - Gracz który zabił gracza")
        public List<Notice> deathMessage = List.of(
            Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął przez {KILLER}!"),
            Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął tragicznie podczas cieżkiej walki!")
        );

        @Description({
            "# EternalCore będzie losować losową wiadomość z poniższej listy, za każdym razem gdy gracz zginie.",
            "# Jeżeli {KILLER} nie będzie uwzględniony to wiadomość zostanie pobrana z tej listy.",
            "# Lista powodów zgonu: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
        })
        public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
            EntityDamageEvent.DamageCause.VOID, Collections.singletonList(Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wypadł z naszego świata!")),
            EntityDamageEvent.DamageCause.FALL, Arrays.asList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z wysokości!"),
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z zabójczego klifu!")
            )
        );

        public List<Notice> unknownDeathCause = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez niezidentyfikowany obiekt bojowy!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera",
            "# {PLAYER} - Gracz który dołączył do serwera",
        })
        public List<Notice> joinMessage = List.of(
            Notice.actionbar("<green>► <green>{PLAYER} <white>dołączył do serwera!"),
            Notice.actionbar("<green>► <white>Witaj na serwerze <green>{PLAYER}<white>!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy",
            "# {PLAYER} - Gracz który dołączył do serwera po raz pierwszy"
        })
        public List<Notice> firstJoinMessage = List.of(
            Notice.actionbar("<green>► {PLAYER} <white>dołączył do serwera po raz pierwszy!"),
            Notice.actionbar("<green>► {PLAYER} <white>zawitał u nas po raz pierwszy!")
        );

        @Description({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz opuści serwer",
            "# {PLAYER} - Gracz który opuścił serwer"
        })
        public List<Notice> quitMessage = List.of(
            Notice.actionbar("<red>► {PLAYER} <white>wylogował się z serwera!"),
            Notice.actionbar("<red>► {PLAYER} <white>opuścił serwer!")
        );

        @Description({ " ", "# {PLAYER} - Gracz który dołączył do serwera" })
        public Notice welcome = Notice.title("<yellow>{PLAYER}", "<yellow>Witaj ponownie na serwerze!");
    }

    @Description({
        " ",
        "# Ta sekcja odpowiada za przeglądanie ekwipunku gracza"
    })
    public PLInventorySection inventory = new PLInventorySection();

    @Getter
    @Contextual
    public static class PLInventorySection implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>► <white>Wyczyszczono ekwipunek!");

        @Description({ " ", "# {PLAYER} - Gracz którego ekwipunek został wyczyszczony" })
        public Notice inventoryClearMessageBy = Notice.chat("<green>► <white>Ekwipunek gracza {PLAYER} został wyczyszczony");
        @Description(" ")
        public Notice cantOpenYourInventory = Notice.chat("<red>✘ <dark_red>Nie możesz otworzyć swojego ekwipunku!");
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
        public Notice feedMessage = Notice.chat("<green>► <white>Zostałeś nakarmiony!");
        @Description(" # {PLAYER} - Gracz który został nakarmiony")
        public Notice feedMessageBy = Notice.chat("<green>► <white>Nakarmiłeś gracza <green>{PLAYER}");

        @Description(" ")
        public Notice healMessage = Notice.chat("<green>► <white>Zostałeś uleczony!");
        @Description("# {PLAYER} - Gracz który został uleczony")
        public Notice healMessageBy = Notice.chat("<green>► <white>Uleczyłeś gracza <green>{PLAYER}");

        @Description(" ")
        public Notice killSelf = Notice.chat("<red>► <dark_red>Popełniłeś samobójstwo!");
        @Description("# {PLAYER} - Gracz który został zabity")
        public Notice killedMessage = Notice.chat("<red>► <dark_red>Zabito gracza <red>{PLAYER}");

        @Description(" ")
        public Notice speedBetweenZeroAndTen = Notice.chat("<red>✘ <dark_red>Błąd: <red>Ustaw prędkość w zakresie 0-10!");
        public Notice speedTypeNotCorrect = Notice.chat("<red>✘ <dark_red>Błąd: <red>Nieprawidłowy typ prędkości");

        @Description("# {SPEED} - Ustawiona prędkość chodzenia lub latania")
        public Notice speedWalkSet = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia na <green>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>► <white>Ustawiono prędkość latania na <green>{SPEED}");

        @Description("# {PLAYER} - Gracz któremu została ustawiona prędkość chodzenia lub latania, {SPEED} - Ustawiona prędkość")
        public Notice speedWalkSetBy = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia gracza <green>{PLAYER} <white>na <green>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>► <white>Ustawiono prędkość latania gracza <green>{PLAYER} <white>na <green>{SPEED}");

        @Description({ " ", "# {STATE} - Status nieśmiertelności" })
        public Notice godMessage = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {STATE} - Status nieśmiertelności")
        public Notice godSetMessage = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");

        @Description({ " ", "# {STATE} - Status latania" })
        public Notice flyMessage = Notice.chat("<green>► <white>Latanie zostało {STATE}");
        @Description("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
        public Notice flySetMessage = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");

        @Description({ " ", "# {PING} - Aktualna ilość pingu." })
        public Notice pingMessage = Notice.chat("<green>► <white>Twój ping: <green>{PING}<white>ms");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {PING} - Aktualna ilość pingu dla gracza.")
        public Notice pingOtherMessage = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>ma ping: <green>{PING}<white>ms");

        @Description(" ")
        public Notice gameModeNotCorrect = Notice.chat("<red>✘ <dark_red>Błąd: <red>Niepoprawny typ!");
        @Description("# {GAMEMODE} - Ustawiony tryb gry")
        public Notice gameModeMessage = Notice.chat("<green>► <white>Ustawiono tryb gry na: <green>{GAMEMODE}");
        @Description("# {PLAYER} - Gracz któremu został ustawiony tryb gry, {GAMEMODE} - Ustawiony tryb gry dla gracza")
        public Notice gameModeSetMessage = Notice.chat("<green>► <white>Ustawiono tryb gry graczowi <green>{PLAYER} <white>na: <green>{GAMEMODE}");

        @Description({ " ", "# {ONLINE} - Aktualna ilość graczy online" })
        public Notice onlinePlayersCountMessage = Notice.chat("<green>► <white>Na serwerze jest: <green>{ONLINE} <white>graczy online!");
        @Description("# {ONLINE} - Aktualna ilość graczy online, {PLAYERS} - Lista graczy online")
        public Notice onlinePlayersMessage = Notice.chat("<green>► <white>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS} ");

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

        @Description({ " ", "# {KILLED} - Liczba zabitych mobów" })
        public Notice butcherCommand = Notice.chat("<green>► <white>Zabiłeś <green>{KILLED} <white>mobów!");

        @Description({ " ", "# {SAFE_CHUNKS} - Liczba bezpiecznych chunków" })
        public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>Błąd: <red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    }

    @Description({ " ", "# Ta sekcja odpowiada za zmianę punktu spawn oraz teleportację graczy na spawn" })
    public PLSpawnSection spawn = new PLSpawnSection();

    @Getter
    @Contextual
    public static class PLSpawnSection implements SpawnSection {
        public Notice spawnSet = Notice.chat("<green>► <white>Ustawiono spawn!");
        public Notice spawnNoSet = Notice.chat("<red>✘ <dark_red>Błąd: <red>Spawn nie został ustawiony!");
        @Description({ " ", "# {PLAYER} - Gracz który teleportował cię na spawn" })
        public Notice spawnTeleportedBy = Notice.chat("<green>► <white>Zostałeś przeteleportowany na spawn przez gracza <green>{PLAYER}<white>!");
        @Description("# {PLAYER} - Gracz który został przeteleportowany na spawn")
        public Notice spawnTeleportedOther = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white> został przeteleportowany na spawn!");
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
        public Notice itemChangeNameMessage = Notice.chat("<green>► <white>Nowa nazwa itemu: <green>{ITEM_NAME}");
        @Description(" ")
        public Notice itemClearNameMessage = Notice.chat("<green>► <white>Wyczyszczono nazwę itemu!");

        @Description({ " ", "# {ITEM_LORE} - Nowe linia opisu" })
        public Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Nowa linia opisu: <green>{ITEM_LORE}");
        @Description(" ")
        public Notice itemClearLoreMessage = Notice.chat("<green>► <white>Wyczyszczono linie opisu!");

        @Description({ " ", "# {ITEM_FLAG} - Nowa flaga itemu" })
        public Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Usunięto flagę: <green>{ITEM_FLAG}");
        public Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Dodano flagę: <green>{ITEM_FLAG}");
        @Description(" ")
        public Notice itemFlagClearedMessage = Notice.chat("<green>► <white>Wyczyszczono flagi!");

        @Description({ " ", "# {ITEM} - Nazwa otrzymanego itemu" })
        public Notice giveReceived = Notice.chat("<green>► <white>Otrzymałeś: <green>{ITEM}");

        @Description({ " ", "# {PLAYER} - Osoba której został przydzielony przedmiot, {ITEM} - Nazwa otrzymanego przedmiotu" })
        public Notice giveGiven = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>otrzymał: <green>{ITEM}");

        @Description(" ")
        public Notice giveNotItem = Notice.chat("<red>✘ <dark_red>Błąd: <red>Podany przedmiot nie istnieje!");
        public Notice repairMessage = Notice.chat("<green>► <white>Naprawiono!");

        @Description({ " ", "# {SKULL} - Nazwa gracza do którego należy głowa" })
        public Notice skullMessage = Notice.chat("<green>► <white>Otrzymałeś głowę gracza: {SKULL}");

        @Description(" ")
        public Notice enchantedMessage = Notice.chat("<green>► <white>Item w twojej ręce został zaklęty!");
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
        public Notice timeSetDay = Notice.chat("<green>► <white>Ustawiono dzień w świecie <green>{WORLD}<white>!");
        public Notice timeSetNight = Notice.chat("<green>► <white>Ustawiono noc w świecie <green>{WORLD}<white>!");

        @Description({ " ", "# {TIME} - Czas" })
        public Notice timeSet = Notice.chat("<green>► <white>Ustawiono czas na <green>{TIME}<white>!");
        public Notice timeAdd = Notice.chat("<green>► <white>Zmieniono czas o <green>{TIME}<white>!");

        @Description({ " ", "# {WORLD} - Świat w którym ustawiono pogode" })
        public Notice weatherSetRain = Notice.chat("<green>► <white>Ustawiono deszcz w świecie <green>{WORLD}<white>!");
        public Notice weatherSetSun = Notice.chat("<green>► <white>Ustawiono słoneczną pogodę w świecie <green>{WORLD}<white>!");
        public Notice weatherSetThunder = Notice.chat("<green>► <white>Ustawiono burze w świecie <green>{WORLD}<white>!>");
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

        public Notice genericContainerOpened = Notice.empty();
        public Notice genericContainerOpenedBy = Notice.chat("<green>► <white>Otwarto kontener przez gracza <green>{PLAYER}<white>!");
        public Notice genericContainerOpenedFor = Notice.chat("<green>► <white>Otwarto kontener dla gracza <green>{PLAYER}<white>!");
    }

    @Description({ " ", "# Informacja zwrotna, gdy gracz zmienia język pluginu na polski" })
    public PLLanguageSection language = new PLLanguageSection();

    @Getter
    @Contextual
    public static class PLLanguageSection implements LanguageSection {
        public Notice languageChanged = Notice.chat("<green>► <white>Zmieniono język na <green>Polski<white>!");
    }

    @Description({ " ", "# Automatyczne wiadomości " })
    public PLAutoMessageSection autoMessage = new PLAutoMessageSection();

    @Getter
    @Contextual
    public static class PLAutoMessageSection implements AutoMessageSection {

        @Description({
            "",
            "# Jeżeli chcesz użyć placeholder'a %server_online% musisz zainstalować plugin",
            "# PlaceholderAPI oraz pobrać placeholdery dla Server'a",
            "# za pomocą komendy /papi ecloud download Server",
        })
        public Map<String, Notice> messages = Map.of(
            "1", Notice.builder()
                .actionBar("<dark_gray>» <gold>Na serwerze jest: <white>%server_online% <gold>graczy online!")
                .sound(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f)
                .build(),

            "2", Notice.builder()
                .chat("<dark_gray>» <gold>Potrzebujesz pomocy od admina?")
                .chat("<dark_gray>» <gold>Użyj komendy <white>/helpop <gold>aby zgłosić problem!")
                .chat("<dark_gray>» <green><click:suggest_command:'/helpop'>Kliknij aby wykonać!</click></green>")
                .sound(Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f)
                .build()
        );

        public Notice enabled = Notice.chat("<green>► <white>Włączono automatyczne wiadomości!");
        public Notice disabled = Notice.chat("<green>► <white>Wyłączono automatyczne wiadomości!");

        @Override
        public Collection<Notice> messages() {
            return this.messages.values();
        }

        @Override
        public Notice enabled() {
            return this.enabled;
        }

        @Override
        public Notice disabled() {
            return this.disabled;
        }
    }
}
