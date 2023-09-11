package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.User;
import org.bukkit.Location;
import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        repository.getHomes().then(homes -> {
            for (Home home : homes) {
                Map<String, Home> homesByUuid = this.homes.computeIfAbsent(home.getOwner(), k -> new HashMap<>());

                homesByUuid.put(home.getName(), home);
            }
        });
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

}
