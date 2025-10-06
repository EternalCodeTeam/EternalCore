package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENMsgMessages extends OkaeriConfig implements MsgMessages {
    Notice noReply = Notice.chat("<red>► <dark_red>You have no one to reply!");

    @Comment("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
    Notice msgYouToTarget =
        Notice.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({" ", "# {SENDER} - Message sender, {MESSAGE} - Message"})
    Notice msgTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
    Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    Notice receiverDisabledMessages = Notice.chat("<red>► <dark_red>This player has disabled private messages!");

    Notice selfMessagesDisabled = Notice.chat("<green>► <white>Private messages have been <red>disabled<white>!");
    Notice selfMessagesEnabled = Notice.chat("<green>► <white>Private messages have been <green>enabled<white>!");

    @Comment("# {PLAYER} - Player")
    Notice otherMessagesDisabled = Notice.chat("<green>► <white>Private messages have been <red>disabled <white>for <green>{PLAYER}<white>!");
    Notice otherMessagesEnabled = Notice.chat("<green>► <white>Private messages have been <green>enabled <white>for <green>{PLAYER}<white>!");

    @Comment(" ")
    Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy has been {STATE}<white>!");
    Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy has been {STATE}<white>!");

    @Comment({" ", "# {PLAYER} - Ignored player"})
    Notice ignorePlayer = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

    @Comment(" ")
    Notice ignoreAll = Notice.chat("<red>► <dark_red>All players have been ignored!");
    Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

    @Comment({" ", "# {PLAYER} - Ignored player."})
    Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>You already ignore this player!");

    @Comment("# {PLAYER} - Unignored player")
    Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been unignored!");

    @Comment(" ")
    Notice unIgnoreAll = Notice.chat("<red>► <dark_red>All players have been unignored!");
    Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

    @Comment({" ", "# {PLAYER} - Ignored player"})
    Notice notIgnorePlayer =
        Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
