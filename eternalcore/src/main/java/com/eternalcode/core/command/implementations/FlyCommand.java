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

    private final NoticeService noticeService;

    public FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Audience audience, CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.notice()
            .message(messages -> messages.other().flyMessage())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().formatEnable() : messages.format().formatDisable())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().flySetMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().formatEnable() : messages.format().formatDisable())
            .audience(audience)
            .send();
    }
}
