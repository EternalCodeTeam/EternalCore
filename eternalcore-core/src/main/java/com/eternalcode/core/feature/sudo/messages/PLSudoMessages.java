package com.eternalcode.core.feature.sudo.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLSudoMessages implements SudoMessages {
    @Description({"# {PLAYER} - Gracz, który wykonał komendę, {TARGET} - Gracz lub konsola, na której wykonano komendę, {COMMAND} - Komenda"})
    public Notice sudoMessageSpy = Notice.chat("<dark_gray>[<dark_red>Sudo<dark_gray>] <red>{PLAYER}<dark_gray> wykonał komendę: <white>{COMMAND} <dark_gray>na: <white>{TARGET}");

    @Description({"# {TARGET} - Gracz lub konsola, na której wykonano komendę, {COMMAND} - Komenda"})
    public Notice sudoMessage = Notice.chat("<green>► <white>Wykonałeś komendę: <green>{COMMAND} <white>na: <green>{TARGET}");
}
