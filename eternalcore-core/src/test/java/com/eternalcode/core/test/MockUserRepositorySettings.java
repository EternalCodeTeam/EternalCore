package com.eternalcode.core.test;

import com.eternalcode.core.user.database.UserRepositorySettings;
import java.time.Duration;

public class MockUserRepositorySettings implements UserRepositorySettings {

    @Override
    public boolean useBatchDatabaseFetching() {
        return false;
    }

    @Override
    public int batchDatabaseFetchSize() {
        return 100;
    }

    @Override
    public Duration cacheLoadTreshold() {
        return Duration.ofDays(7);
    }
}
