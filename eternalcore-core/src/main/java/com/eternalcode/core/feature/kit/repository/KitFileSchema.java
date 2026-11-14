package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import com.eternalcode.core.feature.kit.Kit;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * Maps a {@link Kit} to/from a kit yml file. Items use Bukkit's native ItemStack serialization,
 * so kit files stay human readable and can be edited by hand.
 */
final class KitFileSchema {

    static final String FILE_EXTENSION = ".yml";

    private static final String DISPLAY_NAME = "display-name";
    private static final String COOLDOWN = "cooldown";
    private static final String PERMISSION = "permission";
    private static final String SLOT = "slot";
    private static final String ICON = "icon";
    private static final String ITEMS = "items";
    private static final String COMMANDS = "commands";

    private static final List<String> HEADER = List.of(
        "Kit definition - file name is the kit name.",
        "cooldown: e.g. 30m, 1h30m, 1d (0s = no cooldown)",
        "commands: executed by console, {PLAYER} = player name",
        "Reload with /eternalcore reload"
    );

    private static final String NO_COOLDOWN = "0s";

    private static final TemporalAmountParser<Duration> DURATION_PARSER = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS);

    private KitFileSchema() {
    }

    static YamlConfiguration write(Kit kit) {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.options().setHeader(HEADER);

        yaml.set(DISPLAY_NAME, kit.displayName());
        yaml.set(COOLDOWN, formatCooldown(kit.cooldown()));
        yaml.set(PERMISSION, kit.permission());
        yaml.set(SLOT, kit.slot());
        yaml.set(ICON, kit.icon());
        yaml.set(ITEMS, kit.items());
        yaml.set(COMMANDS, kit.commands());

        return yaml;
    }

    static Kit read(String kitName, YamlConfiguration yaml) {
        String permission = yaml.getString(PERMISSION);
        String cooldown = yaml.getString(COOLDOWN);
        ItemStack icon = yaml.getItemStack(ICON);

        if (permission == null || cooldown == null || icon == null) {
            throw new IllegalStateException("Kit '" + kitName + "' is missing one of required keys: "
                + PERMISSION + ", " + COOLDOWN + ", " + ICON);
        }

        return Kit.builder(kitName)
            .displayName(yaml.getString(DISPLAY_NAME, kitName))
            .cooldown(parseCooldown(cooldown))
            .permission(permission)
            .slot(yaml.getInt(SLOT, Kit.MIN_SLOT))
            .icon(icon)
            .items(readItems(yaml))
            .commands(yaml.getStringList(COMMANDS))
            .build();
    }

    private static String formatCooldown(Duration cooldown) {
        return cooldown.isZero() ? NO_COOLDOWN : DURATION_PARSER.format(cooldown);
    }

    private static Duration parseCooldown(String cooldown) {
        return NO_COOLDOWN.equals(cooldown.trim()) ? Duration.ZERO : DURATION_PARSER.parse(cooldown.trim());
    }

    private static List<ItemStack> readItems(YamlConfiguration yaml) {
        List<?> rawItems = yaml.getList(ITEMS, List.of());

        return rawItems.stream()
            .filter(ItemStack.class::isInstance)
            .map(ItemStack.class::cast)
            .toList();
    }
}
