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
    @DescriptionDocs(description = "Toggle receiving private messages on and off")
    public void execute(@Context Player context, @OptionalArg PrivateChatToggleState state) {
        UUID uniqueId = context.getUniqueId();

        if (state == null) {
            CompletableFuture<PrivateChatToggleState> privateChatToggleState = this.privateChatToggleService.getPrivateChatToggleState(context.getUniqueId());

            privateChatToggleState.thenAccept(toggleState -> {
                if (toggleState.equals(PrivateChatToggleState.OFF)) {
                    this.toggleOn(uniqueId);
                }
                else {
                    this.toggleOff(uniqueId);
                }
            });

            return;
        }

        if (state.equals(PrivateChatToggleState.OFF)) {
            this.toggleOn(uniqueId);
        }
        else {
            this.toggleOff(uniqueId);
        }
    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    @DescriptionDocs(description = "Toggle private messages for other player", arguments = "<player>  <toggle>")
    public void other(@Context CommandSender context, @Arg("player") Player player, @OptionalArg PrivateChatToggleState state) {

        UUID uniqueId = player.getUniqueId();

        if (state == null) {
            CompletableFuture<PrivateChatToggleState> privateChatToggleState = this.privateChatToggleService.getPrivateChatToggleState(uniqueId);
            privateChatToggleState.thenAccept(toggleState -> handleToggle(context, player, toggleState, uniqueId));

            return;
        }

        handleToggle(context, player, state, uniqueId);
    }

    private void handleToggle(CommandSender context, Player player, @NotNull PrivateChatToggleState state, UUID uniqueId) {
        if (state.equals(PrivateChatToggleState.OFF)) {
            this.toggleOn(uniqueId);

            if (this.isCommandSenderSameAsTarget(context, player)) {
                this.noticeService.create()
                    .notice(translation -> translation.privateChat().otherMessagesDisabled())
                    .sender(context)
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }
        else {
            this.toggleOff(uniqueId);

            if (this.isCommandSenderSameAsTarget(context, player)) {
                this.noticeService.create()
                    .notice(translation -> translation.privateChat().otherMessagesEnabled())
                    .sender(context)
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }
    }

    private void toggleOn(UUID uniqueId) {
        this.privateChatToggleService.togglePrivateChat(uniqueId, PrivateChatToggleState.ON);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().selfMessagesDisabled())
            .player(uniqueId)
            .send();
    }

    private void toggleOff(UUID uniqueId) {
        this.privateChatToggleService.togglePrivateChat(uniqueId, PrivateChatToggleState.OFF);

        this.noticeService.create()
            .notice(translation -> translation.privateChat().selfMessagesEnabled())
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
