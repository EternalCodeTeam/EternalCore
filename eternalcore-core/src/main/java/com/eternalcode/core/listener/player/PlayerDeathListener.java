package com.eternalcode.core.listener.player;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import panda.std.Option;
import panda.utilities.StringUtils;

import java.util.List;

@FeatureDocs(
    description = "Send a message to all players when a player dies, you can configure the messages based on damage cause in configuration, see: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html for all damage causes",
    name = "Player Death Message"
)
public class PlayerDeathListener implements Listener {

    private final NoticeService noticeService;

    public PlayerDeathListener(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(StringUtils.EMPTY);

        if (player.getKiller() != null) {
            this.noticeService.create()
                .noticeOption(translation -> RandomUtil.randomElement(translation.event().deathMessage()))
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{KILLER}", player.getKiller().getName())
                .onlinePlayers()
                .send();

            return;
        }

        EntityDamageEvent lastDamageCasue = player.getLastDamageCause();

        if (lastDamageCasue == null) {
            return;
        }

        this.noticeService.create()
            .noticeOption(translation -> {
                EntityDamageEvent.DamageCause cause = lastDamageCasue.getCause();

                List<Notice> notifications = translation.event().deathMessageByDamageCause().get(cause);

                if (notifications == null) {
                    return Option.none();
                }

                return RandomUtil.randomElement(notifications);
            })
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
