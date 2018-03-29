package com.glmis.controller.personnel;

import com.glmis.domain.personnel.VisitingAcademic;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.VisitingAcademicService;
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
public class VisitingAcademicController {
    @Autowired
    VisitingAcademicService visitingAcademicService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    // 根据职员id查询
    @RequestMapping(value = "/displayStuByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<VisitingAcademic> list = this.visitingAcademicService.findByEmployeeId(id, pageable);
        int total = this.visitingAcademicService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addVisitingStudy", method = RequestMethod.POST)
    public Map<String, Object> addVisitingStudy(@RequestBody VisitingAcademic visitingAcademic) throws Exception{
        visitingAcademic.setEmployee(employeeService.findOne(empId));
        this.visitingAcademicService.add(visitingAcademic);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitingStudy", visitingAcademic);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateVisitingStudy", method = RequestMethod.PUT)
    public Map<String, Object> updateVisitingStudy(@RequestBody VisitingAcademic visitingAcademic)throws Exception {
        visitingAcademic.setEmployee(employeeService.findOne(empId));
        this.visitingAcademicService.update(visitingAcademic);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitingStudy", visitingAcademic);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteVisitingStudy", method = RequestMethod.DELETE)
    public void deleteVisitingStudy(@RequestParam("id") Long id)throws Exception {
        this.visitingAcademicService.deleteById(id);
    }
}

