package com.eternalcode.core.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@UtilityClass
public class TextUtils {

    public static void message(Player player, String message) {
        player.sendMessage(ChatUtils.color(message));
    }

    public static void actionBar(Player player, String message) {
        player.sendActionBar(ChatUtils.component(message));
    }

    public static void title(Player player, String title, String subTitle, long in, long stay, long out) {
        Function<Long, Duration> calcDuration = integer -> Duration.of(integer * 50, ChronoUnit.MILLIS);
        Title.Times times = Title.Times.of(calcDuration.apply(in), calcDuration.apply(stay), calcDuration.apply(out));
        Title titleComponent = Title.title(ChatUtils.component(title), ChatUtils.component(subTitle), times);

        player.showTitle(titleComponent);
    }

    public static void bossBar(Player player, String title, BarColor barColor, BarStyle barStyle, float progress) {
        BossBar bossBar = (BossBar) Bukkit.createBossBar(title, barColor, barStyle);
        bossBar.progress(progress);

        player.showBossBar(bossBar);
    }
}
