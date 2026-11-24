package com.eternalcode.core.feature.kit.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.buffer.KitBufferService;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "kit buffer")
@Permission("eternalcore.kit")
class KitBufferCommand {

    private final KitBufferService bufferService;
    private final KitNotices notices;
    private final MinecraftScheduler scheduler;

    @Inject
    KitBufferCommand(KitBufferService bufferService, KitNotices notices, MinecraftScheduler scheduler) {
        this.bufferService = bufferService;
        this.notices = notices;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Claims kit items that did not fit into your inventory")
    void claimBuffer(@Sender Player player) {
        this.bufferService.takeAll(player.getUniqueId())
            .thenAccept(items -> this.scheduler.run(player, () -> this.giveBufferedItems(player, items)))
            .exceptionally(FutureHandler::handleException);
    }

    private void giveBufferedItems(Player player, List<ItemStack> items) {
        if (items.isEmpty()) {
            this.notices.send(player, KitMessages::bufferEmpty);
            return;
        }

        if (!player.isOnline()) {
            this.bufferService.add(player.getUniqueId(), items);
            return;
        }

        Collection<ItemStack> leftovers = player.getInventory().addItem(items.toArray(new ItemStack[0])).values();
        this.bufferService.add(player.getUniqueId(), leftovers);

        Formatter formatter = new Formatter()
            .register(KitNotices.AMOUNT, items.size() - leftovers.size())
            .register("{REMAINING}", leftovers.size());

        this.notices.send(player, KitMessages::bufferClaimed, formatter);
    }
}
