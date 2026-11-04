package com.eternalcode.core.feature.kit;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "kit")
@Permission("eternalcore.kit")
public class KitCommand {

    private final NoticeService noticeService;
    private final KitService kitService;

    @Inject
    public KitCommand(NoticeService noticeService, KitService kitService) {
        this.noticeService = noticeService;
        this.kitService = kitService;
    }

    @Execute
    void list(@Sender Player player) {
        Map<String, Kit> permissionsList = kitService.getPermissionsList();
        List<String> availableKits = new ArrayList<>();

        permissionsList.forEach((permission, kit) -> {
            if (player.hasPermission(permission)) {
                availableKits.add(kit.name());
            }
        });

        Component newName;

        player.setDisplayName();

        UUID uniqueId = player.getUniqueId();

        if (availableKits.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitNoneAvailable())
                .player(uniqueId)
                .send();
            return;
        }

        // Send the header
        this.noticeService.create()
            .notice(translation -> translation.kit().kitAvailableListHeader())
            .player(uniqueId)
            .send();

        // Send a clickable entry for each kit
        for (String kitName : availableKits) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitAvailableListEntry())
                .placeholder("{KIT}", kitName)
                .player(uniqueId)
                .send();
        }
    }

    @Execute
    void kit(@Sender Player player, @Arg("kit name") Kit kit) {

        UUID uniqueId = player.getUniqueId();

        if (!player.hasPermission(kit.permission())) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitNoPermission())
                .placeholder("{PERMISSION}", kit.permission())
                .placeholder("{KIT}", kit.name())
                .player(uniqueId)
                .send();

            return;
        }

        boolean canKit = this.kitService.canKit(uniqueId, kit.name());

        if (!canKit) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitCooldownActive())
                .placeholder("{PERMISSION}", kit.permission())
                .placeholder("{KIT}", kit.name())
                .player(uniqueId)
                .send();

            return;
        }


        boolean successfullyKitted = this.kitService.kitPlayer(player, kit);

        if (successfullyKitted) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitReceived())
                .placeholder("{PERMISSION}", kit.permission())
                .placeholder("{KIT}", kit.name())
                .player(uniqueId)
                .send();

            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.kit().kitReceiveFailed())
            .placeholder("{PERMISSION}", kit.permission())
            .placeholder("{KIT}", kit.name())
            .player(uniqueId)
            .send();

        return;

    }

    @Execute
    @Permission("eternalcore.kit.other")
    void kitOtherPlayer(@Sender CommandSender sender, @Arg("player") Player player, @Arg("kit")Kit kit) {

        UUID targetUniqueId = player.getUniqueId();

        if (this.kitService.kitPlayer(player, kit)) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitGivenToTargetPlayer())
                .placeholder("{PERMISSION}", kit.permission())
                .placeholder("{KIT}", kit.name())
                .sender(sender)
                .send();

            this.noticeService.create()
                .notice(translation -> translation.kit().kitReceived())
                .placeholder("{PERMISSION}", kit.permission())
                .placeholder("{KIT}", kit.name())
                .player(targetUniqueId)
                .send();

            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.kit().kitGiveToTargetPlayerFailed())
            .placeholder("{PERMISSION}", kit.permission())
            .placeholder("{KIT}", kit.name())
            .sender(sender)
            .send();

        this.noticeService.create()
            .notice(translation -> translation.kit().kitReceiveFailed())
            .placeholder("{PERMISSION}", kit.permission())
            .placeholder("{KIT}", kit.name())
            .player(targetUniqueId)
            .send();

    }

}
