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
    Notice homeList = Notice.chat("<color:#9d6eef>► <white>Lista domów: <color:#9d6eef>{HOMES}!");

    @Comment({" ", "# {HOME} - Nazwa domu"})
    Notice create = Notice.chat("<color:#9d6eef>► <white>Stworzono dom o nazwie <color:#9d6eef>{HOME}<white>!");
    Notice delete = Notice.chat("<red>► <white>Usunięto dom o nazwie <red>{HOME}<white>!");
    Notice overrideHomeLocation =
        Notice.chat("<color:#9d6eef>► <white>Nadpisałeś lokalizację domu <color:#9d6eef>{HOME}<white>!");

    @Comment({" ", "# {LIMIT} - Limit domów"})
    Notice limit = Notice.chat("<color:#9d6eef>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    Notice noHomesOwned = Notice.chat("<red>✘ <dark_red>Nie posiadasz żadnego domu!");

    @Comment({" ", "# Wiadomości placeholderów"})
    public String noHomesOwnedPlaceholder = "Nie posiadasz żadnego domu.";

    @Comment({
        " ",
        "# Sekcja wiadomości administracyjnych dla domów graczy",
        "# {HOME} - Nazwa domu, {PLAYER} - Gracz, {HOMES} - Lista domów"
    })
    Notice overrideHomeLocationAsAdmin = Notice.chat(
        "<color:#9d6eef>► <white>Nadpisałeś lokalizację domu <color:#9d6eef>{HOME} <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice playerNoOwnedHomes =
        Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie posiada żadnego domu!");
    Notice createAsAdmin =
        Notice.chat("<color:#9d6eef>► <white>Stworzono dom <color:#9d6eef>{HOME} <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice deleteAsAdmin =
        Notice.chat("<red>► <white>Usunięto dom <red>{HOME} <white>dla gracza <red>{PLAYER}<white>!");
    Notice homeListAsAdmin =
        Notice.chat("<color:#9d6eef>► <white>Lista domów gracza <color:#9d6eef>{PLAYER}<white>: <color:#9d6eef>{HOMES}!");
}
