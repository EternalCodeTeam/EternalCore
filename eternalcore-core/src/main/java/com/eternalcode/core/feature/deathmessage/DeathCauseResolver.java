package com.eternalcode.core.feature.deathmessage;

import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

@Service
public class DeathCauseResolver {

    public DeathContext resolve(Player victim, EntityDamageEvent damageEvent) {
        if (damageEvent == null) {
            return new DeathContext(
                victim,
                null,
                null,
                null,
                DeathContext.DeathType.UNKNOWN
            );
        }

        EntityDamageEvent.DamageCause cause = damageEvent.getCause();

        if (damageEvent instanceof EntityDamageByEntityEvent entityDamage) {
            Player killer = this.resolvePlayerKiller(entityDamage.getDamager());

            if (killer != null && killer.isValid() && !killer.equals(victim)) {
                return new DeathContext(
                    victim,
                    killer,
                    entityDamage.getDamager(),
                    cause,
                    DeathContext.DeathType.PLAYER_KILL
                );
            }

            if (entityDamage.getDamager() instanceof LivingEntity) {
                return new DeathContext(
                    victim,
                    null,
                    entityDamage.getDamager(),
                    cause,
                    DeathContext.DeathType.ENTITY_KILL
                );
            }
        }

        return new DeathContext(
            victim,
            null,
            null,
            cause,
            DeathContext.DeathType.ENVIRONMENT
        );
    }

    private Player resolvePlayerKiller(Entity damager) {
        if (damager instanceof Player player) {
            return player;
        }

        if (damager instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Player player) {
                return player;
            }
        }

        if (damager instanceof TNTPrimed tnt) {
            Entity source = tnt.getSource();
            if (source instanceof Player player) {
                return player;
            }
        }

        return null;
    }
}
