package com.eternalcode.core.feature.deathmessage.handler;

import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.feature.deathmessage.DeathContext;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@Service
public class EnvironmentDeathMessageHandler {

    private final NoticeService noticeService;

    @Inject
    public EnvironmentDeathMessageHandler(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public void handle(DeathContext context) {
        Player victim = context.victim();

        if (victim == null) {
            return;
        }

        String victimName = victim.getName();
        EntityDamageEvent.DamageCause cause = context.cause();

        if (context.deathType() == DeathContext.DeathType.ENTITY_KILL) {
            this.handleEntityKill(victim, context.damager());
            return;
        }

        if (cause != null) {
            this.handleEnvironmentDeath(victimName, cause);
            return;
        }

        this.handleUnknownDeath(victimName);
    }

    private void handleEntityKill(Player victim, Entity killer) {
        if (killer == null || !(killer instanceof LivingEntity)) {
            this.handleUnknownDeath(victim.getName());
            return;
        }

        String victimName = victim.getName();
        String mobName = this.getMobName(killer);
        EntityType mobType = killer.getType();

        this.noticeService.create()
            .noticeOptional(translation -> {
                List<Notice> mobMessages = translation.deathMessage().deathMessageByMobType().get(mobType);

                if (mobMessages != null && !mobMessages.isEmpty()) {
                    return RandomElementUtil.randomElement(mobMessages);
                }

                List<Notice> genericMobMessages = translation.deathMessage().deathMessageByEntity();
                if (genericMobMessages != null && !genericMobMessages.isEmpty()) {
                    return RandomElementUtil.randomElement(genericMobMessages);
                }

                return RandomElementUtil.randomElement(translation.deathMessage().unknownDeathCause());
            })
            .placeholder("{PLAYER}", victimName)
            .placeholder("{MOB}", mobName)
            .placeholder("{MOB_TYPE}", mobType.name())
            .onlinePlayers()
            .send();
    }

    private void handleEnvironmentDeath(String victimName, EntityDamageEvent.DamageCause cause) {
        this.noticeService.create()
            .noticeOptional(translation -> {
                List<Notice> causeMessages = translation.deathMessage().deathMessageByDamageCause().get(cause);

                if (causeMessages != null && !causeMessages.isEmpty()) {
                    return RandomElementUtil.randomElement(causeMessages);
                }

                return RandomElementUtil.randomElement(translation.deathMessage().unknownDeathCause());
            })
            .placeholder("{PLAYER}", victimName)
            .placeholder("{CAUSE}", this.formatCauseName(cause))
            .onlinePlayers()
            .send();
    }

    private void handleUnknownDeath(String victimName) {
        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.deathMessage().unknownDeathCause()))
            .placeholder("{PLAYER}", victimName)
            .onlinePlayers()
            .send();
    }

    private String getMobName(Entity entity) {
        if (entity.getCustomName() != null) {
            return entity.getCustomName();
        }

        return this.formatEntityName(entity.getType());
    }

    private String formatEntityName(EntityType type) {
        String name = type.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
            }
        }

        return formatted.toString().trim();
    }

    private String formatCauseName(EntityDamageEvent.DamageCause cause) {
        String name = cause.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
            }
        }

        return formatted.toString().trim();
    }
}
