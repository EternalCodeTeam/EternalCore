package com.eternalcode.core.feature.teleportrequest.messages;

import static org.assertj.core.api.Assertions.assertThat;

import com.eternalcode.multification.notice.resolver.chat.ChatContent;
import org.junit.jupiter.api.Test;

class ENTeleportRequestMessagesTest {

    @Test
    void shouldDescribeTeleportingToTarget() {
        ENTeleportRequestMessages messages = new ENTeleportRequestMessages();
        ChatContent content = (ChatContent) messages.tpaSentMessage().parts().getFirst().content();

        assertThat(content.messages().getFirst())
            .contains("request to teleport to player")
            .contains("{PLAYER}")
            .doesNotContain("to teleport to you");
    }
}
