package com.eternalcode.core.command.implementation;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.chat.notification.NoticeService;

import com.eternalcode.core.scheduler.Scheduler;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "skull", aliases = "glowa")
@Permission("eternalcore.skull")
public class SkullCommand {

    private final NoticeService noticeService;
    private final Scheduler scheduler;

    public SkullCommand(NoticeService noticeService, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player, @Arg @By("player") String name) {
        this.scheduler.async(() -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).displayName(name).skullOwner(name).build();

            player.getInventory().addItem(item);

            this.noticeService
                .create()
                .message(messages -> messages.other().skullMessage())
                .placeholder("{NICK}", name)
                .player(player.getUniqueId())
                .send();
        });
    }
}
