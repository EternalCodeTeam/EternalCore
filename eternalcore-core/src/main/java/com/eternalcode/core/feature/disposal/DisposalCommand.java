package com.eternalcode.core.feature.disposal;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Command(name = "disposal")
@Permission("eternalcore.disposal")
class DisposalCommand {

    private static final int DISPOSAL_INVENTORY_SIZE = 54;

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final TranslationManager translationManager;
    private final Server server;

    @Inject
    DisposalCommand(
        MiniMessage miniMessage,
        TranslationManager translationManager,
        Server server,
        NoticeService noticeService
    ) {
        this.miniMessage = miniMessage;
        this.translationManager = translationManager;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Opens a disposal")
    void execute(@Sender Player player) {
        this.openDisposal(player);
    }

    @Execute
    @DescriptionDocs(description = "Opens a disposal for target")
    @Permission("eternalcore.disposal.other")
    void executeOther(@Sender CommandSender commandSender, @Arg Player target) {
        this.openDisposal(target);

        this.noticeService.create()
            .sender(commandSender)
            .notice(message -> message.disposal().disposalForTargetOpened())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    private void openDisposal(Player player) {
        Inventory disposalInventory = this.createDisposalInventory();
        player.openInventory(disposalInventory);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(message -> message.disposal().disposalOpened())
            .send();
    }

    private Inventory createDisposalInventory() {
        Translation translation = this.translationManager.getMessages();
        Component containerTitle = this.miniMessage.deserialize(translation.disposal().disposalTitle());
        String serializedTitle = AdventureUtil.SECTION_SERIALIZER.serialize(containerTitle);

        return this.server.createInventory(null, DISPOSAL_INVENTORY_SIZE, serializedTitle);
    }
}
