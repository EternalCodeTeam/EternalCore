package com.eternalcode.core.feature.home;

import com.eternalcode.core.user.User;
import org.bukkit.Location;
import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeManager {

    private final Map<UUID, Map<String, Home>> homes = new HashMap<>();
    private final HomeRepository repository;

    private HomeManager(HomeRepository repository) {
        this.repository = repository;
    }

    public void createHome(User user, String name, Location location) {
        Home home = new Home(user.getUniqueId(), name, location);
        Map<String, Home> userHomes = this.homes.computeIfAbsent(user.getUniqueId(), k -> new HashMap<>());

        userHomes.put(name, home);

        this.repository.saveHome(home);
    }

    public void deleteHome(User user, String name) {
        Map<String, Home> homes = this.homes.get(user.getUniqueId());

        if (homes != null) {
            homes.remove(name);
        }

        this.repository.deleteHome(user, name);
    }

    public boolean hasHomeWithSpecificName(User user, String name) {
        Map<String, Home> homes = this.homes.get(user.getUniqueId());

        if (homes == null) {
            return false;
        }

        return homes.containsKey(name);
    }

    public Option<Home> getHome(UUID uniqueId, String name) {
        Map<String, Home> homes = this.homes.get(uniqueId);

        if (homes == null) {
            return Option.none();
        }

        return Option.of(homes.get(name));
    }

    public Collection<Home> getHomes(UUID user) {
        return Collections.unmodifiableCollection(this.homes.getOrDefault(user, new HashMap<>()).values());
    }

    public static HomeManager create(HomeRepository repository) {
        HomeManager manager = new HomeManager(repository);

        repository.getHomes().then(homes -> {
            for (Home home : homes) {
                Map<String, Home> homesByUuid = manager.homes.computeIfAbsent(home.getOwner(), k -> new HashMap<>());

                homesByUuid.put(home.getName(), home);
            }
        });

        return manager;
    }
}
