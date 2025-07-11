package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationFile
public class WarpConfig extends AbstractConfigurationFile {

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "warps.yml");
    }

    @Comment({"# Warps data", "# These are warp locations, for your own safety, please don't touch it."})
    public Map<String, WarpConfigEntry> warps = new HashMap<>();

    public static class WarpConfigEntry implements Serializable {
        public Position position;
        public List<String> permissions;

        public WarpConfigEntry() {
        }

        public WarpConfigEntry(Position position, List<String> permissions) {
            this.position = position;
            this.permissions = permissions;
        }
    }
}
