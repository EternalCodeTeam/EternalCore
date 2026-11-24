package com.eternalcode.core.feature.kit.claim;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitFullInventoryBehavior;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.buffer.KitBufferService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Collection;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Service
class KitItemDelivery {

    private final KitBufferService bufferService;
    private final KitSettings settings;
    private final Scheduler scheduler;
    private final Server server;

    @Inject
    KitItemDelivery(KitBufferService bufferService, KitSettings settings, Scheduler scheduler, Server server) {
        this.bufferService = bufferService;
        this.settings = settings;
        this.scheduler = scheduler;
        this.server = server;
    }

    boolean isCancelledWhenFull() {
        return this.settings.fullInventoryBehavior() == KitFullInventoryBehavior.CANCEL;
    }

    boolean fits(Player player, List<ItemStack> items) {
        ItemStack[] storage = player.getInventory().getStorageContents();
        Inventory simulation = this.server.createInventory(null, storage.length);

        for (int slot = 0; slot < storage.length; slot++) {
            simulation.setItem(slot, storage[slot] == null ? null : storage[slot].clone());
        }

        return simulation.addItem(items.toArray(new ItemStack[0])).isEmpty();
    }

    int deliver(Player player, Kit kit) {
        Collection<ItemStack> leftovers = player.getInventory().addItem(kit.items().toArray(new ItemStack[0])).values();

        if (leftovers.isEmpty()) {
            return 0;
        }

        if (this.settings.fullInventoryBehavior() == KitFullInventoryBehavior.BUFFER) {
            this.bufferService.add(player.getUniqueId(), leftovers);
            return leftovers.size();
        }

        leftovers.forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
        return 0;
    }

    void dispatchCommands(Player player, Kit kit) {
        List<String> commands = kit.commands();

        if (commands.isEmpty()) {
            return;
        }

        String playerName = player.getName();

        this.scheduler.run(() -> commands.forEach(command -> this.server.dispatchCommand(
            this.server.getConsoleSender(),
            command.replace("{PLAYER}", playerName)
        )));
    }
}
