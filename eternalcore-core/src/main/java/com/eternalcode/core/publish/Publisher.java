package com.eternalcode.core.publish;

public interface Publisher {

    void subscribe(Object subscriber);

    <E extends PublishEvent> E publish(E publishEvent);

    static Publisher empty() {
        return new EmptyPublisher();
    }

}
