package com.daop.sso.service;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
public interface RepeatCommitService {
    /**
     * 创建令牌
     *
     * @return 返回令牌
     */
    String createToken();

    /**
     * 校验令牌
     *
     * @param request 请求
     * @return 是否存在
     */
    boolean checkToken(HttpServletRequest request);
}
