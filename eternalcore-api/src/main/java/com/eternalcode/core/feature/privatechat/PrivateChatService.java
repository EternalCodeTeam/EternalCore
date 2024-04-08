package com.eternalcode.core.feature.privatechat;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface PrivateChatService {

    void enableSpy(UUID uuid);

    void disableSpy(UUID uuid);

    boolean isSpy(UUID uuid);

    void reply(Player sender, String message);

    void privateMessage(Player sender, Player target, String message);
}
