package com.eternalcode.core.feature.warp.command;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;

@LiteArgument(type = Warp.class)
class WarpArgument extends AbstractViewerArgument<Warp> {

    private final WarpService warpService;
    private final NoticeService noticeService;

    @Inject
    WarpArgument(
        WarpService warpService,
        TranslationManager translationManager,
        NoticeService noticeService
    ) {
        super(translationManager);
        this.warpService = warpService;
        this.noticeService = noticeService;
    }

    @Override
    public ParseResult<Warp> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Optional<Warp> warpOption = this.warpService.findWarp(argument);

        return warpOption.map(ParseResult::success)
            .orElseGet(() -> {
                EternalCoreBroadcast<Viewer, Translation, ?> warpNotExistNotice = this.noticeService.create()
                    .sender(invocation.sender())
                    .notice(translation.warp().notExist())
                    .placeholder("{WARP}", argument);

                return ParseResult.failure(warpNotExistNotice);
            });
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Warp> argument,
        SuggestionContext context
    ) {
        return SuggestionResult.of(
            this.warpService.getWarps().stream()
                .map(Warp::getName)
                .collect(Collectors.toList())
        );
    }
}

