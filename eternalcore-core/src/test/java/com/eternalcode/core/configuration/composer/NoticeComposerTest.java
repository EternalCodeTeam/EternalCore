package com.eternalcode.core.configuration.composer;

import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import org.junit.jupiter.api.Test;
import panda.std.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoticeComposerTest {

    private static final NoticeComposer COMPOSER = new NoticeComposer();

    @Test
    void deserializeDefaultNotice() {
        this.assertDeserialize("Hello world!", "Hello world!", NoticeType.CHAT);
    }

    @Test
    void deserializeNoticeTitle() {
        this.assertDeserialize("[TITLE]Hello world!", "Hello world!", NoticeType.TITLE);
    }

    @Test
    void deserializeNoticeChatTitle() {
        this.assertDeserialize("[CHAT,TITLE]Hello world!", "Hello world!", NoticeType.CHAT, NoticeType.TITLE);
    }

    @Test
    void serializeDefaultNotice() {
        Notification notification = Notification.chat("Hello world!");

        this.assertSerialize(notification, "Hello world!");
    }

    @Test
    void serializeNoticeTitle() {
        Notification notification = Notification.title("Hello world!");

        this.assertSerialize(notification, "[TITLE]Hello world!");
    }

    @Test
    void serializeNoticeChatTitle() {
        Notification notification = Notification.of("Hello world!", NoticeType.CHAT, NoticeType.TITLE);

        this.assertSerialize(notification, "[CHAT, TITLE]Hello world!");
    }

    private void assertDeserialize(String source, String expectedMessage, NoticeType... expectedTypes) {
        Result<Notification, Exception> deserialize = COMPOSER.deserialize(source);

        assertTrue(deserialize.isOk());

        Notification notification = deserialize.get();

        assertEquals(expectedMessage, notification.getMessage());
        assertEquals(expectedTypes.length, notification.getTypes().size());

        for (NoticeType expectedType : expectedTypes) {
            assertTrue(notification.getTypes().contains(expectedType));
        }
    }

    private void assertSerialize(Notification notification, String expected) {
        Result<String, Exception> serialize = COMPOSER.serialize(notification);

        assertTrue(serialize.isOk());
        assertEquals(expected, serialize.get());
    }

}
