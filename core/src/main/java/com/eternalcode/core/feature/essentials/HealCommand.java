package com.eternalcode.core.feature.essentials;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@Route(name = "heal")
@Permission("eternalcore.heal")
public class HealCommand {

    private final NoticeService noticeService;

    public HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        this.heal(player);

        this.noticeService.player(player.getUniqueId(), translation -> translation.player().healMessage());
    }

    @Execute
    void execute(Viewer viewer, @Arg Player target) {
        this.heal(target);

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
