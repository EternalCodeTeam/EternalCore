package com.eternalcode.core.litecommand.argument.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENArgumentMessages extends OkaeriConfig implements ArgumentMessages {
    @Comment("# {PERMISSIONS} - Required permission")
    Notice permissionMessage = Notice.chat("<red>✘ <dark_red>You don't have permission to perform this command! <red>({PERMISSIONS})");

    @Comment({" ", "# {USAGE} - Correct usage"})
    Notice usageMessage = Notice.chat("<gold>✘ <white>Correct usage: <gold>{USAGE}");
    Notice usageMessageHead = Notice.chat("<red>✘ <dark_red>Correct usage:");
    Notice usageMessageEntry = Notice.chat("<red>✘ <red>{USAGE}");

    @Comment(" ")
    Notice missingPlayerName = Notice.chat("<red>✘ <dark_red>You must provide a player name!");
    Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>This player is currently offline!");
    Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Command is only for players!");
    Notice numberBiggerThanZero = Notice.chat("<red>✘ <dark_red>The number must be greater than 0!");
    Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>The number must be greater than or equal to 0!");
    Notice stackNumberIncorrect = Notice.chat("<red>✘ <dark_red>Incorrect number!");
    Notice noItem = Notice.chat("<red>✘ <dark_red>You need item to use this command!");
    Notice noMaterial = Notice.chat("<red>✘ <dark_red>This item doesn't exist");
    Notice noArgument = Notice.chat("<red>✘ <dark_red>This argument doesn't exist");
    Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>World <red>{WORLD} <dark_red>doesn't exist!");
    Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Incorrect location format! <red>({LOCATION})");

    public Notice missingPlayer = Notice.chat("<red>✘ <dark_red>Couldn't find player!");
}
