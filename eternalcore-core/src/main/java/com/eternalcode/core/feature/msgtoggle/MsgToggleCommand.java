package com.eternalcode.core.feature.msgtoggle;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
public class MsgToggleCommand {

    private final MsgToggleService msgToggleService;
    private final NoticeService noticeService;

    @Inject
    public MsgToggleCommand(MsgToggleService msgToggleService, NoticeService noticeService) {
        this.msgToggleService = msgToggleService;
        this.noticeService = noticeService;
    }

    public enum STATE {
        ON,
        OFF
    }

    @Execute
    @DescriptionDocs(description = "Toggle receiving private messages on and off")
    public void execute(@Context Player context) {
        CompletableFuture<Boolean> hasMsgToggledOff = this.msgToggleService.hasMsgToggled(context.getUniqueId());

        hasMsgToggledOff.thenAccept(toggledOff -> {
            if (toggledOff) {
                this.toggleOff(context);
            } else {
                this.toggleOn(context);
            }
        });
    }

    @Execute(name = "on")
    @DescriptionDocs(description = "Block incoming private messages")
    public void toggleOn(@Context Player context) {
        this.msgToggleService.toggleMsg(context.getUniqueId(), true);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().msgToggledSelf())
            .player(context.getUniqueId())
            .send();
    }

    @Execute(name = "off")
    @DescriptionDocs(description = "Unblock incoming private messages")
    public void toggleOff(@Context Player context) {
        this.msgToggleService.toggleMsg(context.getUniqueId(), false);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().msgUntoggleSelf())
            .player(context.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Toggle private messages for other player", arguments = "<player>")
    public void other(@Context Player context, @Arg("player") Player player) {
        CompletableFuture<Boolean> hasMsgToggledOff = this.msgToggleService.hasMsgToggled(player.getUniqueId());

        hasMsgToggledOff.thenAccept(toggledOff -> this.other(context, player, !toggledOff ? STATE.ON : STATE.OFF));
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Toggle private messages for other player", arguments = "<player> <toggle>")
    public void other(@Context Player context, @Arg("player") Player player, @Arg("<on/off>") STATE toggle) {
        this.msgToggleService.toggleMsg(player.getUniqueId(), toggle == STATE.ON);

        this.noticeService.create()
            .notice(
                translation -> toggle == STATE.ON ?
                    translation.privateChat().msgUntogglePlayer() :
                    translation.privateChat().msgToggledPlayer()
            )
            .player(context.getUniqueId())
            .placeholder("{PLAYER}", player.getName())
            .send();

    }

}
