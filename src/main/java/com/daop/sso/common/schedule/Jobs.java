package com.daop.sso.common.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.common.schedule
 * @Description:
 * @DATE: 2021-03-01
 * @AUTHOR: Administrator
 **/
@Component("jobs")
public class Jobs {
    Logger log = LoggerFactory.getLogger(Jobs.class);

    public void testMethod() {
        log.info("每隔5秒执行一次, 当前线程名称{}", Thread.currentThread().getName());
    }
}
