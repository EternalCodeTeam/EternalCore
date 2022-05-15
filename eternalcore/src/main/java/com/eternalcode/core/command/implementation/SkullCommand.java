package com.eternalcode.core.command.implementation;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerNameArg;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "skull", aliases = "glowa")
@Permission("eternalcore.command.skull")
public class SkullCommand {

    private final NoticeService noticeService;
    private final EternalCore eternalCore;

    public SkullCommand(NoticeService noticeService, EternalCore eternalCore) {
        this.eternalCore = eternalCore;
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Player player, @Arg(0) @Handler(PlayerNameArg.class) String name) {
        this.eternalCore.getScheduler().runTaskAsynchronously(() -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).displayName(name).skullOwner(name).build();

            player.getInventory().addItem(item);

            this.noticeService
                .notice()
                .message(messages -> messages.other().skullMessage())
                .placeholder("{NICK}", name)
                .player(player.getUniqueId())
                .send();
        });
    }
}
