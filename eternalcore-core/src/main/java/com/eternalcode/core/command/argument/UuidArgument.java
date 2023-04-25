package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import panda.std.Result;

import java.util.UUID;

@ArgumentName("uuid")
public class UuidArgument extends AbstractViewerArgument<UUID> {

    public UuidArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<UUID, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        try {
            return Result.ok(UUID.fromString(argument));
        }
        catch (IllegalArgumentException exception) {
            return Result.error(translation.argument().noArgument());
        }
    }
}
