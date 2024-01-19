package com.eternalcode.core.feature.essentials.item.enchant;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

@Command(name = "enchant")
@Permission("eternalcore.enchant")
class EnchantCommand {

    private final PluginConfiguration configuration;
    private final NoticeService noticeService;

    @Inject
    EnchantCommand(PluginConfiguration configuration, NoticeService noticeService) {
        this.configuration = configuration;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Enchants item in hand", arguments = "<enchantment> <level>")
    void execute(@Context Player player, @Arg Enchantment enchantment, @Arg(EnchantArgument.KEY) int level) {
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

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.item().enchantedMessage())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Enchants item in hand", arguments = "<enchantment> <level> <player>")
    void execute(@Context Player sender, @Arg Enchantment enchantment, @Arg(EnchantArgument.KEY) int level, @Arg Player target) {
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

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.item().enchantedMessageFor())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService.create()
            .player(target.getUniqueId())
            .notice(translation -> translation.item().enchantedMessageBy())
            .placeholder("{PLAYER}", sender.getName())
            .send();
    }

    private void enchantItem(UUID playerId, ItemStack item, Enchantment enchantment, int level) {
        if (this.configuration.items.unsafeEnchantments) {
            item.addUnsafeEnchantment(enchantment, level);
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
