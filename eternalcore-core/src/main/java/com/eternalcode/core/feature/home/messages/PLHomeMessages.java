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
    public Notice homeList = Notice.chat("<green>► <white>Lista domów: <green>{HOMES}!");

    @Comment({" ", "# {HOME} - Nazwa domu"})
    public Notice create = Notice.chat("<green>► <white>Stworzono dom o nazwie <green>{HOME}<white>!");
    public Notice delete = Notice.chat("<red>► <white>Usunięto dom o nazwie <red>{HOME}<white>!");
    public Notice overrideHomeLocation =
        Notice.chat("<green>► <white>Nadpisałeś lokalizację domu <green>{HOME}<white>!");

    @Comment({" ", "# {LIMIT} - Limit domów"})
    public Notice limit = Notice.chat("<green>► <white>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<white>.");
    public Notice noHomesOwned = Notice.chat("<red>✘ <dark_red>Nie posiadasz żadnego domu!");

    @Comment({" ", "# Wiadomości placeholderów"})
    public String noHomesOwnedPlaceholder = "Nie posiadasz żadnego domu.";

    @Comment({
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
    public Notice noHomesOnListAsAdmin =
        Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie posiada domów.");

    @Comment({
        " ",
        "# {PLAYER} - nick właściciela domu na który przeteleportowano się za pomocą /homeadmin home",
        "# {HOME} - nazwa domu"
    })
    public Notice teleportedAsAdmin =
        Notice.chat("<green>► <white>Przeteleportowano do domu gracza: <green>{PLAYER}<white> - <green>{HOME}!");

    @Comment({
        " ",
        "# Wiadomość wysyłana do gracza który nie wpisał poprawnie agumentów do komend /homeadmin"
    })
    public Notice missingArgument = Notice.chat("<dark_red>✘ <red> Missing argument! Please input: <Player name> and <Home name>");

    @Comment({
        " ",
        "# Lista domów graczy sugerowana podczas korzystania z komend /homeadmin gdy użytkownik błędnie wprowadzi nazwę domu",
        "# Placeholder: {PLAYER} - gracz podany w komendzie /homeadmin, {HOMES} - lista dostępnych domów tego gracza"
    })
    public Notice homeNotFound = Notice.chat("<dark_red>✘ <red>Home not found! <dark_red> Available homes for <red>{PLAYER}<dark_red>: <red>{HOMES}");
}
