package com.eternalcode.core.feature.deathmessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

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

        if (player.getKiller() != null) {
            this.noticeService.create()
                .noticeOption(translation -> RandomUtil.randomElement(translation.event().deathMessage()))
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{KILLER}", player.getKiller().getName())
                .onlinePlayers()
                .send();

            return;
        }

        EntityDamageEvent lastDamageCause = player.getLastDamageCause();

        if (lastDamageCause == null) {
            this.noticeService.create()
                .noticeOption(translation -> RandomUtil.randomElement(translation.event().unknownDeathCause()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
            return;
        }

        this.noticeService.create()
            .noticeOption(translation -> {
                EntityDamageEvent.DamageCause cause = lastDamageCause.getCause();

                List<Notice> notifications = translation.event().deathMessageByDamageCause().get(cause);

                if (notifications == null) {
                    return RandomUtil.randomElement(translation.event().unknownDeathCause());
                }

                return RandomUtil.randomElement(notifications);
            })
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
