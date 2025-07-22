package com.eternalcode.core.feature.troll.elderguardian;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.paper.PaperOverlay;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "elderguardian", aliases = {"elder-guardian"})
@Permission("eternalcore.troll.elderguardian")
@Compatibility(from = @Version(minor = 19, patch = 2)) // Requires Minecraft 1.19.2 or higher
public class ElderGuardianCommand {

    private final NoticeService noticeService;

    @Inject
    public ElderGuardianCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = {"Show an elder guardian to a player"}, arguments = {"<player>", "[-s]"})
    void execute(@Context Player sender, @Arg Player target, @Flag("-s") boolean silent) {
        if (silent) {
            PaperOverlay.ELDER_GUARDIAN_SILENT.show(target);

            this.noticeService.create()
                .notice(translation -> translation.troll().elderGuardian().elderGuardianShownSilently())
                .player(sender.getUniqueId())
                .placeholder("{PLAYER}", target.getName())
                .send();
            return;
        }

        PaperOverlay.ELDER_GUARDIAN.show(target);
        this.noticeService.create()
            .notice(translation -> translation.troll().elderGuardian().elderGuardianShown())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
