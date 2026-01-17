package com.eternalcode.core.feature.repair;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@Command(name = "repair")
class RepairCommand {

    private final NoticeService noticeService;
    private final Delay<UUID> delay;
    private final RepairSettings repairSettings;

    @Inject
    RepairCommand(NoticeService noticeService, RepairSettings repairSettings) {
        this.noticeService = noticeService;
        this.repairSettings = repairSettings;
        this.delay = Delay.withDefault(() -> this.repairSettings.repairDelay());
    }

    @Execute
    @DescriptionDocs(description = "Repairs item in hand")
    @Permission("eternalcore.repair")
    void repair(@Sender Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRepairDelay(uuid)) {
            return;
        }

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
                .notice(translation -> translation.repair().cannotRepair())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.repairItem(handItem);

        this.noticeService
            .create()
            .notice(translation -> translation.repair().itemRepaired())
            .player(player.getUniqueId())
            .send();

        this.delay.markDelay(uuid);
    }

    @Execute(name = "all")
    @Permission("eternalcore.repair.all")
    @DescriptionDocs(description = "Repairs all items in inventory")
    void repairAll(@Sender Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRepairDelay(uuid)) {
            return;
        }

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
                .notice(translation -> translation.repair().needDamagedItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .notice(translation -> translation.repair().allItemsRepaired())
            .player(player.getUniqueId())
            .send();

        this.delay.markDelay(uuid);
    }

    @Execute(name = "armor")
    @Permission("eternalcore.repair.armor")
    @DescriptionDocs(description = "Repairs all items in armor")
    void repairArmor(@Sender Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRepairDelay(uuid)) {
            return;
        }

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
                .notice(translation -> translation.repair().needDamagedItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService
            .create()
            .notice(translation -> translation.repair().armorRepaired())
            .player(player.getUniqueId())
            .send();

        this.delay.markDelay(uuid);
    }

    private boolean hasRepairDelay(UUID uuid) {
        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getRemaining(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.repair().delay())
                .placeholder("{TIME}", DurationUtil.format(time, true))
                .player(uuid)
                .send();

            return true;
        }
        return false;
    }

    private void repairItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!(itemMeta instanceof Repairable) || !(itemMeta instanceof Damageable damageable)
            || damageable.getDamage() == 0) {
            return;
        }
        damageable.setDamage(0);
        itemStack.setItemMeta(itemMeta);
    }
}
