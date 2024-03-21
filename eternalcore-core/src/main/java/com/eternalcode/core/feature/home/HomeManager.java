package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.database.HomeRepository;
import com.eternalcode.core.feature.home.event.HomeCreateEvent;
import com.eternalcode.core.feature.home.event.HomeDeleteEvent;
import com.eternalcode.core.feature.home.event.HomeOverrideEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@FeatureDocs(
    name = "Home",
    description = "This feature allows players to set homes and teleport to them. Additionally, eternalcore allows to set limits for the amount of homes with permission"
)
@Service
public class HomeManager implements HomeService {

    private final Map<UUID, Map<String, Home>> userHomes = new HashMap<>();
    private final HomeRepository repository;
    private final UserManager userManager;
    private final EventCaller eventCaller;

    @Inject
    private HomeManager(HomeRepository repository, UserManager userManager, EventCaller eventCaller) {
        this.repository = repository;
        this.userManager = userManager;
        this.eventCaller = eventCaller;

        repository.getHomes().thenAccept(set -> {
            for (Home home : set) {
                Map<String, Home> homesByUuid = this.userHomes.computeIfAbsent(home.getOwner(), k -> new HashMap<>());

                homesByUuid.put(home.getName(), home);
            }
        });
    }

    public Home createHome(User user, String name, Location location) {
        UUID uniqueId = user.getUniqueId();
        Home home = new HomeImpl(uniqueId, name, location);
        Map<String, Home> homes = this.userHomes.computeIfAbsent(uniqueId, k -> new HashMap<>());

        if (this.hasHomeWithSpecificName(user, name)) {
            this.repository.deleteHome(user, name).thenAccept(completable -> {
                HomeOverrideEvent event = new HomeOverrideEvent(user.getUniqueId(), home);
                this.eventCaller.callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                homes.put(name, home);
                this.repository.saveHome(home);
            });

            return home;
        }

        HomeCreateEvent event = new HomeCreateEvent(user.getUniqueId(), home);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return home;
        }

        homes.put(name, home);
        this.repository.saveHome(home);

        return home;
    }

    public void deleteHome(User user, String name) {
        Map<String, Home> homes = this.userHomes.get(user.getUniqueId());

        if (homes == null) {
            return;
        }

        Home home = homes.get(name);

        if (home != null) {
            homes.remove(name);
        }

        HomeDeleteEvent event = new HomeDeleteEvent(user.getUniqueId(), home);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.repository.deleteHome(user, name);
    }

    public boolean hasHomeWithSpecificName(User user, String name) {
        Map<String, Home> homes = this.userHomes.get(user.getUniqueId());

        if (homes == null) {
            return false;
        }

        return homes.containsKey(name);
    }

    public Optional<Home> getHome(UUID uniqueId, String name) {
        Map<String, Home> home = this.userHomes.get(uniqueId);

        if (home == null) {
            return Optional.empty();
        }

        return Optional.of(home.get(name));
    }

    public Collection<Home> getHomes(UUID user) {
        return Collections.unmodifiableCollection(this.userHomes.getOrDefault(user, new HashMap<>()).values());
    }

    public int getAmountOfHomes(UUID user) {
        Map<String, Home> count = this.userHomes.get(user);

        if (count == null) {
            return 0;
        }

        return count.size();
    }

    public int getHomeLimit(Player player, PluginConfiguration.Homes homes) {
        return homes.maxHomes.entrySet().stream()
            .flatMap(entry -> {
                if (player.hasPermission(entry.getKey())) {
                    return Stream.of(entry.getValue());
                }

                return Stream.empty();
            })
            .max(Integer::compareTo)
            .orElse(0);
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(Player player, String homeName) {
        return this.repository.getHome(player.getUniqueId(), homeName);
    }

    @Override
    public CompletableFuture<Void> createHome(String name, UUID owner, Location location) {
        return this.repository.saveHome(new HomeImpl(owner, name, location));
    }

    @Override
    public CompletableFuture<Integer> deleteHome(Player player, String homeName) {
        return this.repository.deleteHome(player.getUniqueId(), homeName);
    }

    @Override
    public CompletableFuture<Boolean> hasHomeWithSpecificName(Player player, String homeName) {
        User user = this.userManager.getOrCreate(player.getUniqueId(), player.getName());

        boolean hasHome = this.hasHomeWithSpecificName(user, homeName);
        return CompletableFuture.completedFuture(hasHome);
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes() {
        return this.repository.getHomes();
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(Player player) {
        return this.repository.getHomes(player.getUniqueId());
    }
}
