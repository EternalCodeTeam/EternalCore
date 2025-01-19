package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.multification.notice.Notice;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENWarpMessages implements WarpMessages {
    @Description("# {WARP} - Warp name")
    public Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp <red>{WARP} <dark_red>already exists!");
    public Notice create = Notice.chat("<green>► <white>Warp <green>{WARP} <white>has been created.");
    public Notice remove = Notice.chat("<red>► <white>Warp <red>{WARP} <white>has been deleted.");
    public Notice notExist = Notice.chat("<red>► <dark_red>This warp doesn't exist");
    public Notice itemAdded = Notice.chat("<green>► <white>Warp has been added to GUI!");
    public Notice noWarps = Notice.chat("<red>✘ <dark_red>There are no warps!");
    public Notice itemLimit =
        Notice.chat("<red>✘ <dark_red>You have reached the limit of warps! Your limit is <red>{LIMIT}<dark_red>.");
    public Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have permission to use this warp ({WARP})!");
    public Notice addPermissions = Notice.chat("<green>► <white>Added permissions to warp <green>{WARP}<white>!");
    public Notice removePermission =
        Notice.chat("<red>► <white>Removed permission <red>{PERMISSION}</red> <white>from warp <red>{WARP}<white>!");
    public Notice noPermissionsProvided = Notice.chat("<red>✘ <dark_red>No permissions provided!");
    public Notice permissionDoesNotExist =
        Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>doesn't exist!");
    public Notice permissionAlreadyExist =
        Notice.chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>already exists!");
    public Notice noPermissionAssigned = Notice.chat("<red>✘ <red>There are no permissions assigned to this warp!");
    public Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>You must provide a warp name!");
    public Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>You must provide a permission!");

    @Description({" ", "# {WARPS} - List of warps (separated by commas)"})
    public Notice available = Notice.chat("<green>► <white>Available warps: <green>{WARPS}");

    @Description({" ", "# Settings for warp inventory"})
    public ENWarpInventory warpInventory = new ENWarpInventory();

    @Getter
    @Accessors(fluent = true)
    @Contextual
    public static class ENWarpInventory implements WarpInventorySection {
        public String title = "<dark_gray>» <green>Available warps:";

        @Description({" ",
                      "# Warps located inside GUI inventory can be customized here. More warps will be added on creation with /setwarp command. "})
        public Map<String, WarpInventoryItem> items = new HashMap<>();

        public void setItems(Map<String, WarpInventoryItem> items) {
            this.items = items;
        }

        public ENWarpInventory.ENBorderSection border = new ENWarpInventory.ENBorderSection();
        public ENWarpInventory.ENDecorationItemsSection decorationItems =
            new ENWarpInventory.ENDecorationItemsSection();

        @Getter
        @Contextual
        public static class ENBorderSection implements BorderSection {

            @Description({" ",
                          "# Changes of border section may affect the appearance of the GUI inventory, after changes adjust slots of existing items."})
            public boolean enabled = true;

            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            public FillType fillType = FillType.BORDER;

            public String name = "";

            public List<String> lore = Collections.emptyList();
        }

        @Getter
        @Contextual
        public static class ENDecorationItemsSection implements DecorationItemsSection {
            public List<ConfigItem> items = List.of();
        }
    }
}
