package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.*;

@Service
class AdminChatServiceImpl implements AdminChatService {

    private final Collection<UUID> persistentAdminChatPlayers = new HashSet<>();

    @Inject
    AdminChatServiceImpl() {}

    @Override
    public boolean changeAdminChatSpy(UUID playerUuid) {
        if (this.persistentAdminChatPlayers.contains(playerUuid)) {
            this.persistentAdminChatPlayers.remove(playerUuid);
            return false;
        } else {
            this.persistentAdminChatPlayers.add(playerUuid);
            return true;
        }
    }

    @Override
    public boolean isAdminChatSpy(UUID playerUuid) {
        return this.persistentAdminChatPlayers.contains(playerUuid);
    }

    @Override
    public Collection<UUID> getAdminChatEnabledPlayers() {
        return Collections.unmodifiableCollection(this.persistentAdminChatPlayers);
    }
}
