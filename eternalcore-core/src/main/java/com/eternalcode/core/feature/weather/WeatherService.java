package com.eternalcode.core.feature.weather;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.GameRule;
import org.bukkit.World;

@Service
class WeatherService {

    private static final int DEFAULT_CLEAR_WEATHER_DURATION = 20 * 60 * 10;

    private final MinecraftScheduler scheduler;

    @Inject
    WeatherService(MinecraftScheduler scheduler) {
        this.scheduler = scheduler;
    }

    void setWeather(World world, WeatherType weatherType, boolean persistent) {
        this.scheduler.run(() -> {
            this.applyWeather(world, weatherType);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, !persistent);
        });
    }

    void clearPersistentWeather(World world) {
        this.scheduler.run(() -> world.setGameRule(GameRule.DO_WEATHER_CYCLE, true));
    }

    private void applyWeather(World world, WeatherType weatherType) {
        switch (weatherType) {
            case SUN -> {
                world.setClearWeatherDuration(DEFAULT_CLEAR_WEATHER_DURATION);
                world.setStorm(false);
                world.setThundering(false);
            }
            case RAIN -> {
                world.setStorm(true);
                world.setThundering(false);
            }
            case THUNDER -> {
                world.setStorm(true);
                world.setThundering(true);
            }
            default -> throw new IllegalStateException("Unsupported weather type: " + weatherType);
        }
    }

}
