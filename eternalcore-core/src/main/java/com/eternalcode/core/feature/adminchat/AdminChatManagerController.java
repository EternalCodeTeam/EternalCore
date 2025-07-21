package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
class AdminChatManagerController implements Listener {

    private final AdminChatService adminChatService;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    AdminChatManagerController (
        AdminChatService adminChatService,
        NoticeService noticeService,
        EventCaller eventCaller,
        Server server
    ) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
        this.server = server;
    }

    @EventHandler
    void onAdminChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.adminChatService.isAdminChatSpy(player.getUniqueId())) {
            return;
        }

        String message = event.getMessage();

        event.setCancelled(true);

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{TEXT}", message);

        this.server.getOnlinePlayers().stream()
            .filter(p -> p.hasPermission(AdminChatCommand.ADMIN_CHAT_SPY_PERMISSION))
            .forEach(p -> notice.player(player.getUniqueId()));

        notice.send();
    }


}
