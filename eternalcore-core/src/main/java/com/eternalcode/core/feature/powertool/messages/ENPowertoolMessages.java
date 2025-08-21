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
    public Notice assigned = Notice.chat("<green>► <white>Assigned command <green>/{COMMAND}<white> to tool <green>{ITEM}.");
    public Notice removed = Notice.chat("<green>► <white>Removed command from tool <green>{ITEM}.");
    public Notice notAssigned = Notice.chat("<red>✘ <dark_red>This item is not a power tool. Use /pt <command> to assign a command to it.");

    @Comment(" ")
    public Notice noItemInMainHand = Notice.chat("<red>✘ <dark_red>You must hold an item in your main hand to assign "
        + "a command/remove a command.");
    public Notice emptyCommand = Notice.chat("<red>✘ <dark_red>The command cannot be empty! Please provide a valid "
        + "command to assign to the power tool.");
}
