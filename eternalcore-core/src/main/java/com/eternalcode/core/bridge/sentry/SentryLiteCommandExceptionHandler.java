package com.eternalcode.core.bridge.sentry;

import dev.rollczi.litecommands.handler.exception.ExceptionHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import io.sentry.Sentry;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;


public class SentryLiteCommandExceptionHandler implements ExceptionHandler<CommandSender, Throwable> {

    private final Logger logger;

    public SentryLiteCommandExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, Throwable t, ResultHandlerChain<CommandSender> resultHandlerChain) {
        Sentry.captureException(t);
        logger.severe("An error occurred while executing the command: " + t.getMessage());
    }
}
