package com.eternalcode.core.feature.kit.claim;

import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitClaimStatus;
import com.eternalcode.core.feature.kit.KitRequirements;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.Optional;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

@Service
public class KitRequirementChecker {

    public Optional<KitClaimResult> findUnmetRequirement(Player player, KitRequirements requirements) {
        if (requirements.hasWorldRequirement() && !requirements.worlds().contains(player.getWorld().getName())) {
            return Optional.of(KitClaimResult.of(KitClaimStatus.WRONG_WORLD));
        }

        if (requirements.hasGameModeRequirement() && !requirements.gameModes().contains(player.getGameMode())) {
            return Optional.of(KitClaimResult.of(KitClaimStatus.WRONG_GAME_MODE));
        }

        if (requirements.hasPlaytimeRequirement()) {
            Duration missing = requirements.minimumPlaytime().minus(this.playtime(player));

            if (!missing.isNegative() && !missing.isZero()) {
                return Optional.of(KitClaimResult.notEnoughPlaytime(missing));
            }
        }

        return Optional.empty();
    }

    private Duration playtime(Player player) {
        return Duration.ofSeconds(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20L);
    }
}
