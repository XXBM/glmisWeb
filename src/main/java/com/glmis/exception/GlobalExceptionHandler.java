package com.glmis.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by xuling on 2016/10/29.
 * 统一异常处理
 * 该类会处理所有在执行标有@RequestMapping注解的方法时，发生的异常
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {
    final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //404 403 500 使用的不同的错误处理页面
//
//    @ExceptionHandler(value = FileUploadBase.FileSizeLimitExceededException.class)
//    public ModelAndView defaultErrorHandle1(HttpServletRequest request,Exception e)throws Exception{
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception",e);
//        modelAndView.addObject("url",request.getRequestURL());
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandle(HttpServletRequest request, Exception e) throws Exception {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", e);
//        modelAndView.addObject("url", request.getRequestURL());
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }

    //异常处理器，次注解的作用是当出现其定义的异常时，进行处理方法，
    //关于JSON的
    @ExceptionHandler(value = Exception.class)
    //@ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, Exception e) {
        logger.debug("异常处理进入");
        ErrorInfo<String> errorInfo = new ErrorInfo<>();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setCode(ErrorInfo.ERROR);
        errorInfo.setData("哎呀！出错啦！");
        errorInfo.setUrl(request.getRequestURI().toString());
        logger.debug("异常处理出口"+request.getRequestURI().toString()+e.getMessage()+e.toString());
        return errorInfo;
    }

//    //异常处理器，次注解的作用是当出现其定义的异常时，进行处理方法，
//    //关于JSON的
//    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
//    //@ResponseBody
//    public ErrorInfo<String> jsonSQL(HttpServletRequest request, Exception e) {
//        System.out.println("异常处理进入");
//        ErrorInfo<String> errorInfo = new ErrorInfo<>();
//        errorInfo.setMessage(e.getMessage());
//        errorInfo.setCode(ErrorInfo.ERROR);
//        errorInfo.setData("哎呀！出错啦！");
//        errorInfo.setUrl(request.getRequestURI().toString());
//        System.out.println("异常处理出口"+request.getRequestURI().toString()+e.getMessage()+e.toString());
//        return errorInfo;
//    }

}
