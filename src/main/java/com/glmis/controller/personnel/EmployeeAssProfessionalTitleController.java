package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssProfessionalTitle;
import com.glmis.service.personnel.EmployeeAssProfessionalTitleService;
import com.glmis.service.personnel.EmployeeService;
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
public class EmployeeAssProfessionalTitleController {

    @Autowired
    EmployeeAssProfessionalTitleService employeeAssProfessionalTitleService;
    Long emplId;
    @Autowired
    EmployeeService employeeService;

    // 根据职员id查询相应的学位信息
    @RequestMapping(value = "/displayNameByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        emplId=id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<EmployeeAssProfessionalTitle> list = this.employeeAssProfessionalTitleService.findByEmployeeId(id, pageable);
        int total = this.employeeAssProfessionalTitleService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addPostName", method = RequestMethod.POST)
    public Map<String, Object> addPostName(@RequestBody EmployeeAssProfessionalTitle employeeAssProfessionalTitle) throws Exception{
        employeeAssProfessionalTitle.setEmployee(employeeService.findOne(emplId));
        this.employeeAssProfessionalTitleService.add(employeeAssProfessionalTitle);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employmentPostName", employeeAssProfessionalTitle);
        return map;
    }

    //修改
    @RequestMapping(value = "/updatePostName", method = RequestMethod.PUT)
    public Map<String, Object> updatePostName(@RequestBody EmployeeAssProfessionalTitle employeeAssProfessionalTitle) throws Exception{
        employeeAssProfessionalTitle.setEmployee(employeeService.findOne(emplId));
        this.employeeAssProfessionalTitleService.update(employeeAssProfessionalTitle);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employmentPostName", employeeAssProfessionalTitle);
        return map;
    }

    //删除
    @RequestMapping(value = "/deletePostName", method = RequestMethod.DELETE)
    public void deletePostName(@RequestParam("id") Long id)throws Exception {
        this.employeeAssProfessionalTitleService.deleteById(id);
    }
}
