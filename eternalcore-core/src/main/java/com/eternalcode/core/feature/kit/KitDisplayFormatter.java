package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.messages.KitGuiMessages;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.shared.Formatter;
import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.GameMode;

@Service
public class KitDisplayFormatter {

    public String type(KitGuiMessages messages, KitType type) {
        return switch (type) {
            case RECURRING -> messages.typeRecurring();
            case ONE_TIME -> messages.typeOneTime();
            case FIRST_JOIN -> messages.typeFirstJoin();
        };
    }

    public String status(KitGuiMessages messages, KitClaimResult result) {
        String status = switch (result.status()) {
            case NO_PERMISSION -> messages.statusNoPermission();
            case ON_COOLDOWN -> messages.statusOnCooldown();
            case ALREADY_CLAIMED -> messages.statusAlreadyClaimed();
            case WRONG_WORLD -> messages.statusWrongWorld();
            case WRONG_GAME_MODE -> messages.statusWrongGameMode();
            case NOT_ENOUGH_PLAYTIME -> messages.statusNotEnoughPlaytime();
            case INVENTORY_FULL -> messages.statusInventoryFull();
            case DATA_NOT_LOADED -> messages.statusDataNotLoaded();
            case SUCCESS, ALREADY_CLAIMING, CANCELLED -> messages.statusAvailable();
        };

        String remaining = this.duration(result.remaining());

        return new Formatter()
            .register(KitNotices.COOLDOWN, remaining)
            .register(KitNotices.PLAYTIME, remaining)
            .format(status);
    }

    public String cooldown(KitGuiMessages messages, Kit kit) {
        return kit.hasCooldown() ? this.duration(kit.cooldown()) : messages.none();
    }

    public String duration(KitGuiMessages messages, Duration duration) {
        return duration.isZero() ? messages.none() : this.duration(duration);
    }

    public String worlds(KitGuiMessages messages, KitRequirements requirements) {
        return requirements.hasWorldRequirement() ? this.join(requirements.worlds()) : messages.any();
    }

    public String gameModes(KitGuiMessages messages, KitRequirements requirements) {
        return requirements.hasGameModeRequirement()
            ? this.join(requirements.gameModes().stream().map(GameMode::name).toList())
            : messages.any();
    }

    public String duration(Duration duration) {
        return DurationUtil.format(duration, true);
    }

    private String join(Collection<String> values) {
        return values.stream().sorted().collect(Collectors.joining(", "));
    }
}
