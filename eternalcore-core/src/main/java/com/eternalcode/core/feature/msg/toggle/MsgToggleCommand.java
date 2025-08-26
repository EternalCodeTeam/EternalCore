package com.eternalcode.core.feature.msg.toggle;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
class MsgToggleCommand {

    private final MsgToggleService msgToggleService;
    private final NoticeService noticeService;

    @Inject
    MsgToggleCommand(MsgToggleService msgToggleService, NoticeService noticeService) {
        this.msgToggleService = msgToggleService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggle receiving private messages")
    void execute(@Sender Player sender) {
        UUID player = sender.getUniqueId();
        this.msgToggleService.toggleState(player)
            .thenAccept(toggledState -> noticePlayer(toggledState, player));
    }

    @Execute(name = "enable")
    @DescriptionDocs(description = "Enable receiving private messages")
    void executeEnable(@Sender Player sender, @Arg MsgState state) {
        UUID player = sender.getUniqueId();
        this.msgToggleService.setState(player, MsgState.ENABLED)
            .thenAccept(ignored -> noticePlayer(MsgState.ENABLED, player));
    }

    @Execute(name = "disable")
    @DescriptionDocs(description = "Disable receiving private messages")
    void executeDisable(@Sender Player sender) {
        UUID player = sender.getUniqueId();
        this.msgToggleService.setState(player, MsgState.DISABLED)
            .thenAccept(ignored -> noticePlayer(MsgState.DISABLED, player));
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>")
    void other(@Sender CommandSender sender, @Arg Player target) {
        UUID player = target.getUniqueId();
        this.msgToggleService.toggleState(player)
            .thenAccept(toggledState -> noticeOtherPlayer(sender, toggledState, target));
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>  <toggle>")
    void other(@Sender CommandSender sender, @Arg Player target, @Arg MsgState state) {
        UUID player = target.getUniqueId();
        this.msgToggleService.setState(player, state)
            .thenAccept(ignored -> noticeOtherPlayer(sender, state, target));
    }

    private void noticeOtherPlayer(CommandSender sender, MsgState state, Player target) {
        this.noticePlayer(state, target.getUniqueId());
        if (sender.equals(target)) {
            return;
        }

        this.noticeService.create()
            .sender(sender)
            .notice(translation -> state == MsgState.DISABLED
                ? translation.msg().otherMessagesDisabled()
                : translation.msg().otherMessagesEnabled()
            )
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    private void noticePlayer(MsgState state, UUID player) {
        this.noticeService.create()
            .player(player)
            .notice(translation -> state == MsgState.ENABLED
                ? translation.msg().selfMessagesEnabled()
                : translation.msg().selfMessagesDisabled()
            )
            .send();
    }

}
