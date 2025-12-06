package com.eternalcode.core.feature.give;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Service
public class GiveService {

    private final GiveSettings giveSettings;
    private final NoticeService noticeService;

    @Inject
    public GiveService(GiveSettings giveSettings, NoticeService noticeService) {
        this.giveSettings = giveSettings;
        this.noticeService = noticeService;
    }

    public boolean giveItem(CommandSender sender, Player player, Material material, int amount) {
        if (this.isInvalidMaterial(material)) {
            this.noticeService.create()
                .notice(translation -> translation.give().itemNotFound())
                .sender(sender)
                .send();

            return false;
        }

        if (amount <= 0) {
            this.noticeService.create()
                .placeholder("{AMOUNT}", String.valueOf(amount))
                .notice(translation -> translation.argument().numberBiggerThanZero())
                .sender(sender)
                .send();
            return false;
        }

        PlayerInventory inventory = player.getInventory();
        GiveResult giveResult = this.processGive(new PlayerContents(inventory.getStorageContents(), inventory.getItemInOffHand()), new ItemStack(material, amount));
        Optional<ItemStack> rest = giveResult.rest();

        if (rest.isPresent() && !this.giveSettings.dropOnFullInventory()) {
            this.noticeService.create()
                .notice(translation -> translation.give().noSpace())
                .sender(sender)
                .send();
            return false;
        }

        inventory.setStorageContents(giveResult.contents().storage);
        inventory.setItemInOffHand(giveResult.contents().extraSlot);

        rest.ifPresent(itemStack -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
        return true;
    }

    private boolean isInvalidMaterial(Material material) {
        return !material.isItem();
    }

    private GiveResult processGive(PlayerContents contents, ItemStack itemToGive) {
        for (int i = 0; i < contents.size(); i++) {
            if (itemToGive.getAmount() < 0) {
                throw new IllegalArgumentException("Item amount cannot be negative");
            }

            if (itemToGive.getAmount() == 0) {
                return new GiveResult(contents, Optional.empty());
            }

            ItemStack content = contents.get(i);

            if (content == null || content.getType().isAir()) {
                contents.set(i, processContentSlot(itemToGive, 0, itemToGive.clone()));
                continue;
            }

            if (!content.isSimilar(itemToGive)) {
                continue;
            }

            contents.set(i, processContentSlot(itemToGive, content.getAmount(), content.clone()));
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

    private record GiveResult(PlayerContents contents, Optional<ItemStack> rest) {}

    private static class PlayerContents {
        private final ItemStack[] storage;
        private ItemStack extraSlot;

        private PlayerContents(ItemStack[] storage, ItemStack extraSlot) {
            this.storage = storage;
            this.extraSlot = extraSlot;
        }

        int size() {
            return this.storage.length + 1;
        }

        ItemStack get(int index) {
            if (index == this.size() - 1) {
                return this.extraSlot;
            }

            return this.storage[index];
        }

        void set(int index, ItemStack item) {
            if (index == this.size() - 1) {
                this.extraSlot = item;
                return;
            }

            this.storage[index] = item;
        }
    }

}
