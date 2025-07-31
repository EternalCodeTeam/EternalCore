package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Service
final class AdminChatServiceImpl implements AdminChatService {

    private final NoticeService noticeService;
    private final Server server;
    private final EventCaller eventCaller;

    private final Set<UUID> playersWithEnabledChat = ConcurrentHashMap.newKeySet();

    @Inject
    AdminChatServiceImpl(
        NoticeService noticeService,
        Server server,
        EventCaller eventCaller
    ) {
        this.noticeService = noticeService;
        this.server = server;
        this.eventCaller = eventCaller;
    }

    @Override
    public boolean toggleChat(@NotNull UUID playerUuid) {
        if (this.playersWithEnabledChat.contains(playerUuid)) {
            this.playersWithEnabledChat.remove(playerUuid);
            return false;
        }
        else {
            this.playersWithEnabledChat.add(playerUuid);
            return true;
        }
    }

    @Override
    public boolean hasEnabledChat(@NotNull UUID playerUuid) {
        return this.playersWithEnabledChat.contains(playerUuid);
    }

    @Override
    @NotNull
    @Unmodifiable
    public Collection<UUID> getPlayersWithEnabledChat() {
        return Collections.unmodifiableSet(this.playersWithEnabledChat);
    }

    @Override
    public void sendAdminChatMessage(@NotNull String message, @NotNull CommandSender sender) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

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
            .filter(this::canSeeAdminChat)
            .forEach(player -> notice.player(player.getUniqueId()));

        notice.send();
    }

    @Override
    public void enableChat(@NotNull UUID playerUuid) {
        this.playersWithEnabledChat.add(playerUuid);
    }

    @Override
    public void disableChat(@NotNull UUID playerUuid) {
        this.playersWithEnabledChat.remove(playerUuid);
    }

    @Override
    public boolean canSeeAdminChat(@NotNull CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }

        return sender.hasPermission(AdminChatPermissionConstant.ADMIN_CHAT_SEE_PERMISSION);
    }
}
