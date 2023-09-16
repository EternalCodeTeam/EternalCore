package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Route(name = "skull")
@Permission("eternalcore.skull")
class SkullCommand {

    private final NoticeService noticeService;
    private final SkullAPI skullAPI;

    @Inject
    SkullCommand(NoticeService noticeService, SkullAPI skullAPI) {
        this.skullAPI = skullAPI;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Gives you a skull of player", arguments = "<player>")
    void execute(Player player, @Arg @By(SkullNicknameArgument.KEY) String name) {
        this.skullAPI.acceptSyncSkull(name, skull -> {
            ItemStack namedSkull = ItemBuilder.from(skull)
                .name(Component.text(name))
                .build();

            ItemStack mainHand = player.getInventory().getItemInMainHand();

            if (mainHand.getType() == Material.PLAYER_HEAD) {
                mainHand.setItemMeta(namedSkull.getItemMeta());
            }
            else {
                player.getInventory().addItem(namedSkull);
            }

            this.noticeService.create()
                .notice(translation -> translation.item().skullMessage())
                .placeholder("{SKULL}", name)
                .player(player.getUniqueId())
                .send();
        });
    }
}
