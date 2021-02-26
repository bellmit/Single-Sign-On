package com.daop.sso.common.schedule.quartz;

import com.daop.sso.common.schedule.JobInfo;
import com.daop.sso.common.utils.job.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @BelongsProject: xinyeiot
 * @BelongsPackage: com.xinye.iot.quartzJob
 * @Description: 不支持并发的定时任务
 * @DATE: 2020-06-02
 * @AUTHOR: Administrator
 **/
public class QuartzDisallowConcurrent extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, JobInfo jobInfo) throws Exception {
        JobInvokeUtil.invokeMethod(jobInfo);
    }
}
