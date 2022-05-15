package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.MaxArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "heal")
@Permission("eternalcore.command.heal")
public class HealCommand {

    private final NoticeService noticeService;

    public HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MaxArgs(1)
    public void execute(CommandSender sender, Audience audience, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        this.noticeService
            .notice()
            .message(messages -> messages.other().healMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().healedMessage())
            .placeholder("{PLAYER}", player.getName())
            .audience(audience)
            .send();
    }

}
