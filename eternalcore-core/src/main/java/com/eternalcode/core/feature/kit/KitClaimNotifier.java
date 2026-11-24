package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.shared.Formatter;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Service
public class KitClaimNotifier {

    private final KitClaimService claimService;
    private final KitDisplayFormatter displayFormatter;
    private final KitNotices notices;

    @Inject
    KitClaimNotifier(KitClaimService claimService, KitDisplayFormatter displayFormatter, KitNotices notices) {
        this.claimService = claimService;
        this.displayFormatter = displayFormatter;
        this.notices = notices;
    }

    public void claimAndNotify(Player player, Kit kit) {
        this.notify(player, kit, this.claimService.claimKit(player, kit));
    }

    public void notify(CommandSender receiver, Kit kit, KitClaimResult result) {
        Formatter formatter = new Formatter()
            .register(KitNotices.KIT, kit.displayName())
            .register(KitNotices.KIT_NAME, kit.name())
            .register(KitNotices.COOLDOWN, this.displayFormatter.duration(result.remaining()))
            .register(KitNotices.PLAYTIME, this.displayFormatter.duration(result.remaining()))
            .register(KitNotices.WORLDS, String.join(", ", kit.requirements().worlds()))
            .register(KitNotices.GAME_MODES, String.join(", ", kit.requirements().gameModes().stream().map(Enum::name).toList()))
            .register(KitNotices.AMOUNT, result.bufferedItems());

        this.resolveMessage(result).ifPresent(message -> this.notices.send(receiver, message, formatter));
    }

    private Optional<Function<KitMessages, Notice>> resolveMessage(KitClaimResult result) {
        return switch (result.status()) {
            case SUCCESS -> Optional.of(this.resolveSuccessMessage(result));
            case NO_PERMISSION -> Optional.of(KitMessages::noPermission);
            case ON_COOLDOWN -> Optional.of(KitMessages::onCooldown);
            case ALREADY_CLAIMED -> Optional.of(KitMessages::alreadyClaimed);
            case WRONG_WORLD -> Optional.of(KitMessages::wrongWorld);
            case WRONG_GAME_MODE -> Optional.of(KitMessages::wrongGameMode);
            case NOT_ENOUGH_PLAYTIME -> Optional.of(KitMessages::notEnoughPlaytime);
            case INVENTORY_FULL -> Optional.of(KitMessages::inventoryFull);
            case CANCELLED -> Optional.of(KitMessages::claimCancelled);
            case DATA_NOT_LOADED -> Optional.of(KitMessages::dataNotLoaded);
            case ALREADY_CLAIMING -> Optional.empty();
        };
    }

    private Function<KitMessages, Notice> resolveSuccessMessage(KitClaimResult result) {
        if (result.bufferedItems() > 0) {
            return KitMessages::claimedToBuffer;
        }

        return KitMessages::claimed;
    }
}
