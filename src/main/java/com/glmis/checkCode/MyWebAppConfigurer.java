package com.glmis.checkCode;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/login")
                .addPathPatterns("/kaptcha/**")
                .addPathPatterns("/index");
        super.addInterceptors(registry);
    }
}
