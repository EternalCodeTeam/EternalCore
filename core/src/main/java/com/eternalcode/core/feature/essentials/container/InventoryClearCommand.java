package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
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
    void execute(Player player) {
        this.clear(player);
    }

    @Execute
    void execute(Viewer audience, @Arg Player target) {
        this.clear(target);

        this.noticeService.create()
            .notice(translation -> translation.inventory().inventoryClearMessage())
            .placeholder("{PLAYER}", target.getName())
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
