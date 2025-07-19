package com.eternalcode.core.publish;

class EmptyPublisher implements Publisher {

    @Override
    public void subscribe(Object subscriber) {
    }

    @Override
    public <E extends PublishEvent> E publish(E publishEvent) {
        return publishEvent;
    }

}
