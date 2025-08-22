package com.eternalcode.core.feature.signeditor;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
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
@Compatibility(from = @Version(minor = 20, patch = 0))
public class SignSideEditorCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    @Inject
    public SignSideEditorCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute(name = "set")
    void execute(@Sender Player player, @Arg Side side, @Arg int line, @Join String text) {
        Block targetBlock = player.getTargetBlockExact(5);

        if (targetBlock == null) {
            return;
        }

        if (!(targetBlock.getState() instanceof Sign sign)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.signEditor().noSignFound())
                .send();

            return;
        }

        SignSide signSide = sign.getSide(side);
        if (line < 1 || line > signSide.getLines().length) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .placeholder("{LINE}", String.valueOf(line))
                .notice(translation -> translation.signEditor().invalidIndex())
                .send();
            return;
        }

        signSide.setLine(line - 1, AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(text)));
        sign.update();

        this.noticeService.create()
            .player(player.getUniqueId())
            .placeholder("{LINE}", String.valueOf(line))
            .placeholder("{TEXT}", text)
            .notice(translation -> translation.signEditor().lineSet())
            .send();
    }
}
