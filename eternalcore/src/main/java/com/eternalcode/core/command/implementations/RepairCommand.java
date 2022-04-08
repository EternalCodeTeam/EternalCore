package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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

        if (handItem == null || handItem.getType().isBlock() || handItem.getType().isAir()) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

      //  handItem.editMeta(this::repairItem);

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "all")
    public void repairAll(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
      //      itemStack.editMeta(this::repairItem);
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "armor")
    public void repairArmor(Player player) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
          //  itemStack.editMeta(this::repairItem);
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().repairMessage())
            .player(player.getUniqueId())
            .send();
    }

    @IgnoreMethod
    private void repairItem(ItemMeta itemMeta) {
        Damageable damageable = (Damageable) itemMeta;
        damageable.setDamage(0);
    }
}
