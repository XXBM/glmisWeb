package com.glmis.checkCode;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginInterceptor implements HandlerInterceptor {
    final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.debug(">>>>>在请求处理之前进行调用（Controller方法调用之前）");
        logger.debug("拦截器");

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        if ("post".equalsIgnoreCase(request.getMethod())) {
            String validateCode = request.getParameter("verifitcaionCode");
            logger.debug("输入的验证码是：{}" , validateCode);
            String realVaildateCode = (String) request.getSession().getAttribute("simpleCaptcha");
            logger.debug("真正显示的验证码是{}" , realVaildateCode);
            //equalsIgnoreCase比较时忽略大小写
            if (logger.isDebugEnabled()){
                logger.debug(""+validateCode.equalsIgnoreCase(realVaildateCode));
            }
            if (!validateCode.equalsIgnoreCase(realVaildateCode)) {
                logger.debug("------");
                response.sendRedirect(request.getContextPath() + "/login.html");
                System.out.println(realVaildateCode != null);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.debug(">>>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
        logger.debug("出口");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug(">>>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
