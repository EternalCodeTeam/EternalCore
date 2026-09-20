package com.eternalcode.core.feature.whois;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.user.User;
import com.eternalcode.core.util.date.DateFormatter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.PlayerIpService;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.UserManager;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Command(name = "whois")
@Permission("eternalcore.whois")
@PermissionDocs(
    name = "WhoIs IP",
    permission = WhoIsCommand.WHOIS_IP_PERMISSION,
    description = "Permission allows to see a player's IP address in /whois"
)
class WhoIsCommand {

    static final String WHOIS_IP_PERMISSION = "eternalcore.whois.ip";
    private static final String UNKNOWN_IP_LABEL = "N/A";

    private final NoticeService noticeService;
    private final UserManager userManager;
    private final PlayerIpService playerIpService;
    private final DateFormatter dateFormatter;

    @Inject
    WhoIsCommand(
        NoticeService noticeService,
        UserManager userManager,
        PlayerIpService playerIpService,
        DateFormatter dateFormatter
    ) {
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.playerIpService = playerIpService;
        this.dateFormatter = dateFormatter;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows information about player (with eternalcore.whois.ip can see Adress)", arguments = "<player>")
    void execute(@Sender CommandSender sender, @Arg Player player) {
        boolean canSeeIp = sender.hasPermission(WHOIS_IP_PERMISSION);

        Optional<String> ipOptional = this.playerIpService.findLastKnownIp(player.getUniqueId());
        User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName()).join();

        var notice = this.noticeService.create()
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
            .placeholder("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()))
            .placeholder("{SPEED}", String.valueOf(player.getFlySpeed()))
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
            .placeholder("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())))
            .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
            .placeholder("{LAST-SEEN}", this.dateFormatter.format(user.getLastSeen()))
            .placeholder("{ACCOUNT-CREATED}", this.dateFormatter.format(user.getAccountCreated()));

        if (canSeeIp) {
            notice = notice
                .placeholder("{IP}", ipOptional.orElse(UNKNOWN_IP_LABEL))
                .messages(translation -> translation.whois().infoWithIp());
        }
        else {
            notice = notice.messages(translation -> translation.whois().info());
        }

        notice.sender(sender).send();
    }
}
