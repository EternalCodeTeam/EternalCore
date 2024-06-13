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
import java.util.UUID;
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
    private final EventCaller eventCaller;

    private final PluginConfiguration pluginConfiguration;

    @Inject
    private HomeManager(
        HomeRepository repository,
        EventCaller eventCaller,
        PluginConfiguration pluginConfiguration
    ) {
        this.repository = repository;
        this.eventCaller = eventCaller;
        this.pluginConfiguration = pluginConfiguration;

        repository.getHomes().thenAccept(homes -> {
            for (Home home : homes) {
                Map<String, Home> homesByUuid = this.userHomes.computeIfAbsent(home.getOwner(), k -> new HashMap<>());

                homesByUuid.put(home.getName(), home);
            }
        });
    }

    @Override
    public Home createHome(UUID playerUniqueId, String name, Location location) {
        Map<String, Home> homes = this.userHomes.computeIfAbsent(playerUniqueId, k -> new HashMap<>());

        Home home = homes.get(name);

        if (home != null) {
            HomeOverrideEvent event = this.eventCaller.callEvent(new HomeOverrideEvent(playerUniqueId, name, playerUniqueId, location));

            if (event.isCancelled()) {
                return home;
            }

            Home homeInEvent = new HomeImpl(playerUniqueId, event.getHomeName(), event.getLocation());
            homes.put(event.getHomeName(), homeInEvent);
            this.repository.deleteHome(playerUniqueId, name).thenAccept(completable -> this.repository.saveHome(homeInEvent));

            return homeInEvent;
        }

        HomeCreateEvent event = new HomeCreateEvent(playerUniqueId, name, playerUniqueId, location);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return null;
        }

        Home homeInEvent = new HomeImpl(playerUniqueId, event.getHomeName(), event.getLocation());
        homes.put(event.getHomeName(), homeInEvent);
        this.repository.saveHome(homeInEvent);

        return homeInEvent;
    }

    @Override
    public void deleteHome(UUID playerUniqueId, String name) {
        Map<String, Home> homes = this.userHomes.get(playerUniqueId);

        if (homes == null) {
            return;
        }

        Home home = homes.get(name);

        if (home == null) {
            return;
        }

        HomeDeleteEvent event = new HomeDeleteEvent(playerUniqueId, home);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        homes.remove(name);
        this.repository.deleteHome(playerUniqueId, name);
    }

    @Override
    public boolean hasHome(UUID playerUniqueId, String name) {
        Map<String, Home> homes = this.userHomes.get(playerUniqueId);

        if (homes == null) {
            return false;
        }

        return homes.containsKey(name);
    }

    @Override
    public Optional<Home> getHome(UUID uniqueId, String name) {
        Map<String, Home> homes = this.userHomes.get(uniqueId);

        if (homes == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(homes.get(name));
    }

    @Override
    public Collection<Home> getHomes(UUID user) {
        return Collections.unmodifiableCollection(this.userHomes.getOrDefault(user, new HashMap<>()).values());
    }

    @Override
    public int getAmountOfHomes(UUID user) {
        Map<String, Home> homes = this.userHomes.get(user);

        if (homes == null) {
            return 0;
        }

        return homes.size();
    }

    @Override
    public int getHomeLimit(Player player) {
        Map<String, Integer> maxHomes = this.pluginConfiguration.homes.maxHomes;

        return maxHomes.entrySet().stream()
            .flatMap(entry -> {
                if (player.hasPermission(entry.getKey())) {
                    return Stream.of(entry.getValue());
                }

                return Stream.empty();
            })
            .max(Integer::compareTo)
            .orElse(0);
    }
}
