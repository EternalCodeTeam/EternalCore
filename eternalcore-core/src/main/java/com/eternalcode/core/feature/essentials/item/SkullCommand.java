package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "skull")
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
    void execute(@Context Player sender, @Arg(SkullNicknameArgument.KEY) String name) {
        this.skullAPI.acceptSyncSkullData(name, skull -> {
            ItemStack namedSkull = ItemBuilder.skull()
                .name(Component.text(name))
                .texture(skull.getValue())
                .build();

            ItemStack mainHand = sender.getInventory().getItemInMainHand();

            if (mainHand.getType() == Material.PLAYER_HEAD) {
                mainHand.setItemMeta(namedSkull.getItemMeta());
            }
            else {
                sender.getInventory().addItem(namedSkull);
            }

            this.noticeService.create()
                .notice(translation -> translation.item().skullMessage())
                .placeholder("{SKULL}", name)
                .player(sender.getUniqueId())
                .send();
        });
    }
}
