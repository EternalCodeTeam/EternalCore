package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.annotations.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@Section(route = "repair", aliases = "napraw")
@Permission("eternalcore.command.repair")
@UsageMessage("&8» &cPoprawne użycie &7/repair [all/armor]")
public class RepairCommand {

    private final NoticeService noticeService;

    public RepairCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void repair(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null || !(handItem.getItemMeta() instanceof Repairable)) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        if (!(handItem.getItemMeta() instanceof Damageable damageable) || damageable.getDamage() == 0) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noDamaged())
                .player(player.getUniqueId())
                .send();

            return;
        }

        repairItem(handItem);

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "all")
    public void repairAll(Player player) {
        boolean exists = false;
        for (ItemStack itemStack : player.getInventory().getContents()) {

            if (itemStack == null || itemStack.getType().isAir()) {
                continue;
            }

            if (!(itemStack.getItemMeta() instanceof Damageable damageable) || damageable.getDamage() == 0) {
                continue;
            }

            exists = true;
            repairItem(itemStack);
        }

        if (!exists) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "armor")
    public void repairArmor(Player player) {
        boolean exists = false;
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {

            if (itemStack == null || itemStack.getType().isAir()) {
                continue;
            }

            if (!(itemStack.getItemMeta() instanceof Damageable damageable) || damageable.getDamage() == 0) {
                continue;
            }

            exists = true;
            repairItem(itemStack);
        }

        if (!exists) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @IgnoreMethod
    private void repairItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!(itemMeta instanceof Repairable) || !(itemMeta instanceof Damageable damageable) || damageable.getDamage() == 0) {
            return;
        }
        damageable.setDamage(0);
        itemStack.setItemMeta(itemMeta);
    }
}
