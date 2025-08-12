package com.eternalcode.core.feature.butcher;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.stream.IntStream;
import org.bukkit.command.CommandSender;

@LiteArgument(type = int.class, name = ButcherArgument.KEY)
class ButcherArgument extends AbstractViewerArgument<Integer> {

    static final String KEY = "chunks";
    private final ButcherSettings butcherSettings;
    private final NoticeService noticeService;
    private final ViewerService viewerService;

    @Inject
    ButcherArgument(TranslationManager translationManager, ButcherSettings butcherSettings, NoticeService noticeService, ViewerService viewerService) {
        super(translationManager);
        this.butcherSettings = butcherSettings;
        this.noticeService = noticeService;
        this.viewerService = viewerService;
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        try {
            int value = Integer.parseInt(argument);

            int safeChunkNumber = this.butcherSettings.safeChunkNumber();

            if (value <= 0) {
                return ParseResult.failure(translation.argument().incorrectNumberOfChunks());
            }

            if (value > safeChunkNumber) {
                NoticeBroadcast placeholder = this.noticeService.create()
                    .notice(translate -> translate.player().safeChunksMessage())
                    .viewer(viewer)
                    .placeholder("{SAFE_CHUNKS}", String.valueOf(safeChunkNumber));

                return ParseResult.failure(placeholder);
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.argument().incorrectNumberOfChunks());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        int safeChunkNumber = this.butcherSettings.safeChunkNumber();

        int range = (safeChunkNumber / 5) + 1;

        return IntStream.range(1, range)
            .map(i -> Math.min(i * 5, safeChunkNumber))
            .mapToObj(String::valueOf)
            .collect(SuggestionResult.collector());
    }
}
