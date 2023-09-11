package com.eternalcode.core.feature.home;

import com.eternalcode.core.command.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import panda.std.Result;

@LiteArgument(type = Home.class)
@ArgumentName("name")
class HomeArgument extends AbstractViewerArgument<Home> {

    private final HomeManager homeManager;

    @Inject
    HomeArgument(ViewerProvider viewerProvider, TranslationManager translationManager, HomeManager homeManager) {
        super(viewerProvider, translationManager);
        this.homeManager = homeManager;
    }

    @Override
    public Result<Home, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());

        return this.homeManager.getHome(viewer.getUniqueId(), argument)
            .toResult(translation.home().notExist());
    }

}
