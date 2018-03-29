//package com.glmis.security;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by dell on 2017/2/7.
// */
//public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
//
//    public AjaxAwareAuthenticationEntryPoint(String loginFormUrl) {
//        super(loginFormUrl);
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//        String ajaxHeader = ((HttpServletRequest)request).getHeader("X-Requested-With");
//        boolean isAjax ="XMLHttpRequest".equals(ajaxHeader);
//        if(isAjax){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Ajax REquest Denied (Session Expired)");
//        }else{
//            super.commence(request,response,authException);
//        }
//    }
//}
