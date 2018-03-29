package com.glmis.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextImpl;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;


/**
 * Created by ibs on 16/12/3.
 */
public class Utils {

    public static SecurityContextImpl securityContext;

    /**获取当前UserName*/
    public static String getCurrentUserName(HttpServletRequest request){
        securityContext = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return securityContext.getAuthentication().getName();
    }

    /**得到一个Pageable对象*/
    public static Pageable getPageable(HttpServletRequest request,
                                       Integer page,
                                       Integer size,
                                       Sort sort){
        if(sort!=null){
            sort = new Sort(Sort.Direction.fromString(request.getParameter("order")),request.getParameter("description"));
        }else{
            sort = new Sort(Sort.Direction.DESC,"id");
        }
        Pageable pageable = new PageRequest(page-1,size,sort);
        return pageable;
    }

    public static String makeMD5(String p) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
            md.update(p.getBytes());
            String password = new BigInteger(1, md.digest()).toString(16);
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * by tony 2013-10-17
     * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
     * @param response HttpServletResponse	写入response
     * @param request HttpServletRequest	写入request
     * @param returnName 返回的文件名
     */
    public static void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, HttpServletRequest request, String returnName) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        //returnName = response.encodeURL(new String(returnName.getBytes(),"iso8859-1"));			//保存的文件名,必须和页面编码一致,否则乱码

        returnName = encodeDownloadFilename(returnName,request.getHeader("user-agent"));

        response.addHeader("Content-Disposition",   "attachment;filename=" + returnName);
        response.setContentLength(byteArrayOutputStream.size());

        ServletOutputStream outputstream = response.getOutputStream();	//取得输出流
        byteArrayOutputStream.writeTo(outputstream);					//写到输出流
        byteArrayOutputStream.close();									//关闭
        outputstream.flush();											//刷数据
    }


    /**
     * 下载文件时，针对不同浏览器，进行附件名的编码
     * @param filename 下载文件名
     * @param agent 客户端浏览器
     * @return 编码后的下载附件名
     * @throws IOException
     */
    public static String encodeDownloadFilename(String filename, String agent) throws IOException {
        if(agent.contains("Firefox")){ // 火狐浏览器Firefox
            filename = "=?UTF-8?B?"+new BASE64Encoder().encode(filename.getBytes("utf-8"))+"?=";
        }else{ // IE及其他浏览器URLEncoder
            filename = URLEncoder.encode(filename,"utf-8");
        }
        return filename;
    }
}
