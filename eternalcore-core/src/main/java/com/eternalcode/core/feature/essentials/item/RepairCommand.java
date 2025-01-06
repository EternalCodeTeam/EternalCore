package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;
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
    private final PluginConfiguration config;

    @Inject
    RepairCommand(NoticeService noticeService, PluginConfiguration config) {
        this.noticeService = noticeService;
        this.config = config;
        this.delay = new Delay<>(() -> this.config.repair.repairDelay());
    }

    @Execute
    @DescriptionDocs(description = "Repairs item in hand")
    @Permission("eternalcore.repair")
    void repair(@Context Player player) {
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

        this.delay.markDelay(uuid, this.config.repair.repairDelay());
    }

    @Execute(name = "all")
    @Permission("eternalcore.repair.all")
    @DescriptionDocs(description = "Repairs all items in inventory")
    void repairAll(@Context Player player) {
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

            this.delay.markDelay(uuid, this.config.repair.repairDelay());
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
            .notice(translation -> translation.item().repairAllMessage())
            .player(player.getUniqueId())
            .send();

        this.delay.markDelay(uuid, this.config.repair.repairDelay());
    }

    @Execute(name = "armor")
    @Permission("eternalcore.repair.armor")
    @DescriptionDocs(description = "Repairs all items in armor")
    void repairArmor(@Context Player player) {
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

        this.delay.markDelay(uuid, this.config.repair.repairDelay());
    }

    private boolean hasRepairDelay(UUID uuid) {
        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.item().repairDelayMessage())
                .placeholder("{TIME}", DurationUtil.format(time))
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
