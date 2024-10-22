package com.eternalcode.core.feature.essentials.item.give;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Service
class GiveService {

    private final PluginConfiguration pluginConfiguration;
    private final NoticeService noticeService;

    @Inject
    public GiveService(PluginConfiguration pluginConfiguration, NoticeService noticeService) {
        this.pluginConfiguration = pluginConfiguration;
        this.noticeService = noticeService;
    }

    public boolean giveItem(CommandSender sender, Player player, Material material, int amount) {
        if (this.isInvalidMaterial(material)) {
            this.noticeService.create()
                .notice(translation -> translation.item().giveNotItem())
                .sender(sender)
                .send();

            return false;
        }

        GiveResult giveResult = this.processGive(player.getInventory().getStorageContents(), new ItemStack(material, amount));
        Optional<ItemStack> rest = giveResult.rest();

        if (rest.isPresent() && !this.pluginConfiguration.items.dropOnFullInventory) {
            this.noticeService.create()
                .notice(translation -> translation.item().giveNoSpace())
                .sender(sender)
                .send();
            return false;
        }

        player.getInventory().setStorageContents(giveResult.result());

        if (rest.isPresent()) {
            player.getWorld().dropItemNaturally(player.getLocation(), rest.get());
        }

        return true;
    }

    private boolean isInvalidMaterial(Material material) {
        return !material.isItem();
    }

    private GiveResult processGive(ItemStack[] contents, ItemStack itemToGive) {
        for (int i = 0; i < contents.length; i++) {
            if (itemToGive.getAmount() < 0) {
                throw new IllegalArgumentException("Item amount cannot be negative");
            }

            if (itemToGive.getAmount() == 0) {
                return new GiveResult(contents, Optional.empty());
            }

            ItemStack content = contents[i];

            if (content == null) {
                contents[i] = processContentSlot(itemToGive, 0, itemToGive.clone());
                continue;
            }

            if (!content.isSimilar(itemToGive)) {
                continue;
            }

            contents[i] = processContentSlot(itemToGive, content.getAmount(), content.clone());
        }

        if (itemToGive.getAmount() > 0) {
            return new GiveResult(contents, Optional.of(itemToGive));
        }

        return new GiveResult(contents, Optional.empty());
    }

    private static ItemStack processContentSlot(ItemStack itemToGive, int amount, ItemStack cloned) {
        int amountToConsume = Math.min(itemToGive.getAmount(), itemToGive.getMaxStackSize() - amount);

        cloned.setAmount(amount + amountToConsume);
        itemToGive.setAmount(itemToGive.getAmount() - amountToConsume);

        return cloned;
    }

    private record GiveResult(ItemStack[] result, Optional<ItemStack> rest) {}

}
