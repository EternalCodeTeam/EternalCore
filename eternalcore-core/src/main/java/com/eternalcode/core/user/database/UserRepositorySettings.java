package com.eternalcode.core.user.database;

import java.time.Duration;

public interface UserRepositorySettings {

    boolean useBatchDatabaseFetching();

    int batchDatabaseFetchSize();

    Duration cacheLoadTreshold();
}
