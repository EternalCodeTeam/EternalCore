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
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Command(name = "near")
@Permission(NearPermissionConstant.NEAR_PERMISSION)
class NearCommand {

    private static final int DEFAULT_RADIUS = 100;
    private static final Duration GLOW_TIME = Duration.ofSeconds(5);
    private static final EntityScope DEFAULT_ENTITY_SCOPE = EntityScope.PLAYER;

    private final NoticeService noticeService;
    private final Scheduler minecraftScheduler;

    @Inject
    public NearCommand(NoticeService noticeService, Scheduler minecraftScheduler) {
        this.noticeService = noticeService;
        this.minecraftScheduler = minecraftScheduler;
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
    void showEntitiesWithScope(
        @Context Player sender,
        @Arg(EntityScopeArgument.KEY) EntityScope entityScope,
        @Arg Optional<Integer> radius
    ) {
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
        Collection<Entity> nearbyEntities = sender.getWorld().getNearbyEntities(sender.getLocation(), radius, radius, radius, entityScope.filter());

        if (nearbyEntities.isEmpty()) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .placeholder("{RADIUS}", String.valueOf(radius))
                .notice(translation -> translation.near().entitiesFound())
                .send();
            return;
        }

        if (sender.hasPermission(NearPermissionConstant.NEAR_GLOW_PERMISSION)) {
            nearbyEntities.forEach(entity -> entity.setGlowing(true));
            this.minecraftScheduler.runLater(
                () -> nearbyEntities.forEach(entity -> entity.setGlowing(false)),
                GLOW_TIME
            );
        }

        Map<EntityType, Integer> countByEntity = new HashMap<>();
        for (Entity entity : nearbyEntities) {
            EntityType type = entity.getType();
            countByEntity.put(type, countByEntity.getOrDefault(type, 0) + 1);
        }

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{ENTITY_AMOUNT}", String.valueOf(nearbyEntities.size()))
            .placeholder("{RADIUS}", String.valueOf(radius))
            .notice(translation -> translation.near().entitiesFound())
            .send();

        countByEntity.entrySet()
            .stream()
            .sorted(Map.Entry.<EntityType, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                String entityTypeName = formatEntityTypeName(entry.getKey());
                String count = entry.getValue().toString();

                this.noticeService.create()
                    .player(sender.getUniqueId())
                    .placeholder("{ENTITY_TYPE}", entityTypeName)
                    .placeholder("{COUNT}", count)
                    .notice(translation -> translation.near().entityEntry())
                    .send();
            });
    }

    private String formatEntityTypeName(EntityType type) {
        return Arrays.stream(type.name().toLowerCase().split("_"))
            .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
            .collect(java.util.stream.Collectors.joining(" "));
    }
}
