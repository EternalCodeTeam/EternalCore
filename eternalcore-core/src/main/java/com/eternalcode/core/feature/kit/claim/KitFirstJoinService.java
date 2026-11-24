package com.eternalcode.core.feature.kit.claim;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Player;

@Service
public class KitFirstJoinService {

    private final KitService kitService;
    private final KitClaimServiceImpl claimService;
    private final KitClaimNotifier claimNotifier;

    @Inject
    KitFirstJoinService(KitService kitService, KitClaimServiceImpl claimService, KitClaimNotifier claimNotifier) {
        this.kitService = kitService;
        this.claimService = claimService;
        this.claimNotifier = claimNotifier;
    }

    public void giveFirstJoinKits(Player player) {
        if (!player.isOnline()) {
            return;
        }

        for (Kit kit : this.kitService.getKits()) {
            if (kit.type() != KitType.FIRST_JOIN) {
                continue;
            }

            KitClaimResult result = this.claimService.claimOnFirstJoin(player, kit);

            if (result.isSuccess()) {
                this.claimNotifier.notify(player, kit, result);
            }
        }
    }
}
