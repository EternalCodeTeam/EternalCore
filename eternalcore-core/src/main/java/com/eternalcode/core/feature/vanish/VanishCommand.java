package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.vanish.messages.VanishMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Function;

@Command(name = "vanish", aliases = {"v"})
@Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION)
class VanishCommand {

    private static final Map<VanishState, Function<VanishMessages, Notice>> MESSAGE_BY_STATE = Map.of(
        new VanishState(true, true), VanishMessages::vanishDisabledOther,
        new VanishState(true, false), VanishMessages::vanishDisabled,
        new VanishState(false, true), VanishMessages::vanishEnabledOther,
        new VanishState(false, false), VanishMessages::vanishEnabled
    );

    private final NoticeService noticeService;

    private final VanishService vanishService;
    @Inject
    public VanishCommand(NoticeService noticeService, VanishService vanishService) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
    }

    @Execute
    @DescriptionDocs(description = "Toggle your vanish state")
    void vanishSelf(@Context Player player) {
        this.toggleVanish(player, player, false);
    }

    @Execute
    @Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION_OTHER)
    @DescriptionDocs(description = "Toggle vanish state for another player")
    void vanishOther(@Context Player player, @Arg Player target) {
        this.toggleVanish(player, target, true);
    }

    private void toggleVanish(Player sender, Player target, boolean isOther) {
        boolean vanished = this.vanishService.isVanished(target);

        if (vanished) {
            this.vanishService.disableVanish(target);
        } else {
            this.vanishService.enableVanish(target);
        }

        Function<VanishMessages, Notice> noticeFunction = MESSAGE_BY_STATE.get(new VanishState(vanished, isOther));

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .notice(messages -> noticeFunction.apply(messages.vanish()))
            .send();
    }

    private record VanishState(boolean vanished, boolean isOther) {
    }
}

