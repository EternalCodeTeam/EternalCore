package com.eternalcode.core.feature.home;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.database.HomeRepository;
import com.eternalcode.core.feature.home.event.HomeCreateEvent;
import com.eternalcode.core.feature.home.event.HomeDeleteEvent;
import com.eternalcode.core.feature.home.event.HomeOverrideEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class HomeServiceImpl implements HomeService {

    private final Map<UUID, Map<String, Home>> userHomes = new HashMap<>();
    private final HomeRepository repository;
    private final EventCaller eventCaller;

    private final PluginConfiguration pluginConfiguration;

    @Inject
    private HomeServiceImpl(
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
    public boolean underLimit(UUID playerUniqueId) {
        return false;
    }

    @Override
    public Home setHome(UUID playerUniqueId, String name, Location location) {
        return null;
    }

    @Override
    public int getHomeLimit(UUID playerUniqueId) {
        return 0;
    }

    public boolean hasHome(UUID playerUniqueId, String name) {
        Map<String, Home> homes = this.userHomes.get(playerUniqueId);

        if (homes == null) {
            return false;
        }

        return homes.containsKey(name);
    }


    public boolean hasHome(UUID playerUniqueId, Home home) {
        Map<String, Home> homes = this.userHomes.get(playerUniqueId);

        if (homes == null) {
            return false;
        }

        return homes.containsValue(home);
    }

    @Override
    public Optional<Home> getHome(UUID playerUniqueId) {
        return Optional.empty();
    }

    @Override
    public Optional<Home> getHome(UUID uniqueId, String name) {
        Map<String, Home> homes = this.userHomes.get(uniqueId);

        if (homes == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(homes.get(name));
    }


    public Optional<Set<Home>> getHomes(UUID user) {
        return Optional.empty();
    }

    @Override
    public boolean hasHomes(UUID playerUniqueId) {
        return false;
    }


    public int getAmountOfHomes(UUID user) {
        Map<String, Home> homes = this.userHomes.get(user);

        if (homes == null) {
            return 0;
        }

        return homes.size();
    }

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
