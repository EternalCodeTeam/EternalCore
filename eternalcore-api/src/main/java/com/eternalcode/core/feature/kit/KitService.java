package com.eternalcode.core.feature.kit;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public interface KitService {
    Map<String, Kit> getPermissionsList();

    Optional<Kit> getKit(String name);

    boolean kitPlayer(Player player, Kit kit);

    boolean canKit(UUID uniqueId, String kitName);

    void changeName(String oldName, String newName);

    boolean createEmptyKit(String kitName);

    boolean createKit(String kitName, PlayerInventory inventory);

    void deleteKit(String name);

    void openKit(Player player, String name);

    void updateKitInventory(String kitName, Inventory newInventory);
}
