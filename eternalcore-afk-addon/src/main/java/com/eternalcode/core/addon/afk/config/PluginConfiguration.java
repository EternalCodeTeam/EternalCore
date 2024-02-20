package com.eternalcode.core.addon.afk.config;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration implements ReloadableConfig {

    public boolean hologramItemEnabled = true;
    public Material hologramItem = Material.BARRIER;

    public List<String> hologramEntries = Arrays.asList(
        "&6This player is &cAFK",
        "&6Since: &c{TIME}"
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
