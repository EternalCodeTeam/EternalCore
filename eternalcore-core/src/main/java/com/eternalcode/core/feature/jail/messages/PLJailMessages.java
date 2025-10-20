package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLJailMessages extends OkaeriConfig implements JailMessages {
    @Comment({" ", "# Sekcja odpowiedzialna za ustawianie lokalizacji jail'a"})
    Notice jailLocationSet = Notice.chat("<green>► <white>Ustawiono lokalizację jail'a!");
    Notice jailLocationRemove = Notice.chat("<red>✘ <dark_red>Usunięto lokalizację jail'a!");
    Notice jailLocationNotSet = Notice.chat("<red>✘ <dark_red>Lokalizacja jail'a nie została ustawiona!");
    Notice jailLocationOverride = Notice.chat("<green>► <white>Nadpisałeś lokalizację jail'a!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwięzienia gracza"})
    Notice jailDetainPrivate = Notice.chat("<green>► <white>Zostałeś uwięziony!");
    Notice jailCannotUseCommand = Notice.chat("<red>✘ <dark_red>Nie możesz użyć tej komendy!");
    @Comment({" ", "# {PLAYER} - Gracz który został uwięziony"})
    Notice jailDetainBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony!");
    @Comment({" ", "# {PLAYER} - Gracz który został uwięziony"})
    Notice jailDetainOverride =
        Notice.chat("<green>► <white>Napisałeś nadaną karę graczowi <green>{PLAYER}<white>!");
    @Comment({" ", "# {REMAINING_TIME} - Pozostały czas do uwolnienia"})
    Notice jailDetainCountdown = Notice.actionbar("<red>Pozostało <green>{REMAINING_TIME} <red>do uwolnienia!");
    @Comment({" ", "# {PLAYER} - Administrator którego nie możesz uwięzić"})
    Notice jailDetainAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz uwięzić administratora <red>{PLAYER}!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwolnienia gracza"})
    @Comment({" ", "# {PLAYER} - Gracz który został uwolniony"})
    Notice jailReleaseBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwolniony!");
    Notice jailReleasePrivate = Notice.actionbar("<green>► <white>Zostałeś uwolniony!");
    Notice jailReleaseAll = Notice.chat("<green>► <white>Wszyscy gracze zostali uwolnieni!");
    Notice jailReleaseNoPlayers = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({" ", "# {PLAYER} - Nazwa gracza"})
    Notice jailIsNotPrisoner = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest uwięziony!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące listy graczy w jail'u"})
    Notice jailListHeader = Notice.chat("<green>► <white>Lista graczy w jail'u:");
    Notice jailListEmpty = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({" ",
                  "# {PLAYER} - Gracz który jest uwięziony, {DETAINED_BY} - Gracz który uwięził gracza, {REMAINING_TIME} - Czas pozostały do uwolnienia"})
    Notice jailListPlayerEntry = Notice.chat(
        "<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony przez <green>{DETAINED_BY} <white>na czas <green>{REMAINING_TIME} <white>!");
}
