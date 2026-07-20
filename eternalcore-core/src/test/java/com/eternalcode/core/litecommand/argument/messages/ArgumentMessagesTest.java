package com.eternalcode.core.litecommand.argument.messages;

import static org.assertj.core.api.Assertions.assertThat;

import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.resolver.chat.ChatContent;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class ArgumentMessagesTest {

    @Test
    void shouldProvideGenericPermissionDefaults() throws ReflectiveOperationException {
        Method method = ArgumentMessages.class.getDeclaredMethod("permissionMessageGeneric");

        assertThat(this.chat((Notice) method.invoke(new ENArgumentMessages())))
            .isEqualTo("<red>✘ <dark_red>You don't have permission to perform this command!");
        assertThat(this.chat((Notice) method.invoke(new PLArgumentMessages())))
            .isEqualTo("<red>✘ <dark_red>Nie masz uprawnień do tej komendy!");
    }

    private String chat(Notice notice) {
        ChatContent content = (ChatContent) notice.parts().getFirst().content();
        return content.messages().getFirst();
    }
}
