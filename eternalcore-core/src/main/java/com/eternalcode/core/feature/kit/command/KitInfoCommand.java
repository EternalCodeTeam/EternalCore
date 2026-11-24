package com.eternalcode.core.feature.kit.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimService;
import com.eternalcode.core.feature.kit.KitDisplayFormatter;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.messages.KitGuiMessages;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "kit info")
@Permission("eternalcore.kit.info")
class KitInfoCommand {

    private final KitClaimService claimService;
    private final KitUsageService usageService;
    private final KitDisplayFormatter displayFormatter;
    private final KitNotices notices;
    private final TranslationManager translationManager;

    @Inject
    KitInfoCommand(
        KitClaimService claimService,
        KitUsageService usageService,
        KitDisplayFormatter displayFormatter,
        KitNotices notices,
        TranslationManager translationManager
    ) {
        this.claimService = claimService;
        this.usageService = usageService;
        this.displayFormatter = displayFormatter;
        this.notices = notices;
        this.translationManager = translationManager;
    }

    @Execute
    @DescriptionDocs(description = "Shows kit details and whether you can claim it", arguments = "<kit>")
    void info(@Sender Player player, @Arg Kit kit) {
        KitGuiMessages messages = this.translationManager.getMessages().kit().gui();
        Formatter formatter = new Formatter()
            .register(KitNotices.KIT, kit.displayName())
            .register(KitNotices.KIT_NAME, kit.name())
            .register(KitNotices.DESCRIPTION, String.join("<newline>", kit.description()))
            .register(KitNotices.TYPE, this.displayFormatter.type(messages, kit.type()))
            .register(KitNotices.COOLDOWN, this.displayFormatter.cooldown(messages, kit))
            .register(KitNotices.ITEMS, kit.items().size())
            .register(KitNotices.WORLDS, this.displayFormatter.worlds(messages, kit.requirements()))
            .register(KitNotices.GAME_MODES, this.displayFormatter.gameModes(messages, kit.requirements()))
            .register(KitNotices.PLAYTIME, this.displayFormatter.duration(messages, kit.requirements().minimumPlaytime()))
            .register(KitNotices.CLAIMS, this.usageService.getUsage(player.getUniqueId(), kit).claimCount())
            .register(KitNotices.STATUS, this.displayFormatter.status(messages, this.claimService.canClaim(player, kit)));

        this.notices.send(player, KitMessages::info, formatter);
    }
}
