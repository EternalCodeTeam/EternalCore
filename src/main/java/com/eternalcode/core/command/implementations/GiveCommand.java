package com.eternalcode.core.command.implementations;

import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "give", aliases = { "i", "item" })
@Permission("eternalcore.command.give")
@UsageMessage("&8» &cPoprawne użycie &7/give <material> [gracz]")
public class GiveCommand {

    private final AudiencesService audiencesService;

    public GiveCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(CommandSender sender, @Arg(0) Material material, @Arg(1) @Handler(PlayerArgument.class) Option<Player> option) {
        if (option.isEmpty()) {
            if (sender instanceof Player player) {
                giveItem(player, material);

                this.audiencesService
                    .notice()
                    .placeholder("{ITEM}", material.name().replaceAll("_", " "))
                    .message(messages -> messages.other().giveReceived())
                    .player(player.getUniqueId())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = option.get();

        giveItem(player, material);

        this.audiencesService
            .notice()
            .placeholder("{ITEM}", material.name().replaceAll("_", " "))
            .message(messages -> messages.other().giveReceived())
            .player(player.getUniqueId())
            .send();

        this.audiencesService
            .notice()
            .placeholder("{ITEM}", material.name().replaceAll("_", " "))
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().giveGiven())
            .sender(sender)
            .send();
    }

    @IgnoreMethod
    private void giveItem(Player player, Material material) {
        int amount = 64;

        if (material.isItem()) {
            amount = 1;
        }

        ItemStack item = new ItemBuilder(material)
            .amount(amount)
            .build();

        player.getInventory().addItem(item);
    }

}
