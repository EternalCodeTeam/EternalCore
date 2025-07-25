package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "vanish", aliases = {"v"})
@Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION)
public class VanishCommand {

    private final NoticeService noticeService;
    private final VanishService vanishService;

    @Inject
    public VanishCommand(NoticeService noticeService, VanishService vanishService) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
    }

    @Execute
    @DescriptionDocs(description = "Toggles vanish on your self.")
    void vanishSelf(@Context Player player) {
        if (this.vanishService.isVanished(player)) {
            this.vanishService.disableVanish(player);
            this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().vanishDisabled());
            return;
        }

        this.vanishService.enableVanish(player);
        this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().vanishEnabled());
    }

    @Execute
    @Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION_OTHER)
    @DescriptionDocs(description = "Toggles vanish on other player.")
    void vanishOther(@Context Player player, @Arg Player target) {
        Formatter formatter = new Formatter()
            .register("{PLAYER}", target.getName());

        if (this.vanishService.isVanished(target)) {
            this.vanishService.disableVanish(target);

            this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().vanishDisabledOther(), formatter);
            return;
        }

        this.vanishService.enableVanish(target);

        this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().vanishEnabledOther(), formatter);
    }

}
