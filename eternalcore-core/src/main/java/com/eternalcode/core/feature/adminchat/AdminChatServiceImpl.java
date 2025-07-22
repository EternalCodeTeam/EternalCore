package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import org.bukkit.Server;

import java.util.*;

@Service
class AdminChatServiceImpl implements AdminChatService {

    private final NoticeService noticeService;
    private final Server server;

    private final Collection<UUID> persistentAdminChatPlayers = new HashSet<>();

    @Inject
    AdminChatServiceImpl(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Override
    public boolean toggleChatPersistent(UUID playerUuid) {
        if (this.persistentAdminChatPlayers.contains(playerUuid)) {
            this.persistentAdminChatPlayers.remove(playerUuid);
            return false;
        } else {
            this.persistentAdminChatPlayers.add(playerUuid);
            return true;
        }
    }

    @Override
    public boolean isPersistentChat(UUID playerUuid) {
        return this.persistentAdminChatPlayers.contains(playerUuid);
    }

    @Override
    public Collection<UUID> getAdminChatEnabledPlayers() {
        return Collections.unmodifiableCollection(this.persistentAdminChatPlayers);
    }

    @Override
    public void sendAdminChatMessage(String message, String playerName) {
        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", playerName)
            .placeholder("{TEXT}", message);

        this.server.getOnlinePlayers().stream()
            .filter(player -> player.hasPermission(AdminChatPermissionConstant.ADMIN_CHAT_SEE_PERMISSION))
            .forEach(player -> notice.player(player.getUniqueId()));

        notice.send();
    }

}
