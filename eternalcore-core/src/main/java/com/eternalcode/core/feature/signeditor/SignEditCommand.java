package com.eternalcode.core.feature.signeditor;

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
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;

@Command(name = "signedit")
@Permission("eternalcode.signedit")
public class SignEditCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    public SignEditCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute
    void execute(@Context Player player, @Arg Integer index, @Join String text) {
        Block targetBlock = player.getTargetBlock(null, 5);

        if (!(targetBlock.getState() instanceof Sign sign)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.signEdit().noSignFound())
                .send();
            return;
        }

        SignSide frontSide = sign.getSide(Side.FRONT);
        if (index < 0 || index >= frontSide.getLines().length) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.signEdit().invalidIndex())
                .send();
            return;
        }

        frontSide.setLine(index, this.miniMessage.deserialize(text).toString());
        sign.update();

        this.noticeService.create()
            .player(player.getUniqueId())
            .placeholder("{INDEX}", index.toString())
            .placeholder("{TEXT}", text)
            .notice(translation -> translation.signEdit().lineSet())
            .send();
    }
}
