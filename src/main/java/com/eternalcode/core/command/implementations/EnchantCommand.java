package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.*;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

@Section(route = "enchant")
@Permission("eternalcore.command.enchant")
@UsageMessage("&8» &cPoprawne użycie &7/enchant <enchant> <level>")
public final class EnchantCommand {

    private final MessagesConfiguration messages;

    public EnchantCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    public void execute(Player player, @Arg(0) String enchant, @Arg(1) String level) {

        Enchantment enchantment = Enchantment.getByName(enchant);

        if (enchantment == null) {
            player.sendMessage(ChatUtils.color(messages.otherMessages.enchantNotFound));
            return;
        }

        if (!NumberUtils.isNumber(level)) {
            player.sendMessage(ChatUtils.color(messages.otherMessages.enchantNotNumber));
            return;
        }

        int levelInt = Integer.parseInt(level);

        player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, levelInt);

        player.sendMessage(ChatUtils.color(messages.otherMessages.enchantSuccess));

    }
}
