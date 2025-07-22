package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import org.bukkit.Server;

import java.util.*;
import org.bukkit.command.CommandSender;

@Service
class AdminChatServiceImpl implements AdminChatService {

    private final NoticeService noticeService;
    private final Server server;
    private final EventCaller eventCaller;

    private final Collection<UUID> playersWithEnabledChat = new HashSet<>();

    @Inject
    AdminChatServiceImpl(NoticeService noticeService, Server server, EventCaller eventCaller) {
        this.noticeService = noticeService;
        this.server = server;
        this.eventCaller = eventCaller;
    }

    @Override
    public boolean toggleChat(UUID playerUuid) {
        if (this.playersWithEnabledChat.contains(playerUuid)) {
            this.playersWithEnabledChat.remove(playerUuid);
            return false;
        } else {
            this.playersWithEnabledChat.add(playerUuid);
            return true;
        }
    }

    @Override
    public boolean hasEnabledChat(UUID playerUuid) {
        return this.playersWithEnabledChat.contains(playerUuid);
    }

    @Override
    public Collection<UUID> getPlayersWithEnabledChat() {
        return Collections.unmodifiableCollection(this.playersWithEnabledChat);
    }

    @Override
    public void sendAdminChatMessage(String message, CommandSender sender) {
        AdminChatEvent event = this.eventCaller.callEvent(new AdminChatEvent(sender, message));

        if (event.isCancelled()) {
            return;
        }

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", sender.getName())
            .placeholder("{TEXT}", event.getContent());

        this.server.getOnlinePlayers().stream()
            .filter(player -> player.hasPermission(AdminChatPermissionConstant.ADMIN_CHAT_SEE_PERMISSION) || player.hasPermission(AdminChatPermissionConstant.ADMIN_CHAT_SPY_PERMISSION))
            .forEach(player -> notice.player(player.getUniqueId()));

        notice.send();
    }

}
