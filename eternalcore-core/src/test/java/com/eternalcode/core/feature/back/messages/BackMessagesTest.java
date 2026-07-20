package com.eternalcode.core.feature.back.messages;

import static org.assertj.core.api.Assertions.assertThat;

import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.resolver.chat.ChatContent;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BackMessagesTest {

    @ParameterizedTest
    @MethodSource("englishMessages")
    void shouldUseInProgressEnglishWording(Notice notice) {
        assertThat(this.chat(notice))
            .containsIgnoringCase("teleporting")
            .doesNotContainIgnoringCase("have been teleported")
            .doesNotContainIgnoringCase("has been teleported");
    }

    @ParameterizedTest
    @MethodSource("polishMessages")
    void shouldUseInProgressPolishWording(Notice notice) {
        assertThat(this.chat(notice))
            .containsIgnoringCase("teleport")
            .doesNotContainIgnoringCase("zostałeś przeteleportowany")
            .doesNotContainIgnoringCase("został przeteleportowany");
    }

    private static Stream<Notice> englishMessages() {
        ENBackMessages messages = new ENBackMessages();
        return Stream.of(
            messages.teleportedToLastTeleportLocation(),
            messages.teleportedTargetPlayerToLastTeleportLocation(),
            messages.teleportedToLastTeleportLocationByAdmin(),
            messages.teleportedToLastDeathLocation(),
            messages.teleportedTargetPlayerToLastDeathLocation(),
            messages.teleportedToLastDeathLocationByAdmin()
        );
    }

    private static Stream<Notice> polishMessages() {
        PLBackMessages messages = new PLBackMessages();
        return Stream.of(
            messages.teleportedToLastTeleportLocation(),
            messages.teleportedTargetPlayerToLastTeleportLocation(),
            messages.teleportedToLastTeleportLocationByAdmin(),
            messages.teleportedToLastDeathLocation(),
            messages.teleportedTargetPlayerToLastDeathLocation(),
            messages.teleportedToLastDeathLocationByAdmin()
        );
    }

    private String chat(Notice notice) {
        ChatContent content = (ChatContent) notice.parts().getFirst().content();
        return content.messages().getFirst();
    }
}
