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
    public Notice locationSet = Notice.chat("<green>► <white>Ustawiono lokalizację jail'a!");
    public Notice locationRemoved = Notice.chat("<red>✘ <dark_red>Usunięto lokalizację jail'a!");
    public Notice locationNotSet = Notice.chat("<red>✘ <dark_red>Lokalizacja jail'a nie została ustawiona!");
    public Notice locationOverride = Notice.chat("<green>► <white>Nadpisałeś lokalizację jail'a!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwięzienia gracza"})
    public Notice detainPrivate = Notice.chat("<green>► <white>Zostałeś uwięziony!");
    public Notice cannotUseCommand = Notice.chat("<red>✘ <dark_red>Nie możesz użyć tej komendy!");
    @Comment({" ", "# {PLAYER} - Gracz który został uwięziony"})
    public Notice detainBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony!");
    @Comment({" ", "# {PLAYER} - Gracz który został uwięziony"})
    public Notice detainOverride =
        Notice.chat("<green>► <white>Napisałeś nadaną karę graczowi <green>{PLAYER}<white>!");
    @Comment({" ", "# {REMAINING_TIME} - Pozostały czas do uwolnienia"})
    public Notice detainCountdown = Notice.actionbar("<red>Pozostało <green>{REMAINING_TIME} <red>do uwolnienia!");
    @Comment({" ", "# {PLAYER} - Administrator którego nie możesz uwięzić"})
    public Notice detainAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz uwięzić administratora <red>{PLAYER}!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące uwolnienia gracza"})
    @Comment({" ", "# {PLAYER} - Gracz który został uwolniony"})
    public Notice releaseBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwolniony!");
    public Notice releasePrivate = Notice.actionbar("<green>► <white>Zostałeś uwolniony!");
    public Notice releaseAll = Notice.chat("<green>► <white>Wszyscy gracze zostali uwolnieni!");
    public Notice releaseNoPlayers = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({" ", "# {PLAYER} - Nazwa gracza"})
    public Notice isNotPrisoner = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest uwięziony!");

    @Comment({" ", "# Sekcja odpowiedzialna za wiadomości dotyczące listy graczy w jail'u"})
    public Notice listHeader = Notice.chat("<green>► <white>Lista graczy w jail'u:");
    public Notice listEmpty = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");
    @Comment({" ",
                  "# {PLAYER} - Gracz który jest uwięziony, {DETAINED_BY} - Gracz który uwięził gracza, {REMAINING_TIME} - Czas pozostały do uwolnienia"})
    public Notice listPlayerEntry = Notice.chat(
        "<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony przez <green>{DETAINED_BY} <white>na czas <green>{REMAINING_TIME} <white>!");
}
