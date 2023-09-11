package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@Route(name = "heal")
class HealCommand {

    private final NoticeService noticeService;

    @Inject
    HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.heal")
    @DescriptionDocs(description = "Heal yourself")
    void execute(Player player) {
        this.heal(player);

        this.noticeService.player(player.getUniqueId(), translation -> translation.player().healMessage());
    }

    @Execute
    @Permission("eternalcore.heal.other")
    @DescriptionDocs(description = "Heal other player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player target) {
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
