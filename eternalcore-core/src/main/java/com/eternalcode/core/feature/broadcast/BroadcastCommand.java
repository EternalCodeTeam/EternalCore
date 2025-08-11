package com.eternalcode.core.feature.broadcast;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.bossbar.BossBar;

import java.time.Duration;
import java.util.function.Function;

@Command(name = "broadcast", aliases = "bc")
@Permission("eternalcore.broadcast")
class BroadcastCommand {

    private final NoticeService noticeService;
    private final BroadcastSettings settings;

    @Inject
    BroadcastCommand(NoticeService noticeService, BroadcastSettings settings) {
        this.noticeService = noticeService;
        this.settings = settings;
    }

    @Execute
    @DescriptionDocs(description = "Broadcasts a CHAT message to all players.", arguments = "[-raw] <text>")
    void executeChat(@Flag("-raw") boolean raw, @Join String text) {
        this.sendBroadcast(formatted -> Notice.chat(formatted), text, raw);
    }

    @Execute(name = "title")
    @DescriptionDocs(description = "Broadcasts a TITLE message to all players.", arguments = "[-raw] <text>")
    void executeTitle(@Flag("-raw") boolean raw, @Join String title) {
        this.sendBroadcast(formatted -> Notice.title(formatted, "", this.settings.titleFadeIn(), this.settings.titleStay(), this.settings.titleFadeOut()), title, raw);
    }

    @Execute(name = "subtitle")
    @DescriptionDocs(description = "Broadcasts a SUBTITLE message to all players.", arguments = "[-raw] <text>")
    void executeSubtitle(@Flag("-raw") boolean raw, @Join String subtitle) {
        this.sendBroadcast(formatted -> Notice.title("", formatted, this.settings.titleFadeIn(), this.settings.titleStay(), this.settings.titleFadeOut()), subtitle, raw);
    }

    @Execute(name = "actionbar")
    @DescriptionDocs(description = "Broadcasts a ACTIONBAR message to all players.", arguments = "[-raw] <text>")
    void executeActionBar(@Flag("-raw") boolean raw, @Join String text) {
        this.sendBroadcast(formatted -> Notice.actionbar(formatted), text, raw);
    }

    @Execute(name = "bossbar")
    @DescriptionDocs(description = "Broadcasts a BOSSBAR message to all players.", arguments = "[-raw] <color> <duration> <text>")
    void executeBossBar(
        @Flag("-raw") boolean raw,
        @Arg BossBar.Color color,
        @Arg Duration duration,
        @Join String text
    ) {
        this.sendBroadcast(formatted -> Notice.bossBar(
            color,
            duration,
            1.0,
            formatted
        ), text, raw);
    }

     private void sendBroadcast(Function<String, Notice> converter, String text, boolean raw) {
        this.noticeService.create()
            .notice(translation -> converter.apply(raw ? text : translation.broadcast().messageFormat()))
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
