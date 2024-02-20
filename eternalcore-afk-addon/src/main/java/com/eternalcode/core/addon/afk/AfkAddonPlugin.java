package com.eternalcode.core.addon.afk;

import com.eternalcode.commons.adventure.AdventureLegacyColorPostProcessor;
import com.eternalcode.commons.adventure.AdventureLegacyColorPreProcessor;
import com.eternalcode.core.EternalCoreApi;
import com.eternalcode.core.EternalCoreApiProvider;
import com.eternalcode.core.addon.afk.config.ConfigService;
import com.eternalcode.core.addon.afk.config.PluginConfiguration;
import com.eternalcode.core.feature.afk.AfkService;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class AfkAddonPlugin extends JavaPlugin {

    private ConfigService configService;
    private PluginConfiguration configuration;

    private AfkHologramService afkHologramService;

    private MiniMessage miniMessage;

    @Override
    public void onEnable() {
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new AdventureLegacyColorPostProcessor())
            .preProcessor(new AdventureLegacyColorPreProcessor())
            .build();

        this.configService = new ConfigService(this.getDataFolder());
        this.configuration = this.configService.load(new PluginConfiguration());

        AfkService afkService = EternalCoreApiProvider.provide().getAfkService();
        Server server = this.getServer();

        this.afkHologramService = new AfkHologramService(this.configuration, this.miniMessage, afkService, this);
        server.getPluginManager().registerEvents(new AfkController(this.afkHologramService, afkService), this);
        server.getScheduler().runTaskTimer(this, new AfkHologramTask(this.afkHologramService, server), 20L, 20L);
    }


}
