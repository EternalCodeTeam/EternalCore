package com.eternalcode.core.feature.near;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Command(name = "near")
@Permission(NearPermissionConstant.NEAR_PERMISSION)
public class NearCommand {

    private final NoticeService noticeService;
    private final Scheduler minecraftScheduler;
    private final NearSettings nearSettings;

    private static final int DEFAULT_RADIUS = 100;
    private static final Duration GLOW_TIME = Duration.ofSeconds(5);
    private static final EntityScope DEFAULT_ENTITY_SCOPE = EntityScope.PLAYER;

    @Inject
    public NearCommand(NoticeService noticeService, Scheduler minecraftScheduler, NearSettings nearSettings) {
        this.noticeService = noticeService;
        this.minecraftScheduler = minecraftScheduler;
        this.nearSettings = nearSettings;
    }

    @Execute
    @DescriptionDocs(description = "Shows all players to the command sender.")
    void showEntities(@Context Player player) {
        this.handleShowEntities(player, DEFAULT_RADIUS, DEFAULT_ENTITY_SCOPE);
    }

    @Execute
    @DescriptionDocs(
        description = "Shows all entities of the specified entity scope within the specified radius to the command sender",
        arguments = {"<entityScope> [radius]"}
    )
    void showEntitiesWithScope(@Context Player sender, @Arg EntityScope entityScope, @Arg Optional<Integer> radius) {
        int actualRadius = radius.orElse(DEFAULT_RADIUS);

        if (actualRadius <= 0) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.argument().numberBiggerThanZero())
                .send();
            return;
        }

        this.handleShowEntities(sender, actualRadius, entityScope);
    }

    private void handleShowEntities(Player sender, int radius, EntityScope entityScope) {

        List<Entity> nearbyEntities = sender.getNearbyEntities(radius, radius, radius).stream()
            .filter(entity -> !entity.getUniqueId().equals(sender.getUniqueId()))
            .toList();

        nearbyEntities = entityScope.filterEntities(nearbyEntities);

        if (nearbyEntities.isEmpty()) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .placeholder("{RADIUS}", String.valueOf(radius))
                .notice(translation -> translation.near().noEntitiesFound())
                .send();
            return;
        }

        if (sender.hasPermission(NearPermissionConstant.NEAR_GLOW_PERMISSION)) {
            nearbyEntities.forEach(entity -> entity.setGlowing(true));
            final List<Entity> finalNearbyEntities = nearbyEntities;
            this.minecraftScheduler.runLater(
                () -> finalNearbyEntities.forEach(entity -> entity.setGlowing(false)),
                GLOW_TIME
            );
        }

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{ENTITYAMOUNT}", String.valueOf(nearbyEntities.size()))
            .placeholder("{RADIUS}", String.valueOf(radius))
            .placeholder("{ENTITYLIST}", new EntityListFormatter(nearbyEntities, this.nearSettings).format())
            .notice(translation -> translation.near().entitiesShown())
            .send();

    }

    private static class EntityListFormatter {

        private final String BULLETPOINT_STYLE;
        private final String BULLETPOINT_SYMBOL;
        private final String LIST_ITEM_STYLE;

        private final Map<EntityType, Long> entityTypeCount;

        public EntityListFormatter(List<Entity> entities, NearSettings nearSettings) {
            this.BULLETPOINT_STYLE = nearSettings.bulletPointStyle();
            this.BULLETPOINT_SYMBOL = nearSettings.bulletPointSymbol();
            this.LIST_ITEM_STYLE = nearSettings.listItemStyle();

            this.entityTypeCount = entities.stream()
                .collect(Collectors.groupingBy(
                    Entity::getType,
                    Collectors.counting()
                ));
        }

        public String format() {
            StringBuilder entityList = new StringBuilder().append("<br>");
            this.entityTypeCount.entrySet().stream()
                .sorted(Map.Entry.<EntityType, Long>comparingByValue().reversed())
                .forEach(entry -> entityList.append(formatEntityLine(entry.getKey(), entry.getValue())));
            return entityList.toString();
        }

        private String formatEntityLine(EntityType type, Long count) {
            String countString = count < 10 ? " " + count : count.toString();
            String typeName = formatEntityTypeName(type);
            return BULLETPOINT_STYLE + "  " + BULLETPOINT_SYMBOL + " " + LIST_ITEM_STYLE + countString + " " + typeName + "<br>";
        }

        private String formatEntityTypeName(EntityType type) {
            return Arrays.stream(type.name().toLowerCase().split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
        }

    }

}
