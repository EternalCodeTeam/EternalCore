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
public class PLWarpMessages implements WarpMessages {
    @Description("# {WARP} - Nazwa warpu")
    public Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
    public Notice create = Notice.chat("<green>► <white>Stworzono warp <green>{WARP}<white>!");
    public Notice remove = Notice.chat("<red>► <white>Usunięto warp <red>{WARP}<white>!");
    public Notice notExist = Notice.chat("<red>► <dark_red>Nie odnaleziono takiego warpu!");
    public Notice itemAdded = Notice.chat("<green>► <white>Dodano warp do GUI!");
    public Notice noWarps = Notice.chat("<red>✘ <dark_red>Nie ma dostępnych warpów!");
    public Notice itemLimit = Notice.chat("<red>✘ <red>Osiągnąłeś limit warpów w GUI! Limit to: {LIMIT}!");
    public Notice noPermission =
        Notice.chat("<red>✘ <red>Nie masz uprawnień do skorzystania z tego warpa <dark_red>{WARP}<red>!");
    public Notice addPermissions = Notice.chat("<green>► <white>Dodano uprawnienia do warpa <green>{WARP}<white>!");
    public Notice removePermission =
        Notice.chat("<red>► <white>Usunięto uprawnienie <red>{PERMISSION}</red> z warpa <red>{WARP}</red>!");
    public Notice noPermissionsProvided = Notice.chat("<red>✘ <red>Nie podano żadnych uprawnień!");
    public Notice permissionDoesNotExist = Notice.chat("<red>✘ <red>Podane uprawnienie nie istnieje ({PERMISSION})!");
    public Notice permissionAlreadyExist = Notice.chat("<red>✘ <red>Podane uprawnienie już istnieje ({PERMISSION})!");
    public Notice noPermissionAssigned = Notice.chat("<red>✘ <red>Ten warp nie ma przypisanych żadnych permisji");
    public Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę warpu!");
    public Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>Musisz podać uprawnienie!");

    @Description({" ", "# {WARPS} - Lista dostępnych warpów"})
    public Notice available = Notice.chat("<green>► <white>Dostepne warpy: <green>{WARPS}!");

    @Description({" ", 
                 "# PRZESTARZAŁE: Te ustawienia zostały przeniesione do pliku warps.yml",
                 "# Ustawienia gui listy dostępnych warpów"})
    @Deprecated
    public PLWarpInventory warpInventory = new PLWarpInventory();

    @Getter
    @Accessors(fluent = true)
    @Contextual
    @Deprecated
    public static class PLWarpInventory implements WarpInventorySection {
        public String title = "<dark_gray>» <green>Lista dostępnych warpów";

        @Description({
            " ",
            "# PRZESTARZAŁE: Te ustawienia zostały przeniesione do pliku warps.yml",
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
        @Contextual
        @Deprecated
        public static class PLBorderSection implements BorderSection {
            @Description({" ",
                          "# PRZESTARZAŁE: Te ustawienia zostały przeniesione do pliku warps.yml",
                          "# Zmiany w tej sekcji mogą wpłynąć na wygląd GUI, zwróć uwagę na zmiany slotów przedmiotów w GUI."})
            public boolean enabled = true;

            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            public FillType fillType = FillType.BORDER;

            public String name = "";

            public List<String> lore = Collections.emptyList();
        }

        @Getter
        @Contextual
        @Deprecated
        public static class PLDecorationItemsSection implements DecorationItemsSection {
            public List<ConfigItem> items = List.of();
        }
    }
}
