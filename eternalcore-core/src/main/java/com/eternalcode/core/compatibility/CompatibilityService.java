package com.eternalcode.core.compatibility;

import io.papermc.lib.PaperLib;

public class CompatibilityService {

    public boolean isCompatible(Class<?> type) {
        Compatibility compatibility = type.getAnnotation(Compatibility.class);
        if (compatibility == null) {
            return true;
        }

        Version from = compatibility.from();
        Version to = compatibility.to();

        int minor = PaperLib.getMinecraftVersion();
        int patch = PaperLib.getMinecraftPatchVersion();

        return isCompatibleFrom(from, minor, patch) && isCompatibleTo(to, minor, patch);
    }

    private boolean isCompatibleTo(Version to, int minor, int patch) {
        return minor < to.minor() || minor == to.minor() && patch <= to.patch();
    }

    private boolean isCompatibleFrom(Version from, int minor, int patch) {
        return minor > from.minor() || minor == from.minor() && patch >= from.patch();
    }

}

