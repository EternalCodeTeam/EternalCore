package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
public class PrivateChatToggleCommand {

    private final PrivateChatToggleService privateChatToggleService;
    private final NoticeService noticeService;

    @Inject
    public PrivateChatToggleCommand(PrivateChatToggleService privateChatToggleService, NoticeService noticeService) {
        this.privateChatToggleService = privateChatToggleService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Switch receiving private messages", arguments = "<toggle>")
    public void execute(@Context Player sender, @OptionalArg PrivateChatToggleState state) {
        UUID uniqueId = sender.getUniqueId();

        if (state == null) {
            CompletableFuture<PrivateChatToggleState> completablePresentState = this.privateChatToggleService.getPrivateChatToggleState(sender.getUniqueId());

            completablePresentState.thenAccept(presentState -> {
                if (presentState == PrivateChatToggleState.DISABLE) {
                    this.enable(uniqueId);
                }
                else {
                    this.disable(uniqueId);
                }
            });

            return;
        }

        if (state == PrivateChatToggleState.DISABLE) {
            this.disable(uniqueId);
        }
        else {
            this.enable(uniqueId);
        }
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Switch receiving private messages for other player", arguments = "<player>  <toggle>")
    public void other(@Context CommandSender sender, @Arg("player") Player target, @OptionalArg PrivateChatToggleState state) {

        UUID uniqueId = target.getUniqueId();

        if (state == null) {
            CompletableFuture<PrivateChatToggleState> completablePresentState = this.privateChatToggleService.getPrivateChatToggleState(uniqueId);
            completablePresentState.thenAccept(presentState -> {
                if (presentState == PrivateChatToggleState.DISABLE) {
                    handleToggle(sender, target, PrivateChatToggleState.ENABLE);
                }
                else {
                    handleToggle(sender, target, PrivateChatToggleState.DISABLE);
                }
            });

            return;
        }

        handleToggle(sender, target, state);
    }

    private void handleToggle(CommandSender sender, Player target, @NotNull PrivateChatToggleState desiredState) {
        UUID uniqueId = target.getUniqueId();

        if (desiredState == PrivateChatToggleState.DISABLE) {
            this.disable(uniqueId);

            if (!this.isCommandSenderSameAsTarget(sender, target)) {
                this.noticeService.create()
                    .notice(translation -> translation.privateChat().otherMessagesDisabled())
                    .sender(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            }
        }
        else {
            this.enable(uniqueId);

            if (!this.isCommandSenderSameAsTarget(sender, target)) {
                this.noticeService.create()
                    .notice(translation -> translation.privateChat().otherMessagesEnabled())
                    .sender(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            }
        }
    }

    private void enable(UUID uniqueId) {
        this.privateChatToggleService.togglePrivateChat(uniqueId, PrivateChatToggleState.ENABLE);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().selfMessagesEnabled())
            .player(uniqueId)
            .send();
    }

    private void disable(UUID uniqueId) {
        this.privateChatToggleService.togglePrivateChat(uniqueId, PrivateChatToggleState.DISABLE);

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
