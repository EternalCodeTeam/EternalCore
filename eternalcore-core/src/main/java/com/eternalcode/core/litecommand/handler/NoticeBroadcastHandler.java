package com.eternalcode.core.litecommand.handler;

import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeBroadcast;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;

@LiteHandler(NoticeBroadcast.class)
public class NoticeBroadcastHandler implements Handler<CommandSender, NoticeBroadcast> {

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, NoticeBroadcast value) {
        value.send();
    }

}
