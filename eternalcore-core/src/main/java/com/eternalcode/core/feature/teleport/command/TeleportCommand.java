package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;

@Command(name = "teleport", aliases = { "tp" })
@Permission("eternalcore.teleport")
class TeleportCommand {

    private static final Placeholders<TeleportContext> CONTEXT = Placeholders.<TeleportContext>builder()
        .with("{PLAYER}", context -> context.player.getName())
        .with("{X}", context -> String.valueOf(context.location.getX()))
        .with("{Y}", context -> String.valueOf(context.location.getY()))
        .with("{Z}", context -> String.valueOf(context.location.getZ()))
        .with("{WORLD}", context -> context.location.getWorld() != null ? context.location.getWorld().getName() : "null")
        .build();

    private static final Placeholders<Player> OTHER_PLAYER = Placeholders.<Player>builder()
        .with("{ARG-PLAYER}", Player::getName)
        .build();

    private record TeleportContext(Player player, Location location) {}

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    @Inject
    TeleportCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport player to player", arguments = "<player> <target-player>")
    void other(@Context Viewer sender, @Arg Player player, @Arg Player target) {
        this.teleportService.teleport(player, target.getLocation());

        Formatter formatter = this.formatter(player, target.getLocation());
        Formatter otherFormatter = OTHER_PLAYER.toFormatter(target);

        this.noticeService.viewer(sender, translation -> translation.teleport().teleportedPlayerToPlayer(), formatter, otherFormatter);
    }

    @Execute
    @DescriptionDocs(description = "Teleport to specified player", arguments = "<player>")
    void execute(@Context Player sender, @Context Viewer senderViewer, @Arg Player player) {
        this.teleportService.teleport(sender, player.getLocation());

        Formatter formatter = this.formatter(player, player.getLocation());

        this.noticeService.viewer(senderViewer, translation -> translation.teleport().teleportedToPlayer(), formatter);
    }

    @Execute
    @DescriptionDocs(description = "Teleport to specified location and world", arguments = "<x> <y> <z> <world>")
    void to(@Context Player sender, @Arg Location location, @Arg World world) {
        location.setWorld(world);

        this.teleportService.teleport(sender, location);

        Formatter formatter = this.formatter(sender, location);

        this.noticeService.player(sender.getUniqueId(), translation -> translation.teleport().teleportedToCoordinates(), formatter);
    }

    @Execute
    @DescriptionDocs(description = "Teleport player to specified player, location and world", arguments = "<x> <y> <z> <player> <world>")
    void to(@Context Viewer sender, @Arg Location location, @Arg Player player, @Arg World world) {
        location.setWorld(world);

        Formatter formatter = this.formatter(player, location);

        this.teleportService.teleport(player, location);
        this.noticeService.viewer(sender, translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates(), formatter);
    }

    private Formatter formatter(Player player, Location location) {
        return CONTEXT.toFormatter(new TeleportContext(player, location));
    }

}
