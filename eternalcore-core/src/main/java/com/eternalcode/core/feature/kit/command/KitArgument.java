package com.eternalcode.core.feature.kit.command;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Locale;
import org.bukkit.command.CommandSender;

@LiteArgument(type = Kit.class)
class KitArgument extends AbstractViewerArgument<Kit> {

    private final KitService kitService;
    private final NoticeService noticeService;

    @Inject
    KitArgument(KitService kitService, TranslationManager translationManager, NoticeService noticeService) {
        super(translationManager);
        this.kitService = kitService;
        this.noticeService = noticeService;
    }

    @Override
    public ParseResult<Kit> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        return this.kitService.findKit(argument.toLowerCase(Locale.ROOT))
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(this.noticeService.create()
                .sender(invocation.sender())
                .notice(translation.kit().notFound())
                .placeholder(KitNotices.KIT, argument)));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Kit> argument, SuggestionContext context) {
        CommandSender sender = invocation.sender();

        return SuggestionResult.of(this.kitService.getKits().stream()
            .filter(kit -> sender.hasPermission(kit.permission()))
            .map(Kit::name)
            .toList());
    }
}
