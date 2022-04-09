package com.eternalcode.core.chat.adventure;

import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.Notification;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Set;
import java.util.function.Function;

public class AdventureNotification extends Notification {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final Component component;

    public AdventureNotification(Component component, NoticeType... types) {
        super(MINI_MESSAGE.serialize(component), types);
        this.component = component;
    }

    public AdventureNotification(Component component, Set<NoticeType> types) {
        super(MINI_MESSAGE.serialize(component), types);
        this.component = component;
    }

    @Override
    public Notification edit(Function<String, String> edit) {
        return this;
    }

}
