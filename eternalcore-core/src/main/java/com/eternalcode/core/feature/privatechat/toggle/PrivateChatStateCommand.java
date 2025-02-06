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
        this.privateChatStateService.getPrivateChatState(sender.getUniqueId()).thenAccept(presentState -> {
            if (presentState == PrivateChatState.DISABLE) {
                this.enable(sender.getUniqueId());
            }
            else {
                this.disable(sender.getUniqueId());
            }
        });
    }


    @Execute
    @DescriptionDocs(description = "Switch receiving private messages", arguments = "<toggle>")
    public void execute(@Context Player sender, @Arg PrivateChatState state) {
        UUID uniqueId = sender.getUniqueId();

        if (state == PrivateChatState.DISABLE) {
            this.disable(uniqueId);
        }
        else {
            this.enable(uniqueId);
        }

    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>  <toggle>")
    public void other(@Context CommandSender sender, @Arg Player target, @Arg PrivateChatState state) {
        handleToggle(sender, target, state);
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>")
    public void other(@Context CommandSender sender, @Arg Player target) {
        this.privateChatStateService.getPrivateChatState(target.getUniqueId()).thenAccept(presentState -> {
            if (presentState == PrivateChatState.DISABLE) {
                this.handleToggle(sender, target, PrivateChatState.ENABLE);
            }
            else {
                this.handleToggle(sender, target, PrivateChatState.DISABLE);
            }
        });
    }

    private void handleToggle(CommandSender sender, Player target, @NotNull PrivateChatState desiredState) {
        UUID uniqueId = target.getUniqueId();

        if (desiredState == PrivateChatState.DISABLE) {
            this.disable(uniqueId);
            if (!this.isCommandSenderSameAsTarget(sender, target)) {
                this.noticeService.create()
                    .notice(translation -> translation.privateChat().otherMessagesDisabled())
                    .sender(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            }

            return;
        }

        this.enable(uniqueId);
        if (!this.isCommandSenderSameAsTarget(sender, target)) {
            this.noticeService.create()
                .notice(translation -> translation.privateChat().otherMessagesEnabled())
                .sender(sender)
                .placeholder("{PLAYER}", target.getName())
                .send();
        }

    }

    private void enable(UUID uniqueId) {
        this.privateChatStateService.togglePrivateChat(uniqueId, PrivateChatState.ENABLE);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().selfMessagesEnabled())
            .player(uniqueId)
            .send();
    }

    private void disable(UUID uniqueId) {
        this.privateChatStateService.togglePrivateChat(uniqueId, PrivateChatState.DISABLE);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().selfMessagesDisabled())
            .player(uniqueId)
            .send();
    }

    private boolean isCommandSenderSameAsTarget(CommandSender context, Player player) {
        if (context instanceof Player commandSender) {
            return commandSender.getUniqueId().equals(player.getUniqueId());
        }
        return false;
    }
}
