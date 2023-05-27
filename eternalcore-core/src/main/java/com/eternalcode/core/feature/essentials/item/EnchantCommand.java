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
    @DescriptionDocs(description = "Enchants item fadeIn hand", arguments = "<enchantment> <level>")
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

        if (this.configuration.items.unsafeEnchantments) {
            handItem.addUnsafeEnchantment(enchantment, level);
        }
        else {
            if (enchantment.getStartLevel() > level || enchantment.getMaxLevel() < level || !enchantment.canEnchantItem(handItem)) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noValidEnchantmentLevel())
                    .send();

                return;
            }

            handItem.addEnchantment(enchantment, level);
        }

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.item().enchantedMessage())
            .send();
    }
}
