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
public class PLWarpMessages extends OkaeriConfig implements WarpMessages {
    @Comment("# {WARP} - Nazwa warpu")
    Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
    Notice create = Notice.chat("<green>► <white>Stworzono warp <green>{WARP}<white>!");
    Notice remove = Notice.chat("<red>► <white>Usunięto warp <red>{WARP}<white>!");
    Notice notExist = Notice.chat("<red>► <dark_red>Nie odnaleziono takiego warpu!");
    Notice itemAdded = Notice.chat("<green>► <white>Dodano warp do GUI!");
    Notice noWarps = Notice.chat("<red>✘ <dark_red>Nie ma dostępnych warpów!");
    Notice itemLimit = Notice.chat("<red>✘ <red>Osiągnąłeś limit warpów w GUI! Limit to: {LIMIT}!");
    Notice noPermission =
        Notice.chat("<red>✘ <red>Nie masz uprawnień do skorzystania z tego warpa <dark_red>{WARP}<red>!");
    Notice addPermissions = Notice.chat("<green>► <white>Dodano uprawnienia do warpa <green>{WARP}<white>!");
    Notice removePermission =
        Notice.chat("<red>► <white>Usunięto uprawnienie <red>{PERMISSION}</red> z warpa <red>{WARP}</red>!");
    Notice noPermissionsProvided = Notice.chat("<red>✘ <red>Nie podano żadnych uprawnień!");
    Notice permissionDoesNotExist = Notice.chat("<red>✘ <red>Podane uprawnienie nie istnieje ({PERMISSION})!");
    Notice permissionAlreadyExist = Notice.chat("<red>✘ <red>Podane uprawnienie już istnieje ({PERMISSION})!");
    Notice noPermissionAssigned = Notice.chat("<red>✘ <red>Ten warp nie ma przypisanych żadnych permisji");
    Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę warpu!");
    Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>Musisz podać uprawnienie!");

    @Comment({" ", "# {WARPS} - Lista dostępnych warpów"})
    Notice available = Notice.chat("<green>► <white>Dostepne warpy: <green>{WARPS}!");

    @Comment({" ", "# Ustawienia gui listy dostępnych warpów"})
    public PLWarpInventory warpInventory = new PLWarpInventory();

    @Getter
    @Accessors(fluent = true)
    public static class PLWarpInventory extends OkaeriConfig implements WarpInventorySection {
        public String title = "<dark_gray>» <green>Lista dostępnych warpów";

        @Comment({
            " ",
            "# Poniższa lista określa przedmioty w GUI, które są wyświetlane w liście dostępnych warpów.",
            "# Możesz edytować przedmioty, a dodawanie kolejnych warpów następuje automatycznie za pomocą komendy /setwarp",
        })
        public Map<String, WarpInventoryItem> items = new HashMap<>();

        public void setItems(Map<String, WarpInventoryItem> items) {
            this.items = items;
        }

        public PLWarpInventory.PLBorderSection border = new PLWarpInventory.PLBorderSection();
        public PLWarpInventory.PLDecorationItemsSection decorationItems =
            new PLWarpInventory.PLDecorationItemsSection();

        @Getter
        public static class PLBorderSection extends OkaeriConfig implements BorderSection {
            @Comment({" ",
                          "# Zmiany w tej sekcji mogą wpłynąć na wygląd GUI, zwróć uwagę na zmiany slotów przedmiotów w GUI."})
            public boolean enabled = true;

            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            public FillType fillType = FillType.BORDER;

            public String name = "";

            public List<String> lore = Collections.emptyList();
        }

        @Getter
        public static class PLDecorationItemsSection extends OkaeriConfig implements DecorationItemsSection {
            public List<ConfigItem> items = List.of();
        }
    }
}
