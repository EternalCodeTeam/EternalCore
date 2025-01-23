package com.eternalcode.core.feature.signeditor;

import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

@Command(name = "signeditor")
@Permission("eternalcode.signeditor")
@Compatibility(to = @Version(minor = 19, patch = 4))
public class SignEditorCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    public SignEditorCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute(name = "setline")
    void execute(@Context Player player, @Arg int index, @Join String text) {
        Block targetBlock = player.getTargetBlock(null, 5);

        if (!(targetBlock.getState() instanceof Sign sign)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.signEditor().noSignFound())
                .send();
            return;
        }

        if (index < 0 || index >= sign.getLines().length) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.signEditor().invalidIndex())
                .send();
            return;
        }

        sign.setLine(index, this.miniMessage.deserialize(text).toString());
        sign.update();

        this.noticeService.create()
            .player(player.getUniqueId())
            .placeholder("{INDEX}", String.valueOf(index))
            .placeholder("{TEXT}", text)
            .notice(translation -> translation.signEditor().lineSet())
            .send();
    }
}
