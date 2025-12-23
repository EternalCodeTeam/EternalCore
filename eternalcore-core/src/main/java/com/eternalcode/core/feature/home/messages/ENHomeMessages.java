package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENHomeMessages extends OkaeriConfig implements HomeMessages {
    @Comment("# {HOMES} - List of homes (separated by commas)")
    Notice homeList = Notice.chat("<color:#9d6eef>► <white>Available homes: <color:#9d6eef>{HOMES}");
    @Comment({
        " ",
        "# Format for single home entry in user's home list. Set to \"{HOME}\" if you want to display basic list.",
        "# {HOME} - Home name"
    })
    String homeListEntryFormat = "<hover:show_text:'<gray>Click to teleport'><click:run_command:'/home {HOME}'>{HOME}</click></hover>";

    @Comment({ " ", "# {HOME} - Home name" })
    Notice create = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been created.");
    Notice delete = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been deleted.");
    Notice overrideHomeLocation = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been overridden.");
    @Comment({ " ", "# {LIMIT} - Homes limit" })
    Notice limit = Notice.chat("<red>✘ <dark_red>You have reached the limit of homes! Your limit is <red>{LIMIT}<dark_red>.");
    Notice noHomesOwned = Notice.chat("<dark_red>✘ <red>You don't have any homes.");

    @Comment({ " ", "# Placeholders messages" })
    String noHomesOwnedPlaceholder = "You don't have any homes.";

    @Comment({
            " ",
            "# Home Admin Section, you can edit player homes as admin",
            "# {HOME} - Home name, {PLAYER} - Player name, {HOMES} - List of homes (separated by commas)"
    })
    Notice overrideHomeLocationAsAdmin = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been overridden for <color:#9d6eef>{PLAYER}<white>.");
    Notice playerNoOwnedHomes = Notice.chat("<dark_red>✘ <red>Player <dark_red>{PLAYER} <red>doesn't have any homes.");
    Notice createAsAdmin = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been created for <color:#9d6eef>{PLAYER}<white>.");
    Notice deleteAsAdmin = Notice.chat("<color:#9d6eef>► <white>Home <color:#9d6eef>{HOME} <white>has been deleted for <color:#9d6eef>{PLAYER}<white>.");
    Notice homeListAsAdmin = Notice.chat("<color:#9d6eef>► <white>Available homes for <color:#9d6eef>{PLAYER}<white>: <color:#9d6eef>{HOMES}");
    @Comment({
        " ",
        "# Format for single home entry in admin's home list. Set to \"{HOME}\" if you want to display basic list.",
        "# {HOME} - Home name, {PLAYER} - Player name"
    })
    String homeListEntryFormatAsAdmin = "<hover:show_text:'<gray>Click to teleport'><click:run_command:'/homeadmin home {PLAYER} {HOME}'>{HOME}</click></hover>";
    Notice noHomesOnListAsAdmin = Notice.chat("<red>► <dark_red>Player <red>{PLAYER} <dark_red>does not have any homes!");

    @Comment({
        " ",
        "# {PLAYER} - username of home owner whom user teleported by using /homeadmin home",
        "# {HOME} - name of the home"
    })
    Notice teleportedAsAdmin = Notice.chat("<color:#9d6eef>► <white>Teleported to: <green>{PLAYER}<white> - <green>{HOME}!");
}
