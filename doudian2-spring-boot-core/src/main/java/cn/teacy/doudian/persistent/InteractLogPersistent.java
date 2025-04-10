package cn.teacy.doudian.persistent;

import cn.teacy.common.doudian.domain.InteractLog;
import cn.teacy.common.interfaces.Persistent;
import lombok.extern.slf4j.Slf4j;

public interface InteractLogPersistent extends Persistent<InteractLog> {

    @Slf4j
    class JUST_LOG implements InteractLogPersistent {
        @Override
        public void save(InteractLog spiAccessLog) {
            log.info("{}", spiAccessLog);
        }
    }
}
