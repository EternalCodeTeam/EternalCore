package com.eternalcode.core.feature.msg;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface MsgService {

    void enableSpy(UUID uuid);

    void disableSpy(UUID uuid);

    boolean isSpy(UUID uuid);

    void reply(Player sender, String message);

    void sendMessage(Player sender, Player target, String message);
}
