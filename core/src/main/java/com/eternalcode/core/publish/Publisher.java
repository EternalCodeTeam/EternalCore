package com.eternalcode.core.publish;

public interface Publisher {

    void subscribe(Subscriber subscriber);

    void publish(PublishContent publishContent);

}
