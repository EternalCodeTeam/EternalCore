package com.eternalcode.core.feature.chat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENChatMessages extends OkaeriConfig implements ChatMessages {
    Notice chatDisabled = Notice.chat("<color:#9d6eef>► <white>Chat has been disabled by <color:#9d6eef>{PLAYER}<white>!");
    Notice chatEnabled = Notice.chat("<color:#9d6eef>► <white>Chat has been enabled by <color:#9d6eef>{PLAYER}<white>!");
    Notice chatCleared = Notice.chat("<color:#9d6eef>► <white>Chat has been cleared by <color:#9d6eef>{PLAYER}<white>!");

    @Comment
    Notice chatAlreadyDisabled = Notice.chat("<red>✘ <dark_red>Chat is already disabled!");
    Notice chatAlreadyEnabled = Notice.chat("<red>✘ <dark_red>Chat is already enabled!");

    @Comment
    Notice slowModeSet = Notice.chat("<color:#9d6eef>► <white>Slow mode has been set to {SLOWMODE}");
    Notice slowModeDisabled = Notice.chat("<color:#9d6eef>► <white>Slow mode has been disabled by <color:#9d6eef>{PLAYER}<white>!");

    @Comment({" ", "# {TIME} - Slow mode time"})
    Notice slowModeNotReady = Notice.chat("<red>✘ <dark_red>You can send the next message in <red>{TIME}<dark_red>!");

    @Comment
    Notice chatDisabledInfo = Notice.chat("<red>✘ <dark_red>Chat is currently disabled!");

    @Comment
    Notice commandNotFound = Notice.chat("<red>✘ <dark_red>Command <red>{COMMAND} <dark_red>does not exist!");

}
