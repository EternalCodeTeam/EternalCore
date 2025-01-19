package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENHomeMessages implements HomeMessages {
    @Description("# {HOMES} - List of homes (separated by commas)")
    public Notice homeList = Notice.chat("<green>► <white>Available homes: <green>{HOMES}");

    @Description({" ", "# {HOME} - Home name"})
    public Notice create = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created.");
    public Notice delete = Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted.");
    public Notice overrideHomeLocation = Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden.");
    @Description({" ", "# {LIMIT} - Homes limit"})
    public Notice limit =
        Notice.chat("<red>► <white>You have reached the limit of homes! Your limit is <red>{LIMIT}<white>.");
    public Notice noHomesOwned = Notice.chat("<dark_red>✘ <red>You don't have any homes.");

    @Description({" ", "# Placeholders messages"})
    public String noHomesOwnedPlaceholder = "You don't have any homes.";

    @Description({
        " ",
        "# Home Admin Section, you can edit player homes as admin",
        "# {HOME} - Home name, {PLAYER} - Player name, {HOMES} - List of homes (separated by commas)"
    })
    public Notice overrideHomeLocationAsAdmin =
        Notice.chat("<green>► <white>Home <green>{HOME} <white>has been overridden for <green>{PLAYER}<white>.");
    public Notice playerNoOwnedHomes =
        Notice.chat("<dark_red>✘ <red>Player <dark_red>{PLAYER} <red>doesn't have any homes.");
    public Notice createAsAdmin =
        Notice.chat("<green>► <white>Home <green>{HOME} <white>has been created for <green>{PLAYER}<white>.");
    public Notice deleteAsAdmin =
        Notice.chat("<red>► <white>Home <red>{HOME} <white>has been deleted for <red>{PLAYER}<white>.");
    public Notice homeListAsAdmin =
        Notice.chat("<green>► <white>Available homes for <green>{PLAYER}<white>: <green>{HOMES}");
}
