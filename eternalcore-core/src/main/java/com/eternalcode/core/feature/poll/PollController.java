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
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!this.pollManager.isMarked(player)) {
            return;
        }

        event.setCancelled(true);

        Poll poll = this.pollManager.getMarkedPoll(player);
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
            if (this.pollManager.isPollActive()) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.poll().pollIsActive())
                    .send();

                this.pollManager.unmarkPlayer(player);

                return;
            }

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().pollCreated())
                .send();

            this.pollManager.unmarkPlayer(player);
            this.pollManager.startTask(poll);

            return;
        }

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> iterator.next().getMessage().apply(translation))
            .send();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        // Remove player from map...
        this.pollManager.unmarkPlayer(player);
    }
}
