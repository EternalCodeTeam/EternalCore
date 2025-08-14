package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.vanish.messages.VanishMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.provider.NoticeProvider;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "vanish", aliases = {"v"})
@Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION)
class VanishCommand {

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
        boolean vanished = this.vanishService.toggleVanish(player);
        this.sendMessage(player, player, vanished ? VanishMessages::vanishEnabled : VanishMessages::vanishDisabled);
    }

    @Execute
    @Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION_OTHER)
    @DescriptionDocs(description = "Toggle vanish state for another player")
    void vanishOther(@Context Player player, @Arg Player target) {
        boolean vanished = this.vanishService.toggleVanish(target);
        this.sendMessage(player, target, vanished ? VanishMessages::vanishEnabledForOther : VanishMessages::vanishDisabledForOther);
        this.sendMessage(target, player, vanished ? VanishMessages::vanishEnabledByStaff : VanishMessages::vanishDisabledByStaff);
    }

    private void sendMessage(Player sender, Player target, NoticeProvider<VanishMessages> message) {
        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .notice(messages -> message.extract(messages.vanish()))
            .send();
    }

}

