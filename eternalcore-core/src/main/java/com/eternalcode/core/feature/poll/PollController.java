package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.CurrentIterator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PollController implements Listener {

    private final NoticeService noticeService;
    private final PollManager pollManager;

    public PollController(NoticeService noticeService, PollManager pollManager) {
        this.noticeService = noticeService;
        this.pollManager = pollManager;
    }

    @EventHandler
    private void onMessageReceived(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if (!this.pollManager.getPollSetupMap().containsKey(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        Poll poll = this.pollManager.getPollSetupMap().get(player.getUniqueId());
        CurrentIterator<PollArgumentValidation> iterator = poll.getArgumentValidationIterator();

        PollArgumentValidation argumentValidation = iterator.current();

        if (!argumentValidation.isValid(poll, message)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().optionNotValid())
                .send();

            return;
        }

        if (!iterator.hasNext()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().pollCreated())
                .send();

            this.pollManager.getPollSetupMap().remove(player.getUniqueId());
            return;
        }

//        this.noticeService.create()
//            .player(player.getUniqueId())
//            .notice(ignore -> )
//                    .send();

        iterator.next();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        // Remove player from map...
        this.pollManager.getPollSetupMap().computeIfPresent(player.getUniqueId(),
            (uuid, ignore) -> this.pollManager.getPollSetupMap().remove(uuid));
    }
}
