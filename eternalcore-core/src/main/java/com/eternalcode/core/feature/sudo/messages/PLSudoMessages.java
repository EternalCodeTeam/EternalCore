package com.eternalcode.core.feature.sudo.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSudoMessages extends OkaeriConfig implements SudoMessages {
    @Comment({"# {PLAYER} - Gracz, który wykonał komendę, {TARGET} - Gracz lub konsola, na której wykonano komendę, {COMMAND} - Komenda"})
    Notice sudoMessageSpy = Notice.chat("<dark_gray>[<dark_red>Sudo<dark_gray>] <red>{PLAYER}<dark_gray> wykonał komendę: <white>{COMMAND} <dark_gray>na: <white>{TARGET}");

    @Comment({"# {TARGET} - Gracz lub konsola, na której wykonano komendę, {COMMAND} - Komenda"})
    Notice sudoMessage = Notice.chat("<color:#9d6eef>► <white>Wykonałeś komendę: <color:#9d6eef>{COMMAND} <white>na: <color:#9d6eef>{TARGET}");
}
