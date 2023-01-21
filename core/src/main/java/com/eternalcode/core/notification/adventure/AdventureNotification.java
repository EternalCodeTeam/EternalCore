package com.eternalcode.core.notification.adventure;

import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Set;
import java.util.function.UnaryOperator;

public class AdventureNotification extends Notification {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public AdventureNotification(Component component, NoticeType... types) {
        super(MINI_MESSAGE.serialize(component), types);
    }

    public AdventureNotification(Component component, Set<NoticeType> types) {
        super(MINI_MESSAGE.serialize(component), types);
    }

    @Override
    public Notification fork(UnaryOperator<String> fork) {
        return this;
    }

}
