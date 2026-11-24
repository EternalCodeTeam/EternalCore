package com.eternalcode.core.feature.kit;

import com.eternalcode.core.publish.PublishEvent;
import java.util.List;

public record KitsLoadedEvent(List<Kit> kits) implements PublishEvent {
}
