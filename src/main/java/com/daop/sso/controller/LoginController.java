package com.daop.sso.controller;

import com.daop.sso.common.utils.ResultVoUtil;
import com.daop.sso.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.controller
 * @Description: 登陆控制器
 * @DATE: 2021-02-26
 * @AUTHOR: Administrator
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {
    public static final Map<String, HttpSession> SESSION_MAP = new HashMap<>(6);
    public static final Map<String, String> TOKEN_MAP = new HashMap<>(6);


    @PostMapping("/login")

    public ResultVO login() {
        return ResultVoUtil.success("接口调用成功");
    }

    @GetMapping("/register")
    public ResultVO register(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String reqToken = request.getHeader("token");
        if (TOKEN_MAP.containsKey(reqToken)) {
            String sessionKey = TOKEN_MAP.get(reqToken);
            if (SESSION_MAP.containsKey(sessionKey)) {
                //TODO 根据Token获取Session
            }
        }


        SESSION_MAP.put(session.getId(), session);
        TOKEN_MAP.put(reqToken, session.getId());


        response.addCookie(new Cookie("token", reqToken));

        return ResultVoUtil.success("暂无权限");

    }

    @PostMapping("/loginCheck")
    public ResultVO loginCheck() {
        return ResultVoUtil.success("检查Token");
    }
}
