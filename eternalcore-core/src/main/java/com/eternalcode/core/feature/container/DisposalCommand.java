package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.commons.adventure.AdventureUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "disposal")
@Permission("eternalcore.disposal")
class DisposalCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final TranslationManager translationManager;
    private final Server server;

    @Inject
    DisposalCommand(MiniMessage miniMessage, TranslationManager translationManager, Server server, NoticeService noticeService) {
        this.miniMessage = miniMessage;
        this.translationManager = translationManager;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Opens a disposal")
    void execute(@Context Player player) {
        this.openDisposal(player);
    }

    @Execute
    @DescriptionDocs(description = "Opens a disposal for target")
    @Permission("eternalcore.disposal.other")
    void executeOther(@Context CommandSender commandSender, @Arg Player target) {
        this.openDisposal(target);

        this.noticeService.create()
            .sender(commandSender)
            .notice(message -> message.container().targetDisposalOpened())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openDisposal(Player player) {
        Translation translation = this.translationManager.getMessages();
        Component containerTitle= this.miniMessage.deserialize(translation.inventory().disposalTitle());
        String serializedContainerTitle = AdventureUtil.SECTION_SERIALIZER.serialize(containerTitle);

        player.openInventory(this.server.createInventory(null, 54, serializedContainerTitle));

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(message -> message.container().disposalOpened())
            .send();
    }
}
