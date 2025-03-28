package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLHomeMessages implements HomeMessages {
    @Description({" ", "# {HOMES} - Lista domów"})
    public Notice homeList = Notice.chat("<green>► <white>Lista domów: <green>{HOMES}!");

    @Description({" ", "# {HOME} - Nazwa domu"})
    public Notice create = Notice.chat("<green>► <white>Stworzono dom o nazwie <green>{HOME}<white>!");
    public Notice delete = Notice.chat("<red>► <white>Usunięto dom o nazwie <red>{HOME}<white>!");
    public Notice overrideHomeLocation =
        Notice.chat("<green>► <white>Nadpisałeś lokalizację domu <green>{HOME}<white>!");

    @Description({" ", "# {LIMIT} - Limit domów"})
    public Notice limit = Notice.chat("<green>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    public Notice noHomesOwned = Notice.chat("<red>✘ <dark_red>Nie posiadasz żadnego domu!");

    @Description({" ", "# Wiadomości placeholderów"})
    public String noHomesOwnedPlaceholder = "Nie posiadasz żadnego domu.";

    @Description({
        " ",
        "# Sekcja wiadomości administracyjnych dla domów graczy",
        "# {HOME} - Nazwa domu, {PLAYER} - Gracz, {HOMES} - Lista domów"
    })
    public Notice overrideHomeLocationAsAdmin = Notice.chat(
        "<green>► <white>Nadpisałeś lokalizację domu <green>{HOME} <white>dla gracza <green>{PLAYER}<white>!");
    public Notice playerNoOwnedHomes =
        Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie posiada żadnego domu!");
    public Notice createAsAdmin =
        Notice.chat("<green>► <white>Stworzono dom <green>{HOME} <white>dla gracza <green>{PLAYER}<white>!");
    public Notice deleteAsAdmin =
        Notice.chat("<red>► <white>Usunięto dom <red>{HOME} <white>dla gracza <red>{PLAYER}<white>!");
    public Notice homeListAsAdmin =
        Notice.chat("<green>► <white>Lista domów gracza <green>{PLAYER}<white>: <green>{HOMES}!");
}
