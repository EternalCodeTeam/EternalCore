package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentStatus;
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

    @Comment("# Title of player punishment GUI. {PLAYER}, {PAGE}, {FILTER} available.")
    public String playerTitle = "<dark_gray>Punishments of <red>{PLAYER} <dark_gray>» {FILTER} <dark_gray>(#{PAGE})";

    @Comment("# Title of recent punishments GUI. {PAGE}, {FILTER} available.")
    public String recentTitle = "<dark_gray>Recent punishments » {FILTER} <dark_gray>(#{PAGE})";

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
        "<gray>Date: <white>{DATE} <dark_gray>({RELATIVE_TIME} ago)"
    );

    @Comment("# Extra lore line appended for punishments that can expire (not for kicks). Same placeholders as entryName.")
    public List<String> entryExpiresLore = List.of(
        "<gray>Expires: <white>{EXPIRES}"
    );

    @Comment("# Extra lore lines appended only when the punishment was revoked. Same placeholders as entryName.")
    public List<String> entryRevokedLore = List.of(
        " ",
        "<gray>Revoked by: <white>{REVOKED_BY}",
        "<gray>Revoked at: <white>{REVOKED_AT}"
    );

    @Comment("# Material of entry item per punishment type (BAN, BAN_IP, MUTE, WARN, KICK).")
    public Map<PunishmentHistoryFilter, Material> typeMaterials = Map.of(
        PunishmentHistoryFilter.BAN, Material.BARRIER,
        PunishmentHistoryFilter.BAN_IP, Material.IRON_BARS,
        PunishmentHistoryFilter.MUTE, Material.BOOK,
        PunishmentHistoryFilter.WARN, Material.YELLOW_DYE,
        PunishmentHistoryFilter.KICK, Material.LEATHER_BOOTS
    );

    @Comment("# Should entry items of still active punishments have an enchantment glint?")
    public boolean glowActive = true;

    @Comment("# Material used when a type has no material configured.")
    public Material defaultMaterial = Material.PAPER;

    @Comment("# Label shown as {STATUS}.")
    public Map<PunishmentStatus, String> statusLabels = Map.of(
        PunishmentStatus.ACTIVE, "<green>Active",
        PunishmentStatus.EXPIRED, "<gray>Expired",
        PunishmentStatus.REVOKED, "<yellow>Revoked",
        PunishmentStatus.INSTANT, "<gray>Kick"
    );

    @Comment("# Label shown for empty values.")
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

    @Comment({ " ", "# Filter button - each click switches to the next filter. {FILTER} - current, {NEXT} - next filter." })
    public Material filterMaterial = Material.HOPPER;
    public String filterName = "<yellow>Filter: <white>{FILTER}";
    public List<String> filterLore = List.of(
        "<gray>Click to show: <white>{NEXT}"
    );

    @Comment("# Filter labels used as {FILTER} and {NEXT}.")
    public Map<PunishmentHistoryFilter, String> filterLabels = Map.of(
        PunishmentHistoryFilter.ALL, "<white>All",
        PunishmentHistoryFilter.BAN, "<red>Bans",
        PunishmentHistoryFilter.BAN_IP, "<dark_red>IP bans",
        PunishmentHistoryFilter.MUTE, "<gold>Mutes",
        PunishmentHistoryFilter.WARN, "<yellow>Warns",
        PunishmentHistoryFilter.KICK, "<gray>Kicks"
    );
}
