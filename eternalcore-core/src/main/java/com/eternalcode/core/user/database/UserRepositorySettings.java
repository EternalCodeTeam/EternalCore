package com.eternalcode.core.user.database;

public interface UserRepositorySettings {

    boolean useBatchDatabaseFetching();

    int batchDatabaseFetchSize();
}
