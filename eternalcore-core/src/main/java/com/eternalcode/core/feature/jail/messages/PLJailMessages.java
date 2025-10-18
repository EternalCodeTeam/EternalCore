package com.eternalcode.core.feature.jail.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLJailMessages extends OkaeriConfig implements JailMessages {

    @Comment("# Wiadomości dotyczące ustawiania lokalizacji więzienia (jail'a)")
    public Notice locationSet = Notice.chat("<green>► <white>Ustawiono lokalizację jail'a!");
    public Notice locationRemoved = Notice.chat("<red>✘ <dark_red>Usunięto lokalizację jail'a!");
    public Notice locationNotSet = Notice.chat("<red>✘ <dark_red>Lokalizacja jail'a nie została ustawiona!");
    public Notice locationOverride = Notice.chat("<green>► <white>Nadpisano istniejącą lokalizację jail'a!");

    @Comment("# Wiadomości dotyczące uwięzienia gracza")
    public Notice detained = Notice.chat("<green>► <white>Zostałeś uwięziony!");
    public Notice cannotUseCommand = Notice.chat("<red>✘ <dark_red>Nie możesz użyć tej komendy!");

    @Comment("# {PLAYER} - nazwa gracza, który został uwięziony")
    public Notice detainBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony!");

    @Comment("# {PLAYER} - nazwa gracza, któremu ponownie nadano karę")
    public Notice detainOverride = Notice.chat("<green>► <white>Nadpisano karę gracza <green>{PLAYER}<white>!");

    @Comment("# {REMAINING_TIME} - czas pozostały do uwolnienia gracza")
    public Notice detainCountdown = Notice.actionbar("<red>Pozostało <green>{REMAINING_TIME} <red>do uwolnienia!");

    @Comment("# {PLAYER} - nazwa administratora, którego nie można uwięzić")
    public Notice detainAdmin = Notice.chat("<red>✘ <dark_red>Nie możesz uwięzić administratora <red>{PLAYER}!");

    @Comment("# Wiadomości dotyczące uwolnienia gracza")
    @Comment("# {PLAYER} - nazwa gracza, który został uwolniony")
    public Notice releaseBroadcast = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwolniony!");

    public Notice released = Notice.actionbar("<green>► <white>Zostałeś uwolniony!");
    public Notice releaseAll = Notice.chat("<green>► <white>Wszyscy gracze zostali uwolnieni!");
    public Notice releaseNoPlayers = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");

    @Comment("# {PLAYER} - nazwa gracza, który nie jest uwięziony")
    public Notice isNotPrisoner = Notice.chat("<red>✘ <dark_red>Gracz {PLAYER} nie jest uwięziony!");

    @Comment("# Wiadomości dotyczące listy graczy przebywających w jail'u")
    public Notice listHeader = Notice.chat("<green>► <white>Lista graczy w jail'u:");
    public Notice listEmpty = Notice.chat("<red>✘ <dark_red>Nikt nie jest uwięziony!");

    @Comment("# {PLAYER} - uwięziony gracz, {DETAINED_BY} - osoba, która go uwięziła, {REMAINING_TIME} - czas pozostały do uwolnienia")
    public Notice listPlayerEntry = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został uwięziony przez <green>{DETAINED_BY} <white>na czas <green>{REMAINING_TIME}<white>!");
}
