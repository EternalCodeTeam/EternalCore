package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENHelpOpMessages implements HelpOpSection {
    @Description("# {PLAYER} - Player who send message on /helpop, {TEXT} - message")
    public Notice format =
        Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<dark_gray>: <white>{TEXT}");
    @Description(" ")
    public Notice send = Notice.chat("<green>► <white>This message has been successfully sent to administration");
    @Description("# {TIME} - Time to next use (cooldown)")
    public Notice helpOpDelay = Notice.chat("<gold>✘ <red>You can use this command for: <gold>{TIME}");
}
