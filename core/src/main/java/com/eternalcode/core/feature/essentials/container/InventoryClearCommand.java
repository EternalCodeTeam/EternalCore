package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Route(name = "clear")
@Permission("eternalcore.clear")
public class InventoryClearCommand {

    private final NoticeService noticeService;

    public InventoryClearCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Viewer audience, CommandSender sender, @Arg @By("or_sender") Player player) {
        this.clear(player);

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.inventory().inventoryClearMessageBy())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();

        this.noticeService.create()
            .notice(translation -> translation.inventory().inventoryClearMessage())
            .player(player.getUniqueId())
            .send();
    }

}
