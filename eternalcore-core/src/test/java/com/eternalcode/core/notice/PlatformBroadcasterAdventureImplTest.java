package com.eternalcode.core.notice;

import com.eternalcode.core.test.MockAudienceProvider;
import com.eternalcode.core.test.MockAudienceProvider.MockAudience;
import com.eternalcode.core.test.MockViewer;
import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PlatformBroadcasterAdventureImplTest {

    MockAudienceProvider audienceProvider;
    PlatformBroadcaster announcer;

    @BeforeEach
    void setUp() {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        this.audienceProvider = new MockAudienceProvider();
        this.announcer = new PlatformBroadcasterAdventureImpl(audienceProvider, miniMessage);
    }

    @Test
    @DisplayName("Announce a chat notification")
    void testAnnounceChat() {
        Viewer viewer = MockViewer.player("Test", UUID.randomUUID());
        MockAudience mockAudience = audienceProvider.join(viewer);

        Notice notice = Notice.chat("Hello, world!");
        announcer.announce(viewer, notice);

        mockAudience.assertMessage("Hello, world!");
    }

    @Test
    @DisplayName("Announce multiple chat notifications")
    void testAnnounceChatMultiple() {
        Viewer viewer = MockViewer.player("Test", UUID.randomUUID());
        MockAudience mockAudience = audienceProvider.join(viewer);

        Notice notice = Notice.builder()
            .chat("Siema siema o teh poże każdy łep lakiemu odstrzelić może")
            .chat("A ty nie możesz bo jesteś słaby")
            .build();

        announcer.announce(viewer, notice);

        mockAudience.assertMessage("Siema siema o teh poże każdy łep lakiemu odstrzelić może");
        mockAudience.assertMessage("A ty nie możesz bo jesteś słaby");
    }

}
