package com.eternalcode.core.home;

import com.eternalcode.core.user.User;
import org.bukkit.Location;
import panda.std.Option;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HomeManager {

    private final Map<UUID, Set<Home>> homes = new HashMap<>();

    public void createHome(User user, String name, Location location) {
        Home home = new Home(name, location);
        Set<Home> homes = this.homes.computeIfAbsent(user.getUniqueId(), k -> new HashSet<>());

        homes.add(home);
    }

    public void deleteHome(User user, String name) {
        Set<Home> homes = this.homes.get(user.getUniqueId());

        if (homes != null) {
            homes.removeIf(home -> home.getName().equals(name));
        }
    }

    public Option<Home> getHome(User user, String name) {
        Set<Home> homes = this.homes.get(user.getUniqueId());

        if (homes == null) {
            return Option.none();
        }

        for (Home home : homes) {
            if (!home.getName().equals(name)) {
                continue;
            }

            return Option.of(home);
        }

        return Option.none();
    }

}
