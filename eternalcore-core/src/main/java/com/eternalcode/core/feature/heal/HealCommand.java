package com.eternalcode.core.feature.heal;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.PotionEffectUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
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
    void healSelf(@Sender Player player) {
        this.heal(player);

        this.noticeService.player(player.getUniqueId(), translation -> translation.heal().healedYourself());
    }

    @Execute
    @Permission("eternalcore.heal.other")
    @DescriptionDocs(description = "Heal other player", arguments = "<player>")
    void healOther(@Sender Viewer viewer, @Arg Player target) {
        this.heal(target);

        this.noticeService.create()
            .notice(translation -> translation.heal().healedYourself())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.heal().healedTargetPlayer())
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }

    private void heal(Player player) {
        /*
         * Sets the player's health to their maximum health.
         * We avoid using Attribute.GENERIC_MAX_HEALTH or Attribute.MAX_HEALTH directly
         * because the Attribute API has changed in recent Paper versions,
         * To maintain compatibility with older versions, we use player.getMaxHealth(),
         * which provides a stable way to get the player's max health.
         */
        double maxHealth = player.getMaxHealth();

        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setRemainingAir(player.getMaximumAir());

        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (!PotionEffectUtil.isNegativeEffect(effect.getType())) {
                continue;
            }

            player.removePotionEffect(effect.getType());
        }
    }
}
