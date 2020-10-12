package com.eastcom.ripple.filter;

import com.alibaba.fastjson.JSONObject;
import com.eastcom.ripple.common.util.Decrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@WebFilter(filterName = "LoginFilter",urlPatterns = {"sys/*"})
@Slf4j
public class LoginFilter implements Filter {

    //状态码
    private static final String LOGIN_AGAIN = "403";

    //定义一个返回结果的json对象
    private JSONObject resultJson = new JSONObject();

    //免登录就可访问的路径(比如:注册,登录,注册页面上的一些获取数据等)
    String[] includeUrls = new String[]{"/sys/login/sysuser","/sys/account/findByAccountName","/sys/employee/updateReserveTimes"};

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info(request.getHeader("token"));

        //设置响应的数据格式和编码
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");

        //当前请求的url
        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.info("filter url:"+uri+"::"+method);

        //判断url是否需要过滤
        boolean needFilter = isNeedFilter(uri);
        if (!needFilter || method.equals("OPTIONS")) { //不需要过滤直接传给下一个过滤器，请求方法为OPTIONS，不用过滤
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // token正确并且有效,则是登录状态
            if(request.getHeader("token") != null){
                //进入过滤
                log.info(".....进入过滤....");
                //进行token验证
                Decrypt decrypt = new Decrypt();
                boolean checkResult = decrypt.deToken(request.getHeader("token"));
                if (!checkResult){
                    log.warn("token无效。。。");
                    resultJson.put("code", LOGIN_AGAIN);
                    resultJson.put("message","对不起，请您先登录了，再进行操作！");
                    //解决响应时的跨域问题
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Cache-Control","no-cache");
                    response.getWriter().write(resultJson.toJSONString());
                    //不执行后面的代码
                    return;
                }
            }else{
                log.warn("没有token。。。");
                resultJson.put("code", LOGIN_AGAIN);
                resultJson.put("message","对不起，请您先登录了，再进行操作！");
                //解决响应时的跨域问题
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Cache-Control","no-cache");
                response.getWriter().write(resultJson.toJSONString());
                //不执行后面的代码
                return;
            }
            log.info("执行下一个。。。");
            filterChain.doFilter(request, response);
        }
    }

    /**
     * @Author: wdd
     * @Description: 是否需要过滤
     * @Date: 2019-02-21 13:20:54
     * @param uri
     */
    private boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
//            if(includeUrl.equals(uri) || includeUrl.equals(uri.substring(0,30)) || includeUrl.equals(uri.substring(0,32))) {
//
//                return false;
//            }
            if( uri.contains(includeUrl)){
                return false;
            }
        }

        return true;
    }


    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化Filter");
    }

    public void destroy() {

    }
}
