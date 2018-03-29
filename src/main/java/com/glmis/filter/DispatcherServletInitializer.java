//package com.glmis.filter;
//import com.glmis.config.WebConfig;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
///**
// * Created by xuling on 2016/11/29.
// * 注册DispatcherServlet Servlet
// *
// */
//public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
////    @Override
////    public void onStartup(ServletContext servletContext) throws ServletException {
////        super.onStartup(servletContext);
////        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);
////        encodingFilter.setInitParameter("encoding", "UTF-8");
////        encodingFilter.setInitParameter("forceEncoding", "true");
////        encodingFilter.setAsyncSupported(true);
////        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
////        ServletRegistration.Dynamic kaptchaServlet = servletContext.addServlet("kaptcha-servlet", KaptchaServlet.class);
////        kaptchaServlet.addMapping("/kaptcha/getKaptchaImage");
////    }
////WebConfig.class
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[]{};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//       return new Class<?>[0];
//      //  return new Class<?>[]{};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
////        return new String[0];
//        return new String[]{"/"};
//    }
//}
