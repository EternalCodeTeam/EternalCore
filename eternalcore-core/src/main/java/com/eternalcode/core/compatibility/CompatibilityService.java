package com.eternalcode.core.compatibility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public class CompatibilityService {

    private static final Pattern MINECRAFT_VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.(\\d+))?");

    public boolean isCompatible(Class<?> type) {
        Compatibility compatibility = type.getAnnotation(Compatibility.class);
        if (compatibility == null) {
            return true;
        }

        Version from = compatibility.from();
        Version to = compatibility.to();

        MinecraftVersion minecraftVersion = parseMinecraftVersion(Bukkit.getMinecraftVersion());

        return isCompatibleFrom(from, minecraftVersion) && isCompatibleTo(to, minecraftVersion);
    }

    static MinecraftVersion parseMinecraftVersion(String bukkitVersion) {
        Matcher matcher = MINECRAFT_VERSION_PATTERN.matcher(bukkitVersion);
        if (!matcher.find()) {
            return new MinecraftVersion(0, 0, 0);
        }

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        String patchGroup = matcher.group(3);
        int patch = patchGroup == null ? 0 : Integer.parseInt(patchGroup);

        return new MinecraftVersion(major, minor, patch);
    }

    boolean isCompatibleTo(Version to, MinecraftVersion current) {
        if (current.major() < to.major()) {
            return true;
        }

        if (current.major() > to.major()) {
            return false;
        }

        if (current.minor() < to.minor()) {
            return true;
        }

        if (current.minor() > to.minor()) {
            return false;
        }

        return current.patch() <= to.patch();
    }

    boolean isCompatibleFrom(Version from, MinecraftVersion current) {
        if (current.major() > from.major()) {
            return true;
        }

        if (current.major() < from.major()) {
            return false;
        }

        if (current.minor() > from.minor()) {
            return true;
        }

        if (current.minor() < from.minor()) {
            return false;
        }

        return current.patch() >= from.patch();
    }

    record MinecraftVersion(int major, int minor, int patch) {
    }

}

