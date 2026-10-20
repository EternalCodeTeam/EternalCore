package com.eternalcode.core.feature.invsee;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Service
public class InvseeServiceImpl implements InvseeService {
    /**
     * First UUID is the player who is inspecting, second UUID is the player being inspected
     */
    private static final HashMap<UUID, UUID> activeInsepctions = new HashMap<>();

    /**
     * First UUID is inspected player, second valie is {@link InvseeStatus} which contains the status of the inspection
     */
    private static final HashMap<UUID, InvseeStatus> inspectionStatus = new HashMap<>();

    private final NoticeService noticeService;

    @Inject
    public InvseeServiceImpl(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public void inspectPlayer(UUID requester, UUID target) {
        Player player = Bukkit.getPlayer(requester);
        Player targetPlayer = Bukkit.getPlayer(target);

        if (player == null || targetPlayer == null) {
            return;
        }

        activeInsepctions.put(player.getUniqueId(), targetPlayer.getUniqueId());
        this.updateOrPutInspectionStatus(targetPlayer.getUniqueId());

        Inventory targetInventory = targetPlayer.getInventory();

        player.openInventory(targetInventory);
    }

    private void updateOrPutInspectionStatus(UUID target) {
        if (!inspectionStatus.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
    }
}
