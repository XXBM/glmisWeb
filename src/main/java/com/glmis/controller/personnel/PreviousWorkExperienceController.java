package com.glmis.controller.personnel;

import com.glmis.domain.personnel.PreviousWorkExperience;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.PreviousWorkExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class PreviousWorkExperienceController {
    final Logger logger = LoggerFactory.getLogger(PreviousWorkExperienceController.class);
    @Autowired
    PreviousWorkExperienceService previousWorkExperienceService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;



    // 根据职员id查询
    @RequestMapping(value = "/displayBeExByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {

        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<PreviousWorkExperience> list = this.previousWorkExperienceService.findByEmployeeId(id, pageable);
        int total = this.previousWorkExperienceService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addBeforeExperiences", method = RequestMethod.POST)
    public Map<String, Object> addBeforeExperiences(HttpServletRequest request, @RequestBody PreviousWorkExperience previousWorkExperience)throws Exception {
//        List<Employee> employees = employeeService.findByEmployeeId(employeeId);
//        previousWorkExperience.setEmployee((Employee)employees);
        previousWorkExperience.setEmployee(employeeService.findOne(empId));
        this.previousWorkExperienceService.add(previousWorkExperience);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beforeExperiences", previousWorkExperience);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateBeforeExperiences", method = RequestMethod.PUT)
    public Map<String, Object> updateBeforeExperiences(HttpServletRequest request, @RequestBody PreviousWorkExperience previousWorkExperience)throws Exception {

//        List<Employee> employees = employeeService.findByEmployeeId(employeeId);
//        previousWorkExperience.setEmployee((Employee)employees);
        previousWorkExperience.setEmployee(employeeService.findOne(empId));
        this.previousWorkExperienceService.update(previousWorkExperience);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beforeExperiences", previousWorkExperience);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteBeforeExperiences", method = RequestMethod.DELETE)
    public void deleteBeforeExperiences(@RequestParam("ids") String  ids)throws Exception {
        logger.debug("deleteBeforeExperiences");
//        this.previousWorkExperienceService.deleteById(id);
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.previousWorkExperienceService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
