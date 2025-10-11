package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.feature.adminchat.messages.PLAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.PLAfkMessages;
import com.eternalcode.core.feature.automessage.messages.PLAutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.PLBroadcastMessages;
import com.eternalcode.core.feature.burn.messages.PLBurnMessages;
import com.eternalcode.core.feature.butcher.messages.PLButcherMessages;
import com.eternalcode.core.feature.clear.messages.PLClearMessages;
import com.eternalcode.core.feature.container.messages.PLContainerMessages;
import com.eternalcode.core.feature.enchant.messages.PLEnchantMessages;
import com.eternalcode.core.feature.feed.messages.PLFeedMessages;
import com.eternalcode.core.feature.fly.messages.PLFlyMessages;
import com.eternalcode.core.feature.freeze.messages.PLFreezeMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.PLDemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.PLElderGuardianMessages;
import com.eternalcode.core.feature.fun.endscreen.messages.PLEndScreenMessages;
import com.eternalcode.core.feature.gamemode.messages.PLGameModeMessages;
import com.eternalcode.core.feature.give.messages.PLGiveMessages;
import com.eternalcode.core.feature.godmode.messages.PLGodModeMessages;
import com.eternalcode.core.feature.heal.messages.PLHealMessages;
import com.eternalcode.core.feature.helpop.messages.PLHelpOpMessages;
import com.eternalcode.core.feature.home.messages.PLHomeMessages;
import com.eternalcode.core.feature.itemedit.messages.PLItemEditMessages;
import com.eternalcode.core.feature.jail.messages.PLJailMessages;
import com.eternalcode.core.feature.kill.messages.PLKillMessages;
import com.eternalcode.core.feature.motd.messages.PLMotdMessages;
import com.eternalcode.core.feature.msg.messages.PLMsgMessages;
import com.eternalcode.core.feature.near.messages.PLNearMessages;
import com.eternalcode.core.feature.onlineplayers.messages.PLOnlineMessages;
import com.eternalcode.core.feature.ping.PLPingMessages;
import com.eternalcode.core.feature.playtime.messages.PLPlaytimeMessages;
import com.eternalcode.core.feature.powertool.messages.PLPowertoolMessages;
import com.eternalcode.core.feature.randomteleport.messages.PLRandomTeleportMessages;
import com.eternalcode.core.feature.repair.messages.PLRepairMessages;
import com.eternalcode.core.feature.seen.messages.PLSeenMessages;
import com.eternalcode.core.feature.setslot.messages.PLSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.PLSignEditorMessages;
import com.eternalcode.core.feature.skull.messages.PLSkullMessages;
import com.eternalcode.core.feature.spawn.messages.PLSpawnMessages;
import com.eternalcode.core.feature.speed.messages.PLSpeedMessages;
import com.eternalcode.core.feature.sudo.messages.PLSudoMessages;
import com.eternalcode.core.feature.teleport.messages.PLTeleportOfflineMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.PLTeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.PLTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.PLTimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.PLVanishMessages;
import com.eternalcode.core.feature.warp.messages.PLWarpMessages;
import com.eternalcode.core.feature.whois.PLWhoIsMessages;
import com.eternalcode.core.litecommand.argument.messages.PLArgumentMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Language;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.entity.EntityDamageEvent;

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

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości w komendzie /near "})
    public PLNearMessages near = new PLNearMessages();

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
        public Notice teleportedToPlayer =
            Notice.chat("<green>► <white>Przeteleportowano do gracza <green>{PLAYER}<white>!");

        @Comment({
            "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz"})
        public Notice teleportedPlayerToPlayer = Notice.chat(
            "<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do gracza <green>{ARG-PLAYER}<white>!");

        @Comment({"# {Y} - Koordynat Y najwyżej położonego bloku"})
        public Notice teleportedToHighestBlock =
            Notice.chat("<green>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

        @Comment(" ")
        public Notice teleportedAllToPlayer =
            Notice.chat("<green>► <white>Przeteleportowano wszystkich graczy do ciebie!");

        // Task
        @Comment({"# {TIME} - Czas teleportacji"})
        public Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleportacja za <green>{TIME}");
        @Comment(" ")
        public Notice teleported = Notice.builder()
            .chat("<green>► <white>Przeteleportowano!")
            .actionBar("<green>► <white>Przeteleportowano!")
            .build();

        public Notice teleporting = Notice.chat("<green>► <white>Teleportowanie...");
        public Notice teleportTaskCanceled =
            Notice.chat("<red>✘ <dark_red>Ruszyłeś się, teleportacja została przerwana!");
        public Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>Teleportujesz się już!");

        // Coordinates XYZ
        @Comment({" ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
        public Notice teleportedToCoordinates = Notice.chat(
            "<green>► <white>Przeteleportowano na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
        @Comment({" ",
                  "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat(
            "<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

        // Back
        @Comment(" ")
        public Notice teleportedToLastLocation =
            Notice.chat("<green>► <white>Przeteleportowano do ostatniej lokalizacji!");
        @Comment({" ", "# {PLAYER} - Gracz który został teleportowany"})
        public Notice teleportedSpecifiedPlayerLastLocation =
            Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do ostatniej lokalizacji!");
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
        public Notice slowModeSet =
            Notice.chat("<green>► <white>Tryb powolnego wysyłania został ustawiony na {SLOWMODE}");

        @Comment({" ", "# {PLAYER} - Gracz który wyłączył tryb powolnego wysyłania wiadomości"})
        public Notice slowModeOff =
            Notice.chat("<green>► <white>Tryb powolnego wysyłania został wyłączony przez <green>{PLAYER}<white>!");

        @Comment({" ", "# {TIME} - Czas powolnego wysyłania wiadomości"})
        public Notice slowMode =
            Notice.chat("<red>✘ <dark_red>Następną wiadomość możesz wysłać za: <red>{TIME}<dark_red>!");

        @Comment(" ")
        public Notice disabledChatInfo = Notice.chat("<red>✘ <dark_red>Czat jest obecnie wyłączony!");

        @Comment(" ")
        public Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Komenda <red>{COMMAND} <dark_red>nie istnieje!");
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /broadcast",
    })
    public PLBroadcastMessages broadcast = new PLBroadcastMessages();

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
    }

    @Comment({
        " ",
        "# Ta sekcja odpowiada za przeglądanie ekwipunku gracza"
    })
    public PLInventorySection inventory = new PLInventorySection();

    @Getter
    public static class PLInventorySection extends OkaeriConfig implements InventorySection {
        public String disposalTitle = "<white><bold>Kosz";
    }

    @Comment("# Ta sekcja odpowiada za komende /clear")
    public PLClearMessages clear = new PLClearMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /feed"
    })
    public PLFeedMessages feed = new PLFeedMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /heal"
    })
    public PLHealMessages heal = new PLHealMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /kill"
    })
    public PLKillMessages kill = new PLKillMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /speed"
    })
    public PLSpeedMessages speed = new PLSpeedMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /god"
    })
    public PLGodModeMessages godmode = new PLGodModeMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /fly"
    })
    public PLFlyMessages fly = new PLFlyMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /ping"
    })
    public PLPingMessages ping = new PLPingMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /gamemode"
    })
    public PLGameModeMessages gamemode = new PLGameModeMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /online"
    })
    public PLOnlineMessages online = new PLOnlineMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /whois"
    })
    public PLWhoIsMessages whois = new PLWhoIsMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /butcher"
    })
    public PLButcherMessages butcher = new PLButcherMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /give"
    })
    public PLGiveMessages give = new PLGiveMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomości komendy /skull"
    })
    public PLSkullMessages skull = new PLSkullMessages();

    @Comment({" ", "# Ta sekcja odpowiada za zmianę punktu spawn oraz teleportację graczy na spawn"})
    public PLSpawnMessages spawn = new PLSpawnMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomosci repair"
    })
    public PLRepairMessages repair = new PLRepairMessages();

    @Comment({
        " ",
        "# Ta sekcja odpowiada za wiadomosci enchant"
    })
    PLEnchantMessages enchant = new PLEnchantMessages();

    @Comment({
        " ",
        "# Komunikaty odpowiedzialne za ustawianie czasu i pogody"
    })
    public PLTimeAndWeatherMessages timeAndWeather = new PLTimeAndWeatherMessages();

    @Comment({
        " ",
        "# Komunikaty odpowiedzialne za kontenery",
    })
    PLContainerMessages container = new PLContainerMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące pojemnosci serwera"})
    public PLSetSlotMessages setSlot = new PLSetSlotMessages();

    @Comment({" ", "# Automatyczne wiadomości "})
    public PLAutoMessageMessages autoMessage = new PLAutoMessageMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące jail'a"})
    public PLJailMessages jailSection = new PLJailMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące Elder Guardian'a"})
    public PLElderGuardianMessages elderGuardian = new PLElderGuardianMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące demo screen'a"})
    public PLDemoScreenMessages demoScreen = new PLDemoScreenMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące końca gry"})
    public PLEndScreenMessages endScreen = new PLEndScreenMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące komendy /burn"})
    public PLBurnMessages burn = new PLBurnMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące trybu niewidoczności graczy"})
    public PLVanishMessages vanish = new PLVanishMessages();

    @Comment({" ", "# Ta sekcja odpowiada za funkcję MOTD (Message of the Day)"})
    public PLMotdMessages motd = new PLMotdMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące teleportu do graczy offline"})
    public PLTeleportOfflineMessages teleportToOfflinePlayer = new PLTeleportOfflineMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości o czasie gry graczy."})
    public PLPlaytimeMessages playtime = new PLPlaytimeMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące powertoolsów."})
    public PLPowertoolMessages powertool = new PLPowertoolMessages();

    @Comment({" ", "# Ta sekcja odpowiada za wiadomości dotyczące zamrażania graczy"})
    public PLFreezeMessages freeze = new PLFreezeMessages();
}
