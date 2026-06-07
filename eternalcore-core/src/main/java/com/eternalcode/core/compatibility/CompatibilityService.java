package com.eternalcode.core.compatibility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public class CompatibilityService {

    private static final Pattern MINECRAFT_VERSION_PATTERN = Pattern.compile("^1\\.(\\d+)(?:\\.(\\d+))?.*");

    public boolean isCompatible(Class<?> type) {
        Compatibility compatibility = type.getAnnotation(Compatibility.class);
        if (compatibility == null) {
            return true;
        }

        Version from = compatibility.from();
        Version to = compatibility.to();

        MinecraftVersion minecraftVersion = parseMinecraftVersion(Bukkit.getBukkitVersion());
        int minor = minecraftVersion.minor();
        int patch = minecraftVersion.patch();

        return isCompatibleFrom(from, minor, patch) && isCompatibleTo(to, minor, patch);
    }

    private static MinecraftVersion parseMinecraftVersion(String bukkitVersion) {
        Matcher matcher = MINECRAFT_VERSION_PATTERN.matcher(bukkitVersion);
        if (!matcher.matches()) {
            return new MinecraftVersion(0, 0);
        }

        int minor = Integer.parseInt(matcher.group(1));
        String patchGroup = matcher.group(2);
        int patch = patchGroup == null ? 0 : Integer.parseInt(patchGroup);

        return new MinecraftVersion(minor, patch);
    }

    private boolean isCompatibleTo(Version to, int minor, int patch) {
        return minor < to.minor() || minor == to.minor() && patch <= to.patch();
    }

    private boolean isCompatibleFrom(Version from, int minor, int patch) {
        return minor > from.minor() || minor == from.minor() && patch >= from.patch();
    }

    private record MinecraftVersion(int minor, int patch) {
    }

}

