package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLHomeMessages extends OkaeriConfig implements HomeMessages {
    @Comment({ " ", "# {HOMES} - Lista domów" })
    Notice homeList = Notice.chat("<color:#9d6eef>► <white>Lista domów: <color:#9d6eef>{HOMES}!");

    @Comment({ " ", "# {HOME} - Nazwa domu" })
    Notice create = Notice.chat("<color:#9d6eef>► <white>Stworzono dom o nazwie <color:#9d6eef>{HOME}<white>!");
    Notice delete = Notice.chat("<color:#9d6eef>► <white>Usunięto dom o nazwie <color:#9d6eef>{HOME}<white>!");
    Notice overrideHomeLocation = Notice
            .chat("<color:#9d6eef>► <white>Nadpisałeś lokalizację domu <color:#9d6eef>{HOME}<white>!");

    @Comment({ " ", "# {LIMIT} - Limit domów" })
    Notice limit = Notice.chat("<red>✘ <dark_red>Osiągnąłeś limit domów! Twój limit to <red>{LIMIT}<dark_red>.");
    Notice noHomesOwned = Notice.chat("<red>✘ <dark_red>Nie posiadasz żadnego domu!");

    @Comment({ " ", "# Wiadomości placeholderów" })
    public String noHomesOwnedPlaceholder = "Nie posiadasz żadnego domu.";

    @Comment({
            " ",
            "# Sekcja wiadomości administracyjnych dla domów graczy",
            "# {HOME} - Nazwa domu, {PLAYER} - Gracz, {HOMES} - Lista domów"
    })
    Notice overrideHomeLocationAsAdmin = Notice.chat(
            "<color:#9d6eef>► <white>Nadpisałeś lokalizację domu <color:#9d6eef>{HOME} <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice playerNoOwnedHomes = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie posiada żadnego domu!");
    Notice createAsAdmin = Notice.chat(
            "<color:#9d6eef>► <white>Stworzono dom <color:#9d6eef>{HOME} <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice deleteAsAdmin = Notice.chat(
            "<color:#9d6eef>► <white>Usunięto dom <color:#9d6eef>{HOME} <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice homeListAsAdmin = Notice
            .chat("<color:#9d6eef>► <white>Lista domów gracza <color:#9d6eef>{PLAYER}<white>: <color:#9d6eef>{HOMES}!");
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
    public Notice missingArgument = Notice.chat("<dark_red>✘ <red>Brakujący argument! Proszę podać: <nazwa gracza> i <nazwa domu>");

    @Comment({
        " ",
        "# Lista domów graczy sugerowana podczas korzystania z komend /homeadmin gdy użytkownik błędnie wprowadzi nazwę domu",
        "# Placeholder: {PLAYER} - gracz podany w komendzie /homeadmin, {HOMES} - lista dostępnych domów tego gracza"
    })
    public Notice homeNotFound = Notice.chat("<dark_red>✘ <red>Nie znaleziono domu! <dark_red>Dostępne domy dla <red>{PLAYER}<dark_red>: <red>{HOMES}");
}
