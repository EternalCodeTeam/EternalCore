package com.eternalcode.core.configuration.composer;

import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.Notification;
import panda.std.Result;
import panda.utilities.text.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationComposer implements SimpleComposer<Notification> {

    private static final String SERIALIZE_FORMAT = "[%s]%s";
    private static final Pattern DESERIALIZE_PATTERN = Pattern.compile("\\[([^]]*)](.*)");

    @Override
    public Result<Notification, Exception> deserialize(String source) {
        Matcher matcher = DESERIALIZE_PATTERN.matcher(source);

        if (!matcher.matches()) {
            return Result.ok(Notification.chat(source));
        }

        String types = matcher.group(1);
        String message = matcher.group(2);
        List<NoticeType> noticeTypes = new ArrayList<>();

        for (String type : types.replace(" ", "").split(",")) {
            try {
                NoticeType noticeType = NoticeType.valueOf(type);

                noticeTypes.add(noticeType);
            }
            catch (IllegalArgumentException exception) {
                return Result.error(new IllegalStateException("Value '" + type + "' is not a notification type! Available types: " + Joiner.on(", ").join(NoticeType.values())));
            }
        }

        return Result.ok(Notification.of(message, noticeTypes));
    }

    @Override
    public Result<String, Exception> serialize(Notification notification) {
        if (notification.getTypes().contains(NoticeType.CHAT) && notification.getTypes().size() == 1) {
            return Result.ok(notification.getMessage());
        }

        return Result.ok(SERIALIZE_FORMAT.formatted(Joiner.on(", ").join(notification.getTypes()), notification.getMessage()));
    }
    
}
