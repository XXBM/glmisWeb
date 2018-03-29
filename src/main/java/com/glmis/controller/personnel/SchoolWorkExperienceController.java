package com.glmis.controller.personnel;

import com.glmis.domain.personnel.SchoolWorkExperience;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.SchoolWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class SchoolWorkExperienceController {
    @Autowired
    SchoolWorkExperienceService schoolWorkExperienceService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;
    // 根据职员id查询
    @RequestMapping(value = "/displayRecByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<SchoolWorkExperience> list = this.schoolWorkExperienceService.findByEmployeeId(id, pageable);
        int total = this.schoolWorkExperienceService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addEmploymentRecord", method = RequestMethod.POST)
    public Map<String, Object> addEmploymentRecord(@RequestBody SchoolWorkExperience schoolWorkExperience)throws Exception {
        schoolWorkExperience.setEmployee(employeeService.findOne(empId));
        this.schoolWorkExperienceService.add(schoolWorkExperience);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employmentRecord", schoolWorkExperience);
        return map;
    }

    //修改
    @RequestMapping(value = "/addEmploymentRecord", method = RequestMethod.PUT)
    public Map<String, Object> updateEmploymentRecord(@RequestBody SchoolWorkExperience schoolWorkExperience)throws Exception {
        schoolWorkExperience.setEmployee(employeeService.findOne(empId));
        this.schoolWorkExperienceService.update(schoolWorkExperience);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employmentRecord", schoolWorkExperience);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteEmploymentRecord", method = RequestMethod.DELETE)
    public void deleteEmploymentRecord(@RequestParam("id") Long id)throws Exception {
        this.schoolWorkExperienceService.deleteById(id);
    }
}
