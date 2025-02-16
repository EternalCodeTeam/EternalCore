package com.eternalcode.core.feature.sudo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@FeatureDocs(name = "Spy sudo", description = "Allows you to spy on other players' sudo commands execution, permission to spy: eternalcore.sudo.spy")
@Command(name = "sudo")
class SudoCommand {

    private final Server server;
    private final NoticeService noticeService;

    @Inject
    SudoCommand(Server server, NoticeService noticeService) {
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute(name = "-console")
    @Permission("eternalcore.sudo.console")
    @DescriptionDocs(description = "Execute command as console", arguments = "<command>")
    void console(@Context Viewer viewer, @Join String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
        this.sendSudoSpy(viewer, this.server.getConsoleSender(), command);
    }

    @Execute
    @Permission("eternalcore.sudo.player")
    @DescriptionDocs(description = "Execute command as player", arguments = "<player> <command>")
    void player(@Context Viewer viewer, @Arg Player target, @Join String command) {
        this.server.dispatchCommand(target, command);
        this.sendSudoSpy(viewer, target, command);
    }

    private void sendSudoSpy(Viewer viewer, CommandSender target, String command) {
        this.noticeService.create()
            .notice(translation -> translation.sudo().sudoMessage())
            .placeholder("{COMMAND}", command)
            .placeholder("{PLAYER}", viewer.getName())
            .placeholder("{TARGET}", target.getName())
            .viewer(viewer)
            .send();

        this.server.getOnlinePlayers().stream()
            .filter(player -> player.hasPermission("eternalcore.sudo.spy"))
            .forEach(player -> this.noticeService.create()
                .notice(translation -> translation.sudo().sudoMessageSpy())
                .placeholder("{COMMAND}", command)
                .placeholder("{PLAYER}", viewer.getName())
                .placeholder("{TARGET}", target.getName())
                .player(player.getUniqueId())
                .send());
    }
}
