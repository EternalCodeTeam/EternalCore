package com.eternalcode.annotations.scan;

import java.util.List;

public interface EternalScanResolver<RESULT> {

    List<RESULT> resolve(EternalScanRecord record);

}
