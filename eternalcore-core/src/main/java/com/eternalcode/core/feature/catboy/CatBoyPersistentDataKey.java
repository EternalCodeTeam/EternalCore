package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

@Service
class CatBoyPersistentDataKey {

    private final NamespacedKey CATBOY_DATA_KEY;

    @Inject
    CatBoyPersistentDataKey(Plugin plugin) {
        CATBOY_DATA_KEY = new NamespacedKey(plugin, "catboy");
    }

    public NamespacedKey getCatboyDataKey() {
        if (this.CATBOY_DATA_KEY == null) {
            throw new IllegalStateException("Catboy data key is not initialized");
        }
        
        return this.CATBOY_DATA_KEY;
    }
}
