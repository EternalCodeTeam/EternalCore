package com.eternalcode.core.feature.kit.placeholder;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.Type;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimService;
import com.eternalcode.core.feature.kit.KitDisplayFormatter;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.KitsLoadedEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.Placeholder;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.bukkit.entity.Player;

@PlaceholdersDocs(
    category = "Kits",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "kit_<kit>_available",
            description = "Returns `true` if the player can claim the kit right now, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "kit_<kit>_cooldown",
            description = "Returns the remaining cooldown of the kit, or an empty string if it is not on cooldown.",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "kit_<kit>_claims",
            description = "Returns how many times the player claimed the kit.",
            returnType = Type.INT,
            requiresPlayer = true
        )
    }
)
@Controller
class KitPlaceholderSetup {

    private final Set<String> registeredKits = ConcurrentHashMap.newKeySet();

    private final PlaceholderRegistry placeholderRegistry;
    private final KitService kitService;
    private final KitClaimService claimService;
    private final KitUsageService usageService;
    private final KitDisplayFormatter displayFormatter;
    private final Scheduler scheduler;

    @Inject
    KitPlaceholderSetup(
        PlaceholderRegistry placeholderRegistry,
        KitService kitService,
        KitClaimService claimService,
        KitUsageService usageService,
        KitDisplayFormatter displayFormatter,
        Scheduler scheduler
    ) {
        this.placeholderRegistry = placeholderRegistry;
        this.kitService = kitService;
        this.claimService = claimService;
        this.usageService = usageService;
        this.displayFormatter = displayFormatter;
        this.scheduler = scheduler;
    }

    @Subscribe(EternalInitializeEvent.class)
    void onInitialize() {
        this.kitService.getKits().forEach(this::register);
    }

    @Subscribe
    void onKitsLoaded(KitsLoadedEvent event) {
        this.scheduler.run(() -> event.kits().forEach(this::register));
    }

    private void register(Kit kit) {
        String kitName = kit.name();

        if (!this.registeredKits.add(kitName)) {
            return;
        }

        this.placeholderRegistry.register(Placeholder.of("kit_" + kitName + "_available", this.forKit(kitName, (player, current) ->
            String.valueOf(this.claimService.canClaim(player, current).isSuccess()))));

        this.placeholderRegistry.register(Placeholder.of("kit_" + kitName + "_cooldown", this.forKit(kitName, (player, current) ->
            this.usageService.getRemainingCooldown(player.getUniqueId(), current).map(this.displayFormatter::duration).orElse(""))));

        this.placeholderRegistry.register(Placeholder.of("kit_" + kitName + "_claims", this.forKit(kitName, (player, current) ->
            String.valueOf(this.usageService.getUsage(player.getUniqueId(), current).claimCount()))));
    }

    private Function<Player, String> forKit(String kitName, BiFunction<Player, Kit, String> resolver) {
        return player -> {
            Optional<Kit> kit = this.kitService.findKit(kitName);
            return kit.map(current -> resolver.apply(player, current)).orElse("");
        };
    }
}
