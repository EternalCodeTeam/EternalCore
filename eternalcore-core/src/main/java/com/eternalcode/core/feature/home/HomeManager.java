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
    private final UserManager userManager;
    private final HomeRepository repository;
    private final EventCaller eventCaller;

    private final PluginConfiguration pluginConfiguration;

    @Inject
    private HomeManager(
        HomeRepository repository,
        UserManager userManager,
        EventCaller eventCaller,
        PluginConfiguration pluginConfiguration
    ) {
        this.repository = repository;
        this.userManager = userManager;
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
        Optional<User> userOptional = this.userManager.getUser(playerUniqueId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        UUID uniqueId = user.getUniqueId();
        Home home = new HomeImpl(uniqueId, name, location);
        Map<String, Home> homes = this.userHomes.computeIfAbsent(uniqueId, k -> new HashMap<>());

        if (this.hasHome(uniqueId, name)) {
            this.repository.deleteHome(user, name).thenAccept(completable -> {
                HomeOverrideEvent event = new HomeOverrideEvent(uniqueId, name, home.getUuid(), location);
                this.eventCaller.callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                Home homeInEvent = new HomeImpl(uniqueId, event.getHomeName(), event.getLocation());
                homes.put(event.getHomeName(), homeInEvent);
                this.repository.saveHome(homeInEvent);
            });

            return home;
        }

        HomeCreateEvent event = new HomeCreateEvent(user.getUniqueId(), name, home.getUuid(), location);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return home;
        }

        homes.put(event.getHomeName(), home);
        this.repository.saveHome(home);

        return home;
    }

    @Override
    public void deleteHome(UUID playerUniqueId, String name) {
        Optional<User> userOptional = this.userManager.getUser(playerUniqueId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        Map<String, Home> homes = this.userHomes.get(user.getUniqueId());

        if (homes == null) {
            return;
        }

        Home home = homes.get(name);

        if (home == null) {
            return;
        }

        HomeDeleteEvent event = new HomeDeleteEvent(user.getUniqueId(), home);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        homes.remove(name);
        this.repository.deleteHome(user, name);
    }

    @Override
    public boolean hasHome(UUID playerUniqueId, String name) {
        Optional<User> userOptional = this.userManager.getUser(playerUniqueId);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        Map<String, Home> homes = this.userHomes.get(user.getUniqueId());

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

        return Optional.of(homes.get(name));
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
