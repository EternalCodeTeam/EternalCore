package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "fly")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/fly <player>")
public class FlyCommand {

    private final LanguageManager languageManager;
    private final NoticeService noticeService;
    private final UserManager userManager;

    public FlyCommand(LanguageManager languageManager, NoticeService noticeService, UserManager userManager) {
        this.languageManager = languageManager;
        this.noticeService = noticeService;
        this.userManager = userManager;
    }

    @Execute
    public void execute(Audience audience, CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        Messages senderMessage = this.languageManager.getMessages(audience.getLanguage());

        this.noticeService.notice()
            .message(messages -> messages.other().flyMessage())
            .placeholder("{STATE}", player.getAllowFlight() ? senderMessage.format().formatEnable() : senderMessage.format().formatDisable())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        Option<User> userOption = this.userManager.getUser(player.getUniqueId());

        if (userOption.isEmpty()) {
            return;
        }

        User user = userOption.get();

        Messages message = this.languageManager.getMessages(user);

        this.noticeService.notice()
            .message(messages -> messages.other().flySetMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{STATE}", player.getAllowFlight() ? message.format().formatEnable() : message.format().formatDisable())
            .audience(audience)
            .send();
    }
}
