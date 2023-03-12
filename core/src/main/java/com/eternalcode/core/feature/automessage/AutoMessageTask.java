package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notification.Notice;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.List;

public class AutoMessageTask implements Runnable {

    private final TranslationManager translationManager;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final AutoMessageService autoMessageService;
    private final Server server;

    public AutoMessageTask(TranslationManager translationManager, NoticeService noticeService, UserManager userManager, AutoMessageService autoMessageService, Server server) {
        this.translationManager = translationManager;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.autoMessageService = autoMessageService;
        this.server = server;
    }

    @Override
    public void run() {
        AutoMessage stack = this.autoMessageService.draw();

        for (Player player : this.server.getOnlinePlayers()) {
            if (player == null) {
                continue;
            }

            Option<User> userOption = this.userManager.getUser(player.getUniqueId());

            if (userOption.isEmpty()) {
                continue;
            }

            User user = userOption.get();

            List<AutoMessage> userStacks = this.translationManager.getMessages(user)
                .autoMessage()
                .messages();

            AutoMessage userStack = this.autoMessageService.extractStack(stack, userStacks);

            Notice notice = this.noticeService.create().user(user);
            userStack.notifications().forEach(notice::staticNotice);
            notice.send();

            if (userStack.sound() != null) {
                player.playSound(player.getLocation(), userStack.sound(), 1.0f, 1.0f);
            }

        }
    }
}
