package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENHelpOpMessages extends OkaeriConfig implements HelpOpSection {
    @Comment("# {PLAYER} - Player who send message on /helpop, {TEXT} - message")
    Notice format =
        Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <color:#9d6eef>{PLAYER}<dark_gray>: <white>{TEXT}");
    @Comment(" ")
    Notice send = Notice.chat("<color:#9d6eef>► <white>This message has been successfully sent to administration");
    @Comment("# {TIME} - Time to next use (cooldown)")
    Notice helpOpDelay = Notice.chat("<red>✘ <white>You can use this command for: <color:#9d6eef>{TIME}");
}
