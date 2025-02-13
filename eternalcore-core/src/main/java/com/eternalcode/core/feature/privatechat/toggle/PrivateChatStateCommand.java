package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
public class PrivateChatStateCommand {

    private final PrivateChatStateService privateChatStateService;
    private final NoticeService noticeService;

    @Inject
    public PrivateChatStateCommand(PrivateChatStateService privateChatStateService, NoticeService noticeService) {
        this.privateChatStateService = privateChatStateService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggle receiving private messages")
    public void execute(@Context Player sender) {
        UUID player = sender.getUniqueId();
        this.privateChatStateService.toggleChatState(player)
            .thenAccept(toggledState -> noticePlayer(toggledState, player));
    }

    @Execute
    @DescriptionDocs(description = "Switch receiving private messages", arguments = "<state>")
    public void execute(@Context Player sender, @Arg PrivateChatState state) {
        UUID player = sender.getUniqueId();
        this.privateChatStateService.setChatState(player, state)
            .thenAccept(ignored -> noticePlayer(state, player));
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>")
    public void other(@Context CommandSender sender, @Arg Player target) {
        UUID player = target.getUniqueId();
        this.privateChatStateService.toggleChatState(player)
            .thenAccept(toggledState -> noticeOtherPlayer(sender, toggledState, target));
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>  <toggle>")
    public void other(@Context CommandSender sender, @Arg Player target, @Arg PrivateChatState state) {
        UUID player = target.getUniqueId();
        this.privateChatStateService.setChatState(player, state)
            .thenAccept(ignored -> noticeOtherPlayer(sender, state, target));
    }

    private void noticeOtherPlayer(CommandSender sender, PrivateChatState state, Player target) {
        if (sender.equals(target)) {
            return;
        }

        this.noticeService.create()
            .sender(sender)
            .notice(translation -> state == PrivateChatState.DISABLE
                ? translation.privateChat().otherMessagesDisabled()
                : translation.privateChat().otherMessagesEnabled()
            )
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    private void noticePlayer(PrivateChatState state, UUID player) {
        this.noticeService.create()
            .player(player)
            .notice(translation -> state == PrivateChatState.ENABLE
                ? translation.privateChat().selfMessagesEnabled()
                : translation.privateChat().selfMessagesDisabled()
            )
            .send();
    }

}
