package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@Command(name = "heal")
class HealCommand {

    private final NoticeService noticeService;

    @Inject
    HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.heal")
    @DescriptionDocs(description = "Heal yourself")
    void execute(@Context Player player) {
        this.heal(player);

        this.noticeService.player(player.getUniqueId(), translation -> translation.player().healMessage());
    }

    @Execute(required = 1)
    @Permission("eternalcore.heal.other")
    @DescriptionDocs(description = "Heal other player", arguments = "<player>")
    void execute(@Context Viewer viewer, @Arg Player target) {
        this.heal(target);

        this.noticeService.create()
            .notice(translation -> translation.player().healMessage())
            .player(target.getUniqueId())
            .send();


        this.noticeService.create()
            .notice(translation -> translation.player().healMessageBy())
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }

    void heal(Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

}
