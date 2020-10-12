package com.eastcom.ripple.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.eastcom.ripple.common.util.Decrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hongzh
 * @date 2020/09/28
 * @description  登录拦截器，如果没有登录，则拦截下来
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    //状态码
    private static final String LOGIN_AGAIN = "403";

    /**
     * 前置：拦截方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置响应的数据格式和编码
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");

        log.info("进入拦截....");
        //获取token
        String token = request.getHeader("token");
        System.out.println(handler);
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            log.info("不是映射到方法...");
            return true;
        }
        //如果请求方式为OPTIONS，则直接放行
        if (request.getMethod().equals("OPTIONS")) return true;
        //进行token验证
        Decrypt decrypt = new Decrypt();
        boolean checkResult = decrypt.deToken(token);
        //如果为true，则验证通过
        if (checkResult){
            log.info("验证通过...");
            return true;
        }
        log.info("验证失败...");
        //返回信息给前台
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", LOGIN_AGAIN);
        resultJson.put("message","对不起，请您先登录了，再进行操作！");
        //解决响应时的跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(resultJson.toJSONString());
        return false;
    }
}