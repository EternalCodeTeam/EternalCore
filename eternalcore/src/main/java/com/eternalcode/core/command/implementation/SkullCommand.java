package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.liteskull.api.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "skull", aliases = "glowa")
@Permission("eternalcore.skull")
public class SkullCommand {

    private final NoticeService noticeService;
    private final SkullAPI skullAPI;

    public SkullCommand(NoticeService noticeService, SkullAPI skullAPI) {
        this.skullAPI = skullAPI;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player, @Arg @By("player") String name) {
        this.skullAPI.acceptSyncSkull(name, skull -> {
            ItemStack namedSkull = ItemBuilder.from(skull)
                .name(Component.text(name))
                .build();

            player.getInventory().addItem(namedSkull);

            this.noticeService.create()
                .message(messages -> messages.other().skullMessage())
                .placeholder("{NICK}", name)
                .player(player.getUniqueId())
                .send();
        });
    }
}
