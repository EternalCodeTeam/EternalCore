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
    Notice homeList = Notice.chat("<green>► <white>Available homes: <green>{HOMES}");

    @Comment({" ", "# {HOME} - Home name"})
    Notice create = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created.");
    Notice delete = Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted.");
    Notice overrideHomeLocation = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden.");
    @Comment({" ", "# {LIMIT} - Homes limit"})
    Notice limit =
        Notice.chat("<red>► <white>You have reached the limit of homes! Your limit is <red>{LIMIT}<white>.");
    Notice noHomesOwned = Notice.chat("<dark_red>✘ <red>You don't have any homes.");

    @Comment({" ", "# Placeholders messages"})
    public String noHomesOwnedPlaceholder = "You don't have any homes.";

    @Comment({
        " ",
        "# Home Admin Section, you can edit player homes as admin",
        "# {HOME} - Home name, {PLAYER} - Player name, {HOMES} - List of homes (separated by commas)"
    })
    Notice overrideHomeLocationAsAdmin =
        Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden for <green>{PLAYER}<white>.");
    Notice playerNoOwnedHomes =
        Notice.chat("<dark_red>✘ <red>Player <dark_red>{PLAYER} <red>doesn't have any homes.");
    Notice createAsAdmin =
        Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created for <green>{PLAYER}<white>.");
    Notice deleteAsAdmin =
        Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted for <red>{PLAYER}<white>.");
    Notice homeListAsAdmin =
        Notice.chat("<green>► <white>Available homes for <green>{PLAYER}<white>: <green>{HOMES}");
}
