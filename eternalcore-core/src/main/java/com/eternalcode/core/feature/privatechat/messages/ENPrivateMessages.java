package com.eternalcode.core.feature.privatechat.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENPrivateMessages implements PrivateChatMessages {
    public Notice noReply = Notice.chat("<red>► <dark_red>You have no one to reply!");

    @Description("# {TARGET} - Player that you want to send messages, {MESSAGE} - Message")
    public Notice privateMessageYouToTarget =
        Notice.chat("<dark_gray>[<gray>You -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description({" ", "# {SENDER} - Message sender, {MESSAGE} - Message"})
    public Notice privateMessageTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>You<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description("# {SENDER} - Sender, {TARGET} - Target, {MESSAGE} - Message")
    public Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description(" ")
    public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy has been {STATE}<white>!");
    public Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy has been {STATE}<white>!");

    @Description({" ", "# {PLAYER} - Ignored player"})
    public Notice ignorePlayer = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

    @Description(" ")
    public Notice ignoreAll = Notice.chat("<red>► <dark_red>All players have been ignored!");
    public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

    @Description({" ", "# {PLAYER} - Ignored player."})
    public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>You already ignore this player!");

    @Description("# {PLAYER} - Unignored player")
    public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been uningored!");

    @Description(" ")
    public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>All players have been uningored!");
    public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

    @Description({" ", "# {PLAYER} - Ignored player"})
    public Notice notIgnorePlayer =
        Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
