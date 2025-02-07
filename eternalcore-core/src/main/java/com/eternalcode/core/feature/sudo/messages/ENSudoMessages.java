package com.eternalcode.core.feature.sudo.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENSudoMessages implements SudoMessages {
    @Description({"# {PLAYER} - Player who executed the command, {COMMAND} - Command that the player executed"})
    public Notice sudoMessageSpy =
        Notice.chat("<dark_gray>[<dark_red>Sudo<dark_gray>] <red>{PLAYER}<dark_gray> executed command: <white>{COMMAND}");
    public Notice sudoMessage =
        Notice.chat("<green>► <white>You executed command: <green>{COMMAND} <white>on player: <green>{PLAYER}");
}
