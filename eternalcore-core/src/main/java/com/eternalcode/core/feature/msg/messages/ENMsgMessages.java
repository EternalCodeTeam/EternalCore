package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENMsgMessages extends OkaeriConfig implements MsgMessages {
    public Notice noReply = Notice.chat("<red>► <dark_red>You have no one to reply!");

    @Comment("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
    public Notice msgYouToTarget =
        Notice.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({" ", "# {SENDER} - Message sender, {MESSAGE} - Message"})
    public Notice msgTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
    public Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    public Notice receiverDisabledMessages = Notice.chat("<red>► <dark_red>This player has disabled private messages!");

    public Notice selfMessagesDisabled = Notice.chat("<green>► <white>Private messages have been <red>disabled<white>!");
    public Notice selfMessagesEnabled = Notice.chat("<green>► <white>Private messages have been <green>enabled<white>!");

    @Comment("# {PLAYER} - Player")
    public Notice otherMessagesDisabled = Notice.chat("<green>► <white>Private messages have been <red>disabled <white>for <green>{PLAYER}<white>!");
    public Notice otherMessagesEnabled = Notice.chat("<green>► <white>Private messages have been <green>enabled <white>for <green>{PLAYER}<white>!");

    @Comment(" ")
    public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy has been {STATE}<white>!");
    public Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy has been {STATE}<white>!");

    @Comment({" ", "# {PLAYER} - Ignored player"})
    public Notice ignorePlayer = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

    @Comment(" ")
    public Notice ignoreAll = Notice.chat("<red>► <dark_red>All players have been ignored!");
    public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

    @Comment({" ", "# {PLAYER} - Ignored player."})
    public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>You already ignore this player!");

    @Comment("# {PLAYER} - Unignored player")
    public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been unignored!");

    @Comment(" ")
    public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>All players have been unignored!");
    public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

    @Comment({" ", "# {PLAYER} - Ignored player"})
    public Notice notIgnorePlayer =
        Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
