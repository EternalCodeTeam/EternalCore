package com.eternalcode.core.telemetry.sentry;

import io.sentry.Sentry;

public class SentryExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Sentry.captureException(throwable);
    }

}
