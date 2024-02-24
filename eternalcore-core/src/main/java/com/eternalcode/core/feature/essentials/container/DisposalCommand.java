package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.commons.adventure.AdventureUtil;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Command(name = "disposal")
@Permission("eternalcore.disposal")
class DisposalCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final TranslationManager translationManager;
    private final UserManager userManager;
    private final Server server;

    @Inject
    DisposalCommand(MiniMessage miniMessage, TranslationManager translationManager, UserManager userManager, Server server, NoticeService noticeService) {
        this.miniMessage = miniMessage;
        this.translationManager = translationManager;
        this.userManager = userManager;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Opens a disposal")
    void execute(@Context Player player) {
        Language language = this.userManager.getUser(player.getUniqueId())
            .map(user -> user.getSettings().getLanguage())
            .orElse(Language.DEFAULT);

        Translation translation = this.translationManager.getMessages(language);
        Component component = this.miniMessage.deserialize(translation.inventory().disposalTitle());
        String serialize = AdventureUtil.SECTION_SERIALIZER.serialize(component);

        player.openInventory(this.server.createInventory(null, 54, serialize));

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(message -> message.container().genericContainerOpened())
            .send();
    }

}
