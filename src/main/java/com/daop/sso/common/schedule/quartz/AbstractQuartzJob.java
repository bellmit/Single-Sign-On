package com.daop.sso.common.schedule.quartz;

import com.daop.sso.common.schedule.JobInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @BelongsProject: xinyeiot
 * @BelongsPackage: com.xinye.iot.quartzJob
 * @Description: 抽象quartz类调用
 * @DATE: 2020-06-02
 * @AUTHOR: Administrator
 **/
public abstract class AbstractQuartzJob implements Job {
    Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(QuartzConstants.TASK_PROPERTIES), jobInfo);
        try {
            // beforeExecute
            before(context, jobInfo);

            // doExecute
            doExecute(context, jobInfo);

            // afterExecute
            after(context, jobInfo);
        } catch (Exception e) {
            log.error("任务执行异常 ::", e);

            // executeFail
            executeFail(context, jobInfo, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文
     * @param jobInfo 定时任务信息类
     */
    protected void before(JobExecutionContext context, JobInfo jobInfo) {
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文
     * @param jobInfo 定时任务信息类
     */
    protected void after(JobExecutionContext context, JobInfo jobInfo) {

    }

    /**
     * 执行失败
     *
     * @param context 工作执行上下文
     * @param jobInfo 定时任务信息类
     */
    protected void executeFail(JobExecutionContext context, JobInfo jobInfo, Exception e) {

    }

    /**
     * 执行方法 子类重载
     *
     * @param context 上下文对象
     * @param jobInfo 定时任务信息类
     * @throws Exception 执行过程中异常
     */
    protected abstract void doExecute(JobExecutionContext context, JobInfo jobInfo) throws Exception;
}
