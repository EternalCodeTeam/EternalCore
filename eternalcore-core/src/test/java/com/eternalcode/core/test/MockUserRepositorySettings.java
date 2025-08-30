package com.eternalcode.core.test;

import com.eternalcode.core.user.database.UserRepositorySettings;

public class MockUserRepositorySettings implements UserRepositorySettings {

    @Override
    public boolean useBatchDatabaseFetching() {
        return false;
    }

    @Override
    public int batchDatabaseFetchSize() {
        return 100;
    }
}
