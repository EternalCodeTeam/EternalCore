package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class PollCreateController implements Listener {

    private static final int MIN_OPTIONS_SIZE = 2;
    private static final int MAX_OPTIONS_SIZE = 5;
    private static final int MAX_DESCRIPTION_LENGTH = 25;

    private final NoticeService noticeService;
    private final PollManager pollManager;
    private final UserManager userManager;

    public PollCreateController(NoticeService noticeService, PollManager pollManager, UserManager userManager) {
        this.noticeService = noticeService;
        this.pollManager = pollManager;
        this.userManager = userManager;
    }

    @EventHandler
    void onMessageReceived(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        String message = event.getMessage();

        if (!this.pollManager.isCreatingPoll(uniqueId)) {
            return;
        }

        Poll.Builder creatingPoll = this.pollManager.getCreatingPoll(uniqueId);
        User user = this.userManager.getUser(uniqueId)
            .orElseThrow(() -> new NullPointerException("User cannot be null!"));

        event.setCancelled(true);

        if (creatingPoll.description() == null) {
            this.handleDescription(user, creatingPoll, message);
            return;
        }

        if (creatingPoll.optionList() == null || creatingPoll.optionList().size() < MIN_OPTIONS_SIZE) {
            boolean isValid = this.handleOptionList(user, creatingPoll, message);

            if (!isValid) {
                return;
            }
        }

        Poll poll = creatingPoll.build();

        if (this.pollManager.isPollActive()) {
            this.noticeService.user(user, translation -> translation.poll().pollAlreadyActive());
            this.pollManager.cancelCreatingPoll(uniqueId);
            return;
        }

        this.pollManager.startPoll(user, poll);
    }

    private void handleDescription(User user, Poll.Builder poll, String message) {
        if (message.length() > MAX_DESCRIPTION_LENGTH) {
            this.noticeService.create()
                .user(user)
                .placeholder("{MAX_LENGTH}", () -> String.valueOf(MAX_DESCRIPTION_LENGTH))
                .notice(translation -> translation.poll().descriptionMaxLengthMessage())
                .send();

            return;
        }

        poll.description(message);
        this.noticeService.user(user, translation -> translation.poll().optionsValidationMessage());
    }

    private boolean handleOptionList(User user, Poll.Builder creatingPoll, String message) {
        String[] arrayOfOptions = message.replaceAll("\\s*,\\s*", ",").split(",");

        int optionsLength = arrayOfOptions.length;

        if (optionsLength < MIN_OPTIONS_SIZE) {
            this.noticeService.create()
                .user(user)
                .placeholder("{MIN_SIZE}", () -> String.valueOf(MIN_OPTIONS_SIZE))
                .notice(translation -> translation.poll().optionsMinOptionSizeMessage())
                .send();

            return false;
        }

        if (optionsLength > MAX_OPTIONS_SIZE) {
            this.noticeService.create()
                .user(user)
                .placeholder("{MAX_SIZE}", () -> String.valueOf(MAX_OPTIONS_SIZE))
                .notice(translation -> translation.poll().optionsMaxOptionSizeMessage())
                .send();
            return false;
        }

        for (String option : arrayOfOptions) {
            if (option.isBlank()) {
                this.noticeService.user(user, translation -> translation.poll().optionsIsEmptyMessage());
                return false;
            }
        }

        List<PollOption> options = Stream.of(arrayOfOptions)
            .map(PollOption::new)
            .toList();

        creatingPoll.optionList(options);
        return true;
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.pollManager.cancelCreatingPoll(player.getUniqueId());
    }

}
