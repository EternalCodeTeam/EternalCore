package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitItemSerializer;
import com.eternalcode.core.feature.kit.KitRequirements;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

@Service
class KitFileSchema {

    static final String FILE_EXTENSION = ".yml";

    private static final String DISPLAY_NAME = "display-name";
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "type";
    private static final String COOLDOWN = "cooldown";
    private static final String PERMISSION = "permission";
    private static final String REQUIREMENTS = "requirements";
    private static final String WORLDS = "worlds";
    private static final String GAME_MODES = "game-modes";
    private static final String MINIMUM_PLAYTIME = "minimum-playtime";
    private static final String SLOT = "slot";
    private static final String ICON = "icon";
    private static final String ITEMS = "items";
    private static final String COMMANDS = "commands";

    private final KitItemSerializer itemSerializer;

    @Inject
    KitFileSchema(KitItemSerializer itemSerializer) {
        this.itemSerializer = itemSerializer;
    }

    YamlConfiguration write(Kit kit) {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.options().setHeader(List.of(
            "Kit definition - the file name is the kit name.",
            "type: RECURRING (cooldown), ONE_TIME, FIRST_JOIN (given on the first join)",
            "cooldown / minimum-playtime: e.g. 30m, 1h30m, 1d (0s = none), cooldown is used only by RECURRING kits",
            "requirements: empty worlds / game-modes = any",
            "commands: executed by console, {PLAYER} = player name",
            "icon and items are stored in a version-safe binary format - change them only with /kitadmin icon and /kitadmin items",
            "Reload with /eternalcore reload"
        ));

        KitRequirements requirements = kit.requirements();

        yaml.set(DISPLAY_NAME, kit.displayName());
        yaml.set(DESCRIPTION, kit.description());
        yaml.set(TYPE, kit.type().name());
        yaml.set(COOLDOWN, DurationUtil.format(kit.cooldown(), true));
        yaml.set(PERMISSION, kit.permission());
        yaml.set(REQUIREMENTS + "." + WORLDS, requirements.worlds().stream().sorted().toList());
        yaml.set(REQUIREMENTS + "." + GAME_MODES, requirements.gameModes().stream().map(GameMode::name).sorted().toList());
        yaml.set(REQUIREMENTS + "." + MINIMUM_PLAYTIME, DurationUtil.format(requirements.minimumPlaytime(), true));
        yaml.set(SLOT, kit.slot());
        yaml.set(COMMANDS, kit.commands());
        yaml.set(ICON, this.itemSerializer.serializeItem(kit.icon()));
        yaml.set(ITEMS, kit.items().stream().map(this.itemSerializer::serializeItem).toList());

        return yaml;
    }

    Kit read(String kitName, YamlConfiguration yaml) {
        String permission = yaml.getString(PERMISSION);
        String icon = yaml.getString(ICON);

        if (permission == null || icon == null) {
            throw new IllegalStateException("Kit '" + kitName + "' is missing one of required keys: " + PERMISSION + ", " + ICON);
        }

        return Kit.builder(kitName)
            .displayName(yaml.getString(DISPLAY_NAME, kitName))
            .description(yaml.getStringList(DESCRIPTION))
            .type(parseType(kitName, yaml.getString(TYPE, KitType.RECURRING.name())))
            .cooldown(parseDuration(yaml.getString(COOLDOWN)))
            .permission(permission)
            .requirements(readRequirements(kitName, yaml.getConfigurationSection(REQUIREMENTS)))
            .slot(yaml.getInt(SLOT, Kit.MIN_SLOT))
            .icon(this.itemSerializer.deserializeItem(icon))
            .items(this.readItems(yaml))
            .commands(yaml.getStringList(COMMANDS))
            .build();
    }

    private List<ItemStack> readItems(YamlConfiguration yaml) {
        return yaml.getStringList(ITEMS).stream()
            .map(this.itemSerializer::deserializeItem)
            .toList();
    }

    private static KitRequirements readRequirements(String kitName, ConfigurationSection section) {
        if (section == null) {
            return KitRequirements.none();
        }

        List<GameMode> gameModes = section.getStringList(GAME_MODES).stream()
            .map(gameMode -> parseGameMode(kitName, gameMode))
            .toList();

        return KitRequirements.of(
            section.getStringList(WORLDS),
            gameModes,
            parseDuration(section.getString(MINIMUM_PLAYTIME))
        );
    }

    private static KitType parseType(String kitName, String type) {
        try {
            return KitType.valueOf(type.trim().toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException exception) {
            throw new IllegalStateException("Kit '" + kitName + "' has unknown type '" + type + "'", exception);
        }
    }

    private static GameMode parseGameMode(String kitName, String gameMode) {
        try {
            return GameMode.valueOf(gameMode.trim().toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException exception) {
            throw new IllegalStateException("Kit '" + kitName + "' has unknown game mode '" + gameMode + "'", exception);
        }
    }

    private static Duration parseDuration(String duration) {
        return duration == null ? Duration.ZERO : DurationUtil.parse(duration);
    }
}
