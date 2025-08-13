package com.eternalcode.core.feature.clear;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "clear")
@Permission("eternalcore.clear")
class InventoryClearCommand {

    private final NoticeService noticeService;

    @Inject
    InventoryClearCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Clears your inventory")
    void execute(@Context Player player) {
        this.clear(player);
    }

    @Execute
    @DescriptionDocs(description = "Clears inventory of player", arguments = "<player>")
    @Permission("eternalcore.clear.other")
    void execute(@Context Viewer audience, @Arg Player target) {
        this.clear(target);

        this.noticeService.create()
            .notice(translation -> translation.clear().targetInvenoryCleared())
            .placeholder("{PLAYER}", target.getName())
            .viewer(audience)
            .send();
    }

    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();

        this.noticeService.create()
            .notice(translation -> translation.clear().inventoryCleared())
            .player(player.getUniqueId())
            .send();
    }

}
