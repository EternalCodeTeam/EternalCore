package com.eternalcode.core.feature.punishment;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PunishmentConfig extends OkaeriConfig implements PunishmentSettings {

    @Comment("# Should muted players be blocked from writing text on signs?")
    public boolean blockUsingSignOnMute = true;

    @Comment("# Should a message be sent when a banned player tries to join the server?")
    public boolean messageWhenBanned = true;

    @Comment("# Message delay when a player is obsessively trying to log in")
    public Duration messageWhenBannedCooldown = Duration.ofSeconds(60);

    @Comment("# Checking length of reason?")
    public boolean reasonLegthEnabled = true;

    @Comment("# Minimum length of a punishment reason")
    public int minReasonLength = 3;

    @Comment("# Maximum length of a punishment reason")
    public int maxReasonLength = 255;

    @Comment("# Label shown instead of an expiration date for permanent punishments")
    public String permanentLabel = "N/A";

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banKickScreen = List.of(
        "<red><bold>You have been banned!!",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}",
        "<white>Expires: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banIpKickScreen = List.of(
        "<red><bold>You have been banned! (IP)",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}",
        "<white>Expires: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get kicked. {OPERATOR}, {REASON} available." })
    public List<String> kickScreen = List.of(
        "<red><bold>You have been kicked!",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}"
    );

    @Comment("# How many punishment history entries are shown per page in the chat-based /history command")
    public int historyPageSize = 10;

    @Comment({ " ", "# Automatic punishment applied when a player reaches a given number of warns." })
    @Comment({ " ", "# Format: \"ACTION:DURATION\" - ACTION is KICK, MUTE or BAN. DURATION is e.g. 1d, 7d, or \"permanent\"." })
    public Map<Integer, String> warnEscalations = Map.of(
        3, "MUTE:1d",
        5, "MUTE:7d",
        10, "BAN:permanent"
    );

    @Comment({ " ", "# How many entries are shown per page in the /history GUI. Must be between 9 and 36 (rounded up to a multiple of 9)." })
    public int historyGuiPageSize = 36;

    @Comment("# How many entries are fetched from the database at once when the GUI needs more (must be <= 100, should be a multiple of historyGuiPageSize)")
    public int historyGuiFetchBatchSize = 90;

    @Comment({ " ", "# How often (in ticks, 20 = 1 second) history entry items refresh their {RELATIVE_TIME} text. Set to 0 to disable live updates." })
    public int historyGuiEntryUpdateIntervalTicks = 20;

    @Comment({ " ", "# Title of the global punishment history GUI window. {PAGE} - current page, {PAGES} - total known pages (shows \"+\" if more might exist)." })
    public String historyGuiTitle = "<dark_gray>Punishments ({PAGE}/{PAGES})";

    @Comment({ " ", "# Title of the per-player punishment history GUI window. {PLAYER}, {PAGE}, {PAGES} available." })
    public String historyGuiPlayerTitle = "<dark_gray>Punishments - <white>{PLAYER} <dark_gray>({PAGE}/{PAGES})";

    @Comment("# Material used for the border/filler slots in the history GUI")
    public Material historyGuiFillerMaterial = Material.BLACK_STAINED_GLASS_PANE;

    @Comment("# Material and name of the \"previous page\" button")
    public Material historyGuiBackArrowMaterial = Material.RED_DYE;

    public String historyGuiBackArrowName = "<gray>Previous page";

    @Comment("# Material and name of the \"next page\" button")
    public Material historyGuiForwardArrowMaterial = Material.LIME_DYE;

    public String historyGuiForwardArrowName = "<gray>Next page";

    @Comment({ " ", "# Name of each history entry item. {ACTION}, {PLAYER}, {OPERATOR}, {REASON}, {DATE} available." })
    public String historyGuiEntryName = "<white>{ACTION} <gray>- <yellow>{PLAYER}";

    @Comment({ " ", "# Lore of each history entry item. {ACTION}, {PLAYER}, {OPERATOR}, {REASON}, {DATE} available." })
    public List<String> historyGuiEntryLore = List.of(
        "<gray>Operator: <white>{OPERATOR}",
        "<gray>Reason: <white>{REASON}",
        "<gray>Date: <white>{DATE} <dark_gray>({RELATIVE_TIME})"
    );

    @Comment({ " ", "# Item material per history action type. Keys must match HistoryAction enum names exactly." })
    public Map<String, Material> historyGuiEntryMaterials = Map.ofEntries(
        Map.entry("BAN", Material.BARRIER),
        Map.entry("UNBAN", Material.LIME_DYE),
        Map.entry("KICK", Material.LEATHER_BOOTS),
        Map.entry("KICK_ALL", Material.LEATHER_BOOTS),
        Map.entry("MUTE", Material.PAPER),
        Map.entry("UNMUTE", Material.MAP),
        Map.entry("WARN", Material.YELLOW_DYE),
        Map.entry("BAN_IP", Material.IRON_BARS),
        Map.entry("EXPIRE", Material.CLOCK)
    );

    @Comment("# Fallback material used when a history action has no entry in historyGuiEntryMaterials")
    public Material historyGuiEntryDefaultMaterial = Material.BOOK;
}
