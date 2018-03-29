package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssAcademicDegree;
import com.glmis.service.personnel.EmployeeAssAcademicDegreeService;
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
public class EmployeeAssAcademicDegreeController {

    @Autowired
    EmployeeAssAcademicDegreeService employeeAssAcademicDegreeService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    // 根据职员id查询相应的学位信息
    @RequestMapping(value = "/displayDegByEmp", method = RequestMethod.GET)
    public Map<String, java.lang.Object> findByEmployeeId(@RequestParam("id") Long id,
                                                          @RequestParam(value = "page") Integer page,
                                                          @RequestParam(value = "rows") Integer size) throws Exception{
        empId = id;
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<EmployeeAssAcademicDegree> list = this.employeeAssAcademicDegreeService.findByEmployeeId(id, pageable);
        int total = this.employeeAssAcademicDegreeService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //实现分页
    @RequestMapping(value = "/displayAllDegree", method = RequestMethod.GET)
    public Map<String, java.lang.Object> findAllDegree(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size)throws Exception {
        Page<EmployeeAssAcademicDegree> list = this.employeeAssAcademicDegreeService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        int total = this.employeeAssAcademicDegreeService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    ////实现分页测试json数据
    @RequestMapping(value = "/displayAllDegreeJson", method = RequestMethod.GET)
    public Map<String, java.lang.Object> findAllDepartments() throws Exception{
        List<EmployeeAssAcademicDegree> list = this.employeeAssAcademicDegreeService.findAllT();
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        int total = this.employeeAssAcademicDegreeService.findAllT().size();
        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    //添加一个新的学位  完成 增
    @RequestMapping(value = "/addDegree", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDegree(@RequestBody EmployeeAssAcademicDegree employeeAssAcademicDegree)throws Exception {
        employeeAssAcademicDegree.setEmployee(employeeService.findOne(empId));
        this.employeeAssAcademicDegreeService.add(employeeAssAcademicDegree);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("degree", employeeAssAcademicDegree);
        return map;
    }

    //修改学位信息    完成 改
    @RequestMapping(value = "/updateDegree", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateDegree(@RequestBody EmployeeAssAcademicDegree employeeAssAcademicDegree){
        employeeAssAcademicDegree.setEmployee(employeeService.findOne(empId));
        this.employeeAssAcademicDegreeService.update(employeeAssAcademicDegree);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("degree", employeeAssAcademicDegree);
        return map;
    }
    //删除一个学位   完成 删
    @RequestMapping(value = "/deleteDegree", method = RequestMethod.DELETE)
    public void deleteDegree(@RequestParam("ids") String ids) throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.employeeAssAcademicDegreeService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
