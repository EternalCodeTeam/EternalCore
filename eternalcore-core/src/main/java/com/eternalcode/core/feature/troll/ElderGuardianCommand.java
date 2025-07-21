package com.eternalcode.core.feature.troll;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "elderguardian", aliases = {"elder-guardian"})
@Permission("eternalcore.troll.elderguardian")
@PermissionDocs(
    name = "Elder Guardian",
    permission = "eternalcore.troll.elderguardian",
    description = "Allows you to show an elder guardian to a player")
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
            AdditionalContainerPaper.ELDER_GUARDIAN_SILENT.open(target);

            this.noticeService.create()
                .notice(translation -> translation.troll().elderGuardianShownSilently())
                .player(sender.getUniqueId())
                .placeholder("{PLAYER}", target.getName())
                .send();
        } else {
            AdditionalContainerPaper.ELDER_GUARDIAN.open(target);

            this.noticeService.create()
                .notice(translation -> translation.troll().elderGuardianShown())
                .player(sender.getUniqueId())
                .placeholder("{PLAYER}", target.getName())
                .send();
        }

    }

}
