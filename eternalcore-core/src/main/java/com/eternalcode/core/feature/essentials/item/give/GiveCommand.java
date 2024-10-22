package com.eternalcode.core.feature.essentials.item.give;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "give", aliases = { "i", "item" })
@Permission("eternalcore.give")
class GiveCommand {

    private final NoticeService noticeService;
    private final GiveService giveService;
    private final PluginConfiguration config;

    @Inject
    GiveCommand(NoticeService noticeService, GiveService giveService, PluginConfiguration config) {
        this.noticeService = noticeService;
        this.giveService = giveService;
        this.config = config;
    }

    @Execute
    @DescriptionDocs(description = "Gives you an item", arguments = "<item>")
    void execute(@Context Player player, @Arg Material material) {
        this.execute(player, material, this.config.items.defaultGiveAmount);
    }

    @Execute
    @DescriptionDocs(description = "Gives an item to another player", arguments = "<item> <player>")
    void execute(@Context CommandSender sender, @Arg Material material, @Arg Player target) {
        this.execute(sender, material, this.config.items.defaultGiveAmount, target);
    }

    @Execute
    @DescriptionDocs(description = "Gives you an item with a custom amount", arguments = "<item> <amount>")
    void execute(@Context Player player, @Arg Material material, @Arg(GiveArgument.KEY) int amount) {
        boolean isSuccess = this.giveService.giveItem(player, player, material, amount);

        if (isSuccess) {
            this.noticeService.create()
                .placeholder("{ITEM}", MaterialUtil.format(material))
                .notice(translation -> translation.item().giveReceived())
                .player(player.getUniqueId())
                .send();
        }
    }

    @Execute
    @DescriptionDocs(description = "Gives an item with a custom amount to another player", arguments = "<item> <amount> <player>")
    void execute(@Context CommandSender sender, @Arg Material material, @Arg(GiveArgument.KEY) int amount, @Arg Player target) {
        boolean isSuccess = this.giveService.giveItem(sender, target, material, amount);

        if (!isSuccess) {
            return;
        }

        String formattedMaterial = MaterialUtil.format(material);
        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", target.getName())
            .notice(translation -> translation.item().giveGiven())
            .sender(sender)
            .send();
    }

}
