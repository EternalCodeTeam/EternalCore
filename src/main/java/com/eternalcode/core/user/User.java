/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.user;

import com.eternalcode.core.chat.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.utilities.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class User {

    private final String name;
    private final UUID uuid;

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void ifOnline(Consumer<Player> playerConsumer) {
        Option.of(Bukkit.getPlayer(name)).peek(playerConsumer);
    }

    public void message(String message) {
        ifOnline(player -> player.sendMessage(ChatUtils.color(message)));
    }

    public void actionBar(String message) {
        ifOnline(player -> player.sendActionBar(Component.text(ChatUtils.color(message))));
    }

    public void title(String title, String subTitle, long in, long stay, long out) {
        Function<Long, Duration> calcDuration = integer -> Duration.of(integer * 50, ChronoUnit.MILLIS);
        Title.Times times = Title.Times.of(calcDuration.apply(in), calcDuration.apply(stay), calcDuration.apply(out));
        Title titleComponent = Title.title(ChatUtils.component(title), ChatUtils.component(subTitle), times);

        ifOnline(player -> player.showTitle(titleComponent));
    }

    public void title(String title, long in, long stay, long out) {
        title(title, StringUtils.EMPTY, in, stay, out);
    }

    public void subTitle(String subTitle, long in, long stay, long out) {
        title(StringUtils.EMPTY, subTitle, in, stay, out);
    }

}
