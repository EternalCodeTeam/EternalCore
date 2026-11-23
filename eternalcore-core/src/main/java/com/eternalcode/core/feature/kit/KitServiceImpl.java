package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.repository.InventoryAdapter;
import com.eternalcode.core.feature.kit.repository.KitRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Service
public class KitServiceImpl implements KitService {

    private final static String PERMISSION_PREFIX = "eternalcore.kit.";

    private final KitRepository repository;
    private final KitSettings kitSettings;

    private final Map<String, Kit> kits = new ConcurrentHashMap<>();
    private final Map<String, Map<UUID, Instant>> cooldowns = new ConcurrentHashMap<>();

    @Inject
    public KitServiceImpl(KitRepository repository, KitSettings kitSettings) {
        this.repository = repository;
        this.kitSettings = kitSettings;
        this.loadAll();
    }

    @Override
    public Map<String, Kit> getPermissionsList() {
        Map<String, Kit> permissionList = new HashMap<>();
        this.kits.forEach((name, kit) -> permissionList.put(kit.permission(), kit));
        return permissionList;
    }

    @Override
    public Optional<Kit> getKit(String name) {
        return Optional.ofNullable(this.kits.get(name));
    }

    @Override
    public boolean kitPlayer(Player player, Kit kit) {
        UUID uniqueId = player.getUniqueId();

        // Check cooldown
        if (!kit.duration().isZero() && !this.canKit(uniqueId, kit.name())) {
            return false;
        }

        // Give the kit items
        this.giveKitItems(player, kit);

        // Apply cooldown if applicable
        if (!kit.duration().isZero()) {
            this.cooldowns.computeIfAbsent(kit.name(), k -> new ConcurrentHashMap<>())
                .put(uniqueId, Instant.now().plus(kit.duration()));
        }

        return true;
    }

    @Override
    public boolean canKit(UUID uniqueId, String kitName) {
        Map<UUID, Instant> kitCooldowns = this.cooldowns.get(kitName);
        if (kitCooldowns == null) {
            return true;
        }

        Instant cooldownEnd = kitCooldowns.get(uniqueId);
        return cooldownEnd == null || cooldownEnd.isBefore(Instant.now());
    }

    @Override
    public void changeName(String oldName, String newName) {
        this.repository.changeKitName(oldName, newName);

        Kit kit = this.kits.get(oldName);
        if (kit == null) {
            return;
        }

        Kit newKit = new Kit(newName, kit.permission(), kit.duration(), kit.inventory(), kit.displayItem(), kit.priority());

        this.kits.remove(oldName);
        this.kits.put(newName, newKit);

        Map<UUID, Instant> kitCooldowns = this.cooldowns.remove(oldName);
        if (kitCooldowns != null) {
            this.cooldowns.put(newName, kitCooldowns);
        }
    }

    @Override
    public boolean createEmptyKit(String kitName) {
        if (this.kits.containsKey(kitName)) {
            return false;
        }

        int size = this.kits.size();


        Kit newKit = new Kit(kitName, PERMISSION_PREFIX + kitName, Duration.ofHours(24), new InventoryData().empty(), this.kitSettings.defaultDisplayItem(), size + 1);

        this.kits.put(kitName, newKit);
        this.repository.saveKit(newKit);

        return true;
    }

    @Override
    public boolean createKit(String kitName, PlayerInventory inventory) {
        if (this.kits.containsKey(kitName)) {
            return false;
        }

        int size = this.kits.size();

        Kit newKit = new Kit(kitName, PERMISSION_PREFIX + kitName, Duration.ofHours(24), InventoryAdapter.convert(inventory), this.kitSettings.defaultDisplayItem(), size + 1);

        this.kits.put(kitName, newKit);
        this.repository.saveKit(newKit);

        return true;
    }

    @Override
    public void deleteKit(String name) {
        this.kits.remove(name);
        this.cooldowns.remove(name);
        this.repository.removeKit(name);
    }

    @Override
    public void openKit(Player player, String name) {
        Kit kit = this.kits.get(name);
        if (kit == null) {
            return;
        }

        KitEditInventoryHolder holder = new KitEditInventoryHolder(kit, Component.text("Editing Kit: " + kit.name()));
        player.openInventory(holder.getInventory());
    }

    @Override
    public void updateKitInventory(String kitName, Inventory newInventory) {
        Kit kit = this.kits.get(kitName);
        if (kit == null) {
            return;
        }

        InventoryData newData = InventoryAdapter.convert(newInventory);
        Kit updatedKit = new Kit(kit.name(), kit.permission(), kit.duration(), newData, kit.displayItem(), kit.priority());

        this.kits.put(kitName, updatedKit);
        this.repository.saveKit(updatedKit);
    }

    private void loadAll() {
        this.repository.getKits()
            .thenAccept(list -> list.forEach(kit -> this.kits.put(kit.name(), kit)));
    }

    private void giveKitItems(Player player, Kit kit) {
        PlayerInventory playerInv = player.getInventory();

        if (kit.inventory() == null || kit.inventory().items == null) {
            return;
        }

        for (Map.Entry<Integer, ItemStack> entry : kit.inventory().items.entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue().clone();

            if (slot >= 0 && slot < playerInv.getSize() && playerInv.getItem(slot) == null) {
                playerInv.setItem(slot, item);
            } else {
                HashMap<Integer, ItemStack> leftovers = playerInv.addItem(item);
                for (ItemStack leftover : leftovers.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), leftover);
                }
            }
        }
    }
}