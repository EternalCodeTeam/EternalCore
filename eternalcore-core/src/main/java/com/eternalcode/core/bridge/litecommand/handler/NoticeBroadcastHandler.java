package com.eternalcode.core.bridge.litecommand.handler;

import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(NoticeBroadcast.class)
class NoticeBroadcastHandler implements ResultHandler<CommandSender, NoticeBroadcast> {

    @Override
    public void handle(Invocation<CommandSender> invocation, NoticeBroadcast result, ResultHandlerChain<CommandSender> chain) {
        result.send();
    }

}
