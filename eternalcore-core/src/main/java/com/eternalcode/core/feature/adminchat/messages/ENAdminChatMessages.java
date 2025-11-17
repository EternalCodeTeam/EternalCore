package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENAdminChatMessages extends OkaeriConfig implements AdminChatMessages {

    @Comment("# {PLAYER} - player name, {TEXT} - message content")
    Notice format = Notice.chat("<dark_gray>[<color:#9d6eef>Admin<dark_gray>] <gradient:#9d6eef:#A1AAFF>{PLAYER}</gradient><dark_gray>: <white>{TEXT}");

    Notice enabled = Notice.chat("<color:#9d6eef>▶ <white>Enabled persistent admin chat!");

    Notice disabled = Notice.chat("<color:#9d6eef>▶ <white>Disabled persistent admin chat!");

    Notice enabledReminder = Notice.actionbar("<color:#9d6eef>AdminChat channel is enabled!");
}
