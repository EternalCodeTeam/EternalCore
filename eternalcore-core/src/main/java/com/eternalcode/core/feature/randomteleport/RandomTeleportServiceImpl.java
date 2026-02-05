package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportResolveWorldUtil.resolveWorld;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

@Service
class RandomTeleportServiceImpl implements RandomTeleportService {

    private final RandomTeleportSettings randomTeleportSettings;
    private final RandomTeleportSafeLocationService safeLocationService;
    private final EventCaller eventCaller;
    private final Map<String, RandomTeleportLocationFilter> registeredFilters;

    @Inject
    RandomTeleportServiceImpl(
        RandomTeleportSettings randomTeleportSettings,
        RandomTeleportSafeLocationService safeLocationService, EventCaller eventCaller
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.safeLocationService = safeLocationService;
        this.eventCaller = eventCaller;
        this.registeredFilters = new ConcurrentHashMap<>();
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player) {
        World world = resolveWorld(player, randomTeleportSettings);
        return this.teleport(player, world);
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player, World world) {
        PreRandomTeleportEvent preRandomTeleportEvent = this.eventCaller.callEvent(new PreRandomTeleportEvent(player));

        if (preRandomTeleportEvent.isCancelled()) {
            return CompletableFuture.completedFuture(new RandomTeleportResult(false, player.getLocation()));
        }

        return this.getSafeRandomLocation(world, this.randomTeleportSettings.teleportAttempts())
            .thenCompose(location -> PaperLib.teleportAsync(player, location).thenApply(success -> {
                RandomTeleportResult teleportResult = new RandomTeleportResult(success, location);

                RandomTeleportEvent event = new RandomTeleportEvent(player, location);
                this.eventCaller.callEvent(event);

                return teleportResult;
            }));
    }

    @Override
    public CompletableFuture<Map<Player, RandomTeleportResult>> teleport(Collection<Player> players) {
        if (players.isEmpty()) {
            return CompletableFuture.completedFuture(Map.of());
        }

        Player firstPlayer = players.iterator().next();
        World world = resolveWorld(firstPlayer, randomTeleportSettings);

        return this.teleport(players, world);
    }

    @Override
    public CompletableFuture<Map<Player, RandomTeleportResult>> teleport(Collection<Player> players, World world) {
        if (players.isEmpty()) {
            return CompletableFuture.completedFuture(Map.of());
        }

        return this.getSafeRandomLocation(world, this.randomTeleportSettings.teleportAttempts())
            .thenCompose(location -> this.teleport(players, location));
    }

    @Override
    public CompletableFuture<Map<Player, RandomTeleportResult>> teleport(
        Collection<Player> players,
        Location location) {
        if (players.isEmpty()) {
            return CompletableFuture.completedFuture(Map.of());
        }

        List<CompletableFuture<Entry<Player, RandomTeleportResult>>> futures = players.stream()
            .map(player -> {
                PreRandomTeleportEvent preRandomTeleportEvent =
                    this.eventCaller.callEvent(new PreRandomTeleportEvent(player));

                if (preRandomTeleportEvent.isCancelled()) {
                    return CompletableFuture.completedFuture(
                        Map.entry(player, new RandomTeleportResult(false, player.getLocation()))
                    );
                }

                return PaperLib.teleportAsync(player, location).thenApply(success -> {
                    RandomTeleportResult teleportResult = new RandomTeleportResult(success, location);
                    this.eventCaller.callEvent(new RandomTeleportEvent(player, location));
                    return Map.entry(player, teleportResult);
                });
            })
            .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        RandomTeleportRadius radius = switch (this.randomTeleportSettings.radiusType()) {
            case STATIC_RADIUS -> this.randomTeleportSettings.radius();
            case WORLD_BORDER_RADIUS -> this.getWorldBorderRadius(world);
        };

        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(world, RandomTeleportRadius.of(radius), attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(
        World world,
        RandomTeleportRadius radius,
        int attemptCount
    ) {
        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return this.getSafeRandomLocation(world, this.getWorldBorderRadius(world), attemptCount);
    }

    private RandomTeleportRadius getWorldBorderRadius(World world) {
        WorldBorder worldBorder = world.getWorldBorder();
        int borderRadius = (int) (worldBorder.getSize() / 2);
        return RandomTeleportRadius.of(-borderRadius, borderRadius, -borderRadius, borderRadius);
    }

    @Override
    public void registerFilter(RandomTeleportLocationFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }

        String filterName = filter.getFilterName();
        if (filterName == null || filterName.isEmpty()) {
            throw new IllegalArgumentException("Filter name cannot be null or empty");
        }

        if (this.registeredFilters.containsKey(filterName)) {
            throw new IllegalArgumentException("Filter with name '" + filterName + "' is already registered");
        }

        this.registeredFilters.put(filterName, filter);
    }

    @Override
    public boolean unregisterFilter(String filterName) {
        if (filterName == null || filterName.isEmpty()) {
            return false;
        }

        return this.registeredFilters.remove(filterName) != null;
    }

    @Override
    public Collection<RandomTeleportLocationFilter> getRegisteredFilters() {
        return Collections.unmodifiableCollection(this.registeredFilters.values());
    }

    Collection<RandomTeleportLocationFilter> getRegisteredFiltersInternal() {
        return this.registeredFilters.values();
    }
}
