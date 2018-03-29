package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EducationQualification;
import com.glmis.service.personnel.EducationQualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class EducationQualificationController {
    @Autowired
    EducationQualificationService educationNameService;

    /**
     * 获取到所有的学历名称
     */
    @RequestMapping(value = "/findAllEducationName", method = RequestMethod.GET)
    public List<EducationQualification> findAllEducationName()throws Exception {
        List<EducationQualification> educationNames = educationNameService.findAllT();
        return educationNames;
    }
    //实现分页
    //GET：请求指定的页面信息，并返回实体主体
    @RequestMapping(value = "/displayAllEduaction", method = RequestMethod.GET)
    public Map<String, Object> findAllEducations(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size)throws Exception {
        Page<EducationQualification> list = this.educationNameService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.educationNameService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
    //增加学历
    //POST：向指定资源提交数据进行处理请求，数据包含在请求体中。（即“增加”操作）
    @RequestMapping(value = "/addEducationQualification",method = RequestMethod.POST)
    public Map<String,Object> addEducation(@RequestBody EducationQualification educationQualification)throws Exception{
        this.educationNameService.add(educationQualification);
        Map<String,Object> map=new HashMap<String ,Object>();
        map.put("educationQualification",educationQualification);
        return map;
    }
    //修改学历
    //PUT：从客户端向服务器传送的数据取代指定的文档内容（即“修改”操作）
    @RequestMapping(value = "/updateEduactionQualification",method =RequestMethod.PUT)
    public Map<String,Object> updateEducation(@RequestBody EducationQualification educationQualification)throws Exception{
        this.educationNameService.update(educationQualification);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("educationQualification",educationQualification);
        return map;
    }
    //删除学历
    /*RequestMethod共计15种请求方式
    * DELETE：请求服务器删除指定页面*/
    @RequestMapping(value = "/deleteEducationQualification",method = RequestMethod.DELETE)
    public void deleteEducation(@RequestParam("id") Long id) throws Exception {
        this.educationNameService.deleteById(id);
    }
}
