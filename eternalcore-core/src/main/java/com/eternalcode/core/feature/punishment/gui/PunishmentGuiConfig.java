package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.PunishmentType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class PunishmentGuiConfig extends OkaeriConfig implements PunishmentGuiSettings {

    @Comment("# Title of player punishment GUI. {PLAYER}, {PAGE} available.")
    public String playerTitle = "<dark_gray>Punishments of <red>{PLAYER} <dark_gray>(#{PAGE})";

    @Comment("# Title of recent punishments GUI. {PAGE} available.")
    public String recentTitle = "<dark_gray>Recent punishments <dark_gray>(#{PAGE})";

    @Comment("# Number of content rows (1-4). Top and bottom rows are reserved for border and navigation.")
    public int contentRows = 4;

    @Comment("# How many GUI pages are fetched from the database at once.")
    public int pagesPerFetch = 3;

    @Comment({ " ", "# Entry item name. {TYPE}, {STATUS}, {PLAYER}, {OPERATOR}, {REASON}, {DATE}, {RELATIVE_TIME}, {EXPIRES}, {REVOKED_BY}, {REVOKED_AT} available." })
    public String entryName = "<red>{TYPE} <dark_gray>» <white>{STATUS}";

    @Comment("# Entry item lore. Same placeholders as entryName.")
    public List<String> entryLore = List.of(
        "<gray>Player: <white>{PLAYER}",
        "<gray>Operator: <white>{OPERATOR}",
        "<gray>Reason: <white>{REASON}",
        "<gray>Date: <white>{DATE} <dark_gray>({RELATIVE_TIME} ago)",
        "<gray>Expires: <white>{EXPIRES}",
        " ",
        "<gray>Revoked by: <white>{REVOKED_BY}",
        "<gray>Revoked at: <white>{REVOKED_AT}"
    );

    @Comment("# Material of entry item per punishment type.")
    public Map<PunishmentType, Material> typeMaterials = Map.of(
        PunishmentType.BAN, Material.BARRIER,
        PunishmentType.BAN_IP, Material.IRON_BARS,
        PunishmentType.MUTE, Material.BOOK,
        PunishmentType.WARN, Material.YELLOW_DYE,
        PunishmentType.KICK, Material.LEATHER_BOOTS
    );

    @Comment("# Material used when a type has no material configured.")
    public Material defaultMaterial = Material.PAPER;

    @Comment("# Label shown as {STATUS}.")
    public Map<PunishmentStatus, String> statusLabels = Map.of(
        PunishmentStatus.ACTIVE, "<green>Active",
        PunishmentStatus.EXPIRED, "<gray>Expired",
        PunishmentStatus.REVOKED, "<yellow>Revoked",
        PunishmentStatus.INSTANT, "<gray>Kick"
    );

    @Comment("# Label shown for empty values (e.g. {REVOKED_BY} when punishment was not revoked).")
    public String noneLabel = "-";

    @Comment({ " ", "# Border item." })
    public Material borderMaterial = Material.GRAY_STAINED_GLASS_PANE;
    public String borderName = " ";

    @Comment("# Navigation items.")
    public Material previousPageMaterial = Material.ARROW;
    public String previousPageName = "<yellow>« Previous page";
    public Material nextPageMaterial = Material.ARROW;
    public String nextPageName = "<yellow>Next page »";

    @Comment("# Item shown when there are no punishments.")
    public Material emptyMaterial = Material.STRUCTURE_VOID;
    public String emptyName = "<gray>No punishments";
}
