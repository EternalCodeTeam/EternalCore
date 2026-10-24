package com.eternalcode.core.feature.invsee;

import java.util.UUID;

public interface InvseeService {
    void inspectPlayer(UUID requester, UUID target);

    boolean isBeingInspected(UUID target);

    void refreshInspection(UUID target);

    void closeInspections(UUID target);
}
