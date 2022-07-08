package com.eternalcode.core.chat.placeholder;

import org.bukkit.entity.Player;

public interface PlayerPlaceholderStack {

    String apply(String text, Player target);

}
