package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class ENWarpMessages extends OkaeriConfig implements WarpMessages {
    @Comment("# {WARP} - Warp name")
    Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp <red>{WARP} <dark_red>already exists!");
    Notice create = Notice.chat("<green>► <white>Warp <green>{WARP} <white>has been created.");
    Notice remove = Notice.chat("<red>► <white>Warp <red>{WARP} <white>has been deleted.");
    Notice notExist = Notice.chat("<red>► <dark_red>This warp doesn't exist");
    Notice itemAdded = Notice.chat("<green>► <white>Warp has been added to GUI!");
    Notice noWarps = Notice.chat("<red>✘ <dark_red>There are no warps!");
    Notice itemLimit =
        Notice.chat("<red>✘ <dark_red>You have reached the limit of warps! Your limit is <red>{LIMIT}<dark_red>.");
    Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have permission to use this warp ({WARP})!");
    Notice addPermissions = Notice.chat("<green>► <white>Added permissions to warp <green>{WARP}<white>!");
    Notice removePermission =
        Notice.chat("<red>► <white>Removed permission <red>{PERMISSION}</red> <white>from warp <red>{WARP}<white>!");
    Notice noPermissionsProvided = Notice.chat("<red>✘ <dark_red>No permissions provided!");
    Notice permissionDoesNotExist =
        Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>doesn't exist!");
    Notice permissionAlreadyExist =
        Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>already exists!");
    Notice noPermissionAssigned = Notice.chat("<red>✘ <red>There are no permissions assigned to this warp!");
    Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>You must provide a warp name!");
    Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>You must provide a permission!");

    @Comment({" ", "# {WARPS} - List of warps (separated by commas)"})
    Notice available = Notice.chat("<green>► <white>Available warps: <green>{WARPS}");

    @Comment({" ", "# Settings for warp inventory"})
    public ENWarpInventory warpInventory = new ENWarpInventory();

    @Getter
    @Accessors(fluent = true)
    public static class ENWarpInventory extends OkaeriConfig implements WarpInventorySection {
        public String title = "<dark_gray>» <green>Available warps:";

        @Comment({" ",
                      "# Warps located inside GUI inventory can be customized here. More warps will be added on creation with /setwarp command. "})
        public Map<String, WarpInventoryItem> items = new HashMap<>();

        public void setItems(Map<String, WarpInventoryItem> items) {
            this.items = items;
        }

        public ENWarpInventory.ENBorderSection border = new ENWarpInventory.ENBorderSection();
        public ENWarpInventory.ENDecorationItemsSection decorationItems =
            new ENWarpInventory.ENDecorationItemsSection();

        @Getter
        public static class ENBorderSection extends OkaeriConfig implements BorderSection {

            @Comment({" ",
                          "# Changes of border section may affect the appearance of the GUI inventory, after changes adjust slots of existing items."})
            public boolean enabled = true;

            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            public FillType fillType = FillType.BORDER;

            public String name = "";

            public List<String> lore = Collections.emptyList();
        }

        @Getter
        public static class ENDecorationItemsSection extends OkaeriConfig implements DecorationItemsSection {
            public List<ConfigItem> items = List.of();
        }
    }
}
