package com.eternalcode.core.feature.give;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.litecommand.argument.StackAmountArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "give")
@Permission("eternalcore.give")
class GiveCommand {

    private final NoticeService noticeService;
    private final GiveService giveService;
    private final GiveSettings giveSettings;

    @Inject
    GiveCommand(NoticeService noticeService, GiveService giveService, GiveSettings giveSettings) {
        this.noticeService = noticeService;
        this.giveService = giveService;
        this.giveSettings = giveSettings;
    }

    @Execute
    @DescriptionDocs(description = "Gives you an item", arguments = "<item>")
    void execute(@Sender Player player, @Arg Material material) {
        this.execute(player, material, this.giveSettings.defaultGiveAmount());
    }

    @Execute
    @DescriptionDocs(description = "Gives an item to another player", arguments = "<player> <item>")
    void execute(@Sender CommandSender sender, @OptionalArg Player target, @Arg Material material) {
        this.execute(sender, target, material, this.giveSettings.defaultGiveAmount());
    }

    @Execute
    @DescriptionDocs(description = "Gives you an item with a custom amount", arguments = "<item> [amount]")
    void execute(@Sender Player player, @Arg Material material, @OptionalArg(StackAmountArgument.KEY) int amount) {
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
    @DescriptionDocs(description = "Gives an item with a custom amount to another player", arguments = "<player> <item> <amount>")
    void execute(@Sender CommandSender sender, @OptionalArg Player target, @Arg Material material, @OptionalArg(StackAmountArgument.KEY) int amount) {
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
