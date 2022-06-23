package com.eternalcode.core.command.implementation.inventory;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "clear")
@Permission("eternalcore.clear")
public class ClearCommand {

    private final NoticeService noticeService;

    public ClearCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Viewer audience, CommandSender sender, @Arg @By("or_sender") Player player) {
        this.clear(player);

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .message(messages -> messages.other().clearByMessage())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();

        this.noticeService.create()
            .message(messages -> messages.other().clearMessage())
            .player(player.getUniqueId())
            .send();
    }

}
