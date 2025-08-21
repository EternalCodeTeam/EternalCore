package com.eternalcode.core.feature.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.give.GiveArgument;
import com.eternalcode.core.feature.give.GiveService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Command(name = "item", aliases = {"i"})
@Permission("eternalcore.item")
public class ItemCommand {

    private final NoticeService noticeService;
    private final GiveService giveService;
    private final PluginConfiguration config;

    @Inject
    public ItemCommand(NoticeService noticeService, GiveService giveService, PluginConfiguration config) {
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
    @DescriptionDocs(description = "Gives an item with a custom amount", arguments = "<item> [amount]")
    void execute(@Context Player player, @Arg Material material, @OptionalArg(GiveArgument.KEY) int amount) {
        boolean isSuccess = this.giveService.giveItem(player, player, material, amount);

        if (isSuccess) {
            this.noticeService.create()
                .placeholder("{ITEM}", MaterialUtil.format(material))
                .notice(translation -> translation.item().giveReceived())
                .player(player.getUniqueId())
                .send();
        }
    }
}
