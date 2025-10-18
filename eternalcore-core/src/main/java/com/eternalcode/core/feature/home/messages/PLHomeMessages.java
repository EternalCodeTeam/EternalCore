package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLHomeMessages extends OkaeriConfig implements HomeMessages {
    @Comment({" ", "# {HOMES} - Lista domów"})
    Notice homeList = Notice.chat("<green>► <white>Lista domów: <green>{HOMES}!");

    @Comment({" ", "# {HOME} - Nazwa domu"})
    Notice create = Notice.chat("<green>► <white>Stworzono dom o nazwie <green>{HOME}<white>!");
    Notice delete = Notice.chat("<red>► <white>Usunięto dom o nazwie <red>{HOME}<white>!");
    Notice overrideHomeLocation =
        Notice.chat("<green>► <white>Nadpisałeś lokalizację domu <green>{HOME}<white>!");

    @Comment({" ", "# {LIMIT} - Limit domów"})
    Notice limit = Notice.chat("<green>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    Notice noHomesOwned = Notice.chat("<red>✘ <dark_red>Nie posiadasz żadnego domu!");

    @Comment({" ", "# Wiadomości placeholderów"})
    public String noHomesOwnedPlaceholder = "Nie posiadasz żadnego domu.";

    @Comment({
        " ",
        "# Sekcja wiadomości administracyjnych dla domów graczy",
        "# {HOME} - Nazwa domu, {PLAYER} - Gracz, {HOMES} - Lista domów"
    })
    Notice overrideHomeLocationAsAdmin = Notice.chat(
        "<green>► <white>Nadpisałeś lokalizację domu <green>{HOME} <white>dla gracza <green>{PLAYER}<white>!");
    Notice playerNoOwnedHomes =
        Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie posiada żadnego domu!");
    Notice createAsAdmin =
        Notice.chat("<green>► <white>Stworzono dom <green>{HOME} <white>dla gracza <green>{PLAYER}<white>!");
    Notice deleteAsAdmin =
        Notice.chat("<red>► <white>Usunięto dom <red>{HOME} <white>dla gracza <red>{PLAYER}<white>!");
    Notice homeListAsAdmin =
        Notice.chat("<green>► <white>Lista domów gracza <green>{PLAYER}<white>: <green>{HOMES}!");
}
