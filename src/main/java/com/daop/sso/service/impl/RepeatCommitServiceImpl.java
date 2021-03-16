package com.daop.sso.service.impl;

import com.daop.sso.common.enums.ResultEnum;
import com.daop.sso.common.exception.CustomException;
import com.daop.sso.common.utils.RedisUtils;
import com.daop.sso.service.RepeatCommitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class RepeatCommitServiceImpl implements RepeatCommitService {
    public static final String PREFIX = "Redis-Token-";
    public static final String TOKEN_NAME = "Token-info";
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 创建 token
     *
     * @return token 值
     */
    @Override
    public String createToken() {
        log.info("创建 Token .....");
        String uid = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        token.append(PREFIX).append(uid);
        redisUtils.set(token.toString(), token.toString(), 10000L);
        if (!token.toString().isEmpty()) {
            log.info("Token 创建成功：{}", token.toString());
            return token.toString();
        }
        return null;
    }

    /**
     * 校验 token
     *
     * @param request 请求
     * @return 是否存在
     */
    @Override
    public boolean checkToken(HttpServletRequest request) {
        log.info("校验 Token ...");
        String token = request.getHeader(TOKEN_NAME);
        // 判断请求头中是否带有 token
        if (StringUtils.isBlank(token)) {
            // 判断请求参数中是否带有 token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {
                throw new CustomException(ResultEnum.ARGUMENT_TYPE_MISMATCH, ResultEnum.ARGUMENT_TYPE_MISMATCH.getMsg());
            }
        }
        if (!redisUtils.hasKey(token)) {
            throw new CustomException(ResultEnum.REPETITIVE_OPERATION, ResultEnum.REPETITIVE_OPERATION.getMsg());
        }
        if (!redisUtils.del(token)) {
            throw new CustomException(ResultEnum.REPETITIVE_OPERATION, ResultEnum.REPETITIVE_OPERATION.getMsg());
        }
        return true;
    }
}
