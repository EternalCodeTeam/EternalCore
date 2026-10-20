package com.eternalcode.core.feature.invsee;

import java.util.UUID;

public interface InvseeService {
    void inspectPlayer(UUID requester, UUID target);
}
