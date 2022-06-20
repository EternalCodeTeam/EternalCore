package com.eternalcode.core.scheduler;

public interface Task {

    void cancel();

    boolean isCanceled();

    boolean isAsync();

}
