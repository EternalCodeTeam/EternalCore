package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.util.date.DateFormatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Material;

import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

final class PunishmentHistoryItemFactory {

    private PunishmentHistoryItemFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static Item create(PunishmentHistoryEntry entry, DateFormatter dateFormatter, MiniMessage miniMessage, PunishmentSettings punishmentSettings) {
        Objects.requireNonNull(entry, "entry cannot be null");
        Objects.requireNonNull(dateFormatter, "dateFormatter cannot be null");
        Objects.requireNonNull(punishmentSettings, "punishmentSettings cannot be null");

        int updateInterval = punishmentSettings.historyGuiEntryUpdateIntervalTicks();

        if (updateInterval <= 0) {
            return Item.simple(render(entry, dateFormatter, miniMessage, punishmentSettings));
        }

        return Item.builder()
            .setItemProvider(player -> render(entry, dateFormatter, miniMessage, punishmentSettings))
            .updatePeriodically(updateInterval)
            .build();
    }

    private static ItemBuilder render(PunishmentHistoryEntry entry, DateFormatter dateFormatter, MiniMessage miniMessage, PunishmentSettings punishmentSettings) {
        String action = entry.action().name();
        String player = entry.target().name();
        String operator = entry.operator().name();
        String reason = entry.reason();
        String date = dateFormatter.format(entry.timestamp());
        String relativeTime = DurationUtil.format(Duration.between(entry.timestamp(), Instant.now()), true);
        String expires = entry.expiresAt()
            .map(expiresAt -> DurationUtil.format(Duration.between(entry.timestamp(), expiresAt), true))
            .orElse(punishmentSettings.permanentLabel());

        String nameTemplate = punishmentSettings.historyGuiEntryName()
            .replace("{ACTION}", action)
            .replace("{PLAYER}", player)
            .replace("{OPERATOR}", operator)
            .replace("{REASON}", reason)
            .replace("{DATE}", date)
            .replace("{EXPIRES}", expires)
            .replace("{RELATIVE_TIME}", relativeTime);

        Component name = miniMessage.deserialize(nameTemplate);

        List<Component> lore = punishmentSettings.historyGuiEntryLore().stream()
            .map(line -> line
                .replace("{ACTION}", action)
                .replace("{PLAYER}", player)
                .replace("{OPERATOR}", operator)
                .replace("{REASON}", reason)
                .replace("{DATE}", date)
                .replace("{RELATIVE_TIME}", relativeTime))
            .map(MiniMessage.miniMessage()::deserialize)
            .toList();

        return new ItemBuilder(materialFor(entry, punishmentSettings))
            .setName(name)
            .setLore(lore);
    }

    private static Material materialFor(PunishmentHistoryEntry entry, PunishmentSettings punishmentSettings) {
        Material configured = punishmentSettings.historyGuiEntryMaterials().get(entry.action().name());

        if (configured != null) {
            return configured;
        }

        return punishmentSettings.historyGuiEntryDefaultMaterial();
    }
}
