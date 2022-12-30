package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Route(name = "enchant")
@Permission("eternalcore.enchant")
public class EnchantCommand {

    private final PluginConfiguration configuration;
    private final NoticeService noticeService;

    public EnchantCommand(PluginConfiguration configuration, NoticeService noticeService) {
        this.configuration = configuration;
        this.noticeService = noticeService;
    }

    @Execute
    @Min(2)
    void execute(Player player, @Arg Enchantment enchantment, @Arg Integer level) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(messages -> messages.argument().noItem())
                .send();

            return;
        }

        if (this.configuration.otherSettings.unsafeEnchantments) {
            handItem.addUnsafeEnchantment(enchantment, level);
        } else {
            if (enchantment.getStartLevel() > level || enchantment.getMaxLevel() < level || !enchantment.canEnchantItem(handItem)) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(messages -> messages.argument().noValidEnchantmentLevel())
                    .send();

                return;
            }

            handItem.addEnchantment(enchantment, level);
        }

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(messages -> messages.other().enchantedMessage())
            .send();
    }
}
