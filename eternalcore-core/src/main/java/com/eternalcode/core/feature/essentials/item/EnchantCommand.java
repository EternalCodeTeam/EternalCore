package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

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
    @DescriptionDocs(description = "Enchants item in hand", arguments = "<enchantment> <level>")
    void execute(Player player, @Arg Enchantment enchantment, @Arg int level) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.argument().noItem())
                .send();

            return;
        }

        this.enchantItem(player.getUniqueId(), handItem, enchantment, level);
    }

    @Execute
    @Min(3)
    @DescriptionDocs(description = "Enchants item in hand", arguments = "<player> <enchantment> <level>")
    void execute(Player sender, @Arg Player target, @Arg Enchantment enchantment, @Arg int level) {
        PlayerInventory targetInventory = target.getInventory();
        ItemStack handItem = targetInventory.getItem(targetInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.argument().noItem())
                .send();

            return;
        }

        this.enchantItem(sender.getUniqueId(), handItem, enchantment, level);
    }

    private void enchantItem(UUID playerId, ItemStack item, Enchantment enchantment, int level) {
        if (this.configuration.items.unsafeEnchantments) {
            item.addUnsafeEnchantment(enchantment, level);
            this.noticeService.create()
                .player(playerId)
                .notice(translation -> translation.item().enchantedMessage())
                .send();
            return;
        }

        if (enchantment.getStartLevel() > level || enchantment.getMaxLevel() < level || !enchantment.canEnchantItem(item)) {
            this.noticeService.create()
                .player(playerId)
                .notice(translation -> translation.argument().noValidEnchantmentLevel())
                .send();
            return;
        }

        item.addEnchantment(enchantment, level);
        this.noticeService.create()
            .player(playerId)
            .notice(translation -> translation.item().enchantedMessage())
            .send();
    }
}
