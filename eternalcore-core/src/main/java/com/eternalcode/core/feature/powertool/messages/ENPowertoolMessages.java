package com.eternalcode.core.feature.powertool.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENPowertoolMessages extends OkaeriConfig implements PowertoolMessages {

    @Comment({" ", "# Available placeholders:",
              "# {COMMAND} - Command that is assigned to the tool",
              "# {ITEM} - Item that is a power tool"}
    )
    Notice assigned =
        Notice.chat("<green>► <white>Assigned command <green>/{COMMAND}<white> to tool <green>{ITEM}.");
    Notice removed = Notice.chat("<green>► <white>Removed command from tool <green>{ITEM}.");
    Notice notAssigned = Notice.chat("<red>✘ <dark_red>This item is not a power tool. Use /powertool <command> "
        + "to assign a command to it.");
    Notice noItemInMainHand = Notice.chat("<red>✘ <dark_red>You must hold an item in your main hand to assign "
        + "a command/remove a command.");
    Notice emptyCommand = Notice.chat("<red>✘ <dark_red>The command cannot be empty! Please provide a valid "
        + "command to assign to the power tool.");
    Notice invalidCommand =
        Notice.chat("<red>✘ <dark_red>The provided command is invalid! Make sure the command exists.");
    Notice executionFailed = Notice.chat(
        "<red>✘ <dark_red>An error occurred while executing the command {COMMAND} assigned to the power tool.");
}
