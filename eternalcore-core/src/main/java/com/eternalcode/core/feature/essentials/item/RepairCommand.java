package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
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
    @DescriptionDocs(description = "Repairs item in hand")
    void repair(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null || !(handItem.getItemMeta() instanceof Repairable)) {
            this.noticeService
                .create()
                .notice(translation -> translation.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        if (!(handItem.getItemMeta() instanceof Damageable damageable) || damageable.getDamage() == 0) {
            this.noticeService
                .create()
                .notice(translation -> translation.argument().noDamaged())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.repairItem(handItem);

        this.noticeService
            .create()
            .notice(translation -> translation.item().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "all")
    @DescriptionDocs(description = "Repairs all items in inventory")
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
            this.repairItem(itemStack);
        }

        if (!exists) {
            this.noticeService
                .create()
                .notice(translation -> translation.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .notice(translation -> translation.item().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "armor")
    @DescriptionDocs(description = "Repairs all items in armor")
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
            this.repairItem(itemStack);
        }

        if (!exists) {
            this.noticeService
                .create()
                .notice(translation -> translation.argument().noDamagedItems())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .notice(translation -> translation.item().repairMessage())
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
