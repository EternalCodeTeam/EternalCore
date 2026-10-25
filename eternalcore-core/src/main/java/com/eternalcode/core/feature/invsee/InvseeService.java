package com.eternalcode.core.feature.invsee;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.PlayerDeathEvent;

public interface InvseeService {
    void inspectPlayer(UUID requester, UUID target);

    boolean isBeingInspected(UUID target);

    boolean isInventoryBlocked(UUID target);

    void refreshInspection(UUID target);

    void closeInspections(UUID target);

    void closeAllInspections();

    void closeInspectionsOnDeath(PlayerDeathEvent event);

    void handleInspectorAction(InvseeInventoryHolder holder, Cancellable event);

    void closeInspection(InvseeInventoryHolder holder);
}
