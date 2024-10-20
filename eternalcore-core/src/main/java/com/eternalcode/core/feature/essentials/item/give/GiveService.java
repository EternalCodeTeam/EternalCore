package com.eternalcode.core.feature.essentials.item.give;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.InventoryUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

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
        this.giveItems(player, material, this.defaultGiveAmount, Optional.empty());
    }

    public void giveItem(Player player, Material material, int amount) {
        this.giveItems(player, material, amount, Optional.empty());
    }

    public void giveItem(Player player, Material material, int amount, Enchantment enchantment, int level) {
        this.giveItems(player, material, amount, Optional.of(new EnchantmentLevelPair(enchantment, level)));
    }

    private void giveItems(
            Player player,
            Material material,
            int amount,
            Optional<EnchantmentLevelPair> enchantmentLevel
    ) {
        if (this.isInvalidMaterial(material)) {
            this.noticeService.create()
                    .notice(translation -> translation.item().giveNotItem())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        this.giveItemStack(player, material, amount, enchantmentLevel);
    }

    private boolean isInvalidMaterial(Material material) {
        return !material.isItem();
    }

    private void giveItemStack(
            Player player,
            Material material,
            int amount,
            Optional<EnchantmentLevelPair> enchantmentLevel
    ) {
        int maxStackSize = material.getMaxStackSize();
        int fullStacks = amount / maxStackSize;
        int rest = amount % maxStackSize;

        for (int i = 0; i < fullStacks; i++) {
            this.giveSingleStack(player, material, maxStackSize, enchantmentLevel);
        }

        if (rest > 0) {
            this.giveSingleStack(player, material, rest, enchantmentLevel);
        }
    }

    private void giveSingleStack(
            Player player,
            Material material,
            int amount,
            Optional<EnchantmentLevelPair> enchantmentLevel
    ) {
        ItemBuilder itemBuilder = ItemBuilder.from(material).amount(amount);

        enchantmentLevel.ifPresent(pair -> {
            if (pair.level() > 0) {
                itemBuilder.enchant(pair.enchantment, pair.level());
            }
        });

        boolean dropOnFullInventory = this.pluginConfiguration.items.dropOnFullInventory;

        if (!InventoryUtil.hasSpace(player.getInventory(), itemBuilder.build())) {
            if (dropOnFullInventory) {
                player.getWorld().dropItemNaturally(player.getLocation(), itemBuilder.build());
                return;
            }

            this.noticeService.create()
                    .notice(translation -> translation.item().giveNoSpace())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        player.getInventory().addItem(itemBuilder.build());
    }

    private record EnchantmentLevelPair(Enchantment enchantment, int level) {
    }
}
