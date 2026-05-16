package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENMsgMessages extends OkaeriConfig implements MsgMessages {
    Notice noReply = Notice.chat("<red>✘ <dark_red>You have no one to reply!");

    @Comment("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
    Notice msgYouToTarget = Notice.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({ " ", "# {SENDER} - Message sender, {MESSAGE} - Message" })
    Notice msgTargetToYou = Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
    Notice socialSpyMessage = Notice.chat(
            "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    Notice receiverDisabledMessages = Notice.chat("<red>✘ <dark_red>This player has disabled private messages!");

    Notice selfMessagesDisabled = Notice
            .chat("<color:#9d6eef>► <white>Private messages have been <red>disabled<white>!");
    Notice selfMessagesEnabled = Notice
            .chat("<color:#9d6eef>► <white>Private messages have been <color:#9d6eef>enabled<white>!");

    @Comment("# {PLAYER} - Player")
    Notice otherMessagesDisabled = Notice.chat(
            "<color:#9d6eef>► <white>Private messages have been <red>disabled <white>for <color:#9d6eef>{PLAYER}<white>!");
    Notice otherMessagesEnabled = Notice.chat(
            "<color:#9d6eef>► <white>Private messages have been <color:#9d6eef>enabled <white>for <color:#9d6eef>{PLAYER}<white>!");

    @Comment(" ")
    Notice socialSpyEnable = Notice.chat("<color:#9d6eef>► <white>SocialSpy has been {STATE}<white>!");
    Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy has been {STATE}<white>!");

    @Comment("# Formatowanie placeholderów")
    public ENPlaceholders placeholders = new ENPlaceholders();

    @Getter
    @Accessors(fluent = true)
    public static class ENPlaceholders extends OkaeriConfig implements MsgMessages.Placeholders {
        private String loading = "<yellow>Loading...";

        private String msgEnabled = "<green>Enabled";
        private String msgDisabled = "<red>Disabled";
        private String socialSpyEnabled = "<green>Enabled";
        private String socialSpyDisabled = "<red>Disabled";
    }

    public ENPlaceholders placeholders() {
        return this.placeholders;
    }

}
