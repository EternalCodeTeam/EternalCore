package com.eternalcode.core.feature.kit;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.kit.cooldown.KitCooldownStore;
import com.eternalcode.core.feature.kit.event.KitClaimEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Claim flow: permission -> cooldown (memory) -> event -> cooldown persisted -> items + commands.
 * Fully synchronous on the player's thread, so spam clicking cannot claim a kit twice.
 */
@Service
class KitClaimServiceImpl implements KitClaimService {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";

    private final KitCooldownService cooldownService;
    private final KitCooldownStore cooldownStore;
    private final EventCaller eventCaller;
    private final Scheduler scheduler;
    private final Server server;

    @Inject
    KitClaimServiceImpl(
        KitCooldownService cooldownService,
        KitCooldownStore cooldownStore,
        EventCaller eventCaller,
        Scheduler scheduler,
        Server server
    ) {
        this.cooldownService = cooldownService;
        this.cooldownStore = cooldownStore;
        this.eventCaller = eventCaller;
        this.scheduler = scheduler;
        this.server = server;
    }

    @Override
    public KitClaimResult claimKit(Player player, Kit kit) {
        if (!player.hasPermission(kit.permission())) {
            return KitClaimResult.of(KitClaimStatus.NO_PERMISSION);
        }

        UUID uniqueId = player.getUniqueId();
        boolean bypassingCooldown = player.hasPermission(KitPermissions.BYPASS_COOLDOWN);
        boolean checkCooldown = !bypassingCooldown && kit.hasCooldown();

        if (checkCooldown && !this.cooldownStore.isLoaded(uniqueId)) {
            return KitClaimResult.of(KitClaimStatus.COOLDOWNS_NOT_LOADED);
        }

        if (checkCooldown) {
            Optional<Duration> remaining = this.cooldownService.getRemainingCooldown(uniqueId, kit);

            if (remaining.isPresent()) {
                return KitClaimResult.onCooldown(remaining.get());
            }
        }

        KitClaimEvent event = this.eventCaller.callEvent(new KitClaimEvent(player, kit, bypassingCooldown));

        if (event.isCancelled()) {
            return KitClaimResult.of(KitClaimStatus.CANCELLED);
        }

        if (!bypassingCooldown) {
            this.cooldownService.applyCooldown(uniqueId, kit);
        }

        this.giveItems(player, kit);
        this.dispatchCommands(player, kit);

        return KitClaimResult.of(KitClaimStatus.SUCCESS);
    }

    private void giveItems(Player player, Kit kit) {
        ItemStack[] items = kit.items().toArray(new ItemStack[0]);
        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(items);

        leftovers.values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
    }

    private void dispatchCommands(Player player, Kit kit) {
        List<String> commands = kit.commands();

        if (commands.isEmpty()) {
            return;
        }

        String playerName = player.getName();

        this.scheduler.run(() -> commands.forEach(command -> this.server.dispatchCommand(
            this.server.getConsoleSender(),
            command.replace(PLAYER_PLACEHOLDER, playerName)
        )));
    }
}
