package com.eternalcode.core.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public enum MessageAction {

    CHAT((player, component) -> player.sendMessage(component)),
    ACTIONBAR(Audience::sendActionBar),
    TITLE((player, component) -> player.showTitle(Title.title(component, Component.text(StringUtils.EMPTY)))),
    SUBTITLE((player, component) -> player.showTitle(Title.title(Component.text(StringUtils.EMPTY), component)));

    private final BiConsumer<Player, Component> consumer;

    MessageAction(BiConsumer<Player, Component> consumer) {
        this.consumer = consumer;
    }

    public void action(Player player, Component component) {
        this.consumer.accept(player, component);
    }
}
