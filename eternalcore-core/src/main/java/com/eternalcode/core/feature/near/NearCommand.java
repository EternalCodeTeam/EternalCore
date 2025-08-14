package com.eternalcode.core.feature.near;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
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
@Permission("eternalcore.feature.near")
public class NearCommand {

    private final NoticeService noticeService;
    private final MinecraftScheduler minecraftScheduler;
    private static final int DEFAULT_RADIUS = 100;
    private static final long GLOW_TIME = 5L;
    private static final EntityScope DEFAULT_ENTITY_SCOPE = EntityScope.PLAYER;

    @Inject
    public NearCommand(NoticeService noticeService, MinecraftScheduler minecraftScheduler) {
        this.minecraftScheduler = minecraftScheduler;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows all players to the command sender.")
    void showEntites(@Context Player player) {
        handleShowEntities(player, DEFAULT_RADIUS, DEFAULT_ENTITY_SCOPE);
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

        handleShowEntities(sender, actualRadius, entityScope);
    }

    private void handleShowEntities(Player sender, int radius, EntityScope entityScope) {

        List<Entity> nearbyEntities = sender.getNearbyEntities(radius, radius, radius).stream()
            .filter(entity -> !(entity.getUniqueId().equals(sender.getUniqueId())))
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
                () -> {
                    finalNearbyEntities.forEach(entity -> entity.setGlowing(false));},
                Duration.ofSeconds(GLOW_TIME)
            );
        }

        Map<EntityType, Long> entityTypeCount = nearbyEntities.stream()
            .collect(Collectors.groupingBy(
                Entity::getType,
                Collectors.counting()
            ));

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{ENTITYAMOUNT}", String.valueOf(nearbyEntities.size()))
            .placeholder("{RADIUS}", String.valueOf(radius))
            .placeholder("{ENTITYLIST}", buildEntityList(entityTypeCount))
            .notice(translation -> translation.near().entitiesShown())
            .send();

    }

    private String buildEntityList(Map<EntityType, Long> entityTypeCount) {
        StringBuilder entityList = new StringBuilder().append("<br>");
        entityTypeCount.entrySet().stream()
            .sorted(Map.Entry.<EntityType, Long>comparingByValue().reversed())
            .forEach(entry -> {
                EntityType type = entry.getKey();
                Long count = entry.getValue();
                entityList.append("<gray>  - <white>");
                if (count < 10) {
                    entityList.append(" ").append(count);
                } else {
                    entityList.append(count);
                }
                entityList.append(" ").append(
                    Arrays.stream(type.name().toLowerCase().split("_"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "))
                );
                entityList.append("<br>");
            });
        return entityList.toString();
    }

}
