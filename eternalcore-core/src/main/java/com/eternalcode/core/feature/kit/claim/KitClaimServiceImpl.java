package com.eternalcode.core.feature.kit.claim;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitClaimService;
import com.eternalcode.core.feature.kit.KitClaimSource;
import com.eternalcode.core.feature.kit.KitClaimStatus;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.event.KitClaimEvent;
import com.eternalcode.core.feature.kit.event.PreKitClaimEvent;
import com.eternalcode.core.feature.kit.usage.KitUsageStore;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;

@Service
class KitClaimServiceImpl implements KitClaimService {

    private final KitUsageService usageService;
    private final KitUsageStore usageStore;
    private final KitRequirementChecker requirementChecker;
    private final KitItemDelivery itemDelivery;
    private final KitClaimLock claimLock;
    private final EventCaller eventCaller;

    @Inject
    KitClaimServiceImpl(
        KitUsageService usageService,
        KitUsageStore usageStore,
        KitRequirementChecker requirementChecker,
        KitItemDelivery itemDelivery,
        KitClaimLock claimLock,
        EventCaller eventCaller
    ) {
        this.usageService = usageService;
        this.usageStore = usageStore;
        this.requirementChecker = requirementChecker;
        this.itemDelivery = itemDelivery;
        this.claimLock = claimLock;
        this.eventCaller = eventCaller;
    }

    @Override
    public KitClaimResult canClaim(Player player, Kit kit) {
        return this.check(player, kit, KitClaimSource.PLAYER);
    }

    @Override
    public KitClaimResult claimKit(Player player, Kit kit) {
        return this.claim(player, kit, KitClaimSource.PLAYER);
    }

    @Override
    public KitClaimResult giveKit(Player player, Kit kit) {
        return this.claim(player, kit, KitClaimSource.ADMIN);
    }

    KitClaimResult claimOnFirstJoin(Player player, Kit kit) {
        return this.claim(player, kit, KitClaimSource.FIRST_JOIN);
    }

    private KitClaimResult claim(Player player, Kit kit, KitClaimSource source) {
        UUID uniqueId = player.getUniqueId();

        if (!this.claimLock.tryAcquire(uniqueId)) {
            return KitClaimResult.of(KitClaimStatus.ALREADY_CLAIMING);
        }

        try {
            KitClaimResult check = this.check(player, kit, source);

            if (!check.isSuccess()) {
                return check;
            }

            PreKitClaimEvent preEvent = this.eventCaller.callEvent(new PreKitClaimEvent(player, kit, source, this.isBypassingCooldown(player)));

            if (preEvent.isCancelled()) {
                return KitClaimResult.of(KitClaimStatus.CANCELLED);
            }

            if (source != KitClaimSource.ADMIN) {
                this.usageStore.recordClaim(uniqueId, kit.name(), Instant.now());
            }

            int bufferedItems = this.itemDelivery.deliver(player, kit);
            this.itemDelivery.dispatchCommands(player, kit);
            this.eventCaller.callEvent(new KitClaimEvent(player, kit, source, bufferedItems));

            return KitClaimResult.success(bufferedItems);
        }
        finally {
            this.claimLock.release(uniqueId);
        }
    }

    private KitClaimResult check(Player player, Kit kit, KitClaimSource source) {
        if (source == KitClaimSource.ADMIN) {
            return this.checkInventory(player, kit);
        }

        if (source == KitClaimSource.PLAYER && !player.hasPermission(kit.permission())) {
            return KitClaimResult.of(KitClaimStatus.NO_PERMISSION);
        }

        UUID uniqueId = player.getUniqueId();

        if (!this.usageService.isLoaded(uniqueId)) {
            return KitClaimResult.of(KitClaimStatus.DATA_NOT_LOADED);
        }

        KitUsage usage = this.usageService.getUsage(uniqueId, kit);

        if (kit.type() != KitType.RECURRING && usage.isClaimed()) {
            return KitClaimResult.of(KitClaimStatus.ALREADY_CLAIMED);
        }

        if (!this.isBypassingCooldown(player)) {
            Optional<Duration> remainingCooldown = this.usageService.getRemainingCooldown(uniqueId, kit);

            if (remainingCooldown.isPresent()) {
                return KitClaimResult.onCooldown(remainingCooldown.get());
            }
        }

        Optional<KitClaimResult> unmetRequirement = this.requirementChecker.findUnmetRequirement(player, kit.requirements());

        return unmetRequirement.orElseGet(() -> this.checkInventory(player, kit));
    }

    private KitClaimResult checkInventory(Player player, Kit kit) {
        if (this.itemDelivery.isCancelledWhenFull() && !this.itemDelivery.fits(player, kit.items())) {
            return KitClaimResult.of(KitClaimStatus.INVENTORY_FULL);
        }

        return KitClaimResult.success(0);
    }

    private boolean isBypassingCooldown(Player player) {
        return player.hasPermission(KitPermissions.BYPASS_COOLDOWN);
    }
}
