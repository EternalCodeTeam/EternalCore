package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PollController implements Listener {

    private final NoticeService noticeService;
    private final PollManager pollManager;
    private final TranslationManager translationManager;
    private final UserManager userManager;

    public PollController(NoticeService noticeService, PollManager pollManager, TranslationManager translationManager, UserManager userManager) {
        this.noticeService = noticeService;
        this.pollManager = pollManager;
        this.translationManager = translationManager;
        this.userManager = userManager;
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

        User user = this.userManager.getUser(player.getUniqueId())
            .orThrow(() -> new NullPointerException("User cannot be null!"));

        Translation userTranslation = this.translationManager.getMessages(user);

        var iterator = poll.getArgumentValidationIterator();
        var argumentValidation = iterator.current();
        var optionalNotificationError = argumentValidation.isValid(poll, message, userTranslation);

        if (optionalNotificationError.isPresent()) {
            System.out.println(optionalNotificationError.get().getMessage());

            this.noticeService.create()
                .user(user)
                .formatter(argumentValidation.getFormatter())
                .notice(ignore -> optionalNotificationError.get())
                .send();

            return;
        }

        if (!iterator.hasNext()) {
            if (this.pollManager.isPollActive()) {
                this.noticeService.create()
                    .user(user)
                    .notice(translation -> translation.poll().pollIsActive())
                    .send();

                this.pollManager.unmarkPlayer(player);
                return;
            }

            this.noticeService.create()
                .onlinePlayers()
                .placeholder("{OWNER}", player.getName())
                .notice(translation -> translation.poll().pollCreated())
                .send();

            this.pollManager.unmarkPlayer(player);
            this.pollManager.startTask(poll);
            return;
        }

        this.noticeService.create()
            .user(user)
            .notice(translation -> iterator.next().getMessage().apply(translation))
            .send();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        this.pollManager.unmarkPlayer(player);
    }
}
