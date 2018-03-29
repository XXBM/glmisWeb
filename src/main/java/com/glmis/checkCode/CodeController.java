package com.glmis.checkCode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by dell on 2017-02-28 .
 *
 * 获取验证码API
 */

@Controller
@RequestMapping(value = "/kaptcha")
public class CodeController {
    final Logger logger = LoggerFactory.getLogger(CodeController.class);
    @RequestMapping(value = "/getKaptchaImage")
    public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("获取验证码------------------------");
        //System.out.println("------------------------");
        //System.out.println("------------------------");
        OutputStream os = response.getOutputStream();
        Map<String,Object> map = ImageCode.getImageCode(60, 20, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime",new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            logger.debug("---------------------------------------");
            return "";
        }
        return null;
    }
}
