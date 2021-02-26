package com.daop.sso.common.schedule;

import lombok.Data;

import java.io.Serializable;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.common.schedule
 * @Description: 定时任务信息
 * @DATE: 2021-02-26
 * @AUTHOR: Administrator
 **/
@Data
public class JobInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 定时任务ID
     */
    private Long jobId;

    /**
     * 定时任务表达式
     */
    private String jobCron;

    /**
     * 定时任务分组
     */
    private String jobGroup;

    /**
     * 定时任务名称
     */
    private String jobName;

    /**
     * 定时任务相关类名
     */
    private String jobClass;

    /**
     * 定时任务相关方法
     */
    private String jobMethod;

    /**
     * 定时任务定时任务状态（0正常 1停止）
     */
    private Integer jobStatus;

    /**
     * 定时任务是否并发执行（0并发 1非并发）
     */
    private Integer jobConcurrent;

    /**
     * 定时任务执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private Integer jobMisfirePolicy;

    /**
     * 定时任务描述
     */
    private String jobDescription;

}
