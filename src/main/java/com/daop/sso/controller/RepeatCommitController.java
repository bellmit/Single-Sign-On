package com.daop.sso.controller;

import com.daop.sso.common.annotation.AutoIdempotent;
import com.daop.sso.common.utils.ResultVoUtil;
import com.daop.sso.service.impl.RepeatCommitServiceImpl;
import com.daop.sso.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @AUTHOR: Administrator
 * @Description: 重复提交测试 接口幂等测试
 * @DATE: 2021-03-16
 */
@RestController
@Slf4j
public class RepeatCommitController {
    @Autowired
    private RepeatCommitServiceImpl repeatCommitService;
    @Autowired
    private Info info;


    @GetMapping("/token")
    public ResultVO token() {
        log.info("请求进来啦");
//        String token = repeatCommitService.createToken();
        return ResultVoUtil.success(info.getTest());
    }

    @PostMapping("/token")
    public ResultVO getToken() {
        log.info("请求进来啦");
        String token = repeatCommitService.createToken();
        return ResultVoUtil.success(token);
    }

    @AutoIdempotent
    @PostMapping("/idempotence")
    public ResultVO idempotence() {
        log.info("测试幂等请求进来啦");
        return ResultVoUtil.success();
    }
}
