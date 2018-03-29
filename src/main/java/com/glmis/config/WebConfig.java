package com.glmis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xuling on 2016/11/5.
 * 配置静态资源和视图解析器
 * @Import 应用其他spring配置
// */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.glmis.*")
//@Import(WebSecurityConfig.class)
public class WebConfig  extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**通过SpringMVC中提供自定义拦截器的扩展，通过自定义完成对超时状态的判断：*/
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new HandlerInterceptorAdapter() {
//
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                if(Utils.getCurrentUserName(request)==null){//判断session中是否还有user信息
//                    System.out.println("123" + Utils.getCurrentUserName(request));
//                    if (request.getHeader("x-requested-with") != null&&
//                            request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){//如果是ajax请求，响应头会有，x-requested-with
//                        response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
//                        System.out.println(request.getHeader("sessionstatus"));
//                        System.out.println(request.getAttribute("javax.servlet.forward.request_uri"));
//                        return false;
//                    }
//                }
////                response.sendRedirect("/login?next=".concat(request.getRequestURI()));//登陆成功后返回当前页面
//                return true;
//            }
////
////            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////                System.out.println("===========HandlerInterceptor1 postHandle");
////            }
////
////            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////                System.out.println("===========HandlerInterceptor1 afterCompletion");
////            }
//        }).addPathPatterns("/**").excludePathPatterns("/login");//登录页面本身（包括POST请求）不能应用Interceptor来拦截，否则会陷入无限循环中：
//        super.addInterceptors(registry);
//    }
}