package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.ExpirablePunishments;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.Revocation;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
class PunishmentHistoryItemFactory {

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
            gui.typeMaterials().getOrDefault(PunishmentHistoryFilter.of(punishment), gui.defaultMaterial()),
            this.renderLine(gui.entryName(), placeholders),
            this.entryLore(punishment, placeholders, now),
            gui.glowActive() && PunishmentStatus.of(punishment, now) == PunishmentStatus.ACTIVE
        );
    }

    private List<Component> entryLore(Punishment punishment, Map<String, String> placeholders, Instant now) {
        PunishmentGuiSettings gui = this.gui();
        List<Component> lore = new ArrayList<>(this.renderLines(gui.entryLore(), placeholders));

        if (PunishmentStatus.of(punishment, now) != PunishmentStatus.INSTANT) {
            lore.addAll(this.renderLines(gui.entryExpiresLore(), placeholders));
        }

        if (this.revocation(punishment).isPresent()) {
            lore.addAll(this.renderLines(gui.entryRevokedLore(), placeholders));
        }

        return List.copyOf(lore);
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

    MenuItem filter(PunishmentHistoryFilter current, Consumer<Player> onClick) {
        PunishmentGuiSettings gui = this.gui();
        Map<String, String> placeholders = Map.of(
            "{FILTER}", this.filterLabel(current),
            "{NEXT}", this.filterLabel(current.next())
        );

        return MenuItem.clickable(
            gui.filterMaterial(),
            this.renderLine(gui.filterName(), placeholders),
            this.renderLines(gui.filterLore(), placeholders),
            onClick
        );
    }

    String filterLabel(PunishmentHistoryFilter filter) {
        return this.gui().filterLabels().getOrDefault(filter, filter.name());
    }

    private Map<String, String> placeholders(Punishment punishment, Instant now) {
        PunishmentStatus status = PunishmentStatus.of(punishment, now);

        return Map.ofEntries(
            Map.entry("{TYPE}", PunishmentHistoryFilter.of(punishment).name()),
            Map.entry("{STATUS}", this.gui().statusLabels().getOrDefault(status, status.name())),
            Map.entry("{PLAYER}", punishment.target().name()),
            Map.entry("{OPERATOR}", punishment.operator().name()),
            Map.entry("{REASON}", punishment.reason()),
            Map.entry("{DATE}", this.dateFormatter.format(punishment.createdAt())),
            Map.entry(
                "{RELATIVE_TIME}",
                DurationUtil.format(Duration.between(punishment.createdAt(), now), true)),
            Map.entry(
                "{EXPIRES}", ExpirablePunishments.find(punishment)
                    .flatMap(ExpirablePunishment::expiresAtOptional)
                    .map(this.dateFormatter::format)
                    .orElse(this.punishmentSettings.permanentLabel())),
            Map.entry(
                "{REVOKED_BY}", this.revocation(punishment)
                    .map(revocation -> revocation.revokedBy().name())
                    .orElse(this.gui().noneLabel())),
            Map.entry(
                "{REVOKED_AT}", this.revocation(punishment)
                    .map(revocation -> this.dateFormatter.format(revocation.revokedAt()))
                    .orElse(this.gui().noneLabel()))
        );
    }

    private Optional<Revocation> revocation(Punishment punishment) {
        return ExpirablePunishments.find(punishment).flatMap(ExpirablePunishment::revocationOptional);
    }

    private MenuItem simple(Material material, String name) {
        return MenuItem.display(material, this.renderLine(name, Map.of()), List.of());
    }

    private MenuItem navigation(Material material, String name, Consumer<Player> onClick) {
        return MenuItem.clickable(material, this.renderLine(name, Map.of()), List.of(), onClick);
    }

    private Component renderLine(String template, Map<String, String> placeholders) {
        return this.renderLines(List.of(template), placeholders).get(0);
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
