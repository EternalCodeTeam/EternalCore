package com.eternalcode.core.feature.essentials;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Max;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "heal")
@Permission("eternalcore.heal")
public class HealCommand {

    private final NoticeService noticeService;

    public HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        this.noticeService.player(player.getUniqueId(), translation -> translation.player().healMessage());
    }

    @Execute
    void execute(Viewer viewer, @Arg Player target) {
        target.setFoodLevel(20);
        target.setHealth(20);
        target.setFireTicks(0);
        target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));

        this.noticeService.create()
            .notice(translation -> translation.player().healMessageBy())
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }

}
