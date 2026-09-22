package com.eternalcode.core.feature.testdependent;

import com.eternalcode.core.feature.testrequired.RequiredService;
import com.eternalcode.core.injector.annotations.Inject;

public class DependentComponent {

    @Inject
    public DependentComponent(RequiredService requiredService) {
    }

}
