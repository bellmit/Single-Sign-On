package com.daop.sso.controller;

import com.daop.sso.common.schedule.JobInfo;
import com.daop.sso.common.utils.ResultVoUtil;
import com.daop.sso.common.utils.job.QuartzUtil;
import com.daop.sso.vo.ResultVO;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.controller
 * @Description:
 * @DATE: 2021-03-01
 * @AUTHOR: Administrator
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class JobController {
    private static Map<Long, JobInfo> jobInfoMap = new HashMap<>(8);

    static {
        for (long i = 1; i < 4; i++) {
            JobInfo jobInfo = new JobInfo();
            jobInfo.setJobId(i);
            jobInfo.setJobCron("*/5 * * * * ?");
            jobInfo.setJobGroup("TestJobGroup");
            jobInfo.setJobName("QuartzJob" + i);
            jobInfo.setJobClass("jobs");
            jobInfo.setJobMethod("testMethod");
            jobInfo.setJobStatus(1);
            jobInfo.setJobConcurrent(0);
            jobInfo.setJobMisfirePolicy(3);
            jobInfo.setJobDescription("This is QuartzJob " + i);
            jobInfoMap.put(i, jobInfo);
        }
    }

    @PostConstruct
    public void initJob() throws SchedulerException {
        log.info("Init Jobs.....");
        List<JobInfo> jobInfos = new ArrayList<>(jobInfoMap.values());
        QuartzUtil.initScheduleJobs(jobInfos);
    }

    @GetMapping("/jobs")
    public ResultVO jobs() {
        return ResultVoUtil.success(jobInfoMap);
    }

    @GetMapping("/pause/{jobId}")
    public ResultVO pause(@PathVariable("jobId") long jobId) throws SchedulerException {
        JobInfo jobInfo = jobInfoMap.get(jobId);
        QuartzUtil.pauseJob(jobInfo);
        jobInfo.setJobStatus(1);
        jobInfoMap.put(jobId, jobInfo);
        return ResultVoUtil.success(jobInfoMap);
    }
    @GetMapping("/start/{jobId}")
    public ResultVO start(@PathVariable("jobId") long jobId) throws SchedulerException {
        JobInfo jobInfo = jobInfoMap.get(jobId);
        QuartzUtil.startJob(jobInfo);
        jobInfo.setJobStatus(0);
        jobInfoMap.put(jobId, jobInfo);
        return ResultVoUtil.success(jobInfoMap);
    }

}
