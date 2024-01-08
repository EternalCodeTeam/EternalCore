package com.eternalcode.core.bridge.commodore;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscriber;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.plugin.Plugin;

@BeanSetup
public class CommodoreSetup implements Subscriber {

    @Bean
    public Commodore commodore(Plugin plugin) {
        return CommodoreProvider.getCommodore(plugin);
    }

}