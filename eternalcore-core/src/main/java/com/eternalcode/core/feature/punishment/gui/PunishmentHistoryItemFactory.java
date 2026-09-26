package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.util.date.DateFormatter;

import net.kyori.adventure.text.Component;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
class PunishmentHistoryItemFactory {

    private static final String TYPE = "{TYPE}";
    private static final String STATUS = "{STATUS}";
    private static final String PLAYER = "{PLAYER}";
    private static final String OPERATOR = "{OPERATOR}";
    private static final String REASON = "{REASON}";
    private static final String DATE = "{DATE}";
    private static final String RELATIVE_TIME = "{RELATIVE_TIME}";
    private static final String EXPIRES = "{EXPIRES}";
    private static final String REVOKED_BY = "{REVOKED_BY}";
    private static final String REVOKED_AT = "{REVOKED_AT}";

    private static final boolean REMOVE_MILLIS = true;
    private static final int SINGLE_LINE_INDEX = 0;

    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateMessageRenderer;
    private final DateFormatter dateFormatter;

    @Inject
    PunishmentHistoryItemFactory(
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateMessageRenderer,
        DateFormatter dateFormatter
    ) {
        this.punishmentSettings = punishmentSettings;
        this.templateMessageRenderer = templateMessageRenderer;
        this.dateFormatter = dateFormatter;
    }

    MenuItem punishment(Punishment punishment, Instant now) {
        PunishmentGuiSettings gui = this.gui();
        Map<String, String> placeholders = this.placeholders(punishment, now);

        return MenuItem.display(
            gui.typeMaterials().getOrDefault(punishment.type(), gui.defaultMaterial()),
            this.renderLine(gui.entryName(), placeholders),
            this.renderLines(gui.entryLore(), placeholders)
        );
    }

    MenuItem border() {
        return this.simple(this.gui().borderMaterial(), this.gui().borderName());
    }

    MenuItem empty() {
        return this.simple(this.gui().emptyMaterial(), this.gui().emptyName());
    }

    MenuItem previousPage(Consumer<Player> onClick) {
        return this.navigation(this.gui().previousPageMaterial(), this.gui().previousPageName(), onClick);
    }

    MenuItem nextPage(Consumer<Player> onClick) {
        return this.navigation(this.gui().nextPageMaterial(), this.gui().nextPageName(), onClick);
    }

    private Map<String, String> placeholders(Punishment punishment, Instant now) {
        PunishmentStatus status = punishment.status(now);

        return Map.ofEntries(
            Map.entry(TYPE, punishment.type().name()),
            Map.entry(STATUS, this.gui().statusLabels().getOrDefault(status, status.name())),
            Map.entry(PLAYER, punishment.target().name()),
            Map.entry(OPERATOR, punishment.operator().name()),
            Map.entry(REASON, punishment.reason()),
            Map.entry(DATE, this.dateFormatter.format(punishment.createdAt())),
            Map.entry(RELATIVE_TIME, DurationUtil.format(Duration.between(punishment.createdAt(), now), REMOVE_MILLIS)),
            Map.entry(EXPIRES, punishment.expiresAtOptional()
                .map(this.dateFormatter::format)
                .orElse(this.punishmentSettings.permanentLabel())),
            Map.entry(REVOKED_BY, punishment.revokedByOptional()
                .map(PunishmentTarget::name)
                .orElse(this.gui().noneLabel())),
            Map.entry(REVOKED_AT, punishment.revokedAtOptional()
                .map(this.dateFormatter::format)
                .orElse(this.gui().noneLabel()))
        );
    }

    private MenuItem simple(Material material, String name) {
        return MenuItem.display(material, this.renderLine(name, Map.of()), List.of());
    }

    private MenuItem navigation(Material material, String name, Consumer<Player> onClick) {
        return MenuItem.clickable(material, this.renderLine(name, Map.of()), List.of(), onClick);
    }

    private Component renderLine(String template, Map<String, String> placeholders) {
        return this.renderLines(List.of(template), placeholders).get(SINGLE_LINE_INDEX);
    }

    private List<Component> renderLines(List<String> template, Map<String, String> placeholders) {
        return this.templateMessageRenderer.render(template, placeholders).stream()
            .map(AdventureUtil::resetItalic)
            .toList();
    }

    private PunishmentGuiSettings gui() {
        return this.punishmentSettings.gui();
    }
}
