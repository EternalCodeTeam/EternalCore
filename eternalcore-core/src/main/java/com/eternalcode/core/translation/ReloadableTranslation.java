package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.feature.language.Language;

public interface ReloadableTranslation extends Translation {

    Language getLanguage();

}
