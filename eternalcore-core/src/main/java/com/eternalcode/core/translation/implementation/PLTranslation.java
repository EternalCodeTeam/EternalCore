package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.bridge.litecommand.argument.messages.PLArgumentMessages;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.adminchat.messages.PLAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.PLAfkMessages;
import com.eternalcode.core.feature.automessage.messages.PLAutoMessageMessages;
import com.eternalcode.core.feature.burn.messages.PLBurnMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.PLDemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.PLElderGuardianMessages;
import com.eternalcode.core.feature.helpop.messages.PLHelpOpMessages;
import com.eternalcode.core.feature.home.messages.PLHomeMessages;
import com.eternalcode.core.feature.itemedit.messages.PLItemEditMessages;
import com.eternalcode.core.feature.jail.messages.PLJailMessages;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.msg.messages.PLMsgMessages;
import com.eternalcode.core.feature.randomteleport.messages.PLRandomTeleportMessages;
import com.eternalcode.core.feature.seen.messages.PLSeenMessages;
import com.eternalcode.core.feature.setslot.messages.PLSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.PLSignEditorMessages;
import com.eternalcode.core.feature.spawn.messages.PLSpawnMessages;
import com.eternalcode.core.feature.sudo.messages.PLSudoMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.PLTeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.PLTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.PLTimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.PLVanishMessages;
import com.eternalcode.core.feature.warp.messages.PLWarpMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class PLTranslation extends AbstractTranslation {

    public PLTranslation() {
        super(Language.PL);
    }

    @Comment({
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

    @Comment({
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

    @Comment("# Ta sekcja odpowiada za wszystkie wiadomości wykorzystywane podczas złego użycia argumentu komendy")
    public PLArgumentMessages argument = new PLArgumentMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za ogólne formatowanie niektórych wartości",
        "# Celem sekcji jest ograniczenie powtarzania się niektórych wiadomości."
    })
    public PLFormatSection format = new PLFormatSection();

    @Comment("# Ta sekcja odpowiada za wiadomości dotyczące edytowanie itemow")
    public PLItemEditMessages itemEdit = new PLItemEditMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /signeditor"
    })
    public PLSignEditorMessages signEditor = new PLSignEditorMessages();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "lang" + File.separator + "pl_messages.yml");
    }

    @Getter
    public static class PLFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>włączony/a";
        public String disable = "<red>wyłączony/a";
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za czat pomocy dla graczy, który jest widoczny dla administracji"
    })
    public PLHelpOpMessages helpOp = new PLHelpOpMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za czat komunikacji między administracją"
    })
    public PLAdminChatMessages adminChat = new PLAdminChatMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadmości komendy /sudo"
    })
    public PLSudoMessages sudo = new PLSudoMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /seen"
    })
    public PLSeenMessages seen = new PLSeenMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za komunikaty związane z teleportacją",
    })
    public PLTeleportSection teleport = new PLTeleportSection();

    @Getter
    public static class PLTeleportSection extends OkaeriConfig implements TeleportSection {
        // teleport
        @Comment({"# {PLAYER} - Gracz który został teleportowany"})
        public Notice teleportedToPlayer = Notice.chat("<green>► <white>Przeteleportowano do gracza <green>{PLAYER}<white>!");

        @Comment({"# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz"})
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do gracza <green>{ARG-PLAYER}<white>!");

        @Comment({"# {Y} - Koordynat Y najwyżej położonego bloku"})
        public Notice teleportedToHighestBlock = Notice.chat("<green>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

        @Comment(" ")
        public Notice teleportedAllToPlayer = Notice.chat("<green>► <white>Przeteleportowano wszystkich graczy do ciebie!");

        // Task
        @Comment({"# {TIME} - Czas teleportacji"})
        public Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleportacja za <green>{TIME}");
        @Comment(" ")
        public Notice teleported = Notice.builder()
            .chat("<green>► <white>Przeteleportowano!")
            .actionBar("<green>► <white>Przeteleportowano!")
            .build();

        public Notice teleporting = Notice.chat("<green>► <white>Teleportowanie...");
        public Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>Ruszyłeś się, teleportacja została przerwana!");
        public Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>Teleportujesz się już!");

        // Coordinates XYZ
        @Comment({" ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
        public Notice teleportedToCoordinates = Notice.chat("<green>► <white>Przeteleportowano na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Comment({" ", "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Comment(" ")
        public Notice teleportedToLastLocation = Notice.chat("<green>► <white>Przeteleportowano do ostatniej lokalizacji!");
        @Comment({" ", "# {PLAYER} - Gracz który został teleportowany"})
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do ostatniej lokalizacji!");
        @Comment(" ")
        public Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Nie ma zapisanej ostatniej lokalizacji!");
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /tprp"
    })
    public PLTeleportToRandomPlayerMessages teleportToRandomPlayer = new PLTeleportToRandomPlayerMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za edycję komunikatów losowej teleportacji",
    })
    public PLRandomTeleportMessages randomTeleport = new PLRandomTeleportMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za edycję ustawień czatu",
    })
    public PLChatSection chat = new PLChatSection();

    @Getter
    public static class PLChatSection extends OkaeriConfig implements ChatSection {
        @Comment({"# {PLAYER} - Gracz który wykonał akcje dla czatu"})
        public Notice disabled = Notice.chat("<green>► <white>Czat został wyłączony przez <green>{PLAYER}<white>!");
        public Notice enabled = Notice.chat("<green>► <white>Czat został włączony przez <green>{PLAYER}<white>!");
        public Notice cleared = Notice.chat("<green>► <white>Czat został wyczyszczony przez <green>{PLAYER}<white>!");

        @Comment(" ")
        public Notice alreadyDisabled = Notice.chat("<red>✘ <dark_red>Czat jest już wyłączony!");
        public Notice alreadyEnabled = Notice.chat("<red>✘ <dark_red>Czat jest już włączony!");

        @Comment({" ", "# {SLOWMODE} - Czas powolnego wysyłania wiadomości"})
        public Notice slowModeSet = Notice.chat("<green>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");

        @Comment({" ", "# {PLAYER} - Gracz który wyłączył tryb powolnego wysyłania wiadomości"})
        public Notice slowModeOff = Notice.chat("<green>► <white>Tryb powolnego wysyłania został wyłączony przez <green>{PLAYER}<white>!");

        @Comment({" ", "# {TIME} - Czas powolnego wysyłania wiadomości"})
        public Notice slowMode = Notice.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za: <red>{TIME}<dark_red>!");

        @Comment(" ")
        public Notice disabledChatInfo = Notice.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");


        @Comment(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");

        @Comment({" ", "# {PLAYER} - Gracz, który otrzymał wiadomość", "# {MESSAGE} - wiadomość", "# {TYPE} - typ wiadomości"})
        public Notice tellrawInfo = Notice.chat("<green>► <white>Wysłano wiadomość typu <green>{TYPE} <white>do <green>{PLAYER} <white>o treści: {MESSAGE}");
        public Notice tellrawAllInfo = Notice.chat("<green>► <white>Wysłano wiadomość typu <green>{TYPE} <white>do <green>wszystkich <white>o treści: {MESSAGE}");

        public Notice tellrawSaved = Notice.chat("<green>► <white>Zapisano wiadomość w kolejce!");
        public Notice tellrawNoSaved = Notice.chat("<red>✘ <dark_red>Nie ma zapisanych wiadomości!");
        public Notice tellrawMultipleSent = Notice.chat("<green>► <white>Wysłano wszystkie zapisane wiadomości!");
        public Notice tellrawCleared = Notice.chat("<green>► <white>Wyczyszczono zapisane wiadomości!");

        @Comment({" ", "# {BROADCAST} - Ogłoszenie"})
        public String alertMessageFormat = "<red><bold>OGŁOSZENIE:</bold> <gray>{BROADCAST}";
        public Notice alertQueueAdded = Notice.chat("<green>► <white>Dodano wiadomość do kolejki!");
        public Notice alertQueueRemovedSingle = Notice.chat("<green>► <white>Usunięto ostatnią wiadomość z kolejki!");
        public Notice alertQueueRemovedAll = Notice.chat("<green>► <white>Usunięto wszystkie wiadomości z kolejki!");
        public Notice alertQueueCleared = Notice.chat("<green>► <white>Wyczyszczono kolejkę wiadomości!");
        public Notice alertQueueEmpty = Notice.chat("<red>✘ <dark_red>Kolejka wiadomości jest pusta!");
        public Notice alertQueueSent = Notice.chat("<green>► <white>Wysłano wszystkie wiadomości z kolejki!");
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za obsługę zapytań tpa,",
        "# Daje możliwość edycji dotyczących tego typu zapytań ",
    })
    public PLTeleportRequestMessages tpa = new PLTeleportRequestMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję punktów szybkiej podróży - warp",
    })
    public PLWarpMessages warp = new PLWarpMessages();

    @Comment({
        " ",
        "# Poniższa sekcja odpowiada za ustawianie i edycję osobistych punktów szybkiej podróży - home",
    })
    public PLHomeMessages home = new PLHomeMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości prywatnych",
    })
    public PLMsgMessages msg = new PLMsgMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości AFK",
    })
    public PLAfkMessages afk = new PLAfkMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za ustawianie i edycję wiadomości o zdarzeniach gracza",
    })
    public PLEventSection event = new PLEventSection();

    @Getter
    public static class PLEventSection extends OkaeriConfig implements EventSection {
        @Comment({
            "# {PLAYER} - Gracz, który zginął",
            "# {KILLER} - Gracz, który zabił (tylko w przypadku PvP)"
        })
        public List<Notice> deathMessage = List.of(
            Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął przez {KILLER}!"),
            Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął tragicznie podczas ciężkiej walki!")
        );

        @Comment({
            "# Wiadomości wyświetlane gdy gracz ginie od konkretnego typu obrażeń",
            "# {PLAYER} - Gracz, który zginął",
            "# {CAUSE} - Przyczyna śmierci (np. UPADEK, VOID)",
            "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
        })
        public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
            EntityDamageEvent.DamageCause.VOID, Collections.singletonList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wypadł w otchłań!")
            ),
            EntityDamageEvent.DamageCause.FALL, Arrays.asList(
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z wysokości!"),
                Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z zabójczego klifu!")
            )
        );

        @Comment("# {PLAYER} - Gracz, który zginął z nieznanej przyczyny")
        public List<Notice> unknownDeathCause = List.of(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez niezidentyfikowany obiekt!")
        );

        @Comment({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera",
            "# {PLAYER} - Gracz który dołączył do serwera",
        })
        public List<Notice> joinMessage = List.of(
            Notice.actionbar("<green>► <green>{PLAYER} <white>dołączył do serwera!"),
            Notice.actionbar("<green>► <white>Witaj na serwerze <green>{PLAYER}<white>!")
        );

        @Comment({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz dołączy do serwera po raz pierwszy",
            "# {PLAYER} - Gracz który dołączył do serwera po raz pierwszy"
        })
        public List<Notice> firstJoinMessage = List.of(
            Notice.actionbar("<green>► {PLAYER} <white>dołączył do serwera po raz pierwszy!"),
            Notice.actionbar("<green>► {PLAYER} <white>zawitał u nas po raz pierwszy!")
        );

        @Comment({
            " ",
            "# Podobnie jak w wiadomości o śmierci, EternalCore będzie losował losową wiadomość z poniższej listy",
            "# za każdym razem gdy gracz opuści serwer",
            "# {PLAYER} - Gracz który opuścił serwer"
        })
        public List<Notice> quitMessage = List.of(
            Notice.actionbar("<red>► {PLAYER} <white>wylogował się z serwera!"),
            Notice.actionbar("<red>► {PLAYER} <white>opuścił serwer!")
        );

        @Comment({" ", "# {PLAYER} - Gracz który dołączył do serwera"})
        public Notice welcome = Notice.title("<yellow>{PLAYER}", "<yellow>Witaj ponownie na serwerze!");
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za przeglądanie ekwipunku gracza"
    })
    public PLInventorySection inventory = new PLInventorySection();

    @Getter
    public static class PLInventorySection extends OkaeriConfig implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>► <white>Wyczyszczono ekwipunek!");

        @Comment({" ", "# {PLAYER} - Gracz którego ekwipunek został wyczyszczony"})
        public Notice inventoryClearMessageBy = Notice.chat("<green>► <white>Ekwipunek gracza {PLAYER} został wyczyszczony");
        @Comment(" ")
        public String disposalTitle = "<white><bold>Kosz";
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za interakcję z graczami za pomocą komend",
        "# Ta sekcja odpowiada za interakcję z graczami za pomocą komend",
    })
    public PLPlayerSection player = new PLPlayerSection();

    @Getter
    public static class PLPlayerSection extends OkaeriConfig implements PlayerSection {
        public Notice feedMessage = Notice.chat("<green>► <white>Zostałeś nakarmiony!");
        @Comment(" # {PLAYER} - Gracz który został nakarmiony")
        public Notice feedMessageBy = Notice.chat("<green>► <white>Nakarmiłeś gracza <green>{PLAYER}");

        @Comment(" ")
        public Notice healMessage = Notice.chat("<green>► <white>Zostałeś uleczony!");
        @Comment("# {PLAYER} - Gracz który został uleczony")
        public Notice healMessageBy = Notice.chat("<green>► <white>Uleczyłeś gracza <green>{PLAYER}");

        @Comment(" ")
        public Notice killSelf = Notice.chat("<red>► <dark_red>Popełniłeś samobójstwo!");
        @Comment("# {PLAYER} - Gracz który został zabity")
        public Notice killedMessage = Notice.chat("<red>► <dark_red>Zabito gracza <red>{PLAYER}");

        @Comment(" ")
        public Notice speedBetweenZeroAndTen = Notice.chat("<red>✘ <dark_red>Ustaw prędkość w zakresie 0-10!");
        public Notice speedTypeNotCorrect = Notice.chat("<red>✘ <dark_red>Nieprawidłowy typ prędkości");

        @Comment("# {SPEED} - Ustawiona prędkość chodzenia lub latania")
        public Notice speedWalkSet = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia na <green>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>► <white>Ustawiono prędkość latania na <green>{SPEED}");

        @Comment("# {PLAYER} - Gracz któremu została ustawiona prędkość chodzenia lub latania, {SPEED} - Ustawiona prędkość")
        public Notice speedWalkSetBy = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia gracza <green>{PLAYER} <white>na <green>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>► <white>Ustawiono prędkość latania gracza <green>{PLAYER} <white>na <green>{SPEED}");

        @Comment({" ", "# {STATE} - Status nieśmiertelności"})
        public Notice godEnable = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");
        public Notice godDisable = Notice.chat("<green>► <white>Tryb nieśmiertelności został {STATE}");

        @Comment("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {STATE} - Status nieśmiertelności")
        public Notice godSetEnable = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");
        public Notice godSetDisable = Notice.chat("<green>► <white>Tryb nieśmiertelności dla gracza <green>{PLAYER} <white>został {STATE}");

        @Comment({" ", "# {STATE} - Status latania"})
        public Notice flyEnable = Notice.chat("<green>► <white>Latanie zostało {STATE}");
        public Notice flyDisable = Notice.chat("<green>► <white>Latanie zostało {STATE}");
        @Comment("# {PLAYER} - Gracz któremu zostało ustawione latanie, {STATE} - Status latania")
        public Notice flySetEnable = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");
        public Notice flySetDisable = Notice.chat("<green>► <white>Latanie dla gracza <green>{PLAYER} <white>zostało {STATE}");

        @Comment({" ", "# {PING} - Aktualna ilość pingu."})
        public Notice pingMessage = Notice.chat("<green>► <white>Twój ping: <green>{PING}<white>ms");
        @Comment("# {PLAYER} - Gracz któremu został ustawiony tryb nieśmiertelności, {PING} - Aktualna ilość pingu dla gracza.")
        public Notice pingOtherMessage = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>ma ping: <green>{PING}<white>ms");

        @Comment(" ")
        public Notice gameModeNotCorrect = Notice.chat("<red>✘ <dark_red>Niepoprawny typ!");
        @Comment("# {GAMEMODE} - Ustawiony tryb gry")
        public Notice gameModeMessage = Notice.chat("<green>► <white>Ustawiono tryb gry na: <green>{GAMEMODE}");
        @Comment("# {PLAYER} - Gracz któremu został ustawiony tryb gry, {GAMEMODE} - Ustawiony tryb gry dla gracza")
        public Notice gameModeSetMessage = Notice.chat("<green>► <white>Ustawiono tryb gry graczowi <green>{PLAYER} <white>na: <green>{GAMEMODE}");

        @Comment({" ", "# {ONLINE} - Aktualna ilość graczy online"})
        public Notice onlinePlayersCountMessage = Notice.chat("<green>► <white>Na serwerze jest: <green>{ONLINE} <white>graczy online!");
        @Comment("# {ONLINE} - Aktualna ilość graczy online, {PLAYERS} - Lista graczy online")
        public Notice onlinePlayersMessage = Notice.chat("<green>► <white>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS} ");

        public List<String> fullServerSlots = List.of(
            " ",
            "<green>► <white>Serwer jest pełen!",
            "<green>► <white>Zakup rangę na naszej stronie!"
        );

        @Comment({
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

        @Comment({" ", "# {KILLED} - Liczba zabitych mobów"})
        public Notice butcherCommand = Notice.chat("<green>► <white>Zabiłeś <green>{KILLED} <white>mobów!");

        @Comment({" ", "# {SAFE_CHUNKS} - Liczba bezpiecznych chunków"})
        public Notice safeChunksMessage = Notice.chat("<red>✘ <dark_red>Przekroczyłeś liczbę bezpiecznych chunków <dark_red>{SAFE_CHUNKS}");
    }

    @Comment({" ", "# Ta sekcja odpowiada za zmianę punktu spawn oraz teleportację graczy na spawn"})
    public PLSpawnMessages spawn = new PLSpawnMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za zmianę nazwy oraz opisu itemu",
        "# Wszystkie zmienne są opcjonalne, możesz je wyłączyć w ustawieniach"
    })
    public PLItemSection item = new PLItemSection();

    @Getter
    public static class PLItemSection extends OkaeriConfig implements ItemSection {
        @Comment({" ", "# {ITEM} - Nazwa otrzymanego itemu"})
        public Notice giveReceived = Notice.chat("<green>► <white>Otrzymałeś: <green>{ITEM}");

        @Comment({" ", "# {PLAYER} - Osoba której został przydzielony przedmiot, {ITEM} - Nazwa otrzymanego przedmiotu"})
        public Notice giveGiven = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>otrzymał: <green>{ITEM}");

        public Notice giveNoSpace = Notice.chat("<red>✘ <dark_red>Brak miejsca w ekwipunku!");

        @Comment(" ")
        public Notice giveNotItem = Notice.chat("<red>✘ <dark_red>Podany przedmiot nie istnieje!");
        public Notice repairMessage = Notice.chat("<green>► <white>Naprawiono trzymany przedmiot!");
        public Notice repairAllMessage = Notice.chat("<green>► <white>Naprawiono wszystkie przedmioty!");

        @Comment({" ", "# {TIME} - Czas przed wysłaniem następnej wiadomości (cooldown)"})
        public Notice repairDelayMessage = Notice.chat("<red>✘ <dark_red>Możesz użyć tej komendy za <dark_red>{TIME}!");

        @Comment({" ", "# {SKULL} - Nazwa gracza do którego należy głowa"})
        public Notice skullMessage = Notice.chat("<green>► <white>Otrzymałeś głowę gracza: {SKULL}");

        @Comment(" ")
        public Notice enchantedMessage = Notice.chat("<green>► <white>Item w twojej ręce został zaklęty!");
        public Notice enchantedMessageFor = Notice.chat("<green>► <white>Item w ręce gracza <green>{PLAYER} <white>został zaklęty!");
        public Notice enchantedMessageBy = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>zaklął twój item!");
    }

    @Comment({
        " ",
        "# Komunikaty odpowiedzialne za ustawianie czasu i pogody",
    })
    public PLTimeAndWeatherMessages timeAndWeather = new PLTimeAndWeatherMessages();

    @Comment({
        " ",
        "# Komunikaty odpowiedzialne za kontenery",
    })
    public PLContainerSection container = new PLContainerSection();

    @Getter
    public static class PLContainerSection extends OkaeriConfig implements ContainerSection {

        @Comment({
            "# Wiadomości z tej sekcji służą do konfiguracji wiadomości z kontenerów",
            "# Kontenery to inaczej skrzynie, workbench itp.",
            "# W tym przypadku wykorzystujemy to do informowania graczy o otwarciu kontenera np. przy komendzie /anvil",
            "# {PLAYER} - Gracz który otworzył kontener"
        })

        public Notice genericContainerOpened = Notice.empty();
        public Notice genericContainerOpenedBy = Notice.chat("<green>► <white>Otwarto kontener przez gracza <green>{PLAYER}<white>!");
        public Notice genericContainerOpenedFor = Notice.chat("<green>► <white>Otwarto kontener dla gracza <green>{PLAYER}<white>!");
    }

    @Comment({" ", "# Informacja zwrotna, gdy gracz zmienia język pluginu na polski"})
    public PLLanguageSection language = new PLLanguageSection();

    @Getter
    public static class PLLanguageSection extends OkaeriConfig implements LanguageSection {
        public Notice languageChanged = Notice.chat("<green>► <white>Zmieniono język na <green>Polski<white>!");

        public List<ConfigItem> decorationItems = List.of(
            ConfigItem.builder()
                .withMaterial(Material.SUNFLOWER)
                .withGlow(true)
                .withSlot(40)
                .withName("&7Nasz discord")
                .withLore(Collections.singletonList("&8» &6https://discord.gg/TRbDApaJaJ"))
                .build()
        );
    }

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące pojemnosci serwera"})
    public PLSetSlotMessages setSlot  = new PLSetSlotMessages();

    @Comment({" ", "# Automatyczne wiadomości "})
    public PLAutoMessageMessages autoMessage = new PLAutoMessageMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące jail'a"})
    public PLJailMessages jailSection = new PLJailMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące Elder Guardian'a"})
    public PLElderGuardianMessages elderGuardian = new PLElderGuardianMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące demo screen'a"})
    public PLDemoScreenMessages demoScreen = new PLDemoScreenMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące komendy /burn"})
    public PLBurnMessages burn = new PLBurnMessages();
  
    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące trybu niewidoczności graczy"})
    public PLVanishMessages vanish = new PLVanishMessages();
}
