package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.multification.notice.Notice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;

public interface WarpMessages {
    Notice warpAlreadyExists();
    Notice notExist();
    Notice create();
    Notice remove();
    Notice available();
    Notice itemAdded();
    Notice noWarps();
    Notice itemLimit();
    Notice noPermission();
    Notice addPermissions();
    Notice removePermission();
    Notice permissionDoesNotExist();
    Notice permissionAlreadyExist();
    Notice noPermissionsProvided();
    Notice missingWarpArgument();
    Notice missingPermissionArgument();

    WarpInventorySection warpInventory();

    interface WarpInventorySection {
        String title();

        Map<String, WarpInventoryItem> items();
        void setItems(Map<String, WarpInventoryItem> items);

        default void addItem(String name, WarpInventoryItem item) {
            Map<String, WarpInventoryItem> items = new HashMap<>(this.items());
            items.put(name, item);

            this.setItems(items);
        }

        default void removeItem(String name) {
            Map<String, WarpInventoryItem> items = new HashMap<>(this.items());
            items.remove(name);

            this.setItems(items);
        }

        WarpInventorySection.BorderSection border();
        WarpInventorySection.DecorationItemsSection decorationItems();

        interface BorderSection {
            boolean enabled();

            Material material();

            WarpInventorySection.BorderSection.FillType fillType();

            String name();

            List<String> lore();

            enum FillType {
                TOP,
                BOTTOM,
                BORDER,
                ALL
            }
        }

        interface DecorationItemsSection {
            List<ConfigItem> items();
        }
    }
}
