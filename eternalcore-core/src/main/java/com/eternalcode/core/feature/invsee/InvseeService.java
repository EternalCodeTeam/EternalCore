package com.eternalcode.core.feature.invsee;

import java.util.UUID;

public interface InvseeService {
    void openInventory(UUID requester, UUID target);
}
