package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
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
import org.bukkit.command.CommandSender;

import java.util.stream.IntStream;

@LiteArgument(type = int.class, name = ButcherArgument.KEY)
class ButcherArgument extends AbstractViewerArgument<Integer> {

    static final String KEY = "chunks";
    private final PluginConfiguration pluginConfiguration;
    private final NoticeService noticeService;

    @Inject
    ButcherArgument(ViewerService viewerService, TranslationManager translationManager, PluginConfiguration pluginConfiguration, NoticeService noticeService) {
        super(viewerService, translationManager);
        this.pluginConfiguration = pluginConfiguration;
        this.noticeService = noticeService;
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        try {
            int value = Integer.parseInt(argument);

            int safeChunkNumber = this.pluginConfiguration.butcher.safeChunkNumber;

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
        int safeChunkNumber = this.pluginConfiguration.butcher.safeChunkNumber;

        int range = (safeChunkNumber / 5) + 1;

        return IntStream.range(1, range)
            .map(i -> Math.min(i * 5, safeChunkNumber))
            .mapToObj(String::valueOf)
            .collect(SuggestionResult.collector());
    }
}
