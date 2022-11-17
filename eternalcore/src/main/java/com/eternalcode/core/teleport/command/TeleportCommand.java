package com.eternalcode.core.teleport.command;

import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.utilities.text.Formatter;

@Route(name = "teleport", aliases = { "tp" })
@Permission("eternalcore.teleport")
public class TeleportCommand {

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

    public TeleportCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute(required = 2)
    void other(Viewer sender, @Arg Player player, @Arg Player target) {
        this.teleportService.teleport(player, target.getLocation());

        Formatter formatter = this.formatter(player, target.getLocation());
        Formatter otherFormatter = OTHER_PLAYER.toFormatter(target);

        this.noticeService.viewer(sender, messages -> messages.teleport().teleportedPlayerToPlayer(), formatter, otherFormatter);
    }

    @Execute(required = 1)
    void execute(Player sender, Viewer senderViewer, @Arg Player player) {
        this.teleportService.teleport(sender, player.getLocation());

        Formatter formatter = this.formatter(sender, player.getLocation());

        this.noticeService.viewer(senderViewer, messages -> messages.teleport().teleportedToPlayer(), formatter);
    }

    @Execute(min = 3, max = 4)
    void to(Player sender, @Arg Location location, @Opt Option<World> world) {
        location.setWorld(world.orElseGet(sender.getWorld()));
        this.teleportService.teleport(sender, location);

        Formatter formatter = this.formatter(sender, location);

        this.noticeService.player(sender.getUniqueId(), messages -> messages.teleport().teleportedToCoordinates(), formatter);
    }

    @Execute(min = 4, max = 5)
    void to(Viewer sender, @Arg Location location, @Arg Player player, @Opt Option<World> world) {
        if (world.isPresent()) {
            location.setWorld(world.get());
        }

        Formatter formatter = this.formatter(player, location);

        this.teleportService.teleport(player, location);
        this.noticeService.viewer(sender, messages -> messages.teleport().teleportedSpecifiedPlayerToCoordinates(), formatter);
    }

    private Formatter formatter(Player player, Location location) {
        return CONTEXT.toFormatter(new TeleportContext(player, location));
    }

}
