package com.eternalcode.core.feature.msgtoggle;

import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
public class MsgToggleCommand {

    private final MsgToggleService msgToggleService;
    private final NoticeService noticeService;

    public MsgToggleCommand(MsgToggleService msgToggleService, NoticeService noticeService) {
        this.msgToggleService = msgToggleService;
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(@Context Player context) {
        boolean hasMsgToggledOff = this.msgToggleService.hasMsgToggledOff(context.getUniqueId());

        if (hasMsgToggledOff) {
            this.on(context);
        } else {
            this.off(context);
        }
    }

    @Execute(name = "on")
    public void on(@Context Player context) {
        this.msgToggleService.toggleMsg(context.getUniqueId(), true);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().msgToggleSelfOn())
            .player(context.getUniqueId())
            .send();
    }

    @Execute(name = "off")
    public void off(@Context Player context) {
        this.msgToggleService.toggleMsg(context.getUniqueId(), false);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().msgToggleSelfOff())
            .player(context.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    public void other(@Context Player context, @Arg("player") Player player) {
        boolean hasMsgToggledOff = this.msgToggleService.hasMsgToggledOff(player.getUniqueId());

        this.other(context, player, !hasMsgToggledOff);
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    public void other(@Context Player context, @Arg("player") Player player, @Arg boolean toggle) {
        this.msgToggleService.toggleMsg(player.getUniqueId(), toggle);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().msgToggleOther(toggle))
            .player(player.getUniqueId())
            .send();

    }
}
