package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@FeatureDocs(
    name = "Home",
    description = "This feature allows players to set homes and teleport to them. Additionally, eternalcore allows to set limits for the amount of homes with permission"
)
@Service
class HomeManager {

    private final Map<UUID, Map<String, Home>> homes = new HashMap<>();
    private final HomeRepository repository;

    @Inject
    private HomeManager(HomeRepository repository) {
        this.repository = repository;

        repository.getHomes().thenAccept(homes -> {
            for (Home home : homes) {
                Map<String, Home> homesByUuid = this.homes.computeIfAbsent(home.getOwner(), k -> new HashMap<>());

                homesByUuid.put(home.getName(), home);
            }
        });
    }

    public void createHome(User user, String name, Location location) {
        UUID uniqueId = user.getUniqueId();
        Home home = new Home(uniqueId, name, location);
        Map<String, Home> userHomes = this.homes.computeIfAbsent(uniqueId, k -> new HashMap<>());

        if (this.hasHomeWithSpecificName(user, name)) {
            this.repository.deleteHome(user, name).thenAccept(completable -> {
                userHomes.put(name, home);
                this.repository.saveHome(home);
            });
            return;
        }

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

    public int getAmountOfHomes(UUID user) {
        Map<String, Home> homes = this.homes.get(user);

        if (homes == null) {
            return 0;
        }

        return homes.size();
    }

    public int getHomesLimit(Player player, PluginConfiguration.Homes homes) {
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

}
