package com.eternalcode.core.bridge.sentry;

import dev.rollczi.litecommands.LiteCommandsException;
import dev.rollczi.litecommands.handler.exception.ExceptionHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import io.sentry.Sentry;
import org.bukkit.command.CommandSender;

public class SentryLiteCommandExceptionHandler implements ExceptionHandler<CommandSender, LiteCommandsException> {

    @Override
    public void handle(Invocation<CommandSender> invocation, LiteCommandsException e, ResultHandlerChain<CommandSender> resultHandlerChain) {
        Sentry.captureException(e);
    }
}
