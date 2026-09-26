package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.PunishmentType;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;

public interface PunishmentGuiSettings {

    String playerTitle();

    String recentTitle();

    int contentRows();

    int pagesPerFetch();

    String entryName();

    List<String> entryLore();

    Map<PunishmentType, Material> typeMaterials();

    Material defaultMaterial();

    Map<PunishmentStatus, String> statusLabels();

    String noneLabel();

    Material borderMaterial();

    String borderName();

    Material previousPageMaterial();

    String previousPageName();

    Material nextPageMaterial();

    String nextPageName();

    Material emptyMaterial();

    String emptyName();

    Material filterMaterial();

    String filterName();

    List<String> filterLore();

    Map<PunishmentHistoryFilter, String> filterLabels();
}
