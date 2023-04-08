package com.eternalcode.core;

import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.ViewerProvider;

public interface EternalCoreApi {

    UserManager getUserManager();

    ViewerProvider getViewerProvider();

}
