package com.eternalcode.core.feature.deathmessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@FeatureDocs(
    description = "Send a message to all players when a player dies, you can configure the messages based on damage cause in configuration, see: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html for all damage causes",
    name = "Player Death Message"
)
@Controller
class DeathMessageController implements Listener {

    private final NoticeService noticeService;

    @Inject
    DeathMessageController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        EntityDamageEvent damageCause = player.getLastDamageCause();

        if (damageCause instanceof EntityDamageByEntityEvent causeByEntity && causeByEntity.getDamager() instanceof Player killer) {
            this.noticeService.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().deathMessage()))
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{KILLER}", killer.getName())
                .onlinePlayers()
                .send();
            return;
        }

        if (damageCause != null) {
            EntityDamageEvent.DamageCause cause = damageCause.getCause();
            this.noticeService.create()
                .noticeOptional(translation -> {
                    List<Notice> notifications = translation.event().deathMessageByDamageCause().get(cause);

                    if (notifications == null) {
                        return RandomElementUtil.randomElement(translation.event().unknownDeathCause());
                    }

                    return RandomElementUtil.randomElement(notifications);
                })
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{CAUSE}", cause.name())
                .onlinePlayers()
                .send();
            return;
        }

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().unknownDeathCause()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
