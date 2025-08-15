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
    public Notice permissionMessage = Notice.chat("<red>✘ <dark_red>You don't have permission to perform this command! <red>({PERMISSIONS})");

    @Comment({" ", "# {USAGE} - Correct usage"})
    public Notice usageMessage = Notice.chat("<gold>✘ <white>Correct usage: <gold>{USAGE}");
    public Notice usageMessageHead = Notice.chat("<green>► <white>Correct usage:");
    public Notice usageMessageEntry = Notice.chat("<green>► <white>{USAGE}");

    @Comment(" ")
    public Notice missingPlayerName = Notice.chat("<red>✘ <dark_red>You must provide a player name!");
    public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>This player is currently offline!");
    public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Command is only for players!");
    public Notice numberBiggerThanZero = Notice.chat("<red>✘ <dark_red>The number must be greater than 0!");
    public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>The number must be greater than or equal to 0!");
    public Notice noItem = Notice.chat("<red>✘ <dark_red>You need item to use this command!");
    public Notice noMaterial = Notice.chat("<red>✘ <dark_red>This item doesn't exist");
    public Notice noArgument = Notice.chat("<red>✘ <dark_red>This argument doesn't exist");
    public Notice noDamaged = Notice.chat("<red>✘ <dark_red>This item can't be repaired");
    public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>You need damaged items to use this command!");
    public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>This enchantment doesn't exist");
    public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>This enchantment level is not supported!");
    public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>Invalid time format!");
    public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>World <red>{WORLD} <dark_red>doesn't exist!");
    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Incorrect number of chunks!");
    public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Incorrect location format! <red>({LOCATION})");
    public Notice noValidEntityScope = Notice.chat("<red>✘ <dark_red>No valid entity scope provided! Use a suggested option.");
}
