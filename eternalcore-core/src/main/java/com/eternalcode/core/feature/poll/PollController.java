package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class PollController implements Listener {

    private final Map<UUID, Poll> pollSetupMap;

    public PollController() {
        this.pollSetupMap = new HashMap<>();
    }

    @EventHandler
    private void onPollSetup(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if (!this.pollSetupMap.containsKey(player.getUniqueId())) {
            return;
        }

        Poll poll = this.pollSetupMap.get(player.getUniqueId());
        Iterator<PollArgumentValidation> iterator = poll.getArgumentValidationIterator();

        if (!iterator.hasNext()) {
            // DO SOMETHING
            return;
        }

        PollArgumentValidation argumentValidation = iterator.next();

        if (!argumentValidation.isValid(poll, message)) {
            // SEND SOMETHING
            return;
        }
    }

    // TODO: IF PLAYERS LEAVES POLL CLOSING
}
