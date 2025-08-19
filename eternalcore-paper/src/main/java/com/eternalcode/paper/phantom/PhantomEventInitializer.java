package com.eternalcode.paper.phantom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PhantomEventInitializer {

    private final Plugin plugin;

    public PhantomEventInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        Bukkit.getPluginManager().registerEvents(new PhantomEventWrapper(), this.plugin);
    }
}
