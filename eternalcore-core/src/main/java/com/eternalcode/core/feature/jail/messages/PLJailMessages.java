package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLJailMessages extends OkaeriConfig implements JailMessages {
    @Comment({ " ", "# Sekcja odpowiedzialna za ustawianie lokalizacji jail'a" })
    Notice jailLocationSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono lokalizację jail'a!");
    Notice jailLocationRemove = Notice.chat("<red>✘ <dark_red>Usunięto lokalizację jail'a!");
    Notice jailLocationNotSet = Notice.chat("<red>✘ <dark_red>Lokalizacja jail'a nie została ustawiona!");
    Notice jailLocationOverride = Notice.chat("<color:#9d6eef>► <white>Nadpisałeś lokalizację jail'a!");

    @Comment({ " ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwięzienia gracza" })
    Notice jailDetainPrivate = Notice.chat("<color:#9d6eef>► <white>Zostałeś uwięziony!");
    Notice jailCannotUseCommand = Notice.chat("<red>✘ <dark_red>Nie możesz użyć tej komendy!");
    @Comment({ " ", "# {PLAYER} - Gracz który został uwięziony" })
    Notice jailDetainBroadcast = Notice
            .chat("<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>został uwięziony!");
    @Comment({ " ", "# {PLAYER} - Gracz który został uwięziony" })
    Notice jailDetainOverride = Notice
            .chat("<color:#9d6eef>► <white>Nadpisałeś nadaną karę graczowi <color:#9d6eef>{PLAYER}<white>!");
    @Comment({ " ", "# {REMAINING_TIME} - Pozostały czas do uwolnienia" })
    Notice jailDetainCountdown = Notice.actionbar("<red>Pozostało <color:#9d6eef>{REMAINING_TIME} <red>do uwolnienia!");
    @Comment({ " ", "# {PLAYER} - Administrator którego nie możesz uwięzić" })
    Notice jailDetainAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz uwięzić administratora <red>{PLAYER}!");

    @Comment({ " ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwolnienia gracza" })
    @Comment({ " ", "# {PLAYER} - Gracz który został uwolniony" })
    Notice jailReleaseBroadcast = Notice
            .chat("<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>został uwolniony!");
    Notice jailReleasePrivate = Notice.actionbar("<color:#9d6eef>► <white>Zostałeś uwolniony!");
    Notice jailReleaseAll = Notice.chat("<color:#9d6eef>► <white>Wszyscy gracze zostali uwolnieni!");
    Notice jailReleaseNoPlayers = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({ " ", "# {PLAYER} - Nazwa gracza" })
    Notice jailIsNotPrisoner = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest uwięziony!");

    @Comment({ " ", "# Sekcja odpowiedzialna za wiadomości dotyczące listy graczy w jail'u" })
    Notice jailListHeader = Notice.chat("<color:#9d6eef>► <white>Lista graczy w jail'u:");
    Notice jailListEmpty = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({ " ",
            "# {PLAYER} - Gracz który jest uwięziony, {DETAINED_BY} - Gracz który uwięził gracza, {REMAINING_TIME} - Czas pozostały do uwolnienia" })
    Notice jailListPlayerEntry = Notice.chat(
            "<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>został uwięziony przez <color:#9d6eef>{DETAINED_BY} <white>na czas <color:#9d6eef>{REMAINING_TIME} <white>!");
}
