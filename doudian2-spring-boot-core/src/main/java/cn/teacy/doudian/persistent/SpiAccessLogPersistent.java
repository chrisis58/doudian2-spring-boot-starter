package cn.teacy.doudian.persistent;

import cn.teacy.common.doudian.domain.SpiAccessLog;
import cn.teacy.common.interfaces.Persistent;
import lombok.extern.slf4j.Slf4j;

public interface SpiAccessLogPersistent extends Persistent<SpiAccessLog> {

    @Slf4j
    class JUST_LOG implements SpiAccessLogPersistent {
        @Override
        public void save(SpiAccessLog spiAccessLog) {
            log.info("{}", spiAccessLog);
        }
    }

}
