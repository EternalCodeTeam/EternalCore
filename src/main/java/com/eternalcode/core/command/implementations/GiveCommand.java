package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.command.binds.PlayerArgument;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.builders.ItemBuilder;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "give", aliases = { "i", "item" })
@Permission("eternalcore.command.give")
@UsageMessage("&8» &cPoprawne użycie &7/give <material> [gracz]")
public class GiveCommand {

    private final MessagesConfiguration messages;
    private final EternalCore eternalCore;
    private final Server server;

    public GiveCommand(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
        this.messages = eternalCore.getConfigurationManager().getMessagesConfiguration();
        this.server = eternalCore.getServer();
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(CommandSender sender, @Arg(0) Material material, @Arg(1) @Handler(PlayerArgument.class) Option<Player> option) {
        if (option.isEmpty()) {
            Option.of(sender).is(Player.class).peek(playerSender -> {
                giveItem(playerSender, material);
                playerSender.sendMessage(ChatUtils.color(this.messages.otherMessages.giveRecived.replace("{ITEM}", material.name().replaceAll("_", " "))));
            }).orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, "&8» &cPoprawne użycie &7/give <material> [player]"));
            return;
        }
        Player player = option.get();

        giveItem(player, material);

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.giveRecived.replace("{ITEM}", material.name().replaceAll("_", " "))));
        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(this.messages.otherMessages.giveGiven, new String[]{ "{PLAYER}", "{ITEM}" }, new String[]{ player.getName(), material.name().replaceAll("_", " ")})));
    }

    @IgnoreMethod
    private void giveItem(Player player, Material material){
        this.server.getScheduler().runTaskAsynchronously(eternalCore, () -> {
            int amount = 64;

            if (material.isItem()){
                amount = 1;
            }

            ItemStack item = new ItemBuilder(material)
                .amount(amount)
                .build();

            player.getInventory().addItem(item);
        });
    }

}
