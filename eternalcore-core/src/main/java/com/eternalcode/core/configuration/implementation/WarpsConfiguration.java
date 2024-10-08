package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.feature.warp.WarpConfigEntry;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ConfigurationFile
public class WarpsConfiguration implements ReloadableConfig {

    public Map<String, WarpConfigEntry> warps = new HashMap<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "warps.yml");
    }

}
