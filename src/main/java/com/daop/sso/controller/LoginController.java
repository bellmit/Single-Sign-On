package com.daop.sso.controller;

import com.daop.sso.common.utils.ResultVoUtil;
import com.daop.sso.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.controller
 * @Description: 登陆控制器
 * @DATE: 2021-02-26
 * @AUTHOR: Administrator
 **/
@RestController
@RequestMapping("/api")
public class LoginController {
    @PostMapping("/login")
    public ResultVO login() {
        return ResultVoUtil.success("接口调用成功");
    }

    @PostMapping("/loginCheck")
    public ResultVO loginCheck() {
        return ResultVoUtil.success("检查Token");
    }
}
