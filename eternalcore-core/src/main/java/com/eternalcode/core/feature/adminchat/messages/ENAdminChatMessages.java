package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENAdminChatMessages extends OkaeriConfig implements AdminChatMessages {

    @Comment({"# {PLAYER} - Player who sent message on adminchat, {TEXT} - message"})
    Notice format = Notice.chat("<dark_gray>[<dark_red>AdminChat<dark_gray>] <red>{PLAYER}<dark_gray>: <white>{TEXT}");

    Notice enabled = Notice.chat("<green>► <white>Enabled persistent admin chat!");

    Notice disabled = Notice.chat("<green>► <white>Disabled persistent admin chat!");

    Notice enabledReminder = Notice.actionbar("<green>AdminChat channel is enabled!");
}
