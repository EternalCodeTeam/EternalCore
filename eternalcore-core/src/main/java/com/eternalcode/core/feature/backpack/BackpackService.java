package com.eternalcode.core.feature.backpack;

import com.eternalcode.core.feature.backpack.repository.BackpackRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import static java.util.stream.Collectors.toMap;

@Service
public class BackpackService {

    private final BackpackRepositoryOrmLite database;
    private final BackpackSettings backpackSettings;
    private final Plugin plugin;
    private final HashMap<UUID, HashMap<Integer, ItemStack>> cache = new HashMap<>();

    @Inject
    public BackpackService(BackpackRepositoryOrmLite database, BackpackSettings backpackSettings, Plugin plugin) {
        this.database = database;
        this.backpackSettings = backpackSettings;
        this.plugin = plugin;
    }

    void openBackpack(Player player, Integer slot) {
        UUID uniqueId = player.getUniqueId();
        Map<Integer, ItemStack> integerItemStackHashMap = this.getBackpack(uniqueId, 1);

        Set<InteractionModifier> modifierSet = new HashSet<>();
        modifierSet.add(InteractionModifier.PREVENT_OTHER_ACTIONS);


        Inventory inventory = new BackpackInventory(plugin, player, backpackSettings.getSize(), backpackSettings.getName()).getInventory();


        if (integerItemStackHashMap != null) {
            integerItemStackHashMap.forEach((integer, itemStack) ->
                inventory.setItem(integer, itemStack)
            );
        }

        player.openInventory(inventory);

    }

    private Map<Integer, ItemStack> getBackpack(UUID uniqueId, int slot) {
        if (this.cache.containsKey(uniqueId)) {
            return this.cache.get(uniqueId);
        }

        Map<Integer, ItemStack> map = new HashMap<>();

        CompletableFuture<Optional<Backpack>> backpackFuture = this.database.getBackpack(uniqueId, slot);
        backpackFuture.thenAccept(optional -> optional.ifPresent(backpack -> map.putAll(backpack.getBackpack())));

        return map;
    }

}
