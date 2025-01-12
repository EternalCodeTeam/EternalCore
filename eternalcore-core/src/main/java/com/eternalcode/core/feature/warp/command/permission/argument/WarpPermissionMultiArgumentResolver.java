package com.eternalcode.core.feature.warp.command.permission.argument;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.MultipleArgumentResolver;
import dev.rollczi.litecommands.input.raw.RawInput;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.range.Range;
import dev.rollczi.litecommands.suggestion.Suggestion;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.command.CommandSender;

@LiteArgument(type = WarpPermissionEntry.class)
public class WarpPermissionMultiArgumentResolver
    implements MultipleArgumentResolver<CommandSender, WarpPermissionEntry> {

    private static final String WARP_PLACEHOLDER_PREFIX = "{WARP}";
    private static final String PERMISSION_PLACEHOLDER_PREFIX = "{PERMISSION}";

    private final PluginConfiguration config;
    private final WarpService warpService;
    private final NoticeService noticeService;
    private final ViewerService viewerService;

    @Inject
    public WarpPermissionMultiArgumentResolver(
        PluginConfiguration config,
        WarpService warpService,
        NoticeService noticeService,
        ViewerService viewerService
    ) {
        this.config = config;
        this.warpService = warpService;
        this.noticeService = noticeService;
        this.viewerService = viewerService;
    }

    @Override
    public ParseResult<WarpPermissionEntry> parse(
        Invocation<CommandSender> invocation,
        Argument<WarpPermissionEntry> argument,
        RawInput rawInput
    ) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        if (!rawInput.hasNext()) {
            return ParseResult.failure(this.noticeService.create()
                .notice(translation -> translation.warp().missingWarpName())
                .viewer(viewer)
            );
        }

        String warpName = rawInput.next();
        Optional<Warp> warp = this.warpService.findWarp(warpName);

        if (warp.isEmpty()) {
            return ParseResult.failure(
                this.noticeService.create()
                    .notice(translation -> translation.warp().notExist())
                    .placeholder(WARP_PLACEHOLDER_PREFIX, warpName)
                    .viewer(viewer)
            );
        }

        if (!rawInput.hasNext()) {
            Collection<String> permissions = warp.get().getPermissions();

            if (permissions.isEmpty()) {
                return ParseResult.failure(this.noticeService.create()
                    .notice(translation -> translation.warp().noPermissionAssigned())
                    .placeholder(WARP_PLACEHOLDER_PREFIX, warpName)
                    .viewer(viewer)
                );
            }

            return ParseResult.failure(this.noticeService.create()
                .notice(translation -> translation.warp().listPermission())
                .placeholder(WARP_PLACEHOLDER_PREFIX, warpName)
                .placeholder(PERMISSION_PLACEHOLDER_PREFIX, String.join(this.config.format.separator, permissions))
                .viewer(viewer)
            );
        }

        String permission = rawInput.next();

        return ParseResult.success(new WarpPermissionEntry(warp.get(), permission));
    }

    @Override
    public Range getRange(Argument<WarpPermissionEntry> argument) {
        return Range.of(2);
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<WarpPermissionEntry> argument,
        SuggestionContext context
    ) {
        Suggestion current = context.getCurrent();
        int index = current.lengthMultilevel();

        if (index == 1) {
            return SuggestionResult.of(
                this.warpService.getWarps().stream()
                    .map(Warp::getName)
                    .toList()
            );
        }

        if (index == 2) {
            String warpName = current.multilevelList().get(0);
            Optional<Warp> warpOptional = this.warpService.findWarp(warpName);

            if (warpOptional.isEmpty()) {
                return SuggestionResult.empty();
            }

            Warp warp = warpOptional.get();
            Collection<String> permissions = warp.getPermissions();

            if (permissions.isEmpty()) {
                return SuggestionResult.empty();
            }

            return SuggestionResult.of(permissions).appendLeft(warpName);
        }

        return SuggestionResult.empty();
    }
}
