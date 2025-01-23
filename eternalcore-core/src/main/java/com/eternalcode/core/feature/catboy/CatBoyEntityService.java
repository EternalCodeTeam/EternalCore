package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

@Service
class CatBoyEntityService {

    private final NamespacedKey catboyNamespacedKey;

    @Inject
    CatBoyEntityService(Plugin plugin) {
        this.catboyNamespacedKey = new NamespacedKey(plugin, "catboy");
    }

    Cat createCatboyEntity(Player player, Cat.Type type) {
        Cat cat = (Cat) player.getWorld().spawnEntity(player.getLocation(), EntityType.CAT);
        cat.setInvulnerable(true);
        cat.setOwner(player);
        cat.setAI(false);
        cat.setCatType(type);

        PersistentDataContainer persistentDataContainer = cat.getPersistentDataContainer();
        persistentDataContainer.set(catboyNamespacedKey, PersistentDataType.BOOLEAN, true);

        return cat;
    }

    boolean isCatboy(Cat cat) {
        return cat.getPersistentDataContainer().has(catboyNamespacedKey, PersistentDataType.BOOLEAN);
    }

}
