package com.eternalcode.core.feature.essentials.item.give;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Service
public class GiveService {

    private final int defaultGiveAmount;

    private final PluginConfiguration pluginConfiguration;
    private final NoticeService noticeService;

    @Inject
    public GiveService(PluginConfiguration pluginConfiguration, NoticeService noticeService) {
        this.pluginConfiguration = pluginConfiguration;
        this.noticeService = noticeService;

        this.defaultGiveAmount = this.pluginConfiguration.items.defaultGiveAmount;
    }

    public void giveItem(Player player, Material material) {
        if (this.isInvalidMaterial(material, player)) {
            return;
        }

        this.giveItems(player, material, this.defaultGiveAmount, Optional.empty());
    }

    public void giveItem(Player player, Material material, int amount) {
        if (this.isInvalidMaterial(material, player)) {
            return;
        }

        this.giveItems(player, material, amount, Optional.empty());
    }

    public void giveItem(Player player, Material material, int amount, Enchantment enchantment, int level) {
        if (this.isInvalidMaterial(material, player)) {
            return;
        }

        this.giveItems(player, material, amount, Optional.of(new EnchantmentLevelPair(enchantment, level)));
    }

    private void giveItems(
        Player player,
        Material material,
        int amount,
        Optional<EnchantmentLevelPair> enchantmentLevel
    ) {
        int maxStackSize = material.getMaxStackSize();
        int fullStacks = amount / maxStackSize;
        int remainder = amount % maxStackSize;

        for (int i = 0; i < fullStacks; i++) {
            this.giveItemStack(player, material, maxStackSize, enchantmentLevel);
        }

        if (remainder > 0) {
            this.giveItemStack(player, material, remainder, enchantmentLevel);
        }
    }

    private boolean isInvalidMaterial(Material material, Player player) {
        if (material.isItem()) {
            this.noticeService.create()
                .notice(translation -> translation.item().giveNotItem())
                .player(player.getUniqueId())
                .send();
            return true;
        }
        return false;
    }

    private void giveItemStack(
        Player player,
        Material material,
        int amount,
        Optional<EnchantmentLevelPair> enchantmentLevel
    ) {
        ItemBuilder itemBuilder = ItemBuilder.from(material).amount(amount);

        enchantmentLevel.ifPresent(pair -> {
            if (pair.level > 0) {
                itemBuilder.enchant(pair.enchantment, pair.level);
            }
        });

        boolean dropOnFullInventory = this.pluginConfiguration.items.dropOnFullInventory;

        if (!hasSpace(player.getInventory(), itemBuilder.build())) {
            if (dropOnFullInventory) {
                player.getWorld().dropItemNaturally(player.getLocation(), itemBuilder.build());
            }

            this.noticeService.create()
                .notice(translation -> translation.item().giveNoSpace())
                .player(player.getUniqueId())
                .send();
            return;
        }

        player.getInventory().addItem(itemBuilder.build());
    }

    private static boolean hasSpace(Inventory inventory, ItemStack itemStack) {
        if (inventory.firstEmpty() != -1) {
            return true;
        }

        for (ItemStack contents : inventory.getContents()) {
            if (contents == null) {
                continue;
            }

            if (!contents.isSimilar(itemStack)) {
                continue;
            }

            if (contents.getMaxStackSize() > contents.getAmount()) {
                return true;
            }
        }

        return false;
    }

    private record EnchantmentLevelPair(Enchantment enchantment, int level) {
    }
}
