package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.feature.back.messages.PLBackMessages;
import com.eternalcode.core.feature.freeze.messages.PLFreezeMessages;
import com.eternalcode.core.feature.near.messages.PLNearMessages;
import com.eternalcode.core.feature.playtime.messages.PLPlaytimeMessages;
import com.eternalcode.core.feature.clear.messages.PLClearMessages;
import com.eternalcode.core.feature.container.messages.PLContainerMessages;
import com.eternalcode.core.feature.repair.messages.PLRepairMessages;
import com.eternalcode.core.litecommand.argument.messages.PLArgumentMessages;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.adminchat.messages.PLAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.PLAfkMessages;
import com.eternalcode.core.feature.automessage.messages.PLAutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.PLBroadcastMessages;
import com.eternalcode.core.feature.burn.messages.PLBurnMessages;
import com.eternalcode.core.feature.butcher.messages.PLButcherMessages;
import com.eternalcode.core.feature.chat.messages.PLChatMessages;
import com.eternalcode.core.feature.clear.messages.PLClearMessages;
import com.eternalcode.core.feature.container.messages.PLContainerMessages;
import com.eternalcode.core.feature.deathmessage.messages.PLDeathMessages;
import com.eternalcode.core.feature.disposal.messages.PLDisposalMessages;
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
import com.eternalcode.core.feature.ignore.messages.PLIgnoreMessages;
import com.eternalcode.core.feature.itemedit.messages.PLItemEditMessages;
import com.eternalcode.core.feature.jail.messages.PLJailMessages;
import com.eternalcode.core.feature.joinmessage.messages.PLJoinMessage;
import com.eternalcode.core.feature.kill.messages.PLKillMessages;
import com.eternalcode.core.feature.motd.messages.PLMotdMessages;
import com.eternalcode.core.feature.msg.messages.PLMsgMessages;
import com.eternalcode.core.feature.near.messages.PLNearMessages;
import com.eternalcode.core.feature.onlineplayers.messages.PLOnlineMessages;
import com.eternalcode.core.feature.ping.PLPingMessages;
import com.eternalcode.core.feature.playtime.messages.PLPlaytimeMessages;
import com.eternalcode.core.feature.powertool.messages.PLPowertoolMessages;
import com.eternalcode.core.feature.quitmessage.messages.PLQuitMessage;
import com.eternalcode.core.feature.randomteleport.messages.PLRandomTeleportMessages;
import com.eternalcode.core.feature.repair.messages.PLRepairMessages;
import com.eternalcode.core.feature.seen.messages.PLSeenMessages;
import com.eternalcode.core.feature.setslot.messages.PLSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.PLSignEditorMessages;
import com.eternalcode.core.feature.skull.messages.PLSkullMessages;
import com.eternalcode.core.feature.spawn.messages.PLSpawnMessages;
import com.eternalcode.core.feature.speed.messages.PLSpeedMessages;
import com.eternalcode.core.feature.sudo.messages.PLSudoMessages;
import com.eternalcode.core.feature.teleport.messages.PLTeleportMessages;
import com.eternalcode.core.feature.teleportoffline.PLTeleportOfflineMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.PLTeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.PLTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.PLTimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.PLVanishMessages;
import com.eternalcode.core.feature.warp.messages.PLWarpMessages;
import com.eternalcode.core.feature.whois.PLWhoIsMessages;
import com.eternalcode.core.litecommand.argument.messages.PLArgumentMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Language;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import lombok.Getter;
import lombok.experimental.Accessors;

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
    @Comment("# Argumenty komend")
    public PLArgumentMessages argument = new PLArgumentMessages();

    @Comment("# Ogólne formatowanie wartości")
    public PLFormatSection format = new PLFormatSection();

    @Getter
    public static class PLFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>włączony/a";
        public String disable = "<red>wyłączony/a";
    }

    @Comment("# Czat")
    public PLChatMessages chat = new PLChatMessages();

    @Comment("# Czat administracji")
    public PLAdminChatMessages adminChat = new PLAdminChatMessages();

    @Comment("# Wiadomości prywatne")
    public PLMsgMessages msg = new PLMsgMessages();

    @Comment("# Broadcast")
    public PLBroadcastMessages broadcast = new PLBroadcastMessages();

    @Comment("# Automatyczne wiadomości")
    public PLAutoMessageMessages autoMessage = new PLAutoMessageMessages();

    @Comment("# Wiadomość dołączenia do serwera")
    public PLJoinMessage join = new PLJoinMessage();

    @Comment("# Wiadomość wyjścia z serwera")
    public PLQuitMessage quit = new PLQuitMessage();

    @Comment("# MOTD (Message of the Day)")
    public PLMotdMessages motd = new PLMotdMessages();

    @Comment("# Wiadomości o śmierci")
    public PLDeathMessages deathMessage = new PLDeathMessages();

    @Comment("# AFK")
    public PLAfkMessages afk = new PLAfkMessages();

    @Comment("# Ignorowanie graczy")
    public PLIgnoreMessages ignore = new PLIgnoreMessages();

    @Comment("# Teleportacja")
    public PLTeleportMessages teleport = new PLTeleportMessages();

    @Comment("# Prośby o teleportację (TPA)")
    public PLTeleportRequestMessages tpa = new PLTeleportRequestMessages();

    @Comment("# Teleportacja do losowego gracza")
    public PLTeleportToRandomPlayerMessages teleportToRandomPlayer = new PLTeleportToRandomPlayerMessages();

    @Comment("# Powrót do poprzedniej lokalizacji")
    public PLBackMessages back = new PLBackMessages();

    @Comment("# Losowa teleportacja")
    public PLRandomTeleportMessages randomTeleport = new PLRandomTeleportMessages();

    @Comment("# Teleportacja do graczy offline")
    public PLTeleportOfflineMessages teleportToOfflinePlayer = new PLTeleportOfflineMessages();

    @Comment("# Osobiste punkty teleportacji (home)")
    public PLHomeMessages home = new PLHomeMessages();

    @Comment("# Punkty teleportacji (warp)")
    public PLWarpMessages warp = new PLWarpMessages();

    @Comment("# Spawn")
    public PLSpawnMessages spawn = new PLSpawnMessages();

    @Comment("# Gracze w pobliżu")
    public PLNearMessages near = new PLNearMessages();

    @Comment("# Leczenie")
    public PLHealMessages heal = new PLHealMessages();

    @Comment("# Karmienie")
    public PLFeedMessages feed = new PLFeedMessages();

    @Comment("# Latanie")
    public PLFlyMessages fly = new PLFlyMessages();

    @Comment("# Prędkość")
    public PLSpeedMessages speed = new PLSpeedMessages();

    @Comment("# Tryb nieśmiertelności")
    public PLGodModeMessages godmode = new PLGodModeMessages();

    @Comment("# Zamrażanie graczy")
    public PLFreezeMessages freeze = new PLFreezeMessages();

    @Comment("# Tryb niewidoczności")
    public PLVanishMessages vanish = new PLVanishMessages();

    @Comment("# Tryb gry")
    public PLGameModeMessages gamemode = new PLGameModeMessages();

    @Comment("# Czas i pogoda")
    public PLTimeAndWeatherMessages timeAndWeather = new PLTimeAndWeatherMessages();

    @Comment("# Ping")
    public PLPingMessages ping = new PLPingMessages();

    @Comment("# Gracze online")
    public PLOnlineMessages online = new PLOnlineMessages();

    @Comment("# Informacje o graczu")
    public PLWhoIsMessages whois = new PLWhoIsMessages();

    @Comment("# Ostatnie logowanie gracza")
    public PLSeenMessages seen = new PLSeenMessages();

    @Comment("# Czas gry")
    public PLPlaytimeMessages playtime = new PLPlaytimeMessages();

    @Comment("# Dawanie przedmiotów")
    public PLGiveMessages give = new PLGiveMessages();

    @Comment("# Czyszczenie ekwipunku")
    public PLClearMessages clear = new PLClearMessages();

    @Comment("# Kosz")
    public PLDisposalMessages disposal = new PLDisposalMessages();

    @Comment("# Kontenery")
    public PLContainerMessages container = new PLContainerMessages();

    @Comment("# Ustawianie slotów")
    public PLSetSlotMessages setSlot = new PLSetSlotMessages();

    @Comment("# Edycja przedmiotów")
    public PLItemEditMessages itemEdit = new PLItemEditMessages();

    @Comment("# Enchant")
    public PLEnchantMessages enchant = new PLEnchantMessages();

    @Comment("# Naprawa")
    public PLRepairMessages repair = new PLRepairMessages();

    @Comment("# Czaszki graczy")
    public PLSkullMessages skull = new PLSkullMessages();

    @Comment("# Edycja tabliczek")
    public PLSignEditorMessages signEditor = new PLSignEditorMessages();

    @Comment("# Powertool")
    public PLPowertoolMessages powertool = new PLPowertoolMessages();

    @Comment("# Zabijanie")
    public PLKillMessages kill = new PLKillMessages();

    @Comment("# Podpalanie")
    public PLBurnMessages burn = new PLBurnMessages();

    @Comment("# Usuwanie mobów")
    public PLButcherMessages butcher = new PLButcherMessages();

    @Comment("# Więzienie")
    public PLJailMessages jailSection = new PLJailMessages();

    @Comment("# Sudo")
    public PLSudoMessages sudo = new PLSudoMessages();

    @Comment("# Czat pomocy")
    public PLHelpOpMessages helpOp = new PLHelpOpMessages();

    @Comment("# Elder Guardian")
    public PLElderGuardianMessages elderGuardian = new PLElderGuardianMessages();

    @Comment("# Demo Screen")
    public PLDemoScreenMessages demoScreen = new PLDemoScreenMessages();

    @Comment("# End Screen")
    public PLEndScreenMessages endScreen = new PLEndScreenMessages();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "lang" + File.separator + "pl_messages.yml");
    }
}
