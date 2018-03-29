package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssPractisingCertification;
import com.glmis.service.personnel.EmployeeAssPractisingCertificationService;
import com.glmis.service.personnel.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */
@RestController
public class EmployeeAssPractisingCertificationController {
    final Logger logger = LoggerFactory.getLogger(EmployeeAssPractisingCertificationController.class);
    @Autowired
    EmployeeAssPractisingCertificationService employeeAssPractisingCertificationService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    /**
     * 定义一个byte[]类型的变量存放照片的二进制数据
     */

    private static byte[] imgByte;
    /**
     * 定义一个int类型的变量存放职业资格的id,用于判断上传图片的状态是编辑还是添加
     * 添加状态设置id为0，编辑状态有id
     */
    private static Integer employeeId = 0;

    /**
     * 根据职员id查询相应的学位信息
     */
    @RequestMapping(value = "/displayQuaByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size) throws Exception{
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<EmployeeAssPractisingCertification> list = this.employeeAssPractisingCertificationService.findByEmployeeId(id, pageable);
        int total = this.employeeAssPractisingCertificationService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
    /**
     * 获取到从前台上传的照片
     */
    @RequestMapping(value = "/uploadQuaImg", method = RequestMethod.POST)
    public void uploadImg(@RequestParam("quaId") Integer quaId, HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//      根据参数名获取到文件
        MultipartFile multipartFile = multipartRequest.getFile("quaImgName");
        imgByte = multipartFile.getBytes();//将文件转化为二进制的格式,将值保存在byte类型的变量里。
        logger.debug("-------------------------------------------{}",quaId);
        empId = quaId;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addQualification", method = RequestMethod.POST)
    public Map<String, Object> addQualification(@RequestBody EmployeeAssPractisingCertification employeeAssPractisingCertification)throws Exception {
        if (employeeId == 0) {
            logger.debug("得到照片为 {}" , employeeAssPractisingCertification.getPhoto());
            employeeAssPractisingCertification.setPhoto(imgByte);
            logger.debug("添加职员图片{}" , imgByte);
        }
        employeeAssPractisingCertification.setEmployee(employeeService.findOne(empId));
        this.employeeAssPractisingCertificationService.add(employeeAssPractisingCertification);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qualification", employeeAssPractisingCertification);
        return map;
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/updateQualification", method = RequestMethod.PUT)
    public Map<String, Object> updateQualification(@RequestBody EmployeeAssPractisingCertification employeeAssPractisingCertification)throws Exception {
//        if ((employeeAssPractisingCertification.getId() - employeeId) == 0) {
//            employeeAssPractisingCertification.setPhoto(imgByte);
//            System.out.println("她们是同一个人");
//        }
        employeeAssPractisingCertification.setPhoto(imgByte);
        employeeAssPractisingCertification.setEmployee(employeeService.findOne(empId));
        this.employeeAssPractisingCertificationService.update(employeeAssPractisingCertification);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qualification", employeeAssPractisingCertification);
        return map;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/deleteQualification", method = RequestMethod.DELETE)
    public void deleteQualification(@RequestParam("id") Long id)throws Exception {
        this.employeeAssPractisingCertificationService.deleteById(id);
    }

    /**
     * 从数据库回显图片到前台页面
     */
    @RequestMapping(value = "/toLookQuaImge", method = RequestMethod.GET)
    public void displayImg(@RequestParam("imgId") Long imgId, HttpServletResponse response) throws Exception {
        EmployeeAssPractisingCertification employeeAssPractisingCertification = employeeAssPractisingCertificationService.findOne(imgId);//获取到员工
        byte[] imgByte = employeeAssPractisingCertification.getPhoto();//获取到照片
        //如果当前图片不为空
        if (imgByte != null) {
            response.setContentType("img/jpeg");
            response.setCharacterEncoding("utf-8");
            OutputStream out = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(imgByte);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, len);
            }
        }
    }
}
