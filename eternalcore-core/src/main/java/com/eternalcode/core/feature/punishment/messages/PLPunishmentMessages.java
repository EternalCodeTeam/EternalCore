package com.eternalcode.core.feature.punishment.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPunishmentMessages extends OkaeriConfig implements PunishmentMessages {

    @Comment({ " ", "# Sekcja odpowiedzialna za banowanie graczy" })
    @Comment({ " ", "# {PLAYER} - Ukarany gracz, {OPERATOR} - Administrator, {REASON} - Powód, {EXPIRES} - Czas wygaśnięcia" })
    Notice banBroadcast = Notice.chat("<red>► <white>Gracz <red>{PLAYER} <white>został zbanowany przez <red>{OPERATOR} <white>na <red>{EXPIRES}<white>! Powód: <gray>{REASON}");
    @Comment({ " ", "# Widoczne tylko dla graczy z uprawnieniem eternalcore.punishment.messages" })
    Notice banBroadcastSilent = Notice.chat("<dark_red>[CICHY BAN] <white>Gracz <red>{PLAYER} <white>został zbanowany przez <red>{OPERATOR} <white>na <red>{EXPIRES}<white>! Powód: <gray>{REASON}");
    @Comment({ " ", "# {PLAYER} - Gracz którego nie można zbanować" })
    Notice banCannotBanAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz zbanować administratora <red>{PLAYER}!");
    @Comment({ " ", "# {MIN} - Minimalna długość, {MAX} - Maksymalna długość" })
    Notice banInvalidReason = Notice.chat("<red>✘ <dark_red>Powód musi mieć od {MIN} do {MAX} znaków!");
    Notice banSuccessPrivate = Notice.chat("<red>► <white>Zbanowałeś gracza <red>{PLAYER}!");
    Notice banAlreadyBanned = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} jest już zbanowany!");
    Notice banPlayerTriesJoin = Notice.chat("<bold><red>Gracz {PLAYER} próbuje wejść na serwer ale jest zbanowany!");

    @Comment({ " ", "# Sekcja odpowiedzialna za banowanie ip graczy" })
    Notice banIpSuccessPrivate = Notice.chat("<red>► <white>Zbanowałeś gracza <red>{PLAYER} <white>oraz jego adres IP!");
    Notice banIpNoAddress = Notice.chat("<red>✘ <dark_red>Nie udało się ustalić adresu IP gracza {PLAYER}! (może nigdy nie dołączył?)");

    @Comment({ " ", "# Sekcja odpowiedzialna za odbanowywanie graczy" })
    Notice unbanBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został odbanowany przez <green>{OPERATOR}!");
    Notice unbanSuccessPrivate = Notice.chat("<green>► <white>Odbanowałeś gracza <green>{PLAYER}!");
    Notice unbanNotBanned = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest zbanowany!");

    @Comment({ " ", "# {IP} - Adres IP, {OPERATOR} - Administrator" })
    Notice unbanIpBroadcast = Notice.chat("<green>► <white>Adres IP <green>{IP} <white>został odbanowany przez <green>{OPERATOR}!");
    Notice unbanIpSuccessPrivate = Notice.chat("<green>► <white>Odbanowałeś adres IP <green>{IP}!");
    Notice unbanIpNotBanned = Notice.chat("<red>✘ <dark_red>Adres IP {IP} nie jest zbanowany!");

    @Comment({ " ", "# Sekcja odpowiedzialna za wyrzucanie graczy" })
    Notice kickBroadcast = Notice.chat("<red>► <white>Gracz <red>{PLAYER} <white>został wyrzucony przez <red>{OPERATOR}<white>! Powód: <gray>{REASON}");
    Notice kickBroadcastSilent = Notice.chat("<dark_red>[CICHY KICK] <white>Gracz <red>{PLAYER} <white>został wyrzucony przez <red>{OPERATOR}<white>! Powód: <gray>{REASON}");
    Notice kickCannotKickAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz wyrzucić administratora <red>{PLAYER}!");
    Notice kickInvalidReason = Notice.chat("<red>✘ <dark_red>Powód musi mieć od {MIN} do {MAX} znaków!");
    Notice kickSuccessPrivate = Notice.chat("<red>► <white>Wyrzuciłeś gracza <red>{PLAYER}!");
    Notice kickNotOnline = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest online!");
    @Comment({ " ", "# {OPERATOR} - Administrator, {REASON} - Powód, {COUNT} - Liczba wyrzuconych graczy" })
    Notice kickAllBroadcast = Notice.chat("<red>► <white>Serwer został wyczyszczony przez <red>{OPERATOR}<white>! "
        + "Powód: <gray>{REASON} <white>(<red>{COUNT}<white> graczy)");

    @Comment({ " ", "# Sekcja odpowiedzialna za wyciszanie graczy" })
    Notice muteBroadcast = Notice.chat("<red>► <white>Gracz <red>{PLAYER} <white>został wyciszony przez <red>{OPERATOR} <white>na <red>{EXPIRES}<white>! Powód: <gray>{REASON}");
    Notice muteBroadcastSilent = Notice.chat("<dark_red>[CICHE WYCISZENIE] <white>Gracz <red>{PLAYER} <white>został wyciszony przez <red>{OPERATOR} <white>na <red>{EXPIRES}<white>! Powód: <gray>{REASON}");
    Notice muteCannotMuteAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz wyciszyć administratora <red>{PLAYER}!");
    Notice muteInvalidReason = Notice.chat("<red>✘ <dark_red>Powód musi mieć od {MIN} do {MAX} znaków!");
    Notice muteSuccessPrivate = Notice.chat("<red>► <white>Wyciszyłeś gracza <red>{PLAYER}!");
    Notice muteAlreadyMuted = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} jest już wyciszony!");
    Notice muteBlockedChat = Notice.chat("<red>✘ <dark_red>Jesteś wyciszony! Powód: <gray>{REASON} <dark_red>Pozostało: <gray>{REMAINING_TIME}");

    @Comment({ " ", "# Sekcja odpowiedzialna za odciszanie graczy" })
    Notice unmuteBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został odciszony przez <green>{OPERATOR}!");
    Notice unmuteSuccessPrivate = Notice.chat("<green>► <white>Odciszyłeś gracza <green>{PLAYER}!");
    Notice unmuteNotMuted = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest wyciszony!");

    @Comment({ " ", "# Sekcja odpowiedzialna za ostrzeżenia graczy" })
    Notice warnBroadcast = Notice.chat("<yellow>► <white>Gracz <yellow>{PLAYER} <white>otrzymał ostrzeżenie od <yellow>{OPERATOR}<white>! Powód: <gray>{REASON}");
    Notice warnBroadcastSilent = Notice.chat("<gold>[CICHE OSTRZEŻENIE] <white>Gracz <yellow>{PLAYER} <white>otrzymał ostrzeżenie od <yellow>{OPERATOR}<white>! Powód: <gray>{REASON}");
    Notice warnCannotWarnAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz ostrzec administratora <red>{PLAYER}!");
    Notice warnInvalidReason = Notice.chat("<red>✘ <dark_red>Powód musi mieć od {MIN} do {MAX} znaków!");
    Notice warnSuccessPrivate = Notice.chat("<yellow>► <white>Ostrzegłeś gracza <yellow>{PLAYER}!");
    @Comment({ " ", "# {PLAYER} - Ukarany, {ACTION} - Rodzaj kary, {EXPIRES} - Czas wygaśnięcia, {COUNT} - Liczba ostrzeżeń" })
    Notice warnEscalationBroadcast = Notice.chat("<gold>► <white>Gracz <yellow>{PLAYER} <white>otrzymał automatyczną karę (<yellow>{ACTION}<white>, {EXPIRES}) za osiągnięcie <yellow>{COUNT} <white>ostrzeżeń!");

    @Comment({ " ", "# {PLAYER} - Sprawdzany gracz, {ACCOUNTS} - Lista alt-kont oddzielona przecinkami" })
    Notice altAccountsFound = Notice.chat("<yellow>► <white>Konta powiązane z <yellow>{PLAYER}<white>: <gray>{ACCOUNTS}");
    Notice altAccountsNone = Notice.chat("<yellow>► <white>Nie znaleziono kont powiązanych z <yellow>{PLAYER}");

    @Comment({ " ", "# Sekcja odpowiedzialna za historię kar" })
    @Comment({ " ", "# {PAGE} - Numer strony" })
    Notice historyHeaderRecent = Notice.chat("<gray>► <white>Ostatnie kary na serwerze <gray>(strona {PAGE}):");
    @Comment({ " ", "# {PLAYER} - Sprawdzany gracz, {PAGE} - Numer strony" })
    Notice historyHeaderPlayer = Notice.chat("<gray>► <white>Historia kar gracza <yellow>{PLAYER} <gray>(strona {PAGE}):");
    @Comment({ " ", "# {DATE} - Data, {ACTION} - Typ kary, {PLAYER} - Ukarany, {OPERATOR} - Administrator, {REASON} - Powód" })
    Notice historyEntry = Notice.chat("<gray>[<white>{DATE}<gray>] <yellow>{ACTION} <white>{PLAYER} <gray>przez <white>{OPERATOR} <gray>- <white>{REASON}");
    Notice historyEmpty = Notice.chat("<gray>Brak wpisów w historii.");
}
