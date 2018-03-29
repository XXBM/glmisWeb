//package com.glmis.filter;
//
//import com.google.code.kaptcha.Constants;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by xuling on 2016/11/24.
// * 将这个判断加在UsernamePasswordAuthenticationFilter之前
// * 因为spring security不带验证码，所以就在登录时，在一个filter中判断验证码
// * 如果正确，则接着往下走，否则的话就重定向到登录界面
// */
//
//public class ValidateCodeUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//    private String defaultFilterProcessesPath;
//
//    public ValidateCodeUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
//        super(defaultFilterProcessesUrl);
//        this.defaultFilterProcessesPath = defaultFilterProcessesUrl;
//        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
//    }
//
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessesPath.equalsIgnoreCase(request.getServletPath())) {
//            String validateCode = request.getParameter("verifitcaionCode");
//            String realVailDateCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
////            equalsIgnoreCase比较时忽略大小写
//            if (realVailDateCode != null && !realVailDateCode.equalsIgnoreCase(validateCode)) {
//                unsuccessfulAuthentication(request, response, new InsufficientAuthenticationException("输入的验证码不正确"));
//                return;
//            }
//
//        }
//
//        chain.doFilter(req, res);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        return null;
//    }
//
//}
