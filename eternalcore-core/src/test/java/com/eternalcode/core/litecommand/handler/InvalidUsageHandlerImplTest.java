package com.eternalcode.core.litecommand.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.provider.NoticeProvider;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.command.CommandRoute;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

class InvalidUsageHandlerImplTest {

    private NoticeService noticeService;
    private ViewerService viewerService;
    private Invocation<CommandSender> invocation;
    private ResultHandlerChain<CommandSender> chain;
    private CommandRoute<CommandSender> route;
    private Viewer viewer;
    private InvalidUsageHandlerImpl handler;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        this.noticeService = mock(NoticeService.class, Answers.RETURNS_DEEP_STUBS);
        this.viewerService = mock(ViewerService.class);
        this.invocation = mock(Invocation.class);
        this.chain = mock(ResultHandlerChain.class);
        this.route = mock(CommandRoute.class);
        this.viewer = mock(Viewer.class);
        this.handler = new InvalidUsageHandlerImpl(this.viewerService, this.noticeService);

        CommandSender sender = mock(CommandSender.class);
        when(this.invocation.sender()).thenReturn(sender);
        when(this.viewerService.any(sender)).thenReturn(this.viewer);
    }

    @Test
    void shouldUseGenericPermissionNoticeForEmptySchematic() {
        InvalidUsage<CommandSender> usage = this.usage(List.of());
        Translation translation = mock(Translation.class, Answers.RETURNS_DEEP_STUBS);
        Notice genericPermission = Notice.chat("generic-permission");
        when(translation.argument().permissionMessageGeneric()).thenReturn(genericPermission);
        ArgumentCaptor<NoticeProvider<Translation>> providerCaptor = this.providerCaptor();
        ArgumentCaptor<Formatter[]> formatterCaptor = ArgumentCaptor.forClass(Formatter[].class);

        this.handler.handle(this.invocation, usage, this.chain);

        verify(this.noticeService).viewer(eq(this.viewer), providerCaptor.capture(), formatterCaptor.capture());
        assertThat(providerCaptor.getValue().extract(translation)).isSameAs(genericPermission);
        assertThat(formatterCaptor.getValue()).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldUseSingleUsageNoticeForOneEntrySchematic() {
        InvalidUsage<CommandSender> usage = this.usage(List.of("/jail list"));
        EternalCoreBroadcast<Viewer, Translation, ?> broadcast = mock(
            EternalCoreBroadcast.class,
            Answers.RETURNS_SELF
        );
        doReturn(broadcast).when(this.noticeService).create();

        this.handler.handle(this.invocation, usage, this.chain);

        verify(this.noticeService).create();
        verify(this.noticeService, never()).viewer(any(), any(), any(Formatter[].class));
    }

    @Test
    void shouldUseHeaderAndEntriesForMultipleEntrySchematic() {
        InvalidUsage<CommandSender> usage = this.usage(List.of("/jail list", "/jail setup"));
        Translation translation = mock(Translation.class, Answers.RETURNS_DEEP_STUBS);
        Notice header = Notice.chat("usage-header");
        Notice entry = Notice.chat("usage-entry");
        Notice genericPermission = Notice.chat("generic-permission");
        when(translation.argument().usageMessageHead()).thenReturn(header);
        when(translation.argument().usageMessageEntry()).thenReturn(entry);
        when(translation.argument().permissionMessageGeneric()).thenReturn(genericPermission);
        ArgumentCaptor<NoticeProvider<Translation>> providerCaptor = this.providerCaptor();
        ArgumentCaptor<Formatter[]> formatterCaptor = ArgumentCaptor.forClass(Formatter[].class);

        this.handler.handle(this.invocation, usage, this.chain);

        verify(this.noticeService, times(3))
            .viewer(eq(this.viewer), providerCaptor.capture(), formatterCaptor.capture());
        assertThat(providerCaptor.getAllValues())
            .extracting(provider -> provider.extract(translation))
            .containsExactly(header, entry, entry)
            .doesNotContain(genericPermission);
        assertThat(formatterCaptor.getAllValues())
            .extracting(formatters -> formatters.length)
            .containsExactly(0, 1, 1);
    }

    private InvalidUsage<CommandSender> usage(List<String> schematics) {
        return new InvalidUsage<>(
            InvalidUsage.Cause.MISSING_ARGUMENT,
            this.route,
            new Schematic(schematics)
        );
    }

    @SuppressWarnings("unchecked")
    private ArgumentCaptor<NoticeProvider<Translation>> providerCaptor() {
        return ArgumentCaptor.forClass(NoticeProvider.class);
    }
}
