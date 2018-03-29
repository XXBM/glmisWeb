package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssEducationQualification;
import com.glmis.service.personnel.EducationQualificationService;
import com.glmis.service.personnel.EmployeeAssEducationQualificationService;
import com.glmis.service.personnel.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class EmployeeAssEducationQualificationController {
    @Autowired
    EmployeeAssEducationQualificationService employeeAssEducationQualificationService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EducationQualificationService educationNameService;

    long empId = 0;

    // 根据职员id查询相应的学历信息并实现分页
    @RequestMapping(value = "/displayEduByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<EmployeeAssEducationQualification> list = this.employeeAssEducationQualificationService.findByEmployeeId(id, pageable);
        int total = this.employeeAssEducationQualificationService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

//    //实现分页
//    @RequestMapping(value = "/displayAllEducation",method = RequestMethod.GET)
//    public Map<String,Object> findAllEducation(@RequestParam(value = "page")Integer page, @RequestParam(value = "rows")Integer size){
//        Page<Education> list= this.educationService.findAllT(new PageRequest(page-1,size));
//        Map<String,Object> map = new HashMap<String,Object>();
//        int total = this.educationService.findAllT().size();
//        map.put("total",total);
//        map.put("rows",list.getContent());
//        return map ;
//    }

    //实现分页测试json数据
    @RequestMapping(value = "/displayAllEducationJson", method = RequestMethod.GET)
    public Map<String, Object> findAllEducation() throws Exception{
        List<EmployeeAssEducationQualification> list = this.employeeAssEducationQualificationService.findAllT();
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.employeeAssEducationQualificationService.findAllT().size();
        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    //添加
    @RequestMapping(value = "/addEducation", method = RequestMethod.POST)
    public Map<String, Object> addEducation(@RequestBody EmployeeAssEducationQualification employeeAssEducationQualification)throws Exception {
        employeeAssEducationQualification.setEmployee(employeeService.findOne(empId));
        this.employeeAssEducationQualificationService.add(employeeAssEducationQualification);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("education", employeeAssEducationQualification);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateEducation", method = RequestMethod.PUT)
    public Map<String, Object> updateEducation(@RequestBody EmployeeAssEducationQualification employeeAssEducationQualification)throws Exception {
        employeeAssEducationQualification.setEmployee(employeeService.findOne(empId));
        this.employeeAssEducationQualificationService.update(employeeAssEducationQualification);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("education", employeeAssEducationQualification);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteEducation", method = RequestMethod.DELETE)
    public void deleteEducation(@RequestParam("ids") String ids) throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.employeeAssEducationQualificationService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
