package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Section(route = "enchant", aliases = {"ench"})
@Permission("eternalcore.command.enchant")
@UsageMessage("&8» &cPoprawne użycie &7/enchant <enchantment> <level>")
public class EnchantCommand {

    private final PluginConfiguration configuration;
    private final NoticeService noticeService;

    public EnchantCommand(PluginConfiguration configuration, NoticeService noticeService) {
        this.configuration = configuration;
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(2)
    public void execute(Player player, @Arg(0) Enchantment enchantment, @Arg(1) Integer level) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService.notice()
                .player(player.getUniqueId())
                .message(messages -> messages.argument().noItem())
                .send();

            return;
        }

        if (this.configuration.otherSettings.unsafeEnchantments) {
            handItem.addUnsafeEnchantment(enchantment, level);
        } else {
            if (enchantment.getStartLevel() > level || enchantment.getMaxLevel() < level || !enchantment.canEnchantItem(handItem)) {
                this.noticeService.notice()
                    .player(player.getUniqueId())
                    .message(messages -> messages.argument().noValidEnchantmentLevel())
                    .send();

                return;
            }

            handItem.addEnchantment(enchantment, level);
        }

        this.noticeService.notice()
            .player(player.getUniqueId())
            .message(messages -> messages.other().enchantedMessage())
            .send();
    }
}
