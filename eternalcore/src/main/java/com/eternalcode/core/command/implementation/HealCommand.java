package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Max;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
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
    @Max(1)
    void execute(CommandSender sender, Viewer viewer, @Arg @By("or_sender") Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        this.noticeService.player(player.getUniqueId(), messages -> messages.other().healMessage());

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .notice(messages -> messages.other().healMessageBy())
            .placeholder("{PLAYER}", player.getName())
            .viewer(viewer)
            .send();
    }

}
