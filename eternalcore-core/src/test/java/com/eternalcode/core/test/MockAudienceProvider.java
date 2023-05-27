package com.eternalcode.core.test;

import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.platform.facet.Facet;
import net.kyori.adventure.platform.facet.FacetAudience;
import net.kyori.adventure.platform.facet.FacetAudienceProvider;
import net.kyori.adventure.platform.facet.FacetBase;
import net.kyori.adventure.platform.facet.FacetComponentFlattener;
import net.kyori.adventure.platform.facet.FacetPointers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import panda.std.Blank;

import java.util.*;

public class MockAudienceProvider extends FacetAudienceProvider<Viewer, MockAudienceProvider.MockAudience> {

    private static final Collection<FacetComponentFlattener.Translator<Blank>> TRANSLATORS = Facet.of();
    private static final ComponentFlattener FLATTENER = FacetComponentFlattener.get(Blank.BLANK, TRANSLATORS);

    public MockAudienceProvider() {
        super(GlobalTranslator.renderer().mapContext(ptr -> ptr.getOrDefault(Identity.LOCALE, Locale.US)));
    }

    public MockAudience join(Viewer viewer) {
        this.addViewer(viewer);

        return this.viewers.get(viewer);
    }

    public void leave(Viewer viewer) {
        this.removeViewer(viewer);
    }

    @Override
    protected @NotNull MockAudienceProvider.MockAudience createAudience(@NotNull Collection<Viewer> viewers) {
        return new MockAudienceProvider.MockAudience(new MockAudienceProvider.Chat(), viewers);
    }

    @Override
    public @NotNull ComponentFlattener flattener() {
        return FLATTENER;
    }

    static final class ViewerPointers extends FacetBase<Viewer> implements Facet.Pointers<Viewer> {
        ViewerPointers() {
            super(Viewer.class);
        }

        @Override
        public void contributePointers(Viewer viewer, net.kyori.adventure.pointer.Pointers.Builder builder) {
            if (viewer.isConsole()) {
                builder.withStatic(FacetPointers.TYPE, FacetPointers.Type.CONSOLE);
            } else {
                builder.withDynamic(Identity.UUID, viewer::getUniqueId);
                builder.withStatic(FacetPointers.TYPE, FacetPointers.Type.PLAYER);
            }
        }
    }

    public class MockAudience extends FacetAudience<Viewer> {

        private final Chat chat;

        private MockAudience(Chat chat, Collection<Viewer> viewers) {
            super(
                MockAudienceProvider.this,
                viewers,
                List.of(chat),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(new ViewerPointers())
            );

            this.chat = chat;
        }

        public void assertMessage(String message) {
            if (!this.chat.messages.contains(message)) {
                throw new AssertionError("Expected message '" + message + "' but got " + this.chat.messages);
            }
        }
    }

    static class Chat implements Facet.Chat<Viewer, String> {

        private final List<String> messages = new ArrayList<>();

        @Override
        public void sendMessage(@NotNull Viewer viewer, @NotNull Identity source, @NotNull String message, @NotNull Object type) {
            messages.add(message);
        }

        @Override
        public @Nullable String createMessage(@NotNull Viewer viewer, @NotNull Component message) {
            return MiniMessage.miniMessage().serialize(message);
        }
    }

}
