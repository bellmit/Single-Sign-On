package com.daop.sso.common.aop.idempotent;

import com.daop.sso.common.annotation.AutoIdempotent;
import com.daop.sso.common.enums.ResultEnum;
import com.daop.sso.common.exception.CustomException;
import com.daop.sso.service.impl.RepeatCommitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @Description: 幂等拦截器
 */
@Component
public class IdempotentInterceptor implements HandlerInterceptor {
    @Autowired
    private RepeatCommitServiceImpl repeatCommitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AutoIdempotent methodAnnotation = method.getAnnotation(AutoIdempotent.class);
        if (methodAnnotation != null) {
            try {
                return repeatCommitService.checkToken(request);
            } catch (Exception e) {
                throw new CustomException(ResultEnum.REPETITIVE_OPERATION, ResultEnum.REPETITIVE_OPERATION.getMsg());
//                ResultVO resultVO = ResultVoUtil.error(101, e.getMessage());
//                writeReturnJson(response, JSON.toJSONString(resultVO));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

   /**
     * 返回的json值
     *
     * @param response
     * @param json
     * @throws Exception
     *//*
    private void writeReturnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }*/
}

