package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.notice.Notice;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.entity.Player;

/**
 * Claims a kit and translates {@link KitClaimResult} into a notice. Shared by /kit and the GUI.
 */
@Service
public class KitClaimNotifier {

    private final KitClaimService claimService;
    private final KitNotices notices;

    @Inject
    KitClaimNotifier(KitClaimService claimService, KitNotices notices) {
        this.claimService = claimService;
        this.notices = notices;
    }

    public void claimAndNotify(Player player, Kit kit) {
        KitClaimResult result = this.claimService.claimKit(player, kit);

        this.notices.send(player, this.resolveMessage(result.status()), Map.of(
            KitNotices.KIT, kit.displayName(),
            KitNotices.KIT_NAME, kit.name(),
            KitNotices.COOLDOWN, DurationUtil.format(result.remainingCooldown(), true)
        ));
    }

    private Function<KitMessages, Notice> resolveMessage(KitClaimStatus status) {
        return switch (status) {
            case SUCCESS -> KitMessages::claimed;
            case NO_PERMISSION -> KitMessages::noPermission;
            case ON_COOLDOWN -> KitMessages::onCooldown;
            case CANCELLED -> KitMessages::claimCancelled;
            case COOLDOWNS_NOT_LOADED -> KitMessages::databaseError;
        };
    }
}
