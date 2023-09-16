package com.eternalcode.core.publish;

public interface Publisher {

    void subscribe(Object subscriber);

    void publish(PublishEvent publishEvent);

}
