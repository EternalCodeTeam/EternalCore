package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@Route(name = "repair")
@Permission("eternalcore.repair")
public class RepairCommand {

    private final NoticeService noticeService;

    public RepairCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void repair(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null || !(handItem.getItemMeta() instanceof Repairable)) {
            this.noticeService
                .create()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        if (!(handItem.getItemMeta() instanceof Damageable damageable) || damageable.getDamage() == 0) {
            this.noticeService
                .create()
                .message(messages -> messages.argument().noDamaged())
                .player(player.getUniqueId())
                .send();

            return;
        }

        repairItem(handItem);

        this.noticeService
            .create()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "all")
    void repairAll(Player player) {
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
                .create()
                .message(messages -> messages.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "armor")
    void repairArmor(Player player) {
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
                .create()
                .message(messages -> messages.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }


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
