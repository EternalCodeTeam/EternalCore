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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.command.god")
public class GodCommand {

    private final LanguageManager languageManager;
    private final NoticeService noticeService;
    private final UserManager userManager;

    public GodCommand(LanguageManager languageManager, NoticeService noticeService, UserManager userManager) {
        this.languageManager = languageManager;
        this.noticeService = noticeService;
        this.userManager = userManager;
    }

    @Execute
    public void execute(CommandSender sender, Audience audience, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        Messages senderMessage = this.languageManager.getMessages(audience.getLanguage());

        this.noticeService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? senderMessage.format().formatEnable() : senderMessage.format().formatDisable())
            .message(messages -> messages.other().godMessage())
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

        this.noticeService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? message.format().formatEnable() : message.format().formatDisable())
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().godSetMessage())
            .audience(audience)
            .send();
    }
}
