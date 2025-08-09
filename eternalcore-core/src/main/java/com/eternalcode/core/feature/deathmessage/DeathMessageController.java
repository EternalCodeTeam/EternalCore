package com.eternalcode.core.feature.deathmessage;

import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

@Controller
class DeathMessageController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;

    @Inject
    DeathMessageController(NoticeService noticeService, VanishService vanishService) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        event.setDeathMessage(null);

        if (this.vanishService.isVanished(victim)) {
            return;
        }

        EntityDamageEvent damageCause = victim.getLastDamageCause();
        if (!(damageCause instanceof EntityDamageByEntityEvent causeByEntity)) {
            this.handleEnvironmentalDeath(victim, damageCause);
            return;
        }

        Entity directDamager = causeByEntity.getDamager();
        if (directDamager instanceof Projectile projectile) {
            this.handleProjectileDeath(victim, projectile);
            return;
        }

        if (directDamager instanceof Player killer) {
            this.handlePvPDeath(victim, killer);
            return;
        }
        this.handleEntityDeath(victim, directDamager);
    }

    private void handleProjectileDeath(Player victim, Projectile projectile) {
        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof Player killerPlayer) {
            String weaponType = this.getProjectileWeaponType(projectile);
            this.noticeService.create()
                .noticeOptional(translation -> {
                    List<Notice> weaponMessages = translation.death().deathMessageByWeapon().get(weaponType);
                    return weaponMessages != null
                        ? RandomElementUtil.randomElement(weaponMessages)
                        : RandomElementUtil.randomElement(translation.death().deathMessage());
                })
                .placeholder("{PLAYER}", victim.getName())
                .placeholder("{KILLER}", killerPlayer.getName())
                .placeholder("{WEAPON}", weaponType.toLowerCase().replace("_", " "))
                .onlinePlayers()
                .send();
            return;
        }

        if (shooter instanceof Entity shooterEntity) {
            String entityType = shooterEntity.getType().name();
            String projectileType = projectile.getType().name();
            this.noticeService.create()
                .noticeOptional(translation -> {
                    List<Notice> entityMessages = translation.death().deathMessageByEntity().get(entityType);
                    if (entityMessages != null) {
                        return RandomElementUtil.randomElement(entityMessages);
                    }
                    List<Notice> projectileMessages = translation.death().deathMessageByEntity().get(projectileType);
                    if (projectileMessages != null) {
                        return RandomElementUtil.randomElement(projectileMessages);
                    }
                    return RandomElementUtil.randomElement(translation.death().deathMessage());
                })
                .placeholder("{PLAYER}", victim.getName())
                .placeholder("{KILLER}", shooterEntity.getName())
                .placeholder("{PROJECTILE}", projectileType.toLowerCase().replace("_", " "))
                .onlinePlayers()
                .send();
            return;
        }

        String projectileType = projectile.getType().name();
        this.noticeService.create()
            .noticeOptional(translation -> {
                List<Notice> projectileMessages = translation.death().deathMessageByEntity().get(projectileType);
                return projectileMessages != null
                    ? RandomElementUtil.randomElement(projectileMessages)
                    : RandomElementUtil.randomElement(translation.death().unknownDeathCause());
            })
            .placeholder("{PLAYER}", victim.getName())
            .placeholder("{KILLER}", projectileType.toLowerCase().replace("_", " "))
            .onlinePlayers()
            .send();
    }

    private void handlePvPDeath(Player victim, Player killer) {
        String weaponType = this.getWeaponType(killer.getInventory().getItemInMainHand());
        this.noticeService.create()
            .noticeOptional(translation -> Optional.ofNullable(translation.death().deathMessageByWeapon().get(weaponType))
                .filter(list -> !list.isEmpty())
                .map(RandomElementUtil::randomElement)
                .orElseGet(() -> RandomElementUtil.randomElement(translation.death().deathMessage())))
            .placeholder("{PLAYER}", victim.getName())
            .placeholder("{KILLER}", killer.getName())
            .placeholder("{WEAPON}", weaponType.toLowerCase().replace("_", " "))
            .onlinePlayers()
            .send();
    }

    private void handleEntityDeath(Player victim, Entity damager) {
        String entityType = damager.getType().name();
        this.noticeService.create()
            .noticeOptional(translation -> {
                List<Notice> entityMessages = translation.death().deathMessageByEntity().get(entityType);
                return entityMessages != null
                    ? RandomElementUtil.randomElement(entityMessages)
                    : RandomElementUtil.randomElement(translation.death().deathMessage());
            })
            .placeholder("{PLAYER}", victim.getName())
            .placeholder("{KILLER}", damager.getName())
            .onlinePlayers()
            .send();
    }

    private void handleEnvironmentalDeath(Player victim, EntityDamageEvent damageCause) {
        if (damageCause == null) {
            this.handleUnknownDeath(victim);
            return;
        }

        EntityDamageEvent.DamageCause cause = damageCause.getCause();
        if (cause == EntityDamageEvent.DamageCause.CONTACT) {
            String blockType = this.getContactBlockType(victim);
            if (blockType != null) {
                this.noticeService.create()
                    .noticeOptional(translation -> {
                        List<Notice> blockMessages = translation.death().deathMessageByEntity().get(blockType);
                        return blockMessages != null
                            ? RandomElementUtil.randomElement(blockMessages)
                            : RandomElementUtil.randomElement(translation.death().unknownDeathCause());
                    })
                    .placeholder("{PLAYER}", victim.getName())
                    .onlinePlayers()
                    .send();
                return;
            }
        }

        this.noticeService.create()
            .noticeOptional(translation -> {
                List<Notice> notifications = translation.death().deathMessageByDamageCause().get(cause);
                return notifications != null
                    ? RandomElementUtil.randomElement(notifications)
                    : RandomElementUtil.randomElement(translation.death().unknownDeathCause());
            })
            .placeholder("{PLAYER}", victim.getName())
            .placeholder("{CAUSE}", cause.name())
            .onlinePlayers()
            .send();
    }

    private void handleUnknownDeath(Player victim) {
        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.death().unknownDeathCause()))
            .placeholder("{PLAYER}", victim.getName())
            .onlinePlayers()
            .send();
    }

    private String getProjectileWeaponType(Projectile projectile) {
        return switch (projectile.getType()) {
            case ARROW, SPECTRAL_ARROW -> "BOW";
            case FIREBALL, SMALL_FIREBALL -> "FIREBALL";
            default -> projectile.getType().name();
        };
    }

    private String getWeaponType(ItemStack item) {
        return item == null || item.getType().isAir()
            ? "HAND"
            : item.getType().name();
    }

    private String getContactBlockType(Player player) {
        Block blockAtFeet = player.getLocation().getBlock();
        Block blockAtEyes = player.getEyeLocation().getBlock();

        for (Block block : new Block[] { blockAtFeet, blockAtEyes }) {
            String type = switch (block.getType()) {
                case CACTUS, MAGMA_BLOCK, SWEET_BERRY_BUSH, WITHER_ROSE -> block.getType().name();
                default -> null;
            };
            if (type != null) {
                return type;
            }
        }
        return null;
    }
}
