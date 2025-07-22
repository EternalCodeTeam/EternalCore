package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENAdminChatMessages implements AdminChatMessages {
    @Description({"# {PLAYER} - Player who sent message on adminchat, {TEXT} - message"})
    public Notice format =
        Notice.chat("<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");

    public Notice enabledPersistentChat =
        Notice.chat("<green>► <white>Enabled persistent admin chat!");

    public Notice disabledPersistentChat =
        Notice.chat("<green>► <white>Disabled persistent admin chat!");

    public Notice actionbarPersistentChatNotify =
        Notice.actionbar("<white>[AdminChat] <gray>> <green>enabled");


}
