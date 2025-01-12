package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

@ConfigurationFile
class WarpConfig implements ReloadableConfig {

    @Exclude
    public static final String WARP_DATA_FILE_PATH = "data" + File.separator + "warps.yml";

    @Description({"# Warps data", "# These are warp locations, for your own safety, please don't touch it."})
    public Map<String, WarpConfigEntry> warps = new HashMap<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, WARP_DATA_FILE_PATH);
    }

    @Contextual
    public static class WarpConfigEntry {
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
